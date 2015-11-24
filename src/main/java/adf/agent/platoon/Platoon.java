package adf.agent.platoon;

import adf.agent.Agent;
import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.action.Action;
import adf.component.tactics.Tactics;
import rescuecore2.standard.entities.StandardEntity;

public abstract class Platoon<E extends StandardEntity> extends Agent<E>
{
	Tactics rootTactics;

	public Platoon(Tactics tactics, boolean isPrecompute, String dataStorageName)
	{
		super(isPrecompute, dataStorageName);
		this.rootTactics = tactics;
	}

	@Override
	protected void postConnect()
	{
		super.postConnect();
		//model.indexClass(StandardEntityURN.ROAD);
		//distance = config.getIntValue(DISTANCE_KEY);

		MessageManager messageManager = new MessageManager();
		this.agentInfo = new AgentInfo(this, model, config);

		rootTactics.initialize(agentInfo, worldInfo, scenarioInfo);

		switch (scenarioInfo.getMode())
		{
			case NON_PRECOMPUTE:
				rootTactics.preparate(agentInfo, worldInfo, scenarioInfo);
				break;
			case PRECOMPUTATION_PHASE:
				rootTactics.precompute(agentInfo, worldInfo, scenarioInfo, precomputeData);
				precomputeData.setReady(true);
				precomputeData.write();
				break;
			case PRECOMPUTED:
				rootTactics.resume(agentInfo, worldInfo, scenarioInfo, precomputeData);
				break;
			default:
		}
	}

	protected void think()
	{
		Action action = rootTactics.think(agentInfo, worldInfo, scenarioInfo);
		if(action != null) {
			send(action.getCommand(this.getID(), this.agentInfo.time));
		}
	}
}
