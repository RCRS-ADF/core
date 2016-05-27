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

    private Map<Class<? extends AbstractModule>, AbstractModule> moduleMap;

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
    public final AbstractModule getModuleInstance(String moduleClass) throws ClassNotFoundException {
        Class<?> c = Class.forName(moduleClass);
        if(AbstractModule.class.isAssignableFrom(c)) {
            return this.getModuleInstance((Class<AbstractModule>) c);
        }
        throw new ClassCastException(moduleClass);
    }

    @SuppressWarnings("unchecked")
    public final AbstractModule getModuleInstance(Class<AbstractModule> moduleClass) throws ClassNotFoundException {
        if(moduleClass == null) {
            throw new NullPointerException();
        }
        AbstractModule instance = this.moduleMap.get(moduleClass);
        if(instance != null) {
            return instance;
        }
        //default Module
        String defaultModule = this.config.getValue(moduleClass.getCanonicalName());
        if(defaultModule != null) {
            try {
                Class<?> c = Class.forName(defaultModule);
                if(AbstractModule.class.isAssignableFrom(c)) {
                    Class<AbstractModule> clazz = (Class<AbstractModule>) c;
                    Constructor<AbstractModule> constructor = clazz.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class);
                    instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this);
                    this.moduleMap.put(moduleClass, instance);
                    this.moduleMap.put(clazz, instance);
                    return instance;
                }
                else {
                    throw new ClassCastException(defaultModule);
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            //other module
            try {
                Constructor<AbstractModule> constructor = moduleClass.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class);
                instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this);
                this.moduleMap.put(moduleClass, instance);
                return instance;

            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        throw new NullPointerException();
    }



}
