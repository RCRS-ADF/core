package adf.launcher;

import adf.launcher.option.*;
import rescuecore2.config.Config;
import rescuecore2.config.ConfigException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigInitializer
{
    public static final File DEFAULT_PATH = new File(System.getProperty("user.dir"), "config");
    
    public static Config getConfig(String... args)
    {
        Config commandLine = analysis(args);
        try {
            if(DEFAULT_PATH.exists())
            {
                Config config = new Config(DEFAULT_PATH);
                config.merge(commandLine);
                return config;
            }
        }
        catch (ConfigException e) {
            e.printStackTrace();
        }
        return commandLine;
    }
    
    public static Config analysis(String... args)
    {
		if(args.length > 0)
        {
            Config config = new Config();
            //set load target
            config.setValue(ConfigKey.KEY_LOADER_CLASS, args[0]);
            //set option
            Map<String, Option> options = initOption();
            for (int i = 1; i < args.length; i++)
            {
                Option option = options.get(args[i]);
                if (option != null)
                {
                    String value = null;
                    if (option.hasValue())
                    {
                        if (i < args.length)
                        { value = args[++i]; }
                        else
                        { throw new RuntimeException("Not specified " + option.getKey() + " value!!"); }
                    }
                    option.setValue(config, value);
                }
            }
            return config;
        }
        else
        {
            throw new RuntimeException("Not specified a Loader Class!!");
        }
    }
    
    private static Map<String, Option> initOption()
    {
		Map<String, Option> options = new HashMap<>();
		registerOption(options, new OptionServer());
		registerOption(options, new OptionHost());
		registerOption(options, new OptionPrecompute());
		registerOption(options, new OptionModuleConfig());
		registerOption(options, new OptionDebug());
        registerOption(options, new OptionDebugData());
        registerOption(options, new OptionDebugFile());
		registerOption(options, new OptionTeam());
		registerOption(options, new OptionAmbulanceTeam());
		registerOption(options, new OptionFireBrigade());
		registerOption(options, new OptionPoliceForce());
		registerOption(options, new OptionAmbulanceCentre());
		registerOption(options, new OptionFireStation());
		registerOption(options, new OptionPoliceOffice());
		return options;
	}

	private static void registerOption(Map<String, Option> options, Option option)
    {
		options.put(option.getKey(), option);
	}
}
