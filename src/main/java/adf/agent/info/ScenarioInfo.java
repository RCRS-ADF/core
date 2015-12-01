package adf.agent.info;

import rescuecore2.config.Config;

public class ScenarioInfo
{
	public enum Mode
	{
		NON_PRECOMPUTE,
		PRECOMPUTED,
		PRECOMPUTATION_PHASE
	}

	private Config config;
	private Mode mode;

	public ScenarioInfo(Config config, Mode mode)
	{
		this.config = config;
		this.mode = mode;
	}

	public ScenarioInfo(Config config)
	{
		this(config, Mode.NON_PRECOMPUTE);
	}

	public void setConfig(Config config)
	{
		this.config = config;
	}

	public Mode getMode()
	{
		return mode;
	}

	public Config getRawConfig()
	{
		return this.config;
	}

	public int getFireExtinguishMaxSum() {
		return this.config.getIntValue("fire.extinguish.max-sum");
	}

	public int getCommsChannelsMaxPlatoon()
	{
		return config.getIntValue("comms.channels.max.platoon");
	}

	public int getKernelAgentsThinkTime()
	{
		return config.getIntValue("kernel.agents.think-time");
	}

	public int getFireTankMaximum()
	{
		return config.getIntValue("fire.tank.maximum");
	}

	public int getClearRepairRate()
	{
		return config.getIntValue("updateInfo.repair.rate");
	}

	public int getKernelStartupConnectTime()
	{
		return config.getIntValue("kernel.startup.connect-time");
	}

	public String getKernelHost()
	{
		return config.getValue("kernel.host");
	}

	public int getScenarioAgentsAt()
	{
		return config.getIntValue("scenario.agents.at");
	}

	public int getPerceptionLosMaxDistance()
	{
		return config.getIntValue("perception.los.max-distance");
	}

	public int getScenarioAgentsFb()
	{
		return config.getIntValue("scenario.agents.fb");
	}

	public int getScenarioAgentsPo()
	{
		return config.getIntValue("scenario.agents.po");
	}

	public String getKernelCommunicationModel()
	{
		return config.getValue("kernel.communication-model");
	}

	public int getPerceptionLosPrecisionDamage()
	{
		return config.getIntValue("perception.los.precision.damage");
	}

	public int getScenarioAgentsAc()
	{
		return config.getIntValue("scenario.agents.ac");
	}

	public int getCommsChannelsMaxOffice() {
		return config.getIntValue("comms.channels.max.centre");
	}

	public int getFireExtinguishMaxDistance()
	{
		return config.getIntValue("fire.extinguish.max-distance");
	}

	public int getKernelAgentsIgnoreuntil()
	{
		return config.getIntValue("kernel.agents.ignoreuntil");
	}

	public int getClearRepairDistance() {
		return this.config.getIntValue("clear.repair.distance");
	}

	public int getCommsChannelsCount()
	{
		return config.getIntValue("comms.channels.count");
	}

	public String getKernelPerception()
	{
		return config.getValue("kernel.perception");
	}

	public int getPerceptionLosPrecisionHp()
	{
		return config.getIntValue("perception.los.precision.hp");
	}

	public int getClearRepairRad()
	{
		return config.getIntValue("updateInfo.repair.rad");
	}

	public int getFireTankRefillHydrantRate()
	{
		return config.getIntValue("fire.tank.refill_hydrant_rate");
	}

	public int getScenarioAgentsPf()
	{
		return config.getIntValue("scenario.agents.pf");
	}

	public int getScenarioAgentsFs()
	{
		return config.getIntValue("scenario.agents.fs");
	}

	public int getVoiceMessagesSize()
	{
		return config.getIntValue("comms.channels.0.messages.size");
	}
}
