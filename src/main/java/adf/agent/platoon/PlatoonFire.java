package adf.agent.platoon;

import adf.component.tactics.TacticsFire;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.StandardEntityURN;

import java.util.EnumSet;
import java.util.List;

public class PlatoonFire extends Platoon<FireBrigade>
{
	public PlatoonFire(TacticsFire tactics, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, List<String> rawDebugData)
	{
		super(tactics, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_FIRE, isDebugMode, rawDebugData);
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
