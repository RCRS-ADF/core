package adf.component.extaction;

import adf.agent.communication.MessageManager;
import adf.agent.develop.DevelopData;
import adf.agent.info.AgentInfo;
import adf.agent.info.ScenarioInfo;
import adf.agent.info.WorldInfo;
import adf.agent.module.ModuleManager;
import adf.agent.precompute.PrecomputeData;
import rescuecore2.worldmodel.EntityID;

import java.util.Collection;

public abstract class ExtCommandAction extends ExtAction {
    public ExtCommandAction(AgentInfo ai, WorldInfo wi, ScenarioInfo si, ModuleManager moduleManager, DevelopData developData) {
        super(ai, wi, si, moduleManager, developData);
    }

    @Override
    public abstract ExtCommandAction calc();

    @Override
    public ExtCommandAction precompute(PrecomputeData precomputeData) {
        super.precompute(precomputeData);
        return this;
    }

    @Override
    public ExtCommandAction resume(PrecomputeData precomputeData) {
        super.resume(precomputeData);
        return this;
    }

    @Override
    public ExtCommandAction preparate() {
        super.preparate();
        return this;
    }

    @Override
    public ExtCommandAction updateInfo(MessageManager messageManager){
        super.updateInfo(messageManager);
        return this;
    }


    @Override
    public ExtCommandAction setTarget(EntityID... targets) {
        return this;
    }

    @Override
    public ExtCommandAction setTarget(Collection<EntityID> targets) {
        return setTarget(targets.toArray(new EntityID[targets.size()]));
    }
}
