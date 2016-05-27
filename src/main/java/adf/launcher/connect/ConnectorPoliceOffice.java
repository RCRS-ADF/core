package adf.launcher.connect;

import adf.agent.office.OfficePolice;
import adf.component.control.ControlPolice;
import adf.component.AbstractLoader;
import adf.launcher.ConfigKey;
import adf.launcher.dummy.control.DummyControlPolice;
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

		try {
			for (int i = 0; i != count; ++i) {
				ControlPolice controlPolice;
				if (loader.getControlPolice() == null)
				{
					System.out.println("[ERROR ] Cannot Load PoliceOffice Control !!");
					controlPolice = new DummyControlPolice();
				}
				else
				{
					controlPolice = loader.getControlPolice();
				}
				boolean isPrecompute = config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false);
				launcher.connect(new OfficePolice(controlPolice, config.getValue(ConfigKey.KEY_MODULE_CONFIG_FILE_NAME), isPrecompute));
				//System.out.println(name);
				connected++;
			}
		}
		catch (ComponentConnectionException | InterruptedException | ConnectionException e)
		{
			//e.printStackTrace();
			//System.out.println("[ERROR ] Cannot Load PoliceOffice Control !!");
		}
		System.out.println("[FINISH] Connect PoliceOffice (success:" + connected + ")");
	}
}
