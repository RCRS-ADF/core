package adf.agent.module;

import adf.agent.config.ModuleConfig;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.component.extaction.ExtAction;
import adf.component.module.AbstractModule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ModuleManager
{
    private Map<String, AbstractModule> moduleMap;
    private Map<String, ExtAction> actionMap;

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
        this.actionMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public final <T extends AbstractModule> T getModule(String moduleName) {
        AbstractModule instance = this.moduleMap.get(moduleName);
        if(instance != null) {
            return (T)instance;
        }
        try {
            String defaultModuleStr = this.moduleConfig.getValue(moduleName);
            if (defaultModuleStr != null) {
                Class<?> moduleClass = Class.forName(defaultModuleStr);
                if (AbstractModule.class.isAssignableFrom(moduleClass)) {
                    instance = this.getModule((Class<AbstractModule>) moduleClass);
                    this.moduleMap.put(moduleName, instance);
                    return (T)instance;
                }
            } else {
                Class<?> moduleClass = Class.forName(moduleName);
                if (AbstractModule.class.isAssignableFrom(moduleClass)) {
                    return (T) this.getModule((Class<AbstractModule>) moduleClass);
                }
            }
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Module name is not found : " + moduleName);
    }

    @SuppressWarnings("unchecked")
    private AbstractModule getModule(Class<AbstractModule> moduleClass) {
        try {
            Constructor<AbstractModule> constructor = moduleClass.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class);
            AbstractModule instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this);
            this.moduleMap.put(moduleClass.getCanonicalName(), instance);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public final ExtAction getExtAction(String actionName) {
        ExtAction instance = this.actionMap.get(actionName);
        if(instance != null) {
            return instance;
        }
        try {
            String defaultStr = this.moduleConfig.getValue(actionName);
            if (defaultStr != null) {
                Class<?> actionClass = Class.forName(defaultStr);
                if (ExtAction.class.isAssignableFrom(actionClass)) {
                    instance = this.getExtAction((Class<ExtAction>) actionClass);
                    this.actionMap.put(actionName, instance);
                    return instance;
                }
            } else {
                Class<?> actionClass = Class.forName(actionName);
                if (ExtAction.class.isAssignableFrom(actionClass)) {
                    return this.getExtAction((Class<ExtAction>) actionClass);
                }
            }
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("ExtAction name is not found : " + actionName);
    }

    @SuppressWarnings("unchecked")
    private ExtAction getExtAction(Class<ExtAction> actionClass) {
        try {
            Constructor<ExtAction> constructor = actionClass.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class);
            ExtAction instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this);
            this.actionMap.put(actionClass.getCanonicalName(), instance);
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
