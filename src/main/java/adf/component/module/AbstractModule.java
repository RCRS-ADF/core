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

    public abstract void precompute(PrecomputeData precomputeData);

    public abstract void resume(PrecomputeData precomputeData);

    public abstract void preparate();

    public void updateInfo(MessageManager messageManager){
    }

    public abstract AbstractModule calc();
}
