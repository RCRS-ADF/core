package adf.agent.action.common;

import adf.agent.action.Action;
import rescuecore2.messages.Message;
import rescuecore2.standard.messages.AKMove;
import rescuecore2.worldmodel.EntityID;

import java.util.List;

public class ActionMove extends Action
{

	private List<EntityID> path;

	private boolean usePosition;
	private int posX;
	private int posY;

	public ActionMove(List<EntityID> movePath)
	{
		super();
		this.usePosition = false;
		this.path = movePath;
	}

	public ActionMove(List<EntityID> movePath, int destinationX, int destinationY)
	{
		super();
		this.usePosition = true;
		this.path = movePath;
		this.posX = destinationX;
		this.posY = destinationY;
	}

	public List<EntityID> getPath()
	{
		return this.path;
	}

	public boolean getUsePosition()
	{
		return this.usePosition;
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
		return this.usePosition ? new AKMove(agentID, time, this.path, this.posX, this.posY) : new AKMove(agentID, time, this.path);
	}
}