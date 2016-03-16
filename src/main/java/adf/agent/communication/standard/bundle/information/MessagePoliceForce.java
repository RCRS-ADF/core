package adf.agent.communication.standard.bundle.information;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.standard.entities.PoliceForce;
import rescuecore2.worldmodel.EntityID;


public class MessagePoliceForce extends StandardMessage
{
	/* below id is same to information.MessagePoliceForce */
	public static final int ACTION_REST = 0;
	public static final int ACTION_MOVE = 1;
	public static final int ACTION_CLEAR = 2;

	private static final int SIZE_ID = 32;
    private static final int SIZE_HP = 32;
	private static final int SIZE_BURIEDNESS = 32;
	private static final int SIZE_DAMAGE = 32;
	private static final int SIZE_POSITION = 32;
	private static final int SIZE_TARGET = 32;
	private static final int SIZE_ACTION = 4;

	protected int rawAgentID;
	protected EntityID agentID;
	protected int rawHumanPosition;
	protected int humanHP;
	protected int humanBuriedness;
	protected int humanDamage;
	protected EntityID humanPosition;
	protected int rawTargetID;
	protected EntityID myTargetID;
	private int myAction;

	public MessagePoliceForce(boolean isRadio, PoliceForce policeForce, int action, EntityID target)
	{
		super(isRadio);
		agentID = policeForce.getID();
		humanHP = policeForce.getHP();
		humanBuriedness = policeForce.getBuriedness();
		humanDamage = policeForce.getDamage();
		humanPosition = policeForce.getPosition();
		this.myTargetID = target;
		this.myAction = action;
	}

	public MessagePoliceForce(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader)
	{
		super(isRadio, from, ttl, bitStreamReader);
		rawAgentID = bitStreamReader.getBits(SIZE_ID);
		humanHP = bitStreamReader.getBits(SIZE_HP);
		humanBuriedness = bitStreamReader.getBits(SIZE_BURIEDNESS);
		humanDamage = bitStreamReader.getBits(SIZE_DAMAGE);
		rawHumanPosition = bitStreamReader.getBits(SIZE_POSITION);
		rawTargetID = bitStreamReader.getBits(SIZE_TARGET);
		myAction = bitStreamReader.getBits(SIZE_ACTION);
	}

	public EntityID getAgentID()
	{
		if (this.agentID == null) {
			this.agentID = new EntityID(this.rawAgentID);
		}
		return this.agentID;
	}

	public int getAction()
	{ return myAction; }

	public EntityID getTargetID()
	{
		if ( myTargetID == null )
		{ myTargetID = new EntityID(rawTargetID); }
		return myTargetID;
	}

	@Override
	public int getByteArraySize()
	{
		return toBitOutputStream().size();
	}

	@Override
	public byte[] toByteArray() {
		return this.toBitOutputStream().toByteArray();
	}

	@Override
	public BitOutputStream toBitOutputStream()
	{
		BitOutputStream bitOutputStream = new BitOutputStream();
		bitOutputStream.writeBits(agentID.getValue(), SIZE_ID);
		bitOutputStream.writeBits(humanHP, SIZE_HP);
		bitOutputStream.writeBits(humanBuriedness, SIZE_BURIEDNESS);
		bitOutputStream.writeBits(humanDamage, SIZE_DAMAGE);
		bitOutputStream.writeBits(humanPosition.getValue(), SIZE_POSITION);
		bitOutputStream.writeBits(myTargetID.getValue(), SIZE_TARGET);
		bitOutputStream.writeBits(myAction, SIZE_ACTION);
		return bitOutputStream;
	}

	public int getHP() { return this.humanHP; }

	public int getBuriedness() { return this.humanBuriedness; }

	public int getDamage() { return this.humanDamage; }

	public EntityID getPosition()
	{
		if (this.humanPosition == null)
		{ this.humanPosition = new EntityID(this.rawHumanPosition); }
		return this.humanPosition;
	}
}

