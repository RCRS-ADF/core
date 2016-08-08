package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

import java.util.ArrayList;
import java.util.List;

public class OptionDebugData extends Option {
    // from rescuecore2.config.Config
    private static final String ARRAY_REGEX = " |,";
    private static final String DATA_REGEX  = ":";

    @Override
    public String getKey()
    {
        return "-dd";
    }

    @Override
    public void setValue(Config config, String[] datas) {
        if (datas.length >= 3) {
            StringBuilder rawDebugData = new StringBuilder(config.getValue(ConfigKey.KEY_DEBUG_DATA, ""));
            int limit = datas.length - 1;
            rawDebugData.append(ARRAY_REGEX);
            for(int i = 1; i <= limit; i++) {
                rawDebugData.append(datas[i]);
                if(i < limit) rawDebugData.append(DATA_REGEX);
            }
            config.setValue(ConfigKey.KEY_DEBUG_DATA, rawDebugData.toString());
        }
    }
}