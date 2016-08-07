package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionDebugData extends Option {

    @Override
    public String getKey()
    {
        return "-dd";
    }

    @Override
    public void setValue(Config config, String[] datas) {
        if (datas.length >= 3) {
            config.setValue(ConfigKey.KEY_MODULE_CONFIG_FILE_NAME, datas[1]);
        }
    }
}