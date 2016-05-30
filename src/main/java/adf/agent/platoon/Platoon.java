package adf.agent.platoon;

import adf.agent.Agent;
import adf.agent.action.Action;
import adf.agent.info.AgentInfo;
import adf.agent.module.ModuleManager;
import adf.component.tactics.Tactics;
import rescuecore2.config.ConfigException;
import rescuecore2.standard.entities.StandardEntity;

public abstract class Platoon<E extends StandardEntity> extends Agent<E>
{
	Tactics rootTactics;

	public Platoon(Tactics tactics, String moduleConfigFileName, boolean isPrecompute, String dataStorageName)
	{
		super(moduleConfigFileName, isPrecompute, dataStorageName);
		this.rootTactics = tactics;
	}

	@Override
	protected void postConnect()
	{
		super.postConnect();
		//model.indexClass(StandardEntityURN.ROAD);
		//distance = config.getIntValue(DISTANCE_KEY);

		this.agentInfo = new AgentInfo(this, model, config);
		this.moduleManager = new ModuleManager(this.agentInfo, this.worldInfo, this.scenarioInfo, this.moduleConfig);

		rootTactics.initialize(agentInfo, worldInfo, scenarioInfo, this.moduleManager, this.messageManager);

		switch (scenarioInfo.getMode())
		{
			case NON_PRECOMPUTE:
				rootTactics.preparate(agentInfo, worldInfo, scenarioInfo, this.moduleManager);
				break;
			case PRECOMPUTATION_PHASE:
				rootTactics.precompute(agentInfo, worldInfo, scenarioInfo, this.moduleManager, precomputeData);
				precomputeData.setReady(true);
				if (!precomputeData.write())
				{
					System.out.println("[ERROR ] Failed write PrecomputeData.");
				}
				break;
			case PRECOMPUTED:
				rootTactics.resume(agentInfo, worldInfo, scenarioInfo, this.moduleManager, precomputeData);
				break;
			default:
		}
	}

	protected void think()
	{
		Action action = rootTactics.think(agentInfo, worldInfo, scenarioInfo, this.moduleManager, this.messageManager);
		if(action != null) {
			send(action.getCommand(this.getID(), this.agentInfo.getTime()));
		}
	}
}
