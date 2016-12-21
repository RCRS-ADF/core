package adf.launcher.connect;

import adf.component.AbstractLoader;
import rescuecore2.components.ComponentLauncher;
import rescuecore2.config.Config;

import javax.annotation.Nonnull;

public abstract class Connector
{
    int connected = 0;

    public abstract void connect(@Nonnull ComponentLauncher launcher, @Nonnull Config config, @Nonnull AbstractLoader loader);

    public int getCountConnected()
    {
        return connected;
    }
}
