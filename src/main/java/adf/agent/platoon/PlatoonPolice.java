package adf.agent.platoon;

import adf.agent.develop.DevelopData;
import adf.component.tactics.TacticsPolice;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;
import java.util.List;

public class PlatoonPolice extends Platoon<PoliceForce>
{
	public PlatoonPolice(TacticsPolice tactics, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, DevelopData developData)
	{
		super(tactics, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_POLICE, isDebugMode, developData);
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
