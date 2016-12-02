package adf.agent.module;

import adf.agent.config.ModuleConfig;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.component.extaction.CommandExecutor;
import adf.component.extaction.ExtAction;
import adf.component.module.AbstractModule;
import rescuecore2.config.NoSuchConfigOptionException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ModuleManager
{
    private Map<String, AbstractModule> moduleMap;
    private Map<String, ExtAction> actionMap;
    private Map<String, CommandExecutor> executorMap;

    private AgentInfo agentInfo;
    private WorldInfo worldInfo;
    private ScenarioInfo scenarioInfo;

    private ModuleConfig moduleConfig;

    private DevelopData developData;

    public ModuleManager(@Nonnull AgentInfo agentInfo, @Nonnull WorldInfo worldInfo, @Nonnull ScenarioInfo scenarioInfo, @Nonnull ModuleConfig moduleConfig, @Nonnull DevelopData developData) {
        this.agentInfo = agentInfo;
        this.worldInfo = worldInfo;
        this.scenarioInfo = scenarioInfo;
        this.moduleConfig = moduleConfig;
        this.developData = developData;
        this.moduleMap = new HashMap<>();
        this.actionMap = new HashMap<>();
        this.executorMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public final <T extends AbstractModule> T getModule(@Nonnull String moduleName, @Nullable String defaultClassName) {
        String className = moduleName;
        try
        { className = this.moduleConfig.getValue(moduleName); }
        catch (NoSuchConfigOptionException ignored) { }

        try {
            Class<?> moduleClass;
            try {
                moduleClass = Class.forName(className);
            }catch (ClassNotFoundException | NullPointerException e) {
                className = defaultClassName;
                moduleClass = Class.forName(className);
            }

            AbstractModule instance = this.moduleMap.get(className);
            if(instance != null) {
                return (T)instance;
            }

            if (AbstractModule.class.isAssignableFrom(moduleClass)) {
                instance = this.getModule((Class<AbstractModule>) moduleClass);
                this.moduleMap.put(className, instance);
                return (T)instance;
            }
        }catch (ClassNotFoundException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Module name is not found : " + className);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public final <T extends AbstractModule> T getModule(@Nonnull String moduleName) {
        return this.getModule(moduleName, "");
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    private AbstractModule getModule(@Nonnull Class<AbstractModule> moduleClass) {
        try {
            Constructor<AbstractModule> constructor = moduleClass.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class, DevelopData.class);
            AbstractModule instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this, this.developData);
            this.moduleMap.put(moduleClass.getCanonicalName(), instance);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public final ExtAction getExtAction(String actionName, String defaultClassName) {
        String className = actionName;
        try
        { className = this.moduleConfig.getValue(actionName); }
        catch (NoSuchConfigOptionException ignored) { }

        try {
            Class<?> actionClass;
            try {
                actionClass = Class.forName(className);
            }catch (ClassNotFoundException | NullPointerException e) {
                className = defaultClassName;
                actionClass = Class.forName(className);
            }

            ExtAction instance = this.actionMap.get(className);
            if(instance != null) {
                return instance;
            }

            if (ExtAction.class.isAssignableFrom(actionClass)) {
                instance = this.getExtAction((Class<ExtAction>) actionClass);
                this.actionMap.put(className, instance);
                return instance;
            }
        }catch (ClassNotFoundException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("ExtAction name is not found : " + className);
    }

    @SuppressWarnings("unchecked")
    public final ExtAction getExtAction(String actionName) {
        return getExtAction(actionName, "");
    }

    @SuppressWarnings("unchecked")
    private ExtAction getExtAction(Class<ExtAction> actionClass) {
        try {
            Constructor<ExtAction> constructor = actionClass.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class, DevelopData.class);
            ExtAction instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this, this.developData);
            this.actionMap.put(actionClass.getCanonicalName(), instance);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public final CommandExecutor getCommandExecutor(String executorName, String defaultClassName) {
        String className = executorName;
        try
        { className = this.moduleConfig.getValue(executorName); }
        catch (NoSuchConfigOptionException ignored) { }

        try {
            Class<?> actionClass;
            try {
                actionClass = Class.forName(className);
            }catch (ClassNotFoundException | NullPointerException e) {
                className = defaultClassName;
                actionClass = Class.forName(className);
            }

            CommandExecutor instance = this.executorMap.get(className);
            if(instance != null) {
                return instance;
            }

            if (ExtAction.class.isAssignableFrom(actionClass)) {
                instance = this.getCommandExecutor((Class<CommandExecutor>) actionClass);
                this.executorMap.put(className, instance);
                return instance;
            }
        }catch (ClassNotFoundException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("CommandExecutor name is not found : " + className);
    }

    @SuppressWarnings("unchecked")
    public final CommandExecutor getCommandExecutor(String executorName) {
        return getCommandExecutor(executorName, "");
    }

    @SuppressWarnings("unchecked")
    private CommandExecutor getCommandExecutor(Class<CommandExecutor> actionClass) {
        try {
            Constructor<CommandExecutor> constructor = actionClass.getConstructor(AgentInfo.class, WorldInfo.class, ScenarioInfo.class, ModuleManager.class, DevelopData.class);
            CommandExecutor instance = constructor.newInstance(this.agentInfo, this.worldInfo, this.scenarioInfo, this, this.developData);
            this.executorMap.put(actionClass.getCanonicalName(), instance);
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
