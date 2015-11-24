package adf.agent.action.ambulance;

import adf.agent.action.Action;
import rescuecore2.messages.Message;
import rescuecore2.standard.messages.AKUnload;
import rescuecore2.worldmodel.EntityID;

public class ActionUnload extends Action
{

	public ActionUnload()
	{
		super();
	}

	@Override
	public Message getCommand(EntityID agentID, int time)
	{
		return new AKUnload(agentID, time);
	}
}
