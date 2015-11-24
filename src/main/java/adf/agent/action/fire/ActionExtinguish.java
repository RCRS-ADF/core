package adf.agent.action.fire;

import adf.agent.action.Action;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.messages.AKExtinguish;
import rescuecore2.worldmodel.EntityID;

public class ActionExtinguish extends Action
{

	protected EntityID target;
	private int power;

	public ActionExtinguish(EntityID targetID, int maxPower)
	{
		super();
		this.target = targetID;
		this.power = maxPower;
	}

	public ActionExtinguish(Building building, int maxPower)
	{
		this(building.getID(), maxPower);
	}

	public int getPower()
	{
		return this.power;
	}

	@Override
	public Message getCommand(EntityID agentID, int time)
	{
		return new AKExtinguish(agentID, time, this.target, this.power);
	}
}