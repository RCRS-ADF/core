package adf.component.module.complex;

import adf.agent.communication.MessageManager;
import adf.agent.debug.DebugData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.Road;

public abstract class RoadSelector extends TargetSelector<Road> {

    public RoadSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DebugData debugData) {
        super(ai, wi, si, moduleManager, debugData);
    }

    @Override
    public RoadSelector precompute(PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Override
    public RoadSelector resume(PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Override
    public RoadSelector preparate() {
        super.preparate();
        return this;
    }

    @Override
    public RoadSelector updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract RoadSelector calc();

    @Override
    public final Road getTargetEntity() {
        return (Road) this.worldInfo.getEntity(this.getTarget());
    }
}

