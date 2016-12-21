package adf.component.tactics;

import javax.annotation.Nullable;

public abstract class TacticsFireBrigade extends Tactics
{
	public TacticsFireBrigade(@Nullable TacticsFireBrigade parent)
	{
		super(parent);
	}

	public TacticsFireBrigade()
	{
		super(null);
	}
}
