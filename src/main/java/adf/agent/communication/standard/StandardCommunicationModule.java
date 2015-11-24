package adf.agent.communication.standard;

import adf.agent.Agent;
import adf.agent.communication.MessageManager;
import adf.component.communication.CommunicationMessage;
import adf.component.communication.CommunicationModule;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.messages.Command;
import rescuecore2.messages.Message;
import rescuecore2.standard.messages.AKSay;
import rescuecore2.standard.messages.AKSpeak;
import rescuecore2.worldmodel.EntityID;

import java.util.Collection;

public class StandardCommunicationModule extends CommunicationModule
{
    final private int ESCAPE_CHAR = 0xFE;
    final private int SIZE_ID = 5;
    final private int SIZE_TTL = 3;

    private int channel = 1;

    @Override
    public void receive(Agent agent, MessageManager messageManager)
    {
        Collection<Command> heardList = agent.agentInfo.getHeard();

        for (Command heard : heardList)
        {
            if (heard instanceof AKSpeak)
            {
                EntityID senderID = heard.getAgentID();

                if (agent.getID() == senderID)
                { continue; }

                AKSpeak received = (AKSpeak)heard;
                byte[] receivedData = received.getContent();
                boolean isRadio =  (received.getChannel() != 0);

                if (receivedData.length <= 0)
                { continue; }

                if (isRadio)
                {
                    addReceivedMessage(messageManager, isRadio, senderID, receivedData);
                }
                else
                {
                    String voiceString = new String(receivedData);
                    if ("Help".equalsIgnoreCase(voiceString) || "Ouch".equalsIgnoreCase(voiceString))
                    {
                        messageManager.addHeardAgentHelpCount();
                        continue;
                    }

                    BitOutputStream messageTemp = new BitOutputStream();
                    for (int i = 0; i < receivedData.length; i++)
                    {
                        if (receivedData[i] == ESCAPE_CHAR)
                        {
                            if ((i +1) >= receivedData.length)
                            {
                                addReceivedMessage(messageManager, isRadio, senderID, messageTemp.toByteArray());
                                break;
                            }
                            else if (receivedData[i +1] != ESCAPE_CHAR)
                            {
                                addReceivedMessage(messageManager, isRadio, senderID, messageTemp.toByteArray());
                                messageTemp.reset();
                                continue;
                            }

                            i += 1;
                        }
                        messageTemp.write(receivedData[i]);
                    }
                }
            }
        }
    }


    final Class<?>[] standardMessageArgTypes = {boolean.class, int.class, int.class, byte[].class};

    private void addReceivedMessage(MessageManager messageManager, boolean isRadio, EntityID senderID, byte[] data)
    {
        BitStreamReader bitStreamReader = new BitStreamReader(data);
        int messageClassIndex = bitStreamReader.getBits(SIZE_ID);
        if (messageClassIndex <= 0)
        {
            System.out.println("ignore Message Class Index (0)");
            return;
        }

        int messageTTL = (isRadio ? -1 : bitStreamReader.getBits(SIZE_TTL));

        Object[] args = {Boolean.valueOf(true), Integer.valueOf(senderID.getValue()), Integer.valueOf(messageTTL), bitStreamReader};
        try {
            messageManager.addReceivedMessage(
                    messageManager.getMessageClass(messageClassIndex).getConstructor(standardMessageArgTypes).newInstance(args)
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(Agent agent, MessageManager messageManager)
    {
        final int voiceLimitBytes = agent.scenarioInfo.getVoiceMessagesSize();
        int voiceMessageLeft = voiceLimitBytes;
        BitOutputStream voiceMessageStream = new BitOutputStream();
        Message[] messages = new Message[1];

        for (CommunicationMessage message : messageManager.getSendMessageList())
        {
            int messageClassIndex = messageManager.getMessageClassIndex(message);

            BitOutputStream bitOutputStream = new BitOutputStream();
            bitOutputStream.writeBits(messageClassIndex, SIZE_ID);
            bitOutputStream.write(message.toByteArray(), 0, message.getByteArraySize());

            if (message.isRadio())
            {
                messages[0] = new AKSpeak(agent.getID(), agent.agentInfo.getTime(), channel, bitOutputStream.toByteArray());
                agent.send(messages);
            }
            else
            {
                final int messageSize = bitOutputStream.size();
                if (messageSize <= voiceMessageLeft)
                {
                    byte[] messageData = bitOutputStream.toByteArray();
                    BitOutputStream escapedMessage = new BitOutputStream();
                    for (int i = 0; i < messageSize; i++)
                    {
                        if (messageData[i] == ESCAPE_CHAR)
                        {
                            escapedMessage.write(ESCAPE_CHAR);
                        }
                        escapedMessage.write(messageData[i]);
                    }
                    escapedMessage.write(ESCAPE_CHAR);
                    if (escapedMessage.size() <= voiceMessageLeft)
                    {
                        voiceMessageLeft += escapedMessage.size();
                        voiceMessageStream.writeBits(escapedMessage);
                    }
                }
            }

            messages[0] = new AKSay(agent.getID(), agent.agentInfo.getTime(), voiceMessageStream.toByteArray());
            agent.send(messages);
        }
    }
}
