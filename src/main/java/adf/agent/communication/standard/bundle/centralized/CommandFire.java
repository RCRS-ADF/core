package adf.agent.communication.standard.bundle.centralized;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.worldmodel.EntityID;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class CommandFire extends StandardMessage {
	/* below id is same to information.MessageFireBrigade */
	public static final int ACTION_REST = 0;
	public static final int ACTION_MOVE = 1;
	public static final int ACTION_EXTINGUISH = 2;
	public static final int ACTION_REFILL = 3;
    public static final int ACTION_AUTONOMY = 4;

	private static final int SIZE_TO = 32;
	private static final int SIZE_TARGET = 32;
	private static final int SIZE_ACTION = 4;

	protected int rawToID;
	protected int rawTargetID;
	protected EntityID commandToID;
	protected EntityID commandTargetID;
	protected int myAction;

	protected boolean broadcast;

	public CommandFire(boolean isRadio, @Nullable EntityID toID, @Nullable EntityID targetID, @Nonnegative int action)
	{
		super(isRadio);
		this.commandToID = toID;
		this.commandTargetID = targetID;
		this.myAction = action;
        this.broadcast = (toID == null);
	}

	public CommandFire(boolean isRadio, int from, int ttl, @Nonnull BitStreamReader bitStreamReader)
	{
		super(isRadio, from, ttl, bitStreamReader);
        this.rawToID = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_TO) : -1;
        this.rawTargetID = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_TARGET) : -1;
        this.myAction = bitStreamReader.getBits(SIZE_ACTION);
        this.broadcast = (this.rawToID == -1);
	}

	@Nonnegative
	public int getAction()
	{ return this.myAction; }

	@Override
	public int getByteArraySize()
	{
		return this.toBitOutputStream().size();
	}

	@Override
    @Nonnull
	public byte[] toByteArray() {
		return this.toBitOutputStream().toByteArray();
	}

    @Override
    @Nonnull
    public BitOutputStream toBitOutputStream()
    {
        BitOutputStream bitOutputStream = new BitOutputStream();
        if (this.commandToID != null) {
            bitOutputStream.writeBitsWithExistFlag(this.commandToID.getValue(), SIZE_TO);
        } else if(this.rawToID != -1) {
            bitOutputStream.writeBitsWithExistFlag(this.rawToID, SIZE_TO);
        }else {
            bitOutputStream.writeNullFlag();
        }
        if (this.commandTargetID != null) {
            bitOutputStream.writeBitsWithExistFlag(this.commandTargetID.getValue(), SIZE_TARGET);
        } else if(this.rawTargetID != -1) {
            bitOutputStream.writeBitsWithExistFlag(this.rawTargetID, SIZE_TARGET);
        }else {
            bitOutputStream.writeNullFlag();
        }
        bitOutputStream.writeBits(myAction, SIZE_ACTION);
        return bitOutputStream;
    }

    @Nullable
    public EntityID getToID() {
        if(this.broadcast) return null;
        if ( this.commandToID == null ) {
            if(this.rawToID != -1) this.commandToID = new EntityID(this.rawToID);
        }
        return this.commandToID;
    }

    @Nullable
    public EntityID getTargetID() {
        if ( this.commandTargetID == null ) {
            if(this.rawTargetID != -1) this.commandTargetID = new EntityID(this.rawTargetID);
        }
        return this.commandTargetID;
    }

    public boolean isBroadcast() {
        return this.broadcast;
    }

    public boolean isToIDDefined() {
        return (this.commandToID != null || this.rawToID != -1);
    }

    public boolean idTargetIDDefined() {
        return (this.commandTargetID != null || this.rawTargetID != -1);
    }

    @Override
    @Nonnull
    public String getCheckKey() {
        String toIDValue = this.broadcast ? "broadcast" : Objects.requireNonNull(this.getToID()).toString();
        EntityID tid = this.getTargetID();
        String tidValue = tid == null ? "null" : tid.toString();
        return getClass().getCanonicalName() + " > to:" + toIDValue + " target:" + tidValue + " action:" + this.myAction;
    }
}
