package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionDebugData extends Option
{
    // from rescuecore2.config.Config
    private static final String DATA_DELIMITER = " |,";

    // -dd "[name]:[value] [name]:[value],[value]"
    // name1:value1|,name2:value1:value2

    @Override
    public boolean hasValue()
    {
        return true;
    }

    @Override
    public String getKey()
    {
        return "-dd";
    }

    @Override
    public void setValue(Config config, String data)
    {
        StringBuilder rawDebugData = new StringBuilder(config.getValue(ConfigKey.KEY_DEVELOP_DATA, ""));
        rawDebugData.append(DATA_DELIMITER);
        rawDebugData.append(data);
        config.setValue(ConfigKey.KEY_DEVELOP_DATA, rawDebugData.toString());
    }
}
