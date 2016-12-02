package adf.component;

import adf.component.control.ControlAmbulance;
import adf.component.control.ControlFire;
import adf.component.control.ControlPolice;
import adf.component.tactics.TacticsAmbulanceTeam;
import adf.component.tactics.TacticsFireBrigade;
import adf.component.tactics.TacticsPoliceForce;
import adf.component.tactics.center.TacticsAmbulanceCentre;
import adf.component.tactics.center.TacticsFireStation;
import adf.component.tactics.center.TacticsPoliceOffice;

abstract public class AbstractLoader
{
	abstract public String getTeamName();

	abstract public TacticsAmbulanceTeam getTacticsAmbulance();
	abstract public TacticsFireBrigade getTacticsFire();
	abstract public TacticsPoliceForce getTacticsPolice();

    abstract public TacticsAmbulanceCentre getTacticsAmbulanceCenter();
    abstract public TacticsFireStation getTacticsFireCenter();
    abstract public TacticsPoliceOffice getTacticsPoliceCenter();

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
