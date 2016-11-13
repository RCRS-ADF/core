package adf.agent.office;

import adf.agent.Agent;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.module.ModuleManager;
import adf.component.tactics.center.TacticsCenter;
import rescuecore2.standard.entities.StandardEntity;

public abstract class Office<E extends StandardEntity> extends Agent<E> {

	TacticsCenter rootTacticsCenter;

	public Office(TacticsCenter tacticsCenter, String moduleConfigFileName, boolean isPrecompute, String datastorageName, boolean isDebugMode, DevelopData developData)
	{
		super(moduleConfigFileName, isPrecompute, datastorageName, isDebugMode, developData);
		this.rootTacticsCenter = tacticsCenter;
	}

	@Override
	protected void postConnect() {
		super.postConnect();
		//model.indexClass(StandardEntityURN.ROAD);
		//distance = config.getIntValue(DISTANCE_KEY);

		this.agentInfo = new AgentInfo(this, model, config);
		this.moduleManager = new ModuleManager(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleConfig, this.developData);

		rootTacticsCenter.initialize(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.messageManager, this.developData);

		switch (scenarioInfo.getMode()) {
			case NON_PRECOMPUTE:
				rootTacticsCenter.preparate(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.developData);
				break;
			case PRECOMPUTED:
				rootTacticsCenter.resume(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, precomputeData, this.developData);
				break;
			default:
		}

		this.worldInfo.registerRollbackListener();
	}

	protected void think() {
		this.rootTacticsCenter.think(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.messageManager, this.developData);
	}
}
