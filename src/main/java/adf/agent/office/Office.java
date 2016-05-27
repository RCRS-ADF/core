package adf.agent.office;

import adf.agent.Agent;
import adf.agent.info.AgentInfo;
import adf.component.control.Control;
import rescuecore2.standard.entities.StandardEntity;

public abstract class Office<E extends StandardEntity> extends Agent<E> {

	Control rootControl;

	public Office(Control control, String moduleConfigFileName, boolean isPrecompute, String datastorageName) {
		super(moduleConfigFileName, isPrecompute, datastorageName);
		this.rootControl = control;
	}

	@Override
	protected void postConnect() {
		super.postConnect();
		//model.indexClass(StandardEntityURN.ROAD);
		//distance = config.getIntValue(DISTANCE_KEY);

		this.agentInfo = new AgentInfo(this, model, config);

		rootControl.initialize(agentInfo, worldInfo, scenarioInfo, this.messageManager);

		switch (scenarioInfo.getMode())
		{
			case NON_PRECOMPUTE:
				rootControl.preparate(agentInfo, worldInfo, scenarioInfo);
				break;
			case PRECOMPUTED:
				rootControl.resume(agentInfo, worldInfo, scenarioInfo, precomputeData);
				break;
			default:
		}
	}

	protected void think()
	{
		rootControl.think(agentInfo, worldInfo, scenarioInfo, this.messageManager);
	}
}
