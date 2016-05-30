package adf.component.extaction;

import adf.agent.action.Action;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import rescuecore2.worldmodel.EntityID;

abstract public class ExtAction {
	protected ScenarioInfo scenarioInfo;
	protected AgentInfo agentInfo;
	protected WorldInfo worldInfo;
	protected ModuleManager moduleManager;

	protected Action result;

	public ExtAction(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager) {
		this.worldInfo = wi;
		this.agentInfo = ai;
		this.scenarioInfo = si;
		this.moduleManager = moduleManager;
		this.result = null;
	}

    public abstract ExtAction setTarget(EntityID... targets);

	public abstract ExtAction calc();

	public Action getAction() {
		return result;
	}
}
