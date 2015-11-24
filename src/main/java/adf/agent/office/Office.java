package adf.agent.office;

import adf.agent.Agent;
import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.component.control.Control;
import rescuecore2.standard.entities.StandardEntity;

public abstract class Office<E extends StandardEntity> extends Agent<E> {

	Control rootControl;

	public Office(Control control, boolean isPrecompute, String datastorageName) {
		super(isPrecompute, datastorageName);
		this.rootControl = control;
	}

	@Override
	protected void postConnect() {
		super.postConnect();
		//model.indexClass(StandardEntityURN.ROAD);
		//distance = config.getIntValue(DISTANCE_KEY);

		MessageManager messageManager = new MessageManager();
		this.agentInfo = new AgentInfo(this, model, config);

		rootControl.initialize(agentInfo, worldInfo, scenarioInfo);

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
		rootControl.think(agentInfo, worldInfo, scenarioInfo);
	}
}
