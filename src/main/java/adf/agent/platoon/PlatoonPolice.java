package adf.agent.platoon;

import adf.agent.develop.DevelopData;
import adf.component.tactics.TacticsPoliceForce;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.standard.entities.StandardEntityURN;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.EnumSet;

public class PlatoonPolice extends Platoon<PoliceForce>
{
	public PlatoonPolice(@Nonnull TacticsPoliceForce tactics, @Nonnull String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, @Nonnull DevelopData developData)
	{
		super(tactics, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_POLICE, isDebugMode, developData);
	}

	@Nonnull
    @Override
	protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum()
	{
		return EnumSet.of(StandardEntityURN.POLICE_FORCE);
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	protected void postConnect()
	{
		super.postConnect();
	}
}
