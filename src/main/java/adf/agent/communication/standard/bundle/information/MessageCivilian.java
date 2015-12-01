package adf.agent.communication.standard.bundle.information;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.standard.entities.Civilian;
import rescuecore2.worldmodel.EntityID;


public class MessageCivilian extends StandardMessage
{
    private static final int SIZE_HP = 32;
	private static final int SIZE_BURIEDNESS = 32;
	private static final int SIZE_DAMAGE = 32;
	private static final int SIZE_POSITION = 32;

	protected int rawHumanPosition;
	protected int humanHP;
	protected int humanBuriedness;
	protected int humanDamage;
	protected EntityID humanPosition;

	public MessageCivilian(boolean isRadio, Civilian civilian, int action, EntityID target)
	{
		super(isRadio);
		humanHP = civilian.getHP();
		humanBuriedness = civilian.getBuriedness();
        humanDamage = civilian.getDamage();
		humanPosition = civilian.getPosition();
	}

	public MessageCivilian(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader)
	{
        super(isRadio, from, ttl, bitStreamReader);
		humanHP = bitStreamReader.getBits(SIZE_HP);
		humanBuriedness = bitStreamReader.getBits(SIZE_BURIEDNESS);
		humanDamage = bitStreamReader.getBits(SIZE_DAMAGE);
		rawHumanPosition = bitStreamReader.getBits(SIZE_POSITION);
	}

	@Override
	public int getByteArraySize() {
		return SIZE_HP + SIZE_BURIEDNESS + SIZE_DAMAGE + SIZE_POSITION;
	}

	@Override
	public byte[] toByteArray() {
		BitOutputStream bitOutputStream = new BitOutputStream();
		bitOutputStream.writeBits(humanHP, SIZE_HP);
		bitOutputStream.writeBits(humanBuriedness, SIZE_BURIEDNESS);
		bitOutputStream.writeBits(humanDamage, SIZE_DAMAGE);
		bitOutputStream.writeBits(humanPosition.getValue(), SIZE_POSITION);
		return bitOutputStream.toByteArray();
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

