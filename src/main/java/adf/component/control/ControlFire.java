package adf.component.control;

import adf.component.tactics.center.TacticsFireCenter;

/**
 * @deprecated change class name {@link TacticsFireCenter}
 */
@Deprecated
public abstract class ControlFire extends TacticsFireCenter
{
	public ControlFire(ControlFire parent)
	{
		super(parent);
	}

	public ControlFire()
	{
		this(null);
	}
}
