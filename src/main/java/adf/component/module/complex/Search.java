package adf.component.module.complex;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.Area;

public abstract class Search extends TargetSelector<Area> {

    public Search(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager) {
        super(ai, wi, si, moduleManager);
    }

    @Override
    public abstract Search precompute(PrecomputeData precomputeData);

    @Override
    public abstract Search resume(PrecomputeData precomputeData);

    @Override
    public abstract Search preparate();

    @Override
    public Search updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract Search calc();
}
