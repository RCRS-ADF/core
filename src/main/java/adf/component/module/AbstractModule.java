package adf.component.module;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
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
    protected DevelopData developData;

    private int countPrecompute;
    private int countResume;
    private int countPreparate;
    private int countUpdateInfo;
    private int countUpdateInfoCurrentTime;

    public AbstractModule(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
        this.worldInfo = wi;
        this.agentInfo = ai;
        this.scenarioInfo = si;
        this.moduleManager = moduleManager;
        this.developData = developData;
        this.countPrecompute = 0;
        this.countResume = 0;
        this.countPreparate = 0;
        this.countUpdateInfo = 0;
        this.countUpdateInfoCurrentTime = 0;
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
        if (this.countUpdateInfoCurrentTime != this.agentInfo.getTime())
        {
            this.countUpdateInfo = 0;
            this.countUpdateInfoCurrentTime = this.agentInfo.getTime();
        }
        this.countUpdateInfo++;
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

    public int getCountUpdateInfo() {
        if (this.countUpdateInfoCurrentTime != this.agentInfo.getTime())
        {
            this.countUpdateInfo = 0;
            this.countUpdateInfoCurrentTime = this.agentInfo.getTime();
        }
        return this.countUpdateInfo;
    }

    public void resetCountPrecompute() {
        this.countPrecompute = 0;
    }

    public void resetCountResume() {
        this.countResume = 0;
    }

    public void resetCountPreparate() {
        this.countPreparate = 0;
    }

    public void resetCountUpdateInfo() {
        this.countUpdateInfo = 0;
    }
}
