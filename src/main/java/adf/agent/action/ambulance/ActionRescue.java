package adf.agent.action.ambulance;

import adf.agent.action.Action;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.Human;
import rescuecore2.standard.messages.AKRescue;
import rescuecore2.worldmodel.EntityID;

public class ActionRescue extends Action
{
	protected EntityID target;

	public ActionRescue(EntityID targetID)
	{
		super();
		this.target = targetID;
	}

	public ActionRescue(Human human)
	{
		this(human.getID());
	}

	@Override
	public Message getCommand(EntityID agentID, int time)
	{
		return new AKRescue(agentID, time, this.target);
	}
}
