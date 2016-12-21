package adf.component.tactics;

import javax.annotation.Nullable;

public abstract class TacticsFireStation extends TacticsCenter {
    public TacticsFireStation(@Nullable TacticsFireStation parent)
    {
        super(parent);
    }

    public TacticsFireStation()
    {
        super(null);
    }
}
