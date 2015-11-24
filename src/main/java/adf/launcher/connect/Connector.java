package adf.launcher.connect;

import adf.component.AbstractLoader;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;

public interface Connector {
    void connect(ComponentLauncher launcher, Config config, AbstractLoader loader);
}
