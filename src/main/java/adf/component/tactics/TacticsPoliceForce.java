package adf.component.tactics;

import javax.annotation.Nullable;

public abstract class TacticsPoliceForce extends Tactics
{
	public TacticsPoliceForce(@Nullable TacticsPoliceForce parent)
	{
		super(parent);
	}

	public TacticsPoliceForce()
	{
		super(null);
	}
}
