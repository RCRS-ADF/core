package adf.agent.develop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public final class DevelopData
{
    public static final String DEFAULT_FILE_NAME = System.getProperty("user.dir") + "data" + File.separator + "develop.json";

    private boolean developFlag;

    private Map<String, Integer> intValues;
    private Map<String, Double> doubleValues;
    private Map<String, String> stringValues;
    private Map<String, Boolean> boolValues;

    private Map<String, List<Integer>> intLists;
    private Map<String, List<Double>> doubleLists;
    private Map<String, List<String>> stringLists;
    private Map<String, List<Boolean>> boolLists;

    public DevelopData(boolean developFlag, String debugDataFileName, String rawData)
    {
        this.developFlag = developFlag;

        this.intValues = new HashMap<>();
        this.doubleValues = new HashMap<>();
        this.stringValues = new HashMap<>();
        this.boolValues = new HashMap<>();

        this.intLists = new HashMap<>();
        this.doubleLists = new HashMap<>();
        this.stringLists = new HashMap<>();
        this.boolLists = new HashMap<>();
        this.setRawData(rawData);
        this.setFileData(debugDataFileName);
    }

    public boolean isDevelopMode()
    {
        return this.developFlag;
    }

    public Integer getInteger(String name, int... defaultValue)
    {
        if (this.developFlag)
        {
            Integer value = this.intValues.get(name);
            if (value == null)
            {
                String rawData = this.stringValues.get(name);
                if (rawData != null && !rawData.equals(""))
                { value = Integer.valueOf(rawData); }
                if (value != null)
                { this.intValues.put(name, value); }
            }
            if (value != null) { return value; }
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public Double getDouble(String name, double... defaultValue)
    {
        if (this.developFlag)
        {
            Double value = this.doubleValues.get(name);
            if (value == null)
            {
                String rawData = this.stringValues.get(name);
                if (rawData != null && !rawData.equals(""))
                { value = Double.valueOf(rawData); }
                if (value != null)
                { this.doubleValues.put(name, value); }
            }
            if (value != null) { return value; }
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public Boolean getBoolean(String name, boolean... defaultValue)
    {
        if (this.developFlag)
        {
            Boolean value = this.boolValues.get(name);
            if (value == null)
            {
                String rawData = this.stringValues.get(name);
                if (rawData != null && !rawData.equals(""))
                { value = Boolean.valueOf(rawData); }
                if (value != null)
                { this.boolValues.put(name, value); }
            }
            if (value != null) { return value; }
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public String getString(String name, String... defaultValue)
    {
        if (this.developFlag)
        {
            String value = this.stringValues.get(name);
            if (value != null) { return value; }
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public List<Integer> getIntegerList(String name)
    {
        return this.getIntegerList(name, null);
    }

    public List<Integer> getIntegerList(String name, List<Integer> defaultValue)
    {
        if (this.developFlag)
        {
            List<Integer> value = this.intLists.get(name);
            if (value == null || value.isEmpty())
            {
                List<String> rawData = this.stringLists.get(name);
                if (rawData != null)
                {
                    value = new ArrayList<>();
                    for (String str : rawData)
                    { value.add(Integer.valueOf(str)); }
                }
                if (value != null)
                { this.intLists.put(name, value); }
            }
            if (value != null) { return value; }
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    public List<Double> getDoubleList(String name)
    {
        return this.getDoubleList(name, null);
    }

    public List<Double> getDoubleList(String name, List<Double> defaultValue)
    {
        if (this.developFlag)
        {
            List<Double> value = this.doubleLists.get(name);
            if (value == null || value.isEmpty())
            {
                List<String> rawData = this.stringLists.get(name);
                if (rawData != null)
                {
                    value = new ArrayList<>();
                    for (String str : rawData)
                    { value.add(Double.valueOf(str)); }
                }
                if (value != null)
                { this.doubleLists.put(name, value); }
            }
            if (value != null) { return value; }
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    public List<String> getStringList(String name)
    {
        return this.getStringList(name, null);
    }

    public List<String> getStringList(String name, List<String> defaultValue)
    {
        if (this.developFlag)
        {
            List<String> value = this.stringLists.get(name);
            if (value != null) { return value; }
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    public List<Boolean> getBooleanList(String name)
    {
        return this.getBooleanList(name, null);
    }

    public List<Boolean> getBooleanList(String name, List<Boolean> defaultValue)
    {
        if (this.developFlag)
        {
            List<Boolean> value = this.boolLists.get(name);
            if (value == null || value.isEmpty())
            {
                List<String> rawData = this.stringLists.get(name);
                if (rawData != null)
                {
                    value = new ArrayList<>();
                    for (String str : rawData)
                    { value.add(Boolean.valueOf(str)); }
                }
                if (value != null)
                { this.boolLists.put(name, value); }
            }
            if (value != null) { return value; }
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setRawData(String rawData)
    {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> dataMap = new HashMap<>();

        try
        {
            dataMap = mapper.readValue(rawData, new TypeReference<Map<String, Object>>(){});
        }
        catch (IOException e)
        { e.printStackTrace(); }

        for (String key : dataMap.keySet())
        {
            Object object = dataMap.get(key);
            if (object instanceof List)
            {
                List<String> list = new ArrayList<>(((List)object).size());
                for (Object o : (List)object)
                { list.add(String.valueOf(o)); }
                this.setStringList(key, list);
            }
            else
            { this.setString(key, String.valueOf(object)); }
        }

        /*
        for (String str : rawData)
        {
            String[] array = str.split(DATA_REGEX);
            if (array.length < 2) continue;
            if (array.length == 2)
            {
                this.setString(array[0], array[1]);
            } else
            {
                List<String> list = new ArrayList<>(array.length);
                Collections.addAll(list, array);
                list.remove(0);
                this.setStringList(array[0], list);
            }
        }
        */
    }

    private void setFileData(String debugDataFileName)
    {
        if (debugDataFileName == null || debugDataFileName.equals("")) { return; }

        String rawData = "";

        try
        {
            rawData = Files.lines(
                    Paths.get(debugDataFileName),
                    Charset.forName("UTF-8")).collect(Collectors.joining(System.getProperty("line.separator")));
        }
        catch (IOException e)
        { e.printStackTrace(); }

        setRawData(rawData);


        /*
        try
        {
            Config config = new Config(new File(debugDataFileName));
            for (String key : config.getAllKeys())
            {
                List<String> value = config.getArrayValue(key);
                if (value.isEmpty()) continue;
                if (value.size() == 1)
                {
                    this.setString(key, value.get(0));
                } else
                {
                    this.setStringList(key, value);
                }
            }
        }
        catch (ConfigException e)
        {
            e.printStackTrace();
        }
        */
    }

    public void clear()
    {
        this.intValues.clear();
        this.doubleValues.clear();
        this.stringValues.clear();
        this.boolValues.clear();

        this.intLists.clear();
        this.doubleLists.clear();
        this.stringLists.clear();
        this.boolLists.clear();
    }

    public Integer setInteger(String name, int value)
    {
        return this.intValues.put(name, value);
    }

    public Double setDouble(String name, double value)
    {
        return this.doubleValues.put(name, value);
    }

    public Boolean setBoolean(String name, boolean value)
    {
        return this.boolValues.put(name, value);
    }

    public String setString(String name, String value)
    {
        return this.stringValues.put(name, value);
    }

    public List<Integer> setIntegerList(String name, List<Integer> value)
    {
        return this.intLists.put(name, value);
    }

    public List<Double> setDoubleList(String name, List<Double> value)
    {
        return this.doubleLists.put(name, value);
    }

    public List<Boolean> setBooleanList(String name, List<Boolean> value)
    {
        return this.boolLists.put(name, value);
    }

    public List<String> setStringList(String name, List<String> value)
    {
        return this.stringLists.put(name, value);
    }
}
