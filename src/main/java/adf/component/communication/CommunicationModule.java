package adf.component.communication;


import adf.agent.Agent;
import adf.agent.communication.MessageManager;

import javax.annotation.Nonnull;

abstract public class CommunicationModule
{
    abstract public void receive(@Nonnull Agent agent, @Nonnull MessageManager messageManager);
    abstract public void send(@Nonnull Agent agent, @Nonnull MessageManager messageManager);
}
