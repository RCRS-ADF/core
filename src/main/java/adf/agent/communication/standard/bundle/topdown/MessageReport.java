package adf.agent.communication.standard.bundle.topdown;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import org.dom4j.Entity;
import rescuecore2.worldmodel.EntityID;

public class MessageReport extends StandardMessage
{
	private static final int SIZE_DONE = 1;
    private static final int SIZE_BROADCAST = 1;
    private static final int SIZE_FROM = 32;

	private boolean reportDone;
	private boolean reportBroadcast;
    private EntityID reportFromID;
    private int rawReportFromID;

	public MessageReport(boolean isRadio, boolean isDone, boolean isBroadcast, EntityID fromID) {
		super(isRadio);
		this.reportDone = isDone;
        this.reportBroadcast = isBroadcast;
        this.reportFromID = fromID;
	}

	public MessageReport(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader) {
		super(isRadio, from, ttl, bitStreamReader);
		this.reportDone = (0 != bitStreamReader.getBits(SIZE_DONE));
        this.reportBroadcast = (0 != bitStreamReader.getBits(SIZE_BROADCAST));
        this.rawReportFromID = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_FROM) : -1;
	}

	public boolean isDone()
	{ return this.reportDone; }

	public boolean isFailed()
	{ return !this.reportDone; }

	public boolean isBroadcast() {
	    return this.reportBroadcast;
    }

    public boolean isFromIDDefined() {
        return (this.reportFromID != null || this.rawReportFromID != -1);
    }

    public EntityID getFromID() {
        if ( this.reportFromID == null ) {
            if(this.rawReportFromID != -1) this.reportFromID = new EntityID(this.rawReportFromID);
        }
        return this.reportFromID;
    }

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
		bitOutputStream.writeBits((this.reportDone?1:0), SIZE_DONE);
        bitOutputStream.writeBits((this.reportBroadcast?1:0), SIZE_BROADCAST);
        if (this.reportFromID != null) {
            bitOutputStream.writeBitsWithExistFlag(this.reportFromID.getValue(), SIZE_FROM);
        } else if(this.rawReportFromID != -1) {
            bitOutputStream.writeBitsWithExistFlag(this.rawReportFromID, SIZE_FROM);
        }else {
            bitOutputStream.writeNullFlag();
        }
		return bitOutputStream;
	}

	@Override
	public String getCheckKey() {
		return getClass().getCanonicalName()
                + " isBroadcast:" + this.isBroadcast()
                + " fromID:" + this.getFromID();
	}
}
