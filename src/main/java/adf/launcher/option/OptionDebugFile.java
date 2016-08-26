package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionDebugFile extends Option
{
    @Override
    public boolean hasValue()
    {
        return true;
    }

    @Override
    public String getKey()
    {
        return "-df";
    }

    @Override
    public void setValue(Config config, String data)
    {
        config.setValue(ConfigKey.KEY_DEBUG_DATA_FILE_NAME, data);
    }
}
