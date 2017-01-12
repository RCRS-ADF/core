package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

import javax.annotation.Nonnull;

public class OptionAmbulanceTeam extends Option
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
		return "-at";
	}

	@Override
	public void setValue(@Nonnull Config config, @Nonnull String data)
	{
		config.setValue(ConfigKey.KEY_AMBULANCE_TEAM_COUNT, data);
	}
}