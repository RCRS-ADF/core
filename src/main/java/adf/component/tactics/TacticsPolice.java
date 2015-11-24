package adf.component.tactics;

public abstract class TacticsPolice extends Tactics
{
	public TacticsPolice(TacticsPolice parent)
	{
		super(parent);
	}

	public TacticsPolice()
	{
		super(null);
	}
}
