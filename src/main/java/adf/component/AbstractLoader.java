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

	abstract public TacticsAmbulanceTeam getTacticsAmbulanceTeam();
	abstract public TacticsFireBrigade getTacticsFireBrigade();
	abstract public TacticsPoliceForce getTacticsPoliceForce();

    abstract public TacticsAmbulanceCentre getTacticsAmbulanceCentre();
    abstract public TacticsFireStation getTacticsFireStation();
    abstract public TacticsPoliceOffice getTacticsPoliceOffice();

	/**
	 * @deprecated change method name {@link #getTacticsAmbulanceCentre()}
	 */
    @Deprecated
	public ControlAmbulance getControlAmbulance() {
		return null;
	}

    /**
     * @deprecated change method name {@link #getTacticsFireStation()}
     */
    @Deprecated
	public ControlFire getControlFire() {
        return null;
    }

    /**
     * @deprecated change method name {@link #getTacticsPoliceOffice()}
     */
    @Deprecated
	public ControlPolice getControlPolice() {
        return null;
    }
}
