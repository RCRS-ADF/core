package adf.component.module.complex;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.Building;

public abstract class BuildingSelector extends TargetSelector<Building> {

    public BuildingSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager) {
        super(ai, wi, si, moduleManager);
    }

    @Override
    public abstract BuildingSelector precompute(PrecomputeData precomputeData);

    @Override
    public abstract BuildingSelector resume(PrecomputeData precomputeData);

    @Override
    public abstract BuildingSelector preparate();

    @Override
    public BuildingSelector updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract BuildingSelector calc();
}