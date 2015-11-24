package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionFireStation extends Option {

	@Override
	public String getKey()
	{
		return "-fs";
	}

	@Override
	public void setValue(Config config, String[] datas) {
		if(datas.length == 2) {
			config.setValue(ConfigKey.KEY_FIRE_STATION_COUNT, datas[1]);
		}
	}
}