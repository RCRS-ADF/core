package adf.agent.precompute;

import adf.agent.info.ScenarioInfo;

import javax.swing.text.Style;
import java.util.*;
import java.util.function.BooleanSupplier;

public class DebugDataSet {

    private ScenarioInfo scenarioInfo;

    private Map<String, Integer> intValues;
    private Map<String, Double>  doubleValues;
    private Map<String, String>  stringValues;
    private Map<String, Boolean> boolValues;

    private Map<String, List<Integer>> intLists;
    private Map<String, List<Double>>  doubleLists;
    private Map<String, List<String>>  stringLists;
    private Map<String, List<Boolean>> boolLists;

    public DebugDataSet(ScenarioInfo scenarioInfo) {
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

    public void clear() {
        this.intValues.clear();
        this.doubleValues.clear();
        this.stringValues.clear();
        this.boolValues.clear();

        this.intLists.clear();
        this.doubleLists.clear();
        this.stringLists.clear();
        this.boolLists.clear();
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

    public List<Integer> getIntegerList(String name) {
        return this.getIntegerList(name, null);
    }

    public List<Integer> getIntegerList(String name, List<Integer> defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            List<Integer> value = this.intLists.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    public List<Double> getDoubleList(String name) {
        return this.getDoubleList(name, null);
    }

    public List<Double> getDoubleList(String name, List<Double> defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            List<Double> value = this.doubleLists.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    public List<String> getStringList(String name) {
        return this.getStringList(name, null);
    }

    public List<String> getStringList(String name, List<String> defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            List<String> value = this.stringLists.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    public List<Boolean> getBooleanList(String name) {
        return this.getBooleanList(name, null);
    }

    public List<Boolean> getBooleanList(String name, List<Boolean> defaultValue) {
        if(this.scenarioInfo.isDebugMode()) {
            List<Boolean> value = this.boolLists.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Integer setInteger(String name, int value)  {
        return this.intValues.put(name, value);
    }

    public Double setDouble(String name, double value) {
        return this.doubleValues.put(name, value);
    }

    public Boolean setBoolean(String name, boolean value) {
        return this.boolValues.put(name, value);
    }

    public String setString(String name, String value) {
        return this.stringValues.put(name, value);
    }

    public List<Integer> setIntegerList(String name, List<Integer> value) {
        return this.intLists.put(name, value);
    }

    public List<Double> setDoubleList(String name, List<Double> value) {
        return this.doubleLists.put(name, value);
    }

    public List<Boolean> setBooleanList(String name, List<Boolean> value) {
        return this.boolLists.put(name, value);
    }

    public List<String> setStringList(String name, List<String> value) {
        return this.stringLists.put(name, value);
    }
}
