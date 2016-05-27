package adf.component.module.complex;


import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.precompute.PrecomputeData;
import adf.component.module.AbstractModule;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.worldmodel.EntityID;

public abstract class TargetSelector<E extends StandardEntity> extends AbstractModule {

    public TargetSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si) {
        super(ai, wi, si);
    }

    public abstract EntityID getTarget();

    @Override
    public abstract TargetSelector<E> precompute(PrecomputeData precomputeData);

    @Override
    public abstract TargetSelector<E> resume(PrecomputeData precomputeData);

    @Override
    public abstract TargetSelector<E> preparate();

    @Override
    public TargetSelector<E> updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract TargetSelector<E> calc();

}
