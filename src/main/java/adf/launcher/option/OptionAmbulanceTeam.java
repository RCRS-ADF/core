package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionAmbulanceTeam extends Option
{

	@Override
	public String getKey()
	{
		return "-at";
	}

	@Override
	public void setValue(Config config, String[] datas)
	{
		if(datas.length == 2)
		{
			config.setValue(ConfigKey.KEY_AMBULANCE_TEAM_COUNT, datas[1]);
		}
	}
}