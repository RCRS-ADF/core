package adf.agent.office;

import adf.agent.develop.DevelopData;
import adf.component.tactics.center.TacticsFireCenter;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class OfficeFire extends Office<Building>
{
	public OfficeFire(TacticsFireCenter tacticsFireCenter, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, DevelopData developData)
	{
		super(tacticsFireCenter, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_FIRE, isDebugMode, developData);
	}

	@Override
	protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum()
	{
		return EnumSet.of(StandardEntityURN.FIRE_STATION);
	}

	@Override
	protected void postConnect()
	{
		super.postConnect();
	}
}
