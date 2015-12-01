package adf.component.algorithm.path;


import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.worldmodel.EntityID;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class PathPlanner {

    protected ScenarioInfo scenarioInfo;
    protected AgentInfo agentInfo;
    protected WorldInfo worldInfo;

    public PathPlanner(AgentInfo ai, WorldInfo wi, ScenarioInfo si) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
    }

    public PathPlanner precompute(PrecomputeData precomputeData) {
        return this;
    }

    public PathPlanner resume(PrecomputeData precomputeData) {
        return this;
    }

    public PathPlanner updateInfo(){
        return this;
    }

    public abstract List<EntityID> getResult();

    public abstract void setFrom(EntityID id);

    public abstract PathPlanner setDist(Collection<EntityID> targets);

    public PathPlanner setDist(EntityID... targets) {
        return this.setDist(Arrays.asList(targets));
    }
}