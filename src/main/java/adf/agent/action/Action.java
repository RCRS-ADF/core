package adf.agent.action;

import rescuecore2.messages.Message;
import rescuecore2.worldmodel.EntityID;

public abstract class Action
{
	public Action()
	{
	}

	/*
	public Action(Platoon agent)
	{
		this.agentID = agent.getID();
	}

	public EntityID getUserID()
	{
		return this.agentID;
	}
	*/

	public abstract Message getCommand(EntityID agentID, int time);
}