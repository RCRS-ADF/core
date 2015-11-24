package adf.component.tactics;

public abstract class TacticsAmbulance extends Tactics
{
	public TacticsAmbulance(TacticsAmbulance parent)
	{
		super(parent);
	}

	public TacticsAmbulance()
	{
		this(null);
	}
}
