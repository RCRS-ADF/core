package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionDebugFile extends Option {
    @Override
    public String getKey()
    {
        return "-df";
    }

    @Override
    public void setValue(Config config, String[] datas) {
        if (datas.length == 2) {
            config.setValue(ConfigKey.KEY_DEBUG_DATA_FILE_NAME, datas[1]);
        }
    }
}
