package adf.component.control;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.develop.DevelopData;
import adf.agent.precompute.PrecomputeData;

public abstract class Control
{
	private Control parentControl;

	public Control(Control parent)
	{
		this.parentControl = parent;
	}

	public Control()
	{
		this(null);
	}

	abstract public void initialize(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager, DevelopData debugData);
	abstract public void resume(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, PrecomputeData precomputeInfo, DevelopData debugData);
	abstract public void preparate(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, DevelopData debugData);
	abstract public void think(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleManager moduleManager, MessageManager messageManager, DevelopData debugData);

	public Control getParentControl()
	{
		return parentControl;
	}
}
