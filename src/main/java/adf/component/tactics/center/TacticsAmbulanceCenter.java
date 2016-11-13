package adf.component.tactics.center;

public abstract class TacticsAmbulanceCenter extends TacticsCenter {
    public TacticsAmbulanceCenter(TacticsAmbulanceCenter parent)
    {
        super(parent);
    }

    public TacticsAmbulanceCenter()
    {
        super(null);
    }
}