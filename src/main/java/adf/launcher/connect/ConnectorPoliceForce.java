package adf.launcher.connect;

import adf.agent.config.ModuleConfig;
import adf.agent.develop.DevelopData;
import adf.agent.platoon.PlatoonPolice;
import adf.component.tactics.TacticsPolice;
import adf.component.AbstractLoader;
import adf.launcher.ConfigKey;
import adf.launcher.dummy.tactics.DummyTacticsPolice;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.connection.ConnectionException;

public class ConnectorPoliceForce implements Connector
{

	@Override
	public void connect(ComponentLauncher launcher, Config config, AbstractLoader loader)
	{
		int count = config.getIntValue(ConfigKey.KEY_POLICE_FORCE_COUNT, 0);
		int connected = 0;

		if (count == 0) {
			return;
		}

		try {
			for (int i = 0; i != count; ++i)
			{
				TacticsPolice tacticsPolice;
				if (loader.getTacticsPolice() == null)
				{
					System.out.println("[ERROR ] Cannot Load PoliceForce Tactics !!");
					tacticsPolice = new DummyTacticsPolice();
				}
				else
				{
					tacticsPolice = loader.getTacticsPolice();
				}
				DevelopData developData = new DevelopData(
						config.getBooleanValue(ConfigKey.KEY_DEVELOP_FLAG, false),
						config.getValue(ConfigKey.KEY_DEVELOP_DATA_FILE_NAME, DevelopData.DEFAULT_FILE_NAME),
						config.getArrayValue(ConfigKey.KEY_DEVELOP_DATA, "")
				);
				launcher.connect(new PlatoonPolice(
						tacticsPolice,
						config.getValue(ConfigKey.KEY_MODULE_CONFIG_FILE_NAME, ModuleConfig.DEFAULT_CONFIG_FILE_NAME),
						config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false),
						config.getBooleanValue(ConfigKey.KEY_DEBUG_FLAG, false),
						developData
				));
				//System.out.println(name);
				connected++;
			}
		}
		catch (ComponentConnectionException | InterruptedException | ConnectionException e)
		{
			//e.printStackTrace();
			//System.out.println("[ERROR ] Cannot Load PoliceForce Tactics !!");
		}
		System.out.println("[FINISH] Connect PoliceForce (success:" + connected + ")");
	}
}
