package adf.component.algorithm.cluster;

import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.EntityID;

import java.util.Collection;

public abstract class Clustering{

    public static final int DEFAULT_SIZE = 10;

    protected WorldInfo worldInfo;
    protected AgentInfo agentInfo;
    protected ScenarioInfo scenarioInfo;

    protected int clusterSize;

    protected Collection<StandardEntity> entities;

    public Clustering(AgentInfo ai, WorldInfo wi, ScenarioInfo si, Collection<StandardEntity> elements) {
        this(ai, wi, si, elements, DEFAULT_SIZE);
    }

    public Clustering(AgentInfo ai, WorldInfo wi, ScenarioInfo si, Collection<StandardEntity> elements, int size) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
        this.clusterSize = size;
        this.entities = elements;
    }

    public Clustering precompute(PrecomputeData precomputeData) {
        return this;
    }

    public Clustering resume(PrecomputeData precomputeData) {
        return this;
    }

    public Clustering updateInfo() {
        return this;
    }

    public Clustering calc() {
        return this;
    }

    public int getClusterNumber() {
        return this.clusterSize;
    }

    public abstract int getClusterIndex(StandardEntity entity);

    public abstract int getClusterIndex(EntityID id);

    public abstract Collection<StandardEntity> getClusterEntities(int index);

    public abstract Collection<EntityID> getClusterEntityIDs(int index);
}
