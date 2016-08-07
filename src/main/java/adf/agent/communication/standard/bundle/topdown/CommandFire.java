package adf.agent.communication.standard.bundle.topdown;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.worldmodel.EntityID;

public class CommandFire extends StandardMessage
{
	/* below id is same to information.MessageFireBrigade */
	public static final int ACTION_REST = 0;
	public static final int ACTION_MOVE = 1;
	public static final int ACTION_EXTINGUISH = 2;
	public static final int ACTION_REFILL = 3;

	private static final int SIZE_TO = 32;
	private static final int SIZE_TARGET = 32;
	private static final int SIZE_ACTION = 4;

	protected int rawToID;
	protected int rawTargetID;
	protected EntityID commandToID;
	protected EntityID commandTargetID;
	protected int myAction;

	protected boolean broadcast;

	public CommandFire(boolean isRadio, EntityID toID, EntityID targetID, int action)
	{
		super(isRadio);
		this.commandToID = toID;
		this.commandTargetID = targetID;
		this.myAction = action;
        this.broadcast = (toID == null);
	}

	public CommandFire(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader)
	{
		super(isRadio, from, ttl, bitStreamReader);
        this.rawToID = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_TO) : -1;
        this.rawTargetID = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_TARGET) : -1;
        this.myAction = bitStreamReader.getBits(SIZE_ACTION);
        this.broadcast = (this.rawToID == -1);
	}

	public int getAction()
	{ return this.myAction; }

	@Override
	public int getByteArraySize()
	{
		return this.toBitOutputStream().size();
	}

	@Override
	public byte[] toByteArray() {
		return this.toBitOutputStream().toByteArray();
	}

    @Override
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

    public EntityID getToID() {
        if(this.broadcast) return null;
        if ( this.commandToID == null ) {
            if(this.rawToID != -1) this.commandToID = new EntityID(this.rawToID);
        }
        return this.commandToID;
    }

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
}
