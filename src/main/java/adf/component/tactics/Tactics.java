package adf.component.tactics;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.action.Action;

public abstract class Tactics
{
	private Tactics parentTactics;

	public Tactics(Tactics parent)
	{
		this.parentTactics = parent;
	}

	public Tactics()
	{
		this(null);
	}

	abstract public void initialize(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager);
	abstract public void precompute(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, PrecomputeData precomputeData);
	abstract public void resume(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, PrecomputeData precomputeData);
	abstract public void preparate(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager);
	abstract public Action think(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager);

	public Tactics getParentTactics()
	{
		return parentTactics;
	}
}
