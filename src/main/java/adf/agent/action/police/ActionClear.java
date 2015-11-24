package adf.agent.action.police;

import adf.agent.platoon.Platoon;
import adf.agent.action.Action;
import rescuecore2.messages.Message;
import rescuecore2.misc.geometry.Vector2D;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.standard.messages.AKClear;
import rescuecore2.standard.messages.AKClearArea;
import rescuecore2.worldmodel.EntityID;

public class ActionClear extends Action
{
	protected EntityID target;
	private boolean useOldFunction;
	private int posX;
	private int posY;

	public ActionClear(EntityID targetID)
	{
		super();
		this.target = targetID;
		this.useOldFunction = true;
	}

	public ActionClear(Blockade blockade)
	{
		this(blockade.getID());
	}

	public ActionClear(Platoon agent, Vector2D vector)
	{
		this((int)(agent.getX() + vector.getX()), (int)(agent.getY() + vector.getY()));
	}

	public ActionClear(int destX, int destY)
	{
		super();
		this.useOldFunction = false;
		this.posX = destX;
		this.posY = destY;
	}

	public boolean getUseOldFunction()
	{
		return this.useOldFunction;
	}

	public int getPosX()
	{
		return this.posX;
	}

	public int getPosY()
	{
		return this.posY;
	}

	@Override
	public Message getCommand(EntityID agentID, int time)
	{
		if (this.useOldFunction)
		{
			return new AKClear(agentID, time, this.target);
		} else
		{
			return new AKClearArea(agentID, time, this.posX, this.posY);
		}
	}
}
