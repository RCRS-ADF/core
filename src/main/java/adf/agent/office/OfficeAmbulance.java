package adf.agent.office;

import adf.component.control.ControlAmbulance;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;

public class OfficeAmbulance extends Office<Building>
{
	public OfficeAmbulance(ControlAmbulance control, String moduleConfigFileName, boolean isPrecompute)
	{
		super(control, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_AMBULANCE);
	}

	@Override
	protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum()
	{
		return EnumSet.of(StandardEntityURN.AMBULANCE_CENTRE);
	}

	@Override
	protected void postConnect()
	{
		super.postConnect();
	}
}
