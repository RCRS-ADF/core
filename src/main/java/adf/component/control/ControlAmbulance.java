package adf.component.control;

public abstract class ControlAmbulance extends Control
{
	public ControlAmbulance(ControlAmbulance parent)
	{
		super(parent);
	}

	public ControlAmbulance()
	{
		this(null);
	}
}
