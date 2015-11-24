package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionTeam extends Option
{

	@Override
	public String getKey()
	{
		return "-t";
	}

	@Override
	public void setValue(Config config, String[] datas)
	{
		if (datas.length == 7)
		{
			config.setValue(ConfigKey.KEY_FIRE_BRIGADE_COUNT, datas[1]);
			config.setValue(ConfigKey.KEY_FIRE_STATION_COUNT, datas[2]);

			config.setValue(ConfigKey.KEY_POLICE_FORCE_COUNT, datas[3]);
			config.setValue(ConfigKey.KEY_POLICE_OFFICE_COUNT, datas[4]);

			config.setValue(ConfigKey.KEY_AMBULANCE_TEAM_COUNT, datas[5]);
			config.setValue(ConfigKey.KEY_AMBULANCE_CENTRE_COUNT, datas[6]);
		}
	}
}
