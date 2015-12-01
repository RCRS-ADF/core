package adf.agent;

import adf.agent.communication.MessageManager;
import adf.agent.communication.standard.StandardCommunicationModule;
import adf.agent.communication.standard.bundle.StandardMessageBundle;
import adf.agent.info.AgentInfo;
import adf.agent.precompute.PrecomputeData;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.component.communication.CommunicationModule;
import rescuecore2.components.AbstractAgent;
import rescuecore2.messages.Command;
import rescuecore2.messages.Message;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.standard.messages.AKSubscribe;
import rescuecore2.worldmodel.ChangeSet;

import java.util.*;

public abstract class Agent<E extends StandardEntity> extends AbstractAgent<StandardWorldModel, E>
{
	final protected static String DATASTORAGE_FILE_NAME_AMBULANCE = "ambulance.bin";
	final protected static String DATASTORAGE_FILE_NAME_FIRE = "fire.bin";
	final protected static String DATASTORAGE_FILE_NAME_POLICE = "police.bin";

	ScenarioInfo.Mode mode;
	public AgentInfo agentInfo;
	public WorldInfo worldInfo;
	public ScenarioInfo scenarioInfo;
	protected PrecomputeData precomputeData;
	protected MessageManager messageManager;
	protected CommunicationModule communicationModule;
	protected  boolean isPrecompute;
	int ignoreTime;

	public Agent(boolean isPrecompute, String dataStorageName)
	{
		this.isPrecompute = isPrecompute;

		if (isPrecompute)
		{
			this.precomputeData.removeData(dataStorageName);
			this.mode = ScenarioInfo.Mode.PRECOMPUTATION_PHASE;
		}

		precomputeData = new PrecomputeData(dataStorageName);
		messageManager = new MessageManager();

		if (!isPrecompute)
		{
			if (precomputeData.isReady())
			{
				this.mode = ScenarioInfo.Mode.PRECOMPUTED;
			}
			else
			{
				this.mode = ScenarioInfo.Mode.NON_PRECOMPUTE;
			}
		}
	}

	@Override
	public final String[] getRequestedEntityURNs()
	{
		EnumSet<StandardEntityURN> set = getRequestedEntityURNsEnum();
		String[] result = new String[set.size()];
		int i = 0;
		for (StandardEntityURN next : set)
		{
			result[i++] = next.toString();
		}
		return result;
	}

	protected abstract EnumSet<StandardEntityURN> getRequestedEntityURNsEnum();

	@Override
	protected StandardWorldModel createWorldModel()
	{
		return new StandardWorldModel();
	}

	@Override
	protected void postConnect()
	{
		super.postConnect();
		if (shouldIndex())
		{
			model.index();
		}

		this.ignoreTime = config.getIntValue(kernel.KernelConstants.IGNORE_AGENT_COMMANDS_KEY);

		this.worldInfo = new WorldInfo(model);
		this.scenarioInfo = new ScenarioInfo(config, mode);

		this.communicationModule = null;

		System.out.println("Connected - " + this);
	}

	@Override
	protected void think(int time, ChangeSet changed, Collection<Command> heard)
	{
		this.agentInfo.setTime(time);

		if ( 1 == time )
		{
			if (this.communicationModule != null)
			{
				System.out.println("This agent's CommunicationModule is modified - " + this);
			}
			else
			{
				this.communicationModule = new StandardCommunicationModule();
			}
			messageManager.registerMessageBundle(new StandardMessageBundle());
		}

		if (time <= this.ignoreTime)
		{
			send(new AKSubscribe(getID(), time, 1));
		}

		this.agentInfo.setHeard(heard);
		this.agentInfo.setChanged(changed);
		this.worldInfo.setChanged(changed);

		if (time > this.ignoreTime)
		{
			this.messageManager.refresh();
			this.communicationModule.receive(this, messageManager);
		}

		think();

		if (time > this.ignoreTime)
		{
			this.communicationModule.send(this, messageManager);
		}
	}

	abstract protected void think();

	protected boolean shouldIndex()
	{
		return true;
	}

	public double getX()
	{
		return me().getLocation(model).first();
	}

	public double getY()
	{
		return me().getLocation(model).second();
	}

	public void send(Message[] msgs)
	{
		for(Message msg : msgs) super.send(msg);
	}

	public void send(List<Message> msgs)
	{
		for(Message msg : msgs) super.send(msg);
	}
}
