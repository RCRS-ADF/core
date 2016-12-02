package adf.component.tactics.center;

public abstract class TacticsAmbulanceCentre extends TacticsCenter {
    public TacticsAmbulanceCentre(TacticsAmbulanceCentre parent)
    {
        super(parent);
    }

    public TacticsAmbulanceCentre()
    {
        super(null);
    }
}