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

import javax.annotation.Nonnull;

/**
 * @deprecated change class name {@link TargetDetector}
 */
@Deprecated
public abstract class TargetSelector<E extends StandardEntity> extends TargetDetector<E> {

    public TargetSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    public abstract EntityID getTarget();

    @Nonnull
    @Override
    public TargetSelector<E> precompute(@Nonnull PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Nonnull
    @Override
    public TargetSelector<E> resume(@Nonnull PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Nonnull
    @Override
    public TargetSelector<E> preparate() {
        super.preparate();
        return this;
    }

    @Nonnull
    @Override
    public TargetSelector<E> updateInfo(@Nonnull MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Nonnull
    @Override
    public abstract TargetSelector<E> calc();
}
