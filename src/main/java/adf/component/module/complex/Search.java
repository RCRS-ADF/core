package adf.component.module.complex;

import adf.agent.communication.MessageManager;
import adf.agent.debug.DebugData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.Area;

public abstract class Search extends TargetSelector<Area> {

    public Search(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DebugData debugData) {
        super(ai, wi, si, moduleManager, debugData);
    }

    @Override
    public Search precompute(PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Override
    public Search resume(PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Override
    public Search preparate() {
        super.preparate();
        return this;
    }

    @Override
    public Search updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract Search calc();

    @Override
    public final Area getTargetEntity() {
        return (Area)this.worldInfo.getEntity(this.getTarget());
    }
}
