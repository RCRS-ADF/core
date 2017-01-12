package adf.launcher.option;

import rescuecore2.Constants;
import rescuecore2.config.Config;

import javax.annotation.Nonnull;

public class OptionHost extends Option
{
	@Override
	public boolean hasValue()
	{
		return true;
	}

	@Nonnull
	@Override
	public String getKey()
	{
		return "-h";
	}

	@Override
	public void setValue(@Nonnull Config config, @Nonnull String data)
	{
		config.setValue(Constants.KERNEL_HOST_NAME_KEY, data);
	}
}