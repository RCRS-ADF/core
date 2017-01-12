package adf.component.module.complex;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import adf.component.module.AbstractModule;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.EntityID;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class TargetDetector<E extends StandardEntity> extends AbstractModule {

    public TargetDetector(@Nonnull AgentInfo ai, @Nonnull WorldInfo wi, @Nonnull ScenarioInfo si, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    @CheckReturnValue
    public abstract EntityID getTarget();

    @Nonnull
    @Override
    public abstract TargetDetector<E> calc();

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public TargetDetector<E> precompute(@Nonnull PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public TargetDetector<E> resume(@Nonnull PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public TargetDetector<E> preparate() {
        super.preparate();
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public TargetDetector<E> updateInfo(@Nonnull MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }
}