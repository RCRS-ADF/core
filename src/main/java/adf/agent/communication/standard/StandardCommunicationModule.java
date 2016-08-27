package adf.agent.communication.standard;

import adf.agent.Agent;
import adf.agent.communication.MessageManager;
import adf.component.communication.CommunicationMessage;
import adf.component.communication.CommunicationModule;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import adf.launcher.ConsoleOutput;
import rescuecore2.messages.Command;
import rescuecore2.messages.Message;
import rescuecore2.standard.messages.AKSay;
import rescuecore2.standard.messages.AKSpeak;
import rescuecore2.worldmodel.EntityID;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

public class StandardCommunicationModule extends CommunicationModule
{
    final private int ESCAPE_CHAR = 0x41;
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


    final Class<?>[] standardMessageArgTypes = {boolean.class, int.class, int.class, BitStreamReader.class};

    private void addReceivedMessage(MessageManager messageManager, boolean isRadio, EntityID senderID, byte[] data)
    {
        BitStreamReader bitStreamReader = new BitStreamReader(data);
        int messageClassIndex = bitStreamReader.getBits(SIZE_ID);
        if (messageClassIndex <= 0)
        {
            ConsoleOutput.out(ConsoleOutput.State.WARN, "ignore Message Class Index (0)");
            return;
        }

        int messageTTL = (isRadio ? -1 : bitStreamReader.getBits(SIZE_TTL));

        Object[] args = {Boolean.valueOf(isRadio), Integer.valueOf(senderID.getValue()), Integer.valueOf(messageTTL), bitStreamReader};
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
        //BitOutputStream voiceMessageStream = new BitOutputStream();
        ByteArrayOutputStream voiceMessageStream = new ByteArrayOutputStream();

        Message[] messages = new Message[1];

        for (CommunicationMessage message : messageManager.getSendMessageList())
        {
            int messageClassIndex = messageManager.getMessageClassIndex(message);

            BitOutputStream bitOutputStream = new BitOutputStream();
            bitOutputStream.writeBits(messageClassIndex, SIZE_ID);
            bitOutputStream.writeBits(message.toBitOutputStream());

            if (message.isRadio())
            {
                messages[0] = new AKSpeak(agent.getID(), agent.agentInfo.getTime(), channel, bitOutputStream.toByteArray());
                agent.send(messages);
            }
            else
            {
                int messageSize = (int)Math.ceil(((double)bitOutputStream.size()) / 8.0);
                if (messageSize <= voiceMessageLeft)
                {
                    byte[] messageData = bitOutputStream.toByteArray();
                    ByteArrayOutputStream escapedMessage = new ByteArrayOutputStream();
                    for (int i = 0; i < messageSize; i++)
                    {
                        if (messageData[i] == ESCAPE_CHAR)
                        {
                            escapedMessage.write(ESCAPE_CHAR);
                        }
                        escapedMessage.write(messageData[i]);
                    }
                    escapedMessage.toByteArray();
                    escapedMessage.write(ESCAPE_CHAR);
                    if (escapedMessage.size() <= voiceMessageLeft)
                    {
                        voiceMessageLeft -= escapedMessage.size();
                        try
                        {
                            voiceMessageStream.write(escapedMessage.toByteArray());
                        }
                        catch (IOException e) { e.printStackTrace(); }
                    }
                }
            }
        }

        //messages[0] = new AKSay(agent.getID(), agent.agentInfo.getTime(), voiceMessageStream.toByteArray());
        if (voiceMessageStream.size() > 0)
        {
            messages[0] = new AKSpeak(agent.getID(), agent.agentInfo.getTime(), 0, voiceMessageStream.toByteArray());
            agent.send(messages);
        }
    }
}
