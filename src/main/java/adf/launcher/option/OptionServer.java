package adf.launcher.option;

import rescuecore2.Constants;
import rescuecore2.config.Config;

public class OptionServer extends Option
{

	@Override
	public String getKey()
	{
		return "-s";
	}

	@Override
	public void setValue(Config config, String[] datas)
	{
		if (datas.length == 3)
		{
			config.setValue(Constants.KERNEL_HOST_NAME_KEY, datas[1]);
			config.setValue(Constants.KERNEL_PORT_NUMBER_KEY, datas[2]);
		}
	}
}