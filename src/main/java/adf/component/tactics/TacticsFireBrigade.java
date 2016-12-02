package adf.component.tactics;

public abstract class TacticsFireBrigade extends Tactics
{
	public TacticsFireBrigade(TacticsFireBrigade parent)
	{
		super(parent);
	}

	public TacticsFireBrigade()
	{
		this(null);
	}
}
