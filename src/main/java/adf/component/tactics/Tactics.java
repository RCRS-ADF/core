package adf.component.tactics;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import adf.agent.precompute.PrecomputeData;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.action.Action;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Tactics
{
	@Nullable
	private Tactics parentTactics;

	public Tactics(@Nullable Tactics parent)
	{
		this.parentTactics = parent;
	}

	public Tactics()
	{
		this(null);
	}

	abstract public void initialize(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull MessageManager messageManager, @Nonnull DevelopData developData);
	abstract public void precompute(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull PrecomputeData precomputeData, @Nonnull DevelopData developData);
	abstract public void resume(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull PrecomputeData precomputeData, @Nonnull DevelopData developData);
	abstract public void preparate(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData);

	@Nonnull
	abstract public Action think(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull MessageManager messageManager, @Nonnull DevelopData developData);

	@Nullable
	public Tactics getParentTactics()
	{
		return parentTactics;
	}
}
