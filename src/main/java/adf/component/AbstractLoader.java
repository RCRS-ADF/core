package adf.component;

import adf.component.tactics.*;

import javax.annotation.Nonnull;

abstract public class AbstractLoader
{
    @Nonnull
    abstract public String getTeamName();

    @Nonnull
    abstract public TacticsAmbulanceTeam getTacticsAmbulanceTeam();
    @Nonnull
    abstract public TacticsFireBrigade getTacticsFireBrigade();
    @Nonnull
    abstract public TacticsPoliceForce getTacticsPoliceForce();

    @Nonnull
    abstract public TacticsAmbulanceCentre getTacticsAmbulanceCentre();
    @Nonnull
    abstract public TacticsFireStation getTacticsFireStation();
    @Nonnull
    abstract public TacticsPoliceOffice getTacticsPoliceOffice();
}
