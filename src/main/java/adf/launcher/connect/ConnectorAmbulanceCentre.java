package adf.launcher.connect;

import adf.agent.office.OfficeAmbulance;
import adf.component.control.ControlAmbulance;
import adf.component.AbstractLoader;
import adf.launcher.ConfigKey;
import rescuecore2.components.ComponentConnectionException;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.connection.ConnectionException;

public class ConnectorAmbulanceCentre implements Connector {
	@Override
	public void connect(ComponentLauncher launcher, Config config, AbstractLoader loader)
	{
		int count = config.getIntValue(ConfigKey.KEY_AMBULANCE_CENTRE_COUNT, 0);
		int connected = 0;

		if (count == 0) {
			return;
		}
		if (loader.getControlAmbulance() == null) {
			System.out.println("[ERROR ] Cannot Load AmbulanceCentre Control !!");
			return;
		}
		try {
			for (int i = 0; i != count; ++i) {
				ControlAmbulance controlAmbulance = loader.getControlAmbulance();
				boolean isPrecompute = config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false);
				launcher.connect(new OfficeAmbulance(controlAmbulance, isPrecompute));
				//System.out.println(name);
				connected++;
			}
		}
		catch (ComponentConnectionException | InterruptedException | ConnectionException e) {
			//e.printStackTrace();
			//System.out.println("[ERROR ] Cannot Load AmbulanceCentre Control !!");
		}
		System.out.println("[FINISH] Connect AmbulanceCentre (success:" + connected + ")");
	}
}
