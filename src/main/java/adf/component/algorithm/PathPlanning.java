package adf.component.algorithm;


import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.worldmodel.EntityID;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class PathPlanning {

    protected ScenarioInfo scenarioInfo;
    protected AgentInfo agentInfo;
    protected WorldInfo worldInfo;

    public PathPlanning(AgentInfo ai, WorldInfo wi, ScenarioInfo si) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
    }

    public PathPlanning precompute(PrecomputeData precomputeData) {
        return this;
    }

    public PathPlanning resume(PrecomputeData precomputeData) {
        return this;
    }

    public PathPlanning preparate()
    {
        return this;
    }

    public PathPlanning updateInfo(){
        return this;
    }

    public PathPlanning calc()
    {
        return this;
    }

    public abstract List<EntityID> getResult();

    public abstract PathPlanning setFrom(EntityID id);

    public abstract PathPlanning setDestination(Collection<EntityID> targets);

    public PathPlanning setDestination(EntityID... targets) {
        return this.setDestination(Arrays.asList(targets));
    }
}