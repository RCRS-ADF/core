package adf.component.tactics;

import javax.annotation.Nullable;

public abstract class TacticsAmbulanceTeam extends Tactics
{
	public TacticsAmbulanceTeam(@Nullable TacticsAmbulanceTeam parent)
	{
		super(parent);
	}

	public TacticsAmbulanceTeam()
	{
		super(null);
	}
}
