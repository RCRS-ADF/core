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

    protected int countPrecompute;
    protected int countResume;
    protected int countPreparate;

    public AbstractModule(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
        this.moduleManager = moduleManager;
        this.countPrecompute = 0;
        this.countResume = 0;
        this.countPreparate = 0;
    }

    public AbstractModule precompute(PrecomputeData precomputeData) {
        this.countPrecompute++;
        return this;
    }

    public AbstractModule resume(PrecomputeData precomputeData) {
        this.countResume++;
        return this;
    }

    public AbstractModule preparate() {
        this.countPreparate++;
        return this;
    }

    public AbstractModule updateInfo(MessageManager messageManager){
        return this;
    }

    public abstract AbstractModule calc();

    public int getCountPrecompute() {
        return this.countPrecompute;
    }

    public int getCountResume() {
        return this.countResume;
    }

    public int getCountPreparate() {
        return this.countPreparate;
    }
}
