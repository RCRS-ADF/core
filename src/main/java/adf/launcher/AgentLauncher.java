package adf.launcher;

import adf.component.AbstractLoader;
import adf.launcher.connect.*;
import rescuecore2.Constants;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.components.TCPComponentLauncher;
import rescuecore2.config.Config;
import rescuecore2.registry.Registry;
import rescuecore2.standard.entities.StandardEntityFactory;
import rescuecore2.standard.entities.StandardPropertyFactory;
import rescuecore2.standard.messages.StandardMessageFactory;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class AgentLauncher {
    
    private Config config;
    
    private AbstractLoader loader;

    private List<Connector> connectors;

	public AgentLauncher(String... args) throws ClassNotFoundException, ClassCastException, InstantiationException, IllegalAccessException {
	    this.init(args);
	}
	
	private void init(String... args) throws ClassNotFoundException, ClassCastException, InstantiationException, IllegalAccessException {
	    this.initSystem();
		this.config = ConfigInitializer.getConfig(args);
        this.initConnector();
	}
	
	private void initSystem() {
	    //register rescue system
		Registry.SYSTEM_REGISTRY.registerEntityFactory(StandardEntityFactory.INSTANCE);
		Registry.SYSTEM_REGISTRY.registerMessageFactory(StandardMessageFactory.INSTANCE);
		Registry.SYSTEM_REGISTRY.registerPropertyFactory(StandardPropertyFactory.INSTANCE);
	}

	private void initConnector() throws ClassNotFoundException, ClassCastException, InstantiationException, IllegalAccessException {
		//load AbstractLoader
		URLClassLoader classLoader = (URLClassLoader)this.getClass().getClassLoader();
		Class c = classLoader.loadClass(this.config.getValue(ConfigKey.KEY_LOADER_CLASS));
		this.loader = (AbstractLoader)c.newInstance();
		// set connectors
		this.connectors = new ArrayList<>();
		//platoon
		this.registerConnector(new ConnectorAmbulanceTeam());
		this.registerConnector(new ConnectorFireBrigade());
		this.registerConnector(new ConnectorPoliceForce());
		//office
		this.registerConnector(new ConnectorAmbulanceCentre());
		this.registerConnector(new ConnectorFireStation());
		this.registerConnector(new ConnectorPoliceOffice());
	}

	public void registerConnector(Connector connector) {
		this.connectors.add(connector);
	}

	public void start() {
		String host = this.config.getValue(Constants.KERNEL_HOST_NAME_KEY, Constants.DEFAULT_KERNEL_HOST_NAME);
		int port = this.config.getIntValue(Constants.KERNEL_PORT_NUMBER_KEY, Constants.DEFAULT_KERNEL_PORT_NUMBER);
		ComponentLauncher launcher = new TCPComponentLauncher(host, port, this.config);
		System.out.println("[START ] Connect Server (host:" + host + ", port:" + port + ")");
		
		for (Connector connector : this.connectors) {
			connector.connect(launcher, this.config, loader);
		}
		System.out.println("[END   ] Success Connect Server");

		if (this.config.getBooleanValue(ConfigKey.KEY_PRECOMPUTE, false)) {
			System.exit(0);
		}
	}
}
