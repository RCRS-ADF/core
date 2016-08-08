package adf.agent.platoon;

import adf.component.tactics.TacticsPolice;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;
import java.util.List;

public class PlatoonPolice extends Platoon<PoliceForce>
{
	public PlatoonPolice(TacticsPolice tactics, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, String debugDataFileName, List<String> rawDebugData)
	{
		super(tactics, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_POLICE, isDebugMode, debugDataFileName, rawDebugData);
	}

	@Override
	protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum()
	{
		return EnumSet.of(StandardEntityURN.POLICE_FORCE);
	}

	@Override
	protected void postConnect()
	{
		super.postConnect();
	}
}
