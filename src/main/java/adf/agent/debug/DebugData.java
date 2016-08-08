package adf.agent.debug;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public final class DebugData {
    public static final String DEFAULT_FILE_NAME = "debug" +File.separator + "debug_data.txt";
    private static final String DATA_REGEX  = ":";

    private boolean debugFlag;

    private Map<String, Integer> intValues;
    private Map<String, Double>  doubleValues;
    private Map<String, String>  stringValues;
    private Map<String, Boolean> boolValues;

    private Map<String, List<Integer>> intLists;
    private Map<String, List<Double>>  doubleLists;
    private Map<String, List<String>>  stringLists;
    private Map<String, List<Boolean>> boolLists;

    public DebugData(boolean debugFlag, String debugDataFileName, List<String> rawData) {
        this.debugFlag = debugFlag;

        this.intValues = new HashMap<>();
        this.doubleValues = new HashMap<>();
        this.stringValues = new HashMap<>();
        this.boolValues = new HashMap<>();

        this.intLists = new HashMap<>();
        this.doubleLists = new HashMap<>();
        this.stringLists = new HashMap<>();
        this.boolLists = new HashMap<>();
        this.setRawData(rawData);
    }

    public boolean isDebugMode() {
        return this.debugFlag;
    }

    public Integer getInteger(String name, int... defaultValue) {
        if(this.debugFlag) {
            Integer value = this.intValues.get(name);
            if(value == null) {
                String rawData = this.stringValues.get(name);
                if(rawData != null && !rawData.equals("")) {
                    value = Integer.valueOf(rawData);
                }
                if(value != null) {
                    this.intValues.put(name, value);
                }
            }
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public Double getDouble(String name, double... defaultValue) {
        if(this.debugFlag) {
            Double value = this.doubleValues.get(name);
            if(value == null) {
                String rawData = this.stringValues.get(name);
                if(rawData != null && !rawData.equals("")) {
                    value = Double.valueOf(rawData);
                }
                if(value != null) {
                    this.doubleValues.put(name, value);
                }
            }
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public Boolean getBoolean(String name, boolean... defaultValue) {
        if(this.debugFlag) {
            Boolean value = this.boolValues.get(name);
            if(value == null) {
                String rawData = this.stringValues.get(name);
                if(rawData != null && !rawData.equals("")) {
                    value = Boolean.valueOf(rawData);
                }
                if(value != null) {
                    this.boolValues.put(name, value);
                }
            }
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public String getString(String name, String... defaultValue) {
        if(this.debugFlag) {
            String value = this.stringValues.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null && defaultValue.length > 0) ? defaultValue[0] : null;
    }

    public List<Integer> getIntegerList(String name) {
        return this.getIntegerList(name, null);
    }

    public List<Integer> getIntegerList(String name, List<Integer> defaultValue) {
        if(this.debugFlag) {
            List<Integer> value = this.intLists.get(name);
            if(value == null || value.isEmpty()) {
                List<String> rawData = this.stringLists.get(name);
                if(rawData != null) {
                    value = new ArrayList<>();
                    for(String str : rawData) {
                        value.add(Integer.valueOf(str));
                    }
                }
                if(value != null) {
                    this.intLists.put(name, value);
                }
            }
            if(value != null) return value;
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    public List<Double> getDoubleList(String name) {
        return this.getDoubleList(name, null);
    }

    public List<Double> getDoubleList(String name, List<Double> defaultValue) {
        if(this.debugFlag) {
            List<Double> value = this.doubleLists.get(name);
            if(value == null || value.isEmpty()) {
                List<String> rawData = this.stringLists.get(name);
                if(rawData != null) {
                    value = new ArrayList<>();
                    for(String str : rawData) {
                        value.add(Double.valueOf(str));
                    }
                }
                if(value != null) {
                    this.doubleLists.put(name, value);
                }
            }
            if(value != null) return value;
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    public List<String> getStringList(String name) {
        return this.getStringList(name, null);
    }

    public List<String> getStringList(String name, List<String> defaultValue) {
        if(this.debugFlag) {
            List<String> value = this.stringLists.get(name);
            if(value != null) return value;
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    public List<Boolean> getBooleanList(String name) {
        return this.getBooleanList(name, null);
    }

    public List<Boolean> getBooleanList(String name, List<Boolean> defaultValue) {
        if(this.debugFlag) {
            List<Boolean> value = this.boolLists.get(name);
            if(value == null || value.isEmpty()) {
                List<String> rawData = this.stringLists.get(name);
                if(rawData != null) {
                    value = new ArrayList<>();
                    for(String str : rawData) {
                        value.add(Boolean.valueOf(str));
                    }
                }
                if(value != null) {
                    this.boolLists.put(name, value);
                }
            }
            if(value != null) return value;
        }
        return (defaultValue != null) ? defaultValue : null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setRawData(List<String> rawData) {
        for(String str : rawData) {
            String[] array = str.split(DATA_REGEX);
            if(array.length < 2) continue;
            if(array.length == 2) {
                this.setString(array[0], array[1]);
            } else {
                List<String> list = new ArrayList<>(array.length);
                Collections.addAll(list, array);
                list.remove(0);
                this.setStringList(array[0], list);
            }
        }
    }

    private void setFileData(String debugDataFileName) {
        if(debugDataFileName == null || debugDataFileName.equals("")) return;
        try {
            for(String str : Files.readAllLines((new File(debugDataFileName)).toPath())){
                String[] array = str.split(DATA_REGEX);
                if(array.length < 2) continue;
                if(array.length == 2) {
                    this.setString(array[0], array[1]);
                } else {
                    List<String> list = new ArrayList<>(array.length);
                    Collections.addAll(list, array);
                    list.remove(0);
                    this.setStringList(array[0], list);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
