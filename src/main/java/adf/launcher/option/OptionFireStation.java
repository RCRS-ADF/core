package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

import javax.annotation.Nonnull;

public class OptionFireStation extends Option
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
		return "-fs";
	}

	@Override
	public void setValue(@Nonnull Config config, @Nonnull String data)
	{
        config.setValue(ConfigKey.KEY_FIRE_STATION_COUNT, data);
	}
}