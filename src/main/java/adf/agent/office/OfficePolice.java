package adf.agent.office;

import adf.component.control.ControlPolice;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;
import java.util.List;

public class OfficePolice extends Office<Building>
{
	public OfficePolice(ControlPolice control, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, String debugDataFileName, List<String> rawDebugData)
	{
		super(control, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_POLICE, isDebugMode, debugDataFileName, rawDebugData);
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
