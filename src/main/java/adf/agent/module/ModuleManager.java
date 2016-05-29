package adf.agent.module;

import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.component.module.AbstractModule;
import rescuecore2.config.Config;
import rescuecore2.config.ConfigException;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    private static final File moduleConfigPath = new File(System.getProperty("user.dir"), "config" + File.separator + "module.cfg");

    private Map<String, AbstractModule> moduleMap;

    private AgentInfo agentInfo;
    private WorldInfo worldInfo;
    private ScenarioInfo scenarioInfo;

    private Config config;
    //private boolean debugMode;

    public ModuleManager(AgentInfo ai, WorldInfo wi, ScenarioInfo si) {
        this.agentInfo = ai;
        this.worldInfo = wi;
        this.scenarioInfo = si;
        this.moduleMap = new HashMap<>();
        try {
            this.config = new Config(moduleConfigPath);
        } catch (ConfigException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public final AbstractModule getModuleInstance(String moduleClassStr) throws ClassNotFoundException {
        AbstractModule instance = this.moduleMap.get(moduleClassStr);
        if(instance != null) {
            return instance;
        }
        Class<?> moduleClass = Class.forName(moduleClassStr);
        if(AbstractModule.class.isAssignableFrom(moduleClass)) {
            return this.getModuleInstance((Class<AbstractModule>) moduleClass);
        }
        throw new ClassCastException(moduleClassStr);
    }

    @SuppressWarnings("unchecked")
    private AbstractModule getModuleInstance(Class<AbstractModule> moduleClass) throws ClassNotFoundException {
        //default Module
        String defaultModuleStr = this.config.getValue(moduleClass.getCanonicalName());
        if(defaultModuleStr != null) {
            try {
                Class<?> tmpClass = Class.forName(defaultModuleStr);
                if(AbstractModule.class.isAssignableFrom(tmpClass)) {
                    Class<AbstractModule> defaultClass = (Class<AbstractModule>) tmpClass;
                    Constructor<AbstractModule> constructor = defaultClass.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class);
                    AbstractModule instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this);
                    this.moduleMap.put(moduleClass.getCanonicalName(), instance);
                    this.moduleMap.put(defaultModuleStr, instance);
                    return instance;
                }
                else {
                    throw new ClassCastException(defaultModuleStr);
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            //other module
            try {
                Constructor<AbstractModule> constructor = moduleClass.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class);
                AbstractModule instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this);
                this.moduleMap.put(moduleClass.getCanonicalName(), instance);
                return instance;

            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        throw new NullPointerException();
    }



}
