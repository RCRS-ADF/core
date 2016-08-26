package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.Constants;
import rescuecore2.config.Config;

public class OptionDebug extends Option
{
	@Override
	public boolean hasValue()
	{
		return true;
	}

	@Override
	public String getKey()
	{
		return "-d";
	}

	@Override
	public void setValue(Config config, String data)
	{
		config.setValue(ConfigKey.KEY_DEBUG_FLAG, data);
	}
}