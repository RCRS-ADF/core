package adf.component.module.algorithm;

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
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Clustering extends AbstractModule{

    public Clustering(@Nonnull AgentInfo ai, @Nonnull WorldInfo wi, @Nonnull ScenarioInfo si, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    @Nonnegative
    public abstract int getClusterNumber();

    public abstract int getClusterIndex(@Nonnull StandardEntity entity);

    public abstract int getClusterIndex(@Nonnull EntityID id);

    @CheckReturnValue
    public abstract Collection<StandardEntity> getClusterEntities(int index);

    @CheckReturnValue
    public abstract Collection<EntityID> getClusterEntityIDs(int index);

    @Nonnull
    public List<Collection<StandardEntity>> getAllClusterEntities() {
        int number = this.getClusterNumber();
        List<Collection<StandardEntity>> result = new ArrayList<>(number);
        for(int i = 0; i < number; i++) {
            result.add(i, this.getClusterEntities(i));
        }
        return result;
    }

    @Nonnull
    public List<Collection<EntityID>> getAllClusterEntityIDs() {
        int number = this.getClusterNumber();
        List<Collection<EntityID>> result = new ArrayList<>(number);
        for(int i = 0; i < number; i++) {
            result.add(i, this.getClusterEntityIDs(i));
        }
        return result;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public Clustering precompute(@Nonnull PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public Clustering resume(@Nonnull PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public Clustering preparate() {
        super.preparate();
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public Clustering updateInfo(@Nonnull MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Nonnull
    @Override
    public abstract Clustering calc();
}
