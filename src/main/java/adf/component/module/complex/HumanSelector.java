package adf.component.module.complex;


import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.Human;

public abstract class HumanSelector extends TargetSelector<Human> {

    public HumanSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si) {
        super(ai, wi, si);
    }

    @Override
    public abstract HumanSelector precompute(PrecomputeData precomputeData);

    @Override
    public abstract HumanSelector resume(PrecomputeData precomputeData);

    @Override
    public abstract HumanSelector preparate();

    @Override
    public HumanSelector updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract HumanSelector calc();
}
