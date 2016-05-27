package adf.component.module;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.precompute.PrecomputeData;

public abstract class AbstractModule {
    protected ScenarioInfo scenarioInfo;
    protected AgentInfo agentInfo;
    protected WorldInfo worldInfo;

    public AbstractModule(AgentInfo ai, WorldInfo wi, ScenarioInfo si) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
    }

    public abstract AbstractModule precompute(PrecomputeData precomputeData);

    public abstract AbstractModule resume(PrecomputeData precomputeData);

    public abstract AbstractModule preparate();

    public AbstractModule updateInfo(MessageManager messageManager){
        return this;
    }

    public abstract AbstractModule calc();
}
