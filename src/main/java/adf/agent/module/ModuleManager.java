package adf.agent.module;

import adf.agent.config.ModuleConfig;
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

public class ModuleManager
{
    private Map<String, AbstractModule> moduleMap;

    private AgentInfo agentInfo;
    private WorldInfo worldInfo;
    private ScenarioInfo scenarioInfo;

    private ModuleConfig moduleConfig;

    public ModuleManager(AgentInfo agentInfo, WorldInfo worldInfo, ScenarioInfo scenarioInfo, ModuleConfig moduleConfig) {
        this.agentInfo = agentInfo;
        this.worldInfo = worldInfo;
        this.scenarioInfo = scenarioInfo;
        this.moduleConfig = moduleConfig;
        this.moduleMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public final AbstractModule getModuleInstance(String moduleName) {
        AbstractModule instance = this.moduleMap.get(moduleName);
        if(instance != null) {
            return instance;
        }
        try {
            String defaultModuleStr = this.moduleConfig.getValue(moduleName);
            if (defaultModuleStr != null) {
                Class<?> moduleClass = Class.forName(defaultModuleStr);
                if (AbstractModule.class.isAssignableFrom(moduleClass)) {
                    instance = this.getModuleInstance((Class<AbstractModule>) moduleClass);
                    this.moduleMap.put(moduleName, instance);
                    return instance;
                }
            } else {
                Class<?> moduleClass = Class.forName(moduleName);
                if (AbstractModule.class.isAssignableFrom(moduleClass)) {
                    return this.getModuleInstance((Class<AbstractModule>) moduleClass);
                }
            }
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Module name is not found : " + moduleName);
    }

    @SuppressWarnings("unchecked")
    private AbstractModule getModuleInstance(Class<AbstractModule> moduleClass) {
        try {
            Constructor<AbstractModule> constructor = moduleClass.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class);
            AbstractModule instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this);
            this.moduleMap.put(moduleClass.getCanonicalName(), instance);
            return instance;

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public ModuleConfig getModuleConfig()
    {
        return this.moduleConfig;
    }
}
