package adf.agent.office;

import adf.agent.Agent;
import adf.agent.info.AgentInfo;
import adf.agent.module.ModuleManager;
import adf.component.control.Control;
import rescuecore2.config.ConfigException;
import rescuecore2.standard.entities.StandardEntity;

import java.util.List;

public abstract class Office<E extends StandardEntity> extends Agent<E> {

	Control rootControl;

	public Office(Control control, String moduleConfigFileName, boolean isPrecompute, String datastorageName, boolean isDebugMode, String debugDataFileName, List<String> rawDebugData)
	{
		super(moduleConfigFileName, isPrecompute, datastorageName, isDebugMode, debugDataFileName, rawDebugData);
		this.rootControl = control;
	}

	@Override
	protected void postConnect() {
		super.postConnect();
		//model.indexClass(StandardEntityURN.ROAD);
		//distance = config.getIntValue(DISTANCE_KEY);

		this.agentInfo = new AgentInfo(this, model, config);
		this.moduleManager = new ModuleManager(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleConfig, this.debugData);

		rootControl.initialize(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.messageManager, this.debugData);

		switch (scenarioInfo.getMode()) {
			case NON_PRECOMPUTE:
				rootControl.preparate(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.debugData);
				break;
			case PRECOMPUTED:
				rootControl.resume(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, precomputeData, this.debugData);
				break;
			default:
		}

		this.worldInfo.registerListener();
	}

	protected void think() {
		this.rootControl.think(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleManager, this.messageManager, this.debugData);
	}
}
