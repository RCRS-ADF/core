package adf.agent.communication.standard.bundle;

import adf.agent.communication.standard.bundle.information.*;
import adf.agent.communication.standard.bundle.topdown.*;
import adf.component.communication.CommunicationMessage;
import adf.component.communication.MessageBundle;

import java.util.ArrayList;
import java.util.List;

public class StandardMessageBundle extends MessageBundle
{
    @Override
    public List<Class<? extends CommunicationMessage>> getMessageClassList()
    {
        List<Class<? extends CommunicationMessage>> messageClassList = new ArrayList<>();

        messageClassList.add(MessageDummy.class);
        //information
        messageClassList.add(MessageAmbulanceTeam.class);
        messageClassList.add(MessageBuilding.class);
        messageClassList.add(MessageCivilian.class);
        messageClassList.add(MessageFireBrigade.class);
        messageClassList.add(MessagePoliceForce.class);
        messageClassList.add(MessageRoad.class);
        //topdown
        messageClassList.add(CommandAmbulance.class);
        messageClassList.add(CommandFire.class);
        messageClassList.add(CommandPolice.class);
        messageClassList.add(CommandScout.class);
        messageClassList.add(MessageReport.class);

        return messageClassList;
    }
}
