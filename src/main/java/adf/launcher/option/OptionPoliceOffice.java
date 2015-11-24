package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionPoliceOffice extends Option
{

	@Override
	public String getKey()
	{
		return "-po";
	}

	@Override
	public void setValue(Config config, String[] datas)
	{
		if(datas.length == 2)
		{
			config.setValue(ConfigKey.KEY_POLICE_OFFICE_COUNT, datas[1]);
		}
	}
}