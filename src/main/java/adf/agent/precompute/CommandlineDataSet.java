package adf.agent.precompute;

import adf.agent.info.ScenarioInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandlineDataSet {

    private ScenarioInfo scenarioInfo;

    private Map<String, Integer> intValues;
    private Map<String, Double>  doubleValues;
    private Map<String, String>  stringValues;
    private Map<String, Boolean> boolValues;

    public Map<String, List<Integer>> intLists;
    public Map<String, List<Double>> doubleLists;
    public Map<String, List<String>> stringLists;
    public Map<String, List<Boolean>> boolLists;

    public CommandlineDataSet(ScenarioInfo scenarioInfo) {
        this.scenarioInfo = scenarioInfo;

        this.intValues = new HashMap<>();
        this.doubleValues = new HashMap<>();
        this.stringValues = new HashMap<>();
        this.boolValues = new HashMap<>();

        this.intLists = new HashMap<>();
        this.doubleLists = new HashMap<>();
        this.stringLists = new HashMap<>();
        this.boolLists = new HashMap<>();
    }

    public Integer setInteger(String name, int value)  {
        if(this.scenarioInfo.isDebugMode()) {
            return this.intValues.put(name, value);
        }
        return null;
    }

    public Double setDouble(String name, double value) {
        if(this.scenarioInfo.isDebugMode()) {
            return this.doubleValues.put(name, value);
        }
        return null;
    }

    public Boolean setBoolean(String name, boolean value) {
        if(this.scenarioInfo.isDebugMode()) {
            return this.boolValues.put(name, value);
        }
        return null;
    }

    public String setString(String name, String value) {
        if(this.scenarioInfo.isDebugMode()) {
            return this.stringValues.put(name, value);
        }
        return null;
    }

    public Integer getInteger(String name, int... defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            Integer value = this.intValues.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public Double getDouble(String name, double... defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            Double value = this.doubleValues.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public Boolean getBoolean(String name, boolean... defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            Boolean value = this.boolValues.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public String getString(String name, String... defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            String value = this.stringValues.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public List<Integer> getIntegerList(String name, List<Integer>... defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            List<Integer> value = this.intLists.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public List<Double> getDoubleList(String name, List<Double>... defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            List<Double> value = this.doubleLists.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public List<String> getStringList(String name, List<String>... defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            List<String> value = this.stringLists.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public List<Boolean> getBooleanList(String name, List<Boolean>... defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            List<Boolean> value = this.boolLists.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }
}
