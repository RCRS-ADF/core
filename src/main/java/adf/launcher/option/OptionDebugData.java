package adf.launcher.option;

import adf.launcher.ConfigKey;
import rescuecore2.config.Config;

public class OptionDebugData extends Option
{
    // from rescuecore2.config.Config
    private static final String DATA_DELIMITER = " |,";
    private static final String INNER_DELIMITER  = ":";

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
        String[] splitedData = data.split(" ");
        for (String dataset : splitedData)
        {
            String[] nameAndValue = dataset.split(":");
            if (nameAndValue.length == 2)
            {
                StringBuilder rawDebugData = new StringBuilder(config.getValue(ConfigKey.KEY_DEVELOP_DATA, ""));
                rawDebugData.append(DATA_DELIMITER);
                rawDebugData.append(nameAndValue[0]);
                rawDebugData.append(INNER_DELIMITER);
                String[] innerSplitedData = nameAndValue[1].split(",");
                for(int i = 0; i < innerSplitedData.length; i++)
                {
                    rawDebugData.append(innerSplitedData[i]);
                    if((i +1) < innerSplitedData.length)
                    { rawDebugData.append(INNER_DELIMITER); }
                }
                config.setValue(ConfigKey.KEY_DEVELOP_DATA, rawDebugData.toString());
            }
        }
    }
}