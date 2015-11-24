package adf.component.algorithm.path;


import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import rescuecore2.worldmodel.EntityID;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class PathPlanner {

    public ScenarioInfo scenarioInfo;
    public AgentInfo agentInfo;
    public WorldInfo worldInfo;

    public PathPlanner(WorldInfo wi, AgentInfo ai, ScenarioInfo si) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
    }

    public abstract void clear();

    public abstract List<EntityID> getResult();

    public abstract void setFrom(EntityID id);

    public abstract void setDist(Collection<EntityID> targets);

    public void setDist(EntityID... targets) {
        this.setDist(Arrays.asList(targets));
    }
}