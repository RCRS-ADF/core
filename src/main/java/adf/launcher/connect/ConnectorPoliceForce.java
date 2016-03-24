package adf.launcher.connect;

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
				boolean isPrecompute = config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false);
				launcher.connect(new PlatoonPolice(tacticsPolice, isPrecompute));
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
