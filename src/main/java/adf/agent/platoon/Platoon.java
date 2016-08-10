package adf.agent.platoon;

import adf.agent.Agent;
import adf.agent.action.Action;
import adf.agent.info.AgentInfo;
import adf.agent.module.ModuleManager;
import adf.component.tactics.Tactics;
import rescuecore2.standard.entities.StandardEntity;

import java.util.List;

public abstract class Platoon<E extends StandardEntity> extends Agent<E> {
	private Tactics rootTactics;

	Platoon(Tactics tactics, String moduleConfigFileName, boolean isPrecompute, String dataStorageName, boolean isDebugMode, String debugDataFileName, List<String> rawDebugData) {
		super(moduleConfigFileName, isPrecompute, dataStorageName, isDebugMode, debugDataFileName, rawDebugData);
		this.rootTactics = tactics;
	}

	@Override
	protected void postConnect()
	{
		super.postConnect();
		//model.indexClass(StandardEntityURN.ROAD);
		//distance = config.getIntValue(DISTANCE_KEY);

		this.agentInfo = new AgentInfo(this, this.model, this.config);
		this.moduleManager = new ModuleManager(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleConfig, this.debugData);

		this.rootTactics.initialize(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.messageManager, this.debugData);

		switch (this.scenarioInfo.getMode()) {
			case NON_PRECOMPUTE:
				this.rootTactics.preparate(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.debugData);
                this.worldInfo.registerListener();
				break;
			case PRECOMPUTATION_PHASE:
				this.rootTactics.precompute(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.precomputeData, this.debugData);
				this.precomputeData.setReady(true, this.worldInfo);
				if (!this.precomputeData.write()) {
					System.out.println("[ERROR ] Failed to write PrecomputeData.");
				}
				break;
			case PRECOMPUTED:
				this.rootTactics.resume(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.precomputeData, this.debugData);
                this.worldInfo.registerListener();
				break;
			default:
		}
	}

	protected void think() {
		Action action = this.rootTactics.think(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.messageManager, this.debugData);
		if(action != null) {
			this.agentInfo.setExecutedAction(this.agentInfo.getTime(), action);
			send(action.getCommand(this.getID(), this.agentInfo.getTime()));
		}
	}
}
