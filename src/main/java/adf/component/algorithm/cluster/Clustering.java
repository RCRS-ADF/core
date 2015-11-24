package adf.component.algorithm.cluster;

import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.worldmodel.EntityID;

import java.util.Collection;

public abstract class Clustering{

    protected WorldInfo worldInfo;
    protected AgentInfo agentInfo;
    protected ScenarioInfo scenarioInfo;

    protected int clusterSize;

    protected Collection<EntityID> entityIDs;

    public Clustering(WorldInfo wi, AgentInfo ai, ScenarioInfo si, Collection<EntityID> elements) {
        this(wi, ai, si, elements, -1);
    }

    public Clustering(WorldInfo wi, AgentInfo ai, ScenarioInfo si, Collection<EntityID> elements, int size) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
        this.clusterSize = size;
        this.entityIDs = elements;
    }

    public Clustering calc() {
        return this;
    }

    public abstract int getClusterNumber();

    public abstract int getClusterIndex(EntityID id);

    public abstract Collection<EntityID> getClusterEntities(int index);

    //public abstract Collection<ClusterData> getClusters();
}
