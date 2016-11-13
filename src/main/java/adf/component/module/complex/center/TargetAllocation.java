package adf.component.module.complex.center;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import adf.component.module.AbstractModule;
import rescuecore2.worldmodel.EntityID;

import java.util.Map;

public abstract class TargetAllocation extends AbstractModule {

    public TargetAllocation(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    public abstract Map<EntityID, EntityID> getResult();

    @Override
    public abstract TargetAllocation calc();

    @Override
    public TargetAllocation precompute(PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Override
    public TargetAllocation resume(PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Override
    public TargetAllocation preparate() {
        super.preparate();
        return this;
    }

    @Override
    public TargetAllocation updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }
}
