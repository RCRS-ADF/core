package adf.component.control;

import adf.component.tactics.center.TacticsPoliceCenter;

/**
 * @deprecated change class name {@link TacticsPoliceCenter}
 */
@Deprecated
public abstract class ControlPolice extends TacticsPoliceCenter
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
