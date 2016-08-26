package adf.agent.platoon;

import adf.agent.develop.DevelopData;
import adf.component.tactics.TacticsAmbulance;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;
import java.util.List;

public class PlatoonAmbulance extends Platoon<AmbulanceTeam>
{
	public PlatoonAmbulance(TacticsAmbulance tactics, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, DevelopData developData)
	{
		super(tactics, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_AMBULANCE, isDebugMode, developData);
	}

	@Override
	protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum()
	{
		return EnumSet.of(StandardEntityURN.AMBULANCE_TEAM);
	}

	@Override
	protected void postConnect() {
		super.postConnect();
	}
}
