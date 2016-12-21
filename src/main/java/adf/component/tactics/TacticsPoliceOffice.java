package adf.component.tactics;

import javax.annotation.Nullable;

public abstract class TacticsPoliceOffice extends TacticsCenter {
    public TacticsPoliceOffice(@Nullable TacticsPoliceOffice parent)
    {
        super(parent);
    }

    public TacticsPoliceOffice()
    {
        super(null);
    }
}
