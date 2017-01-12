package adf.component.module.algorithm;


import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import adf.component.module.AbstractModule;
import rescuecore2.misc.Pair;
import rescuecore2.worldmodel.EntityID;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class PathPlanning extends AbstractModule{

    public PathPlanning(@Nonnull AgentInfo ai, @Nonnull WorldInfo wi, @Nonnull ScenarioInfo si, @Nonnull ModuleManager moduleManager, @Nonnull DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    @CheckReturnValue
    public abstract List<EntityID> getResult();

    @Nonnull
    public abstract PathPlanning setFrom(@Nonnull EntityID id);

    @Nonnull
    public abstract PathPlanning setDestination(@Nonnull Collection<EntityID> targets);

    @Nonnull
    public PathPlanning setDestination(EntityID... targets) {
        return this.setDestination(Arrays.asList(targets));
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public PathPlanning precompute(@Nonnull PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public PathPlanning resume(@Nonnull PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public PathPlanning preparate() {
        super.preparate();
        return this;
    }

    @Nonnull
    @OverridingMethodsMustInvokeSuper
    @Override
    public PathPlanning updateInfo(@Nonnull MessageManager messageManager){
        super.updateInfo(messageManager);
        return this;
    }

    @Nonnull
    @Override
    public abstract PathPlanning calc();

    public double getDistance()
    {
        double sum = 0.0;
        List<EntityID> path = getResult();
        if (path == null || path.size() <= 1)
        { return sum; }

        Pair<Integer, Integer> prevPoint = null;
        for (EntityID id : path)
        {
            Pair<Integer, Integer> point = worldInfo.getLocation(worldInfo.getEntity(id));
            if (prevPoint != null)
            {
                int x = prevPoint.first() - point.first();
                int y = prevPoint.second() - point.second();
                sum += x*x + y*y;
            }
            prevPoint = point;
        }

        return Math.sqrt(sum);
    }

    // Alias
    public double getDistance(EntityID from, EntityID dest)
    { return this.setFrom(from).setDestination(dest).calc().getDistance(); }

    @CheckReturnValue
    public List<EntityID> getResult(EntityID from, EntityID dest)
    { return this.setFrom(from).setDestination(dest).calc().getResult(); }
}
