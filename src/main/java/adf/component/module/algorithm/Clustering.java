package adf.component.module.algorithm;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import adf.component.module.AbstractModule;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.EntityID;

import java.util.Collection;

public abstract class Clustering extends AbstractModule{

    public Clustering(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData debugData) {
        super(ai, wi, si, moduleManager, debugData);
    }

    public abstract int getClusterNumber();

    public abstract int getClusterIndex(StandardEntity entity);

    public abstract int getClusterIndex(EntityID id);

    public abstract Collection<StandardEntity> getClusterEntities(int index);

    public abstract Collection<EntityID> getClusterEntityIDs(int index);

    @Override
    public Clustering precompute(PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Override
    public Clustering resume(PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Override
    public Clustering preparate() {
        super.preparate();
        return this;
    }

    @Override
    public Clustering updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract Clustering calc();
}
