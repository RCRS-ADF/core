package adf.launcher.connect;

import adf.agent.config.ModuleConfig;
import adf.agent.develop.DevelopData;
import adf.agent.platoon.PlatoonFire;
import adf.component.tactics.TacticsFireBrigade;
import adf.component.AbstractLoader;
import adf.launcher.ConfigKey;
import adf.launcher.ConsoleOutput;
import adf.launcher.dummy.tactics.DummyTacticsFireBrigade;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.connection.ConnectionException;

public class ConnectorFireBrigade extends Connector {
	@Override
	public void connect(ComponentLauncher launcher, Config config, AbstractLoader loader)
	{
		int count = config.getIntValue(ConfigKey.KEY_FIRE_BRIGADE_COUNT, 0);

		if (count == 0) {
			return;
		}

		try {
			for (int i = 0; i != count; ++i) {
				TacticsFireBrigade tacticsFireBrigade;
				if (loader.getTacticsFire() == null)
				{
					ConsoleOutput.error("Cannot Load FireBrigade Tactics");
					tacticsFireBrigade = new DummyTacticsFireBrigade();
				}
				else
				{
					tacticsFireBrigade = loader.getTacticsFire();
				}
				DevelopData developData = new DevelopData(
						config.getBooleanValue(ConfigKey.KEY_DEVELOP_FLAG, false),
						config.getValue(ConfigKey.KEY_DEVELOP_DATA_FILE_NAME, DevelopData.DEFAULT_FILE_NAME),
						config.getArrayValue(ConfigKey.KEY_DEVELOP_DATA, "")
				);
				launcher.connect(new PlatoonFire(
                        tacticsFireBrigade,
						config.getValue(ConfigKey.KEY_MODULE_CONFIG_FILE_NAME, ModuleConfig.DEFAULT_CONFIG_FILE_NAME),
						config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false),
						config.getBooleanValue(ConfigKey.KEY_DEBUG_FLAG, false),
						developData
				));
				//System.out.println(name);
				connected++;
			}
		}
		catch (ComponentConnectionException | InterruptedException | ConnectionException e) {
			//e.printStackTrace();
			//System.out.println("[ERROR ] Cannot Load FireBrigade Tactics !!");
		}
		ConsoleOutput.finish("Connect FireBrigade (success:" + connected + ")");
	}
}
