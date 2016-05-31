package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.Constants;
import rescuecore2.config.Config;

public class OptionDebug extends Option
{

	@Override
	public String getKey()
	{
		return "-d";
	}

	@Override
	public void setValue(Config config, String[] datas)
	{
		if (datas.length == 2)
		{
			config.setValue(ConfigKey.KEY_DEBUG_FLAG, datas[1]);
		}
	}
}