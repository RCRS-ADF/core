package adf.component.control;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import adf.agent.precompute.PrecomputeData;
import adf.component.tactics.TacticsCenter;

import javax.annotation.Nonnull;

/**
 * @deprecated change class name {@link TacticsCenter}
 */
@Deprecated
public class Control extends TacticsCenter
{
	private Control parentControl;

	public Control(Control parent)
	{
		this.parentControl = parent;
	}

	public Control()
	{
		this(null);
	}

	public void initialize(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull MessageManager messageManager, @Nonnull DevelopData developData) {
    }

	public void resume(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull PrecomputeData precomputeInfo, @Nonnull DevelopData developData) {
    }

	public void preparate(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData) {
    }

	public void think(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull MessageManager messageManager, @Nonnull DevelopData developData) {
    }

	public Control getParentControl()
	{
		return parentControl;
	}
}
