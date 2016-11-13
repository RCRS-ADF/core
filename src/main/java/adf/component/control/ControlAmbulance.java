package adf.component.control;

import adf.component.tactics.center.TacticsAmbulanceCenter;

/**
 * @deprecated change class name {@link TacticsAmbulanceCenter}
 */
@Deprecated
public abstract class ControlAmbulance extends TacticsAmbulanceCenter
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
