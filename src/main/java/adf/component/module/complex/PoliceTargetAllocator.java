package adf.component.module.complex;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class PoliceTargetAllocator extends TargetAllocator {
    public PoliceTargetAllocator(@Nonnull AgentInfo ai, @Nonnull WorldInfo wi, @Nonnull ScenarioInfo si, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    @Nonnull
    @Override
    public abstract PoliceTargetAllocator calc();

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public PoliceTargetAllocator resume(@Nonnull PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public PoliceTargetAllocator preparate() {
        super.preparate();
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public PoliceTargetAllocator updateInfo(@Nonnull MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }
}
