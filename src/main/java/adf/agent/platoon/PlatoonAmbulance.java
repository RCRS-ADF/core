package adf.agent.platoon;

import adf.agent.develop.DevelopData;
import adf.component.tactics.TacticsAmbulanceTeam;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.StandardEntityURN;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.EnumSet;

public class PlatoonAmbulance extends Platoon<AmbulanceTeam>
{
	public PlatoonAmbulance(@Nonnull TacticsAmbulanceTeam tactics, @Nonnull String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, @Nonnull DevelopData developData)
	{
		super(tactics, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_AMBULANCE, isDebugMode, developData);
	}

	@Nonnull
    @Override
	protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum()
	{
		return EnumSet.of(StandardEntityURN.AMBULANCE_TEAM);
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	protected void postConnect() {
		super.postConnect();
	}
}
