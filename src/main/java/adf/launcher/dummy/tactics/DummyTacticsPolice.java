package adf.launcher.dummy.tactics;

import adf.agent.action.Action;
import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import adf.agent.precompute.PrecomputeData;
import adf.component.tactics.TacticsPolice;

public class DummyTacticsPolice extends TacticsPolice
{
    @Override
    public void initialize(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager, DevelopData debugData)
    {

    }

    @Override
    public void precompute(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, PrecomputeData precomputeData, DevelopData debugData)
    {

    }

    @Override
    public void resume(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, PrecomputeData precomputeData, DevelopData debugData)
    {

    }

    @Override
    public void preparate(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, DevelopData debugData)
    {

    }

    @Override
    public Action think(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager, DevelopData debugData)
    {
        return null;
    }
}
