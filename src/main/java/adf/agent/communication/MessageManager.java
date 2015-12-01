package adf.agent.communication;

import adf.agent.communication.standard.bundle.StandardMessageBundle;
import adf.component.communication.CommunicationMessage;
import adf.component.communication.MessageBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageManager
{
    private int standardMessageClassCount;
    private int customMessageClassCount;
    private HashMap<Integer, Class<? extends CommunicationMessage>> messageClassMap;
    private HashMap<Class<? extends CommunicationMessage>, Integer> messageClassIDMap;
    private List<CommunicationMessage> sendMessageList;
    private List<CommunicationMessage> receivedMessageList;
    private int heardAgentHelpCount;

    public MessageManager()
    {
        standardMessageClassCount = 1;   // 00001
        customMessageClassCount = 16;    // 10000
        messageClassMap = new HashMap<>(32);
        messageClassIDMap = new HashMap<>(32);
        sendMessageList = new ArrayList<>();
        this.receivedMessageList = new ArrayList<>();
        heardAgentHelpCount = 0;
    }

    public boolean registerMessageClass(int index, Class<? extends CommunicationMessage> messageClass)
    {
        if (index > 31)
        {
            throw new IllegalArgumentException("index maximum is 31");
        }
        if (messageClassMap.containsKey(index))
        {
            //throw new IllegalArgumentException("index(" + index + ") is already registrated");
            System.out.println("index(" + index + ") is already registrated/"+ messageClass.getName() +" is ignored");
            return false;
        }

        messageClassMap.put(index, messageClass);
        messageClassIDMap.put(messageClass, index);

        return true;
    }

    public void registerMessageBundle(MessageBundle messageBundle)
    {
        for (Class<? extends CommunicationMessage> messageClass : messageBundle.getMessageClassList())
        {
            this.registerMessageClass(
                    (messageBundle.getClass().equals(StandardMessageBundle.class) ? standardMessageClassCount++ : customMessageClassCount++),
                    messageClass);
        }
    }

    public Class<? extends CommunicationMessage> getMessageClass(int index)
    {
        if (!messageClassMap.containsKey(index))
        {
            return null;
        }

        return messageClassMap.get(index);
    }

    public int getMessageClassIndex(CommunicationMessage message)
    {
        if (!messageClassMap.containsValue(message.getClass()))
        {
            throw new IllegalArgumentException(message.getClass().getName() + " isnot registorated to manager");
        }

        return messageClassIDMap.get(message.getClass());
    }

    public void addMessage(CommunicationMessage message)
    {
        sendMessageList.add(message);
    }

    public List<CommunicationMessage> getSendMessageList()
    {
        return this.sendMessageList;
    }

    public void addReceivedMessage(CommunicationMessage message)
    {
        receivedMessageList.add(message);
    }

    public List<CommunicationMessage> getReceivedMessageList()
    {
        return this.receivedMessageList;
    }

    public void addHeardAgentHelpCount()
    {
        this.heardAgentHelpCount++;
    }

    public int getHeardAgentHelpCount()
    {
        return this.heardAgentHelpCount;
    }

    public void refresh()
    {
        this.sendMessageList.clear();
        this.receivedMessageList.clear();
        this.heardAgentHelpCount = 0;
    }
}
