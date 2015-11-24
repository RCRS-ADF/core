package adf.launcher.connect;

import adf.agent.office.OfficePolice;
import adf.component.control.ControlPolice;
import adf.component.AbstractLoader;
import adf.launcher.ConfigKey;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.connection.ConnectionException;

public class ConnectorPoliceOffice implements Connector
{

	@Override
	public void connect(ComponentLauncher launcher, Config config, AbstractLoader loader)
	{
		int count = config.getIntValue(ConfigKey.KEY_POLICE_OFFICE_COUNT, 0);
		int connected = 0;

		if (count == 0)
		{
			return;
		}
		if (loader.getControlPolice() == null) {
			System.out.println("[ERROR ] Cannot Load PoliceOffice Control !!");
			return;
		}

		try {
			for (int i = 0; i != count; ++i) {
				ControlPolice controlPolice = loader.getControlPolice();
				boolean isPrecompute = config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false);
				launcher.connect(new OfficePolice(controlPolice, isPrecompute));
				//System.out.println(name);
				connected++;
			}
		}
		catch (ComponentConnectionException | InterruptedException | ConnectionException e)
		{
			//e.printStackTrace();
			System.out.println("[ERROR ] Cannot Load PoliceOffice Control !!");
		}
		System.out.println("[FINISH] Connect PoliceOffice (success:" + connected + ")");
	}
}
