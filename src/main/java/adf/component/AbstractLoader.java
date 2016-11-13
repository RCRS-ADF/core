package adf.component;

import adf.component.control.ControlAmbulance;
import adf.component.control.ControlFire;
import adf.component.control.ControlPolice;
import adf.component.tactics.TacticsAmbulance;
import adf.component.tactics.TacticsFire;
import adf.component.tactics.TacticsPolice;
import adf.component.tactics.center.TacticsAmbulanceCenter;
import adf.component.tactics.center.TacticsFireCenter;
import adf.component.tactics.center.TacticsPoliceCenter;

abstract public class AbstractLoader
{
	abstract public String getTeamName();

	abstract public TacticsAmbulance getTacticsAmbulance();
	abstract public TacticsFire getTacticsFire();
	abstract public TacticsPolice getTacticsPolice();

    abstract public TacticsAmbulanceCenter getTacticsAmbulanceCenter();
    abstract public TacticsFireCenter getTacticsFireCenter();
    abstract public TacticsPoliceCenter getTacticsPoliceCenter();

	/**
	 * @deprecated change method name {@link #getTacticsAmbulanceCenter()}
	 */
    @Deprecated
	public ControlAmbulance getControlAmbulance() {
		return null;
	}

    /**
     * @deprecated change method name {@link #getTacticsFireCenter()}
     */
    @Deprecated
	public ControlFire getControlFire() {
        return null;
    }

    /**
     * @deprecated change method name {@link #getTacticsPoliceCenter()}
     */
    @Deprecated
	public ControlPolice getControlPolice() {
        return null;
    }
}
