package adf.launcher.dummy.tactics;

import adf.agent.action.Action;
import adf.agent.action.common.ActionRest;
import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import adf.agent.precompute.PrecomputeData;
import adf.component.tactics.TacticsAmbulanceTeam;

import javax.annotation.Nonnull;

public class DummyTacticsAmbulanceTeam extends TacticsAmbulanceTeam
{
    @Override
    public void initialize(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull MessageManager messageManager, @Nonnull DevelopData developData)
    {

    }

    @Override
    public void precompute(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull PrecomputeData precomputeData, @Nonnull DevelopData developData)
    {

    }

    @Override
    public void resume(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull PrecomputeData precomputeData, @Nonnull DevelopData developData)
    {

    }

    @Override
    public void preparate(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData)
    {

    }

    @Nonnull
    @Override
    public Action think(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleManager moduleManager, @Nonnull MessageManager messageManager, @Nonnull DevelopData developData)
    {
        return new ActionRest();
    }
}
