package adf.component.tactics;

import javax.annotation.Nullable;

public abstract class TacticsAmbulanceCentre extends TacticsCenter {
    public TacticsAmbulanceCentre(@Nullable TacticsAmbulanceCentre parent)
    {
        super(parent);
    }

    public TacticsAmbulanceCentre()
    {
        super(null);
    }
}