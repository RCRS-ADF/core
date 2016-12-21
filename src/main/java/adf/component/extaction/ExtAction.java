package adf.component.extaction;

import adf.agent.action.Action;
import adf.agent.communication.MessageManager;
import adf.agent.communication.standard.bundle.centralized.CommandAmbulance;
import adf.agent.communication.standard.bundle.centralized.CommandFire;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import adf.component.module.complex.BuildingDetector;
import rescuecore2.worldmodel.EntityID;

import javax.annotation.*;
import java.util.Collection;

abstract public class ExtAction {
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

    @Nullable
	protected Action result;

	public ExtAction(@Nonnull AgentInfo ai, @Nonnull WorldInfo wi, @Nonnull ScenarioInfo si, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData) {
		this.worldInfo = wi;
		this.agentInfo = ai;
		this.scenarioInfo = si;
		this.moduleManager = moduleManager;
        this.developData = developData;
		this.result = null;
        this.countPrecompute = 0;
        this.countResume = 0;
        this.countPreparate = 0;
        this.countUpdateInfo = 0;
        this.countUpdateInfoCurrentTime = 0;
	}

	@Nonnull
    public abstract ExtAction setTarget(EntityID targets);

    /**
     * @deprecated  {@link #setTarget(EntityID)}
     */
	@Deprecated
    @Nonnull
	public ExtAction setTarget(EntityID... targets) {
	    if(targets != null && targets.length > 0) {
	        return this.setTarget(targets[0]);
        }
        return this;
    }

    @Nonnull
	public abstract ExtAction calc();

	@CheckForNull
	public Action getAction() {
		return result;
	}

	@Nonnull
    @OverridingMethodsMustInvokeSuper
	public ExtAction precompute(@Nonnull PrecomputeData precomputeData) {
		this.countPrecompute++;
		return this;
	}

    @Nonnull
    @OverridingMethodsMustInvokeSuper
	public ExtAction resume(@Nonnull PrecomputeData precomputeData) {
		this.countResume++;
		return this;
	}

    @Nonnull
    @OverridingMethodsMustInvokeSuper
	public ExtAction preparate() {
		this.countPreparate++;
		return this;
	}

    @Nonnull
    @OverridingMethodsMustInvokeSuper
	public ExtAction updateInfo(@Nonnull MessageManager messageManager){
		if (this.countUpdateInfoCurrentTime != this.agentInfo.getTime())
		{
			this.countUpdateInfo = 0;
			this.countUpdateInfoCurrentTime = this.agentInfo.getTime();
		}
		this.countUpdateInfo++;
		return this;
	}

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
