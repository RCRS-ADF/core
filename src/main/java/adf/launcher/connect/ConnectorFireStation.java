package adf.launcher.connect;

import adf.agent.office.OfficeFire;
import adf.component.control.ControlFire;
import adf.component.AbstractLoader;
import adf.launcher.ConfigKey;
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
		
		if (loader.getControlFire() == null)
		{
		    System.out.println("[ERROR ] Cannot Load FireStation Control !!");
			return;
		}
		
		try {
			for (int i = 0; i != count; ++i) {
				ControlFire controlFire = loader.getControlFire();
				boolean isPrecompute = config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false);
				launcher.connect(new OfficeFire(controlFire, isPrecompute));
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
