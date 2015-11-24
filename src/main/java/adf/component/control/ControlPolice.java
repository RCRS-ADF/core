package adf.component.control;

public abstract class ControlPolice extends Control
{
	public ControlPolice(ControlPolice parent)
	{
		super(parent);
	}

	public ControlPolice()
	{
		super(null);
	}
}
