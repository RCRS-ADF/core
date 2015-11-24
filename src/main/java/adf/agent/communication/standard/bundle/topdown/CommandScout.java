package adf.agent.communication.standard.bundle.topdown;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.worldmodel.EntityID;

public class CommandScout extends StandardMessage
{
	private static final int SIZE_TO = 32;
	private static final int SIZE_TARGET = 32;
	private static final int SIZE_RANGE = 32;

	protected int rawToID;
	protected int rawTargetID;
	protected EntityID commandToID;
	protected EntityID commandTargetID;
	private int scoutRange;

	public CommandScout(boolean isRadio, EntityID toID, EntityID targetID, int range)
	{
		super(isRadio);
		this.commandToID = toID;
		this.commandTargetID = targetID;
		this.scoutRange = range;
	}

	public CommandScout(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader)
	{
		super(isRadio, from, ttl, bitStreamReader);
		rawToID = bitStreamReader.getBits(SIZE_TO);
		rawTargetID = bitStreamReader.getBits(SIZE_TARGET);
		scoutRange = bitStreamReader.getBits(SIZE_RANGE);
	}

	public int getRange()
	{ return scoutRange; }

	@Override
	public int getByteArraySize() {
		return SIZE_TO + SIZE_TARGET + SIZE_RANGE;
	}

	@Override
	public byte[] toByteArray() {
		BitOutputStream bitOutputStream = new BitOutputStream();
		bitOutputStream.writeBits(commandToID.getValue(), SIZE_TO);
		bitOutputStream.writeBits(commandTargetID.getValue(), SIZE_TARGET);
		bitOutputStream.writeBits(scoutRange, SIZE_RANGE);
		return bitOutputStream.toByteArray();
	}

	public EntityID getToID()
	{
		if ( commandToID == null )
		{ commandToID = new EntityID(rawToID); }
		return commandToID;
	}

	public EntityID getTargetID()
	{
		if ( commandTargetID == null )
		{ commandTargetID = new EntityID(rawTargetID); }
		return commandTargetID;
	}
}
