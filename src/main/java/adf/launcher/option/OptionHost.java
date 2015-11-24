package adf.launcher.option;

import rescuecore2.Constants;
import rescuecore2.config.Config;

public class OptionHost extends Option
{

	@Override
	public String getKey()
	{
		return "-h";
	}

	@Override
	public void setValue(Config config, String[] datas)
	{
		if (datas.length == 2)
		{
			config.setValue(Constants.KERNEL_HOST_NAME_KEY, datas[1]);
		}
	}
}