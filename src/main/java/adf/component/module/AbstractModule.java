package adf.component.module;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class AbstractModule
{
    @Nonnull
    protected ScenarioInfo scenarioInfo;
    @Nonnull
    protected AgentInfo agentInfo;
    @Nonnull
    protected WorldInfo worldInfo;
    @Nonnull
    protected ModuleManager moduleManager;
    @Nonnull
    protected DevelopData developData;

    private int countPrecompute;
    private int countResume;
    private int countPreparate;
    private int countUpdateInfo;
    private int countUpdateInfoCurrentTime;

    public AbstractModule(@Nonnull AgentInfo ai, @Nonnull WorldInfo wi, @Nonnull ScenarioInfo si, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData) {
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

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    public AbstractModule precompute(@Nonnull PrecomputeData precomputeData) {
        this.countPrecompute++;
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    public AbstractModule resume(@Nonnull PrecomputeData precomputeData) {
        this.countResume++;
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    public AbstractModule preparate() {
        this.countPreparate++;
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    public AbstractModule updateInfo(@Nonnull MessageManager messageManager){
        if (this.countUpdateInfoCurrentTime != this.agentInfo.getTime())
        {
            this.countUpdateInfo = 0;
            this.countUpdateInfoCurrentTime = this.agentInfo.getTime();
        }
        this.countUpdateInfo++;
        return this;
    }

    @Nonnull
    public abstract AbstractModule calc();

    @Nonnegative
    public int getCountPrecompute() {
        return this.countPrecompute;
    }

    @Nonnegative
    public int getCountResume() {
        return this.countResume;
    }

    @Nonnegative
    public int getCountPreparate() {
        return this.countPreparate;
    }

    @Nonnegative
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
