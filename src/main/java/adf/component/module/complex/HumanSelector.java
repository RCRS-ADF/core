package adf.component.module.complex;


import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.Human;

import javax.annotation.Nonnull;

/**
 * @deprecated change class name {@link HumanDetector}
 */
@Deprecated
public abstract class HumanSelector extends HumanDetector {

    public HumanSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    @Nonnull
    @Override
    public HumanSelector precompute(@Nonnull PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Nonnull
    @Override
    public HumanSelector resume(@Nonnull PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Nonnull
    @Override
    public HumanSelector preparate() {
        super.preparate();
        return this;
    }

    @Nonnull
    @Override
    public HumanSelector updateInfo(@Nonnull MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Nonnull
    @Override
    public abstract HumanSelector calc();
}
