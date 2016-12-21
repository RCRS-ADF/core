package adf.agent.office;

import adf.agent.develop.DevelopData;
import adf.component.tactics.TacticsAmbulanceCentre;
import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.StandardEntityURN;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class OfficeAmbulance extends Office<Building>
{
	public OfficeAmbulance(TacticsAmbulanceCentre tacticsAmbulanceCenter, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, DevelopData developData)
	{
		super(tacticsAmbulanceCenter, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_AMBULANCE, isDebugMode, developData);
	}

	@Nonnull
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
