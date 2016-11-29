package adf.agent.action;

import rescuecore2.messages.Message;
import rescuecore2.worldmodel.EntityID;

import javax.annotation.Nonnull;

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

	@Nonnull
	public abstract Message getCommand(@Nonnull EntityID agentID, int time);
}