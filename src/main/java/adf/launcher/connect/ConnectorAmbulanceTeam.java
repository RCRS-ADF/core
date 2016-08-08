package adf.launcher.connect;

import adf.agent.config.ModuleConfig;
import adf.agent.platoon.PlatoonAmbulance;
import adf.component.AbstractLoader;
import adf.component.tactics.TacticsAmbulance;
import adf.launcher.ConfigKey;
import adf.launcher.dummy.tactics.DummyTacticsAmbulance;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.connection.ConnectionException;

import java.util.ArrayList;

public class ConnectorAmbulanceTeam implements Connector {
	@Override
	public void connect(ComponentLauncher launcher, Config config, AbstractLoader loader)
	{
		int count = config.getIntValue(ConfigKey.KEY_AMBULANCE_TEAM_COUNT, 0);
		int connected = 0;
        //check
		if (count == 0) {
			return;
		}

        //connect
		try {
			for (int i = 0; i != count; ++i) {
				TacticsAmbulance tacticsAmbulance;
				if (loader.getTacticsAmbulance() == null)
				{
					System.out.println("[ERROR ] Cannot Load AmbulanceTeam Tactics !!");
					tacticsAmbulance = new DummyTacticsAmbulance();
				}
				else
				{
					tacticsAmbulance = loader.getTacticsAmbulance();
				}
				boolean isPrecompute = config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false);
				boolean isDebugMode = config.getBooleanValue(ConfigKey.KEY_DEBUG_FLAG, false);
				launcher.connect(new PlatoonAmbulance(
						tacticsAmbulance,
						config.getValue(ConfigKey.KEY_MODULE_CONFIG_FILE_NAME, ModuleConfig.DEFAULT_CONFIG_FILE_NAME),
						isPrecompute,
						isDebugMode,
						config.getArrayValue(ConfigKey.KEY_DEBUG_DATA)
				));
				//System.out.println(name);
				connected++;
			}
		}
		catch (ComponentConnectionException | InterruptedException | ConnectionException e) {
			//e.printStackTrace();
			//System.out.println("[ERROR ] Cannot Load AmbulanceTeam Tactics !!");
		}
		System.out.println("[FINISH] Connect AmbulanceTeam (success:" + connected + ")");
	}
}
