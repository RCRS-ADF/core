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

public abstract class TargetSelector<E extends StandardEntity> extends AbstractModule {

    public TargetSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData debugData) {
        super(ai, wi, si, moduleManager, debugData);
    }

    public abstract EntityID getTarget();

    @SuppressWarnings("unchecked")
    public E getTargetEntity() {
        return (E)this.worldInfo.getEntity(this.getTarget());
    }

    @Override
    public TargetSelector<E> precompute(PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Override
    public TargetSelector<E> resume(PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Override
    public TargetSelector<E> preparate() {
        super.preparate();
        return this;
    }

    @Override
    public TargetSelector<E> updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract TargetSelector<E> calc();

}
