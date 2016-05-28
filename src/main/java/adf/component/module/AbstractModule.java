package adf.component.module;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;

public abstract class AbstractModule {
    protected ScenarioInfo scenarioInfo;
    protected AgentInfo agentInfo;
    protected WorldInfo worldInfo;
    protected ModuleManager moduleManager;

    public AbstractModule(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
        this.moduleManager = moduleManager;
    }

    public abstract AbstractModule precompute(PrecomputeData precomputeData);

    public abstract AbstractModule resume(PrecomputeData precomputeData);

    public abstract AbstractModule preparate();

    public AbstractModule updateInfo(MessageManager messageManager){
        return this;
    }

    public abstract AbstractModule calc();
}
