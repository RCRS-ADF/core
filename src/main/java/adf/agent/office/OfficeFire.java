package adf.agent.office;

import adf.component.control.ControlFire;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class OfficeFire extends Office<Building>
{
	public OfficeFire(ControlFire control, String moduleConfigFileName, boolean isPrecompute)
	{
		super(control, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_FIRE);
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
