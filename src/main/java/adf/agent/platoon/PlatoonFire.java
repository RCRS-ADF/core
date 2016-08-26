package adf.agent.platoon;

import adf.agent.develop.DevelopData;
import adf.component.tactics.TacticsFire;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;
import java.util.List;

public class PlatoonFire extends Platoon<FireBrigade>
{
	public PlatoonFire(TacticsFire tactics, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, DevelopData developData)
	{
		super(tactics, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_FIRE, isDebugMode, developData);
	}

	@Override
	protected EnumSet<StandardEntityURN> getRequestedEntityURNsEnum()
	{
		return EnumSet.of(StandardEntityURN.FIRE_BRIGADE);
	}

	@Override
	protected void postConnect()
	{
		super.postConnect();
	}
}
