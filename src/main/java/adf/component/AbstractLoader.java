package adf.component;

import adf.component.control.ControlAmbulance;
import adf.component.control.ControlFire;
import adf.component.control.ControlPolice;
import adf.component.tactics.TacticsAmbulance;
import adf.component.tactics.TacticsFire;
import adf.component.tactics.TacticsPolice;

abstract public class AbstractLoader
{
	abstract public String getTeamName();

	abstract public TacticsAmbulance getTacticsAmbulance();
	abstract public TacticsFire getTacticsFire();
	abstract public TacticsPolice getTacticsPolice();

	abstract public ControlAmbulance getControlAmbulance();
	abstract public ControlFire getControlFire();
	abstract public ControlPolice getControlPolice();
}
