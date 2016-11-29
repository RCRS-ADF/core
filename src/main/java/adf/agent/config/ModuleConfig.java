package adf.agent.config;

import rescuecore2.config.Config;
import rescuecore2.config.ConfigException;

import javax.annotation.Nonnull;
import java.io.File;

public class ModuleConfig extends Config
{
    public static final String DEFAULT_CONFIG_FILE_NAME = "config" +File.separator + "module.cfg";

    public ModuleConfig(@Nonnull String fileName) throws ConfigException
    {
        super(new File(fileName));
    }
}
