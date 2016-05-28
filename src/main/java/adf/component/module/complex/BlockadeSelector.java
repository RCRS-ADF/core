package adf.component.module.complex;

import adf.agent.communication.MessageManager;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.Blockade;

public abstract class BlockadeSelector extends TargetSelector<Blockade> {

    public BlockadeSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager) {
        super(ai, wi, si, moduleManager);
    }

    @Override
    public abstract BlockadeSelector precompute(PrecomputeData precomputeData);

    @Override
    public abstract BlockadeSelector resume(PrecomputeData precomputeData);

    @Override
    public abstract BlockadeSelector preparate();

    @Override
    public BlockadeSelector updateInfo(MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Override
    public abstract BlockadeSelector calc();
}

