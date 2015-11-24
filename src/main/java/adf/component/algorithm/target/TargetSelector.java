package adf.component.algorithm.target;


import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.EntityID;

public abstract class TargetSelector<E extends StandardEntity> {

    public ScenarioInfo scenarioInfo;
    public AgentInfo agentInfo;
    public WorldInfo worldInfo;

    public TargetSelector(WorldInfo wi, AgentInfo ai, ScenarioInfo si) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
    }

    public TargetSelector calc() {
        return this;
    }

    public abstract EntityID getTarget();

    //public abstract boolean hasTarget();

    //public abstract EntityID getNextPoint();
}
