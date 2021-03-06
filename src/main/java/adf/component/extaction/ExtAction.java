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

import java.util.Collection;

abstract public class ExtAction {
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

	protected Action result;

	public ExtAction(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
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

    public abstract ExtAction setTarget(EntityID targets);

    /**
     * @deprecated  {@link #setTarget(EntityID)}
     */
	@Deprecated
	public ExtAction setTarget(EntityID... targets) {
	    if(targets != null && targets.length > 0) {
	        return this.setTarget(targets[0]);
        }
        return this;
    }

	public abstract ExtAction calc();

	public Action getAction() {
		return result;
	}

	public ExtAction precompute(PrecomputeData precomputeData) {
		this.countPrecompute++;
		return this;
	}

	public ExtAction resume(PrecomputeData precomputeData) {
		this.countResume++;
		return this;
	}

	public ExtAction preparate() {
		this.countPreparate++;
		return this;
	}

	public ExtAction updateInfo(MessageManager messageManager){
		if (this.countUpdateInfoCurrentTime != this.agentInfo.getTime())
		{
			this.countUpdateInfo = 0;
			this.countUpdateInfoCurrentTime = this.agentInfo.getTime();
		}
		this.countUpdateInfo++;
		return this;
	}

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
