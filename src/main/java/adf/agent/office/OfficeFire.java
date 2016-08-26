package adf.agent.office;

import adf.agent.develop.DevelopData;
import adf.component.control.ControlFire;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;
import java.util.List;

public class OfficeFire extends Office<Building>
{
	public OfficeFire(ControlFire control, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, DevelopData developData)
	{
		super(control, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_FIRE, isDebugMode, developData);
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
