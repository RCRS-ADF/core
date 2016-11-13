package adf.component.module.complex.center;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.worldmodel.EntityID;

import java.util.Map;

public abstract class PoliceTargetAllocation extends TargetAllocation {
    public PoliceTargetAllocation(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    @Override
    public abstract Map<EntityID, EntityID> getResult();

    @Override
    public abstract PoliceTargetAllocation calc();

    @Override
    public PoliceTargetAllocation precompute(PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Override
    public PoliceTargetAllocation resume(PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Override
    public PoliceTargetAllocation preparate() {
        super.preparate();
        return this;
    }

    @Override
    public PoliceTargetAllocation updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }
}
