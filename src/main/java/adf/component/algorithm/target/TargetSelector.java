package adf.component.algorithm.target;


import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.EntityID;

public abstract class TargetSelector<E extends StandardEntity> {

    protected ScenarioInfo scenarioInfo;
    protected AgentInfo agentInfo;
    protected WorldInfo worldInfo;

    public TargetSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
    }

    public TargetSelector<E> precompute(PrecomputeData precomputeData) {
        return this;
    }

    public TargetSelector<E> resume(PrecomputeData precomputeData) {
        return this;
    }

    public TargetSelector<E> updateInfo() {
        return this;
    }

    public TargetSelector<E> calc() {
        return this;
    }

    public abstract EntityID getTarget();
}
