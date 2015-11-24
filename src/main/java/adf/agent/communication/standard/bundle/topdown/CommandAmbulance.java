package adf.agent.communication.standard.bundle.topdown;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.worldmodel.EntityID;

public class CommandAmbulance extends StandardMessage
{
	/* below id is same to information.MessageAmbulanceTeam */
	public static final int ACTION_REST = 0;
	public static final int ACTION_MOVE = 1;
	public static final int ACTION_RESCUE = 2;
	public static final int ACTION_LOAD = 3;

	private static final int SIZE_TO = 32;
	private static final int SIZE_TARGET = 32;
	private static final int SIZE_ACTION = 4;

	protected int rawToID;
	protected int rawTargetID;
	protected EntityID commandToID;
	protected EntityID commandTargetID;
	private int myAction;

	public CommandAmbulance(boolean isRadio, EntityID toID, EntityID targetID, int action)
	{
		super(isRadio);
		this.commandToID = toID;
		this.commandTargetID = targetID;
		this.myAction = action;
	}

	public CommandAmbulance(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader)
	{
		super(isRadio, from, ttl, bitStreamReader);
		rawToID = bitStreamReader.getBits(SIZE_TO);
		rawTargetID = bitStreamReader.getBits(SIZE_TARGET);
		myAction = bitStreamReader.getBits(SIZE_ACTION);
	}

	public int getAction()
	{ return myAction; }

	@Override
	public int getByteArraySize() {
		return SIZE_TO + SIZE_TARGET + SIZE_ACTION;
	}

	@Override
	public byte[] toByteArray() {
		BitOutputStream bitOutputStream = new BitOutputStream();
		bitOutputStream.writeBits(commandToID.getValue(), SIZE_TO);
		bitOutputStream.writeBits(commandTargetID.getValue(), SIZE_TARGET);
		bitOutputStream.writeBits(myAction, SIZE_ACTION);
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
