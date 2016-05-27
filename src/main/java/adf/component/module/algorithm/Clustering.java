package adf.component.module.algorithm;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.precompute.PrecomputeData;
import adf.component.module.AbstractModule;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.EntityID;

import java.util.Collection;

public abstract class Clustering extends AbstractModule{

    public Clustering(AgentInfo ai, WorldInfo wi, ScenarioInfo si) {
        super(ai, wi, si);
    }

    public abstract int getClusterNumber();

    public abstract int getClusterIndex(StandardEntity entity);

    public abstract int getClusterIndex(EntityID id);

    public abstract Collection<StandardEntity> getClusterEntities(int index);

    public abstract Collection<EntityID> getClusterEntityIDs(int index);

    @Override
    public abstract Clustering precompute(PrecomputeData precomputeData);

    @Override
    public abstract Clustering resume(PrecomputeData precomputeData);

    @Override
    public abstract Clustering preparate();

    @Override
    public Clustering updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract Clustering calc();
}
