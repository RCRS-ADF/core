package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionFireBrigade extends Option
{

	@Override
	public String getKey()
	{
		return "-fb";
	}

	@Override
	public void setValue(Config config, String[] datas)
	{
		if(datas.length == 2)
		{
			config.setValue(ConfigKey.KEY_FIRE_BRIGADE_COUNT, datas[1]);
		}
	}
}