package adf.launcher.connect;

import adf.agent.platoon.PlatoonAmbulance;
import adf.component.tactics.TacticsAmbulance;
import adf.component.AbstractLoader;
import adf.launcher.ConfigKey;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.connection.ConnectionException;

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
		if (loader.getTacticsAmbulance() == null) {
		    System.out.println("[ERROR ] Cannot Load AmbulanceTeam Tactics !!");
			return;
		}
        //connect
		try {
			for (int i = 0; i != count; ++i) {
				TacticsAmbulance tacticsAmbulance = loader.getTacticsAmbulance();
				boolean isPrecompute = config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false);
				launcher.connect(new PlatoonAmbulance(tacticsAmbulance, isPrecompute));
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
