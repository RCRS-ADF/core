package adf.agent.platoon;

import adf.agent.develop.DevelopData;
import adf.component.tactics.TacticsFireBrigade;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.entities.StandardEntityURN;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class PlatoonFire extends Platoon<FireBrigade>
{
	public PlatoonFire(TacticsFireBrigade tactics, String moduleConfigFileName, boolean isPrecompute, boolean isDebugMode, DevelopData developData)
	{
		super(tactics, moduleConfigFileName, isPrecompute, DATASTORAGE_FILE_NAME_FIRE, isDebugMode, developData);
	}

	@Nonnull
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
