package adf.launcher.connect;

import adf.agent.config.ModuleConfig;
import adf.agent.develop.DevelopData;
import adf.agent.office.OfficePolice;
import adf.component.AbstractLoader;
import adf.component.tactics.center.TacticsPoliceCenter;
import adf.launcher.ConfigKey;
import adf.launcher.ConsoleOutput;
import adf.launcher.dummy.control.DummyTacticsPoliceCenter;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.connection.ConnectionException;

public class ConnectorPoliceOffice extends Connector
{
	@Override
	public void connect(ComponentLauncher launcher, Config config, AbstractLoader loader)
	{
		int count = config.getIntValue(ConfigKey.KEY_POLICE_OFFICE_COUNT, 0);

		if (count == 0)
		{
			return;
		}

		try {
			for (int i = 0; i != count; ++i) {
				TacticsPoliceCenter tacticsPoliceCenter;
				if (loader.getTacticsPoliceCenter() == null && loader.getControlPolice() == null)
				{
					ConsoleOutput.error("Cannot Load PoliceOffice Tactics");
					tacticsPoliceCenter = new DummyTacticsPoliceCenter();
				}
				else
				{
					tacticsPoliceCenter = loader.getTacticsPoliceCenter();
					if(tacticsPoliceCenter == null) {
						tacticsPoliceCenter = loader.getControlPolice();
					}
				}
				DevelopData developData = new DevelopData(
						config.getBooleanValue(ConfigKey.KEY_DEVELOP_FLAG, false),
						config.getValue(ConfigKey.KEY_DEVELOP_DATA_FILE_NAME, DevelopData.DEFAULT_FILE_NAME),
						config.getArrayValue(ConfigKey.KEY_DEVELOP_DATA, "")
				);
				launcher.connect(new OfficePolice(
						tacticsPoliceCenter,
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
			//System.out.println("[ERROR ] Cannot Load PoliceOffice Control !!");
		}
		ConsoleOutput.finish("Connect PoliceOffice (success:" + connected + ")");
	}
}
