package adf.component.tactics;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import adf.agent.precompute.PrecomputeData;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.action.Action;
import adf.component.module.AbstractModule;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.ArrayList;
import java.util.List;

public abstract class Tactics
{
	private Tactics parentTactics;
	private List<AbstractModule> modules = new ArrayList<>();

	public Tactics(Tactics parent)
	{
		this.parentTactics = parent;
	}

	public Tactics()
	{
		this(null);
	}


	protected void registerModule(AbstractModule module)
	{
		modules.add(module);
	}

	protected boolean unregisterModule(AbstractModule module)
	{
		return modules.remove(module);
    }

	abstract public void initialize(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager, DevelopData developData);

	@OverridingMethodsMustInvokeSuper
	public void precompute(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, PrecomputeData precomputeData, DevelopData developData)
	{
		for (AbstractModule module : modules)
		{
			module.precompute(precomputeData);
		}
	}

	@OverridingMethodsMustInvokeSuper
	public void resume(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, PrecomputeData precomputeData, DevelopData developData)
	{
		for (AbstractModule module : modules)
		{
			module.resume(precomputeData);
		}
	}

	@OverridingMethodsMustInvokeSuper
	public void preparate(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, DevelopData developData)
	{
		for (AbstractModule module : modules)
		{
			module.preparate();
		}
	}

	@OverridingMethodsMustInvokeSuper
	public Action think(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager, DevelopData developData)
	{
		for (AbstractModule module : modules)
		{
			module.updateInfo(messageManager);
		}

		return null;
	}

	public Tactics getParentTactics()
	{
		return parentTactics;
	}
}
