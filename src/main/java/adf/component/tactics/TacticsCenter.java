package adf.component.tactics;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TacticsCenter {
    @Nullable
    private TacticsCenter parentControl;

    public TacticsCenter(@Nullable TacticsCenter parent)
    {
        this.parentControl = parent;
    }

    public TacticsCenter()
    {
        this(null);
    }

    abstract public void initialize(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull MessageManager messageManager, @Nonnull DevelopData developData);
    abstract public void resume(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull PrecomputeData precomputeInfo, @Nonnull DevelopData developData);
    abstract public void preparate(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData);
    abstract public void think(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull MessageManager messageManager, @Nonnull DevelopData developData);

    @Nullable
    public TacticsCenter getParentControl()
    {
        return parentControl;
    }
}
