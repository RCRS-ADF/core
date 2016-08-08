package adf.launcher.connect;

import adf.agent.config.ModuleConfig;
import adf.agent.office.OfficeFire;
import adf.component.control.ControlFire;
import adf.component.AbstractLoader;
import adf.launcher.ConfigKey;
import adf.launcher.dummy.control.DummyControlFire;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.connection.ConnectionException;

public class ConnectorFireStation implements Connector
{

	@Override
	public void connect(ComponentLauncher launcher, Config config, AbstractLoader loader)
	{
		int count = config.getIntValue(ConfigKey.KEY_FIRE_STATION_COUNT, 0);
		int connected = 0;

		if (count == 0) {
			return;
		}
		
		try {
			for (int i = 0; i != count; ++i) {
				ControlFire controlFire;
				if (loader.getControlFire() == null)
				{
					System.out.println("[ERROR ] Cannot Load FireStation Control !!");
					controlFire = new DummyControlFire();
				}
				else
				{
					controlFire = loader.getControlFire();
				}
				boolean isPrecompute = config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false);
				boolean isDebugMode = config.getBooleanValue(ConfigKey.KEY_DEBUG_FLAG, false);
				launcher.connect(new OfficeFire(
						controlFire,
						config.getValue(ConfigKey.KEY_MODULE_CONFIG_FILE_NAME, ModuleConfig.DEFAULT_CONFIG_FILE_NAME),
						isPrecompute,
						isDebugMode,
						config.getArrayValue(ConfigKey.KEY_DEBUG_DATA)
				));
				//System.out.println(name);
				connected++;
			}
		}
		catch (ComponentConnectionException | InterruptedException | ConnectionException e)
		{
			//e.printStackTrace();
			//System.out.println("[ERROR ] Cannot Load FireStation Control !!");
		}
		System.out.println("[FINISH] Connect FireStation (success:" + connected + ")");
	}
}
