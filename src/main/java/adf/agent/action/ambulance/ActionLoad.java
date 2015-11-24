package adf.agent.action.ambulance;

import adf.agent.action.Action;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.standard.messages.AKLoad;
import rescuecore2.worldmodel.EntityID;

public class ActionLoad extends Action
{
	protected EntityID target;

	public ActionLoad(EntityID targetID)
	{
		super();
		this.target = targetID;
	}

	public ActionLoad(Civilian civilian)
	{
		this(civilian.getID());
	}

	@Override
	public Message getCommand(EntityID agentID, int time)
	{
		return new AKLoad(agentID, time, this.target);
	}
}
