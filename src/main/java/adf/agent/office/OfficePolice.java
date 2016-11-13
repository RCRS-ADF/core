package adf.agent.office;

import adf.agent.develop.DevelopData;
import adf.component.tactics.center.TacticsPoliceCenter;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class OfficePolice extends Office<Building>
{
	public OfficePolice(TacticsPoliceCenter tacticsPoliceCenter, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, DevelopData developData)
	{
		super(tacticsPoliceCenter, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_POLICE, isDebugMode, developData);
	}

	@Override
	protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum()
	{
		return EnumSet.of(StandardEntityURN.POLICE_OFFICE);
	}

	@Override
	protected void postConnect()
	{
		super.postConnect();
	}
}
