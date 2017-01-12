package adf.launcher.option;

import rescuecore2.config.Config;

import javax.annotation.Nonnull;

public abstract class Option
{
    public abstract boolean hasValue();
    @Nonnull
    public abstract String getKey();
    public abstract void setValue(@Nonnull Config config, @Nonnull String data);
}
