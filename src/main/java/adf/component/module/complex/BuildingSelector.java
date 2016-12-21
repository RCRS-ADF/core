package adf.component.module.complex;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.standard.entities.Building;

import javax.annotation.Nonnull;

/**
 * @deprecated change class name {@link BuildingDetector}
 */
@Deprecated
public abstract class BuildingSelector extends BuildingDetector {

    public BuildingSelector(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    @Nonnull
    @Override
    public BuildingSelector precompute(@Nonnull PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Nonnull
    @Override
    public BuildingSelector resume(@Nonnull PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Nonnull
    @Override
    public BuildingSelector preparate() {
        super.preparate();
        return this;
    }

    @Nonnull
    @Override
    public BuildingSelector updateInfo(@Nonnull MessageManager messageManager) {
        super.updateInfo(messageManager);
        return this;
    }

    @Nonnull
    @Override
    public abstract BuildingSelector calc();
}
