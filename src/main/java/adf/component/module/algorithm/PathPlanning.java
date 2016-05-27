package adf.component.module.algorithm;


import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.precompute.PrecomputeData;
import adf.component.module.AbstractModule;
import rescuecore2.worldmodel.EntityID;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class PathPlanning extends AbstractModule{

    public PathPlanning(AgentInfo ai, WorldInfo wi, ScenarioInfo si) {
        super(ai, wi, si);
    }

    public abstract List<EntityID> getResult();

    public abstract PathPlanning setFrom(EntityID id);

    public abstract PathPlanning setDestination(Collection<EntityID> targets);

    public PathPlanning setDestination(EntityID... targets) {
        return this.setDestination(Arrays.asList(targets));
    }

    @Override
    public abstract PathPlanning precompute(PrecomputeData precomputeData);

    @Override
    public abstract PathPlanning resume(PrecomputeData precomputeData);

    @Override
    public abstract PathPlanning preparate();

    @Override
    public PathPlanning updateInfo(MessageManager messageManager){
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract PathPlanning calc();
}