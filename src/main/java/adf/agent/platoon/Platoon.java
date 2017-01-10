package adf.agent.platoon;

import adf.agent.Agent;
import adf.agent.action.Action;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.module.ModuleManager;
import adf.component.tactics.Tactics;
import adf.launcher.ConsoleOutput;
import rescuecore2.standard.entities.StandardEntity;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class Platoon<E extends StandardEntity> extends Agent<E> {
	@Nonnull
	private Tactics rootTactics;

	Platoon(@Nonnull Tactics tactics, @Nonnull String moduleConfigFileName, boolean isPrecompute, @Nonnull String dataStorageName, boolean isDebugMode, @Nonnull DevelopData developData) {
		super(moduleConfigFileName, isPrecompute, dataStorageName, isDebugMode, developData);
		this.rootTactics = tactics;
	}

	@Override
    @OverridingMethodsMustInvokeSuper
	protected void postConnect()
	{
		super.postConnect();
		//model.indexClass(StandardEntityURN.ROAD);
		//distance = config.getIntValue(DISTANCE_KEY);

		this.agentInfo = new AgentInfo(this, this.model);
		this.moduleManager = new ModuleManager(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleConfig, this.developData);

		this.rootTactics.initialize(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.messageManager, this.developData);

		switch (this.scenarioInfo.getMode()) {
			case NON_PRECOMPUTE:
				this.rootTactics.preparate(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.developData);
                this.worldInfo.registerRollbackListener();
				break;
			case PRECOMPUTATION_PHASE:
				this.rootTactics.precompute(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.precomputeData, this.developData);
				this.precomputeData.setReady(true, this.worldInfo);
				if (!this.precomputeData.write()) {
					ConsoleOutput.out(ConsoleOutput.State.ERROR, "[ERROR ] Failed to write PrecomputeData.");
				}
				break;
			case PRECOMPUTED:
				this.rootTactics.resume(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.precomputeData, this.developData);
                this.worldInfo.registerRollbackListener();
				break;
			default:
		}
	}

	@Override
    @OverridingMethodsMustInvokeSuper
	protected void think() {
		Action action = this.rootTactics.think(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.messageManager, this.developData);
		if(action != null) {
			this.agentInfo.setExecutedAction(this.agentInfo.getTime(), action);
			send(action.getCommand(this.getID(), this.agentInfo.getTime()));
		}
	}
}
