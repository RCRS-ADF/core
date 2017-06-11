package adf.agent.communication.standard.bundle.information;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.agent.communication.standard.bundle.StandardMessagePriority;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.standard.entities.AmbulanceTeam;
import rescuecore2.worldmodel.EntityID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class MessageAmbulanceTeam extends StandardMessage
{
	/* below id is same to information.MessageAmbulanceTeam */
	public static final int ACTION_REST = 0;
	public static final int ACTION_MOVE = 1;
	public static final int ACTION_RESCUE = 2;
	public static final int ACTION_LOAD = 3;
	public static final int ACTION_UNLOAD = 4;

	private static final int SIZE_ID = 32;
	private static final int SIZE_HP = 14;
	private static final int SIZE_BURIEDNESS = 13;
	private static final int SIZE_DAMAGE = 14;
	private static final int SIZE_POSITION = 32;
	private static final int SIZE_TARGET = 32;
	private static final int SIZE_ACTION = 4;

	protected int rawAgentID;
	protected EntityID agentID;
	protected Integer rawHumanPosition;
	protected Integer humanHP;
	protected Integer humanBuriedness;
	protected Integer humanDamage;
	protected EntityID humanPosition;
	protected Integer rawTargetID;
	protected EntityID myTargetID;
	protected int myAction;

	public MessageAmbulanceTeam(boolean isRadio, @Nonnull AmbulanceTeam ambulanceTeam, int action, @Nullable EntityID target)
	{
		this(isRadio, StandardMessagePriority.NORMAL, ambulanceTeam, action, target);
	}

	public MessageAmbulanceTeam(boolean isRadio, StandardMessagePriority sendingPriority, @Nonnull AmbulanceTeam ambulanceTeam, int action, @Nullable EntityID target)
	{
		super(isRadio, sendingPriority);
		this.agentID = ambulanceTeam.getID();
		this.humanHP = ambulanceTeam.isHPDefined() ? ambulanceTeam.getHP() : -1;
		this.humanBuriedness = ambulanceTeam.isBuriednessDefined() ? ambulanceTeam.getBuriedness() : -1;
        this.humanDamage = ambulanceTeam.isDamageDefined() ? ambulanceTeam.getDamage() : -1;
		this.humanPosition = ambulanceTeam.isPositionDefined() ? ambulanceTeam.getPosition() : null;
		this.myTargetID = target;
		this.myAction = action;
	}

	public MessageAmbulanceTeam(boolean isRadio, int from, int ttl, @Nonnull BitStreamReader bitStreamReader)
	//public MessageAmbulanceTeam(int time, int ttl, int hp, int buriedness, int damage, int position, int id, int target, int action)
	{
		super(isRadio, from, ttl, bitStreamReader);
		this.rawAgentID = bitStreamReader.getBits(SIZE_ID);
		this.humanHP = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_HP) : -1;
		this.humanBuriedness = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_BURIEDNESS) : -1;
		this.humanDamage = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_DAMAGE) : -1;
		this.rawHumanPosition = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_POSITION) : -1;
		this.rawTargetID = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_TARGET) : -1;
		this.myAction = bitStreamReader.getBits(SIZE_ACTION);
	}

	@Nonnull
	public EntityID getAgentID() {
		if (this.agentID == null) {
			this.agentID = new EntityID(this.rawAgentID);
		}
		return this.agentID;
	}

	public int getAction() {
	    return this.myAction;
	}

	@Nullable
	public EntityID getTargetID() {
		if ( this.myTargetID == null ) {
		    if(this.rawTargetID != -1) this.myTargetID = new EntityID(this.rawTargetID);
		}
		return this.myTargetID;
	}

	@Override
	public int getByteArraySize() {
		return this.toBitOutputStream().size();
	}

	@Override
	@Nonnull
	public byte[] toByteArray() {
		return this.toBitOutputStream().toByteArray();
	}

	@Override
	@Nonnull
	public BitOutputStream toBitOutputStream() {
        BitOutputStream bitOutputStream = new BitOutputStream();
        bitOutputStream.writeBits(this.agentID.getValue(), SIZE_ID);
        if (this.humanHP != -1) {
            bitOutputStream.writeBitsWithExistFlag(this.humanHP, SIZE_HP);
        } else {
            bitOutputStream.writeNullFlag();
        }
        if (this.humanBuriedness != -1) {
            bitOutputStream.writeBitsWithExistFlag(this.humanBuriedness, SIZE_BURIEDNESS);
        } else {
            bitOutputStream.writeNullFlag();
        }
        if (this.humanDamage != -1) {
            bitOutputStream.writeBitsWithExistFlag(this.humanDamage, SIZE_DAMAGE);
        } else {
            bitOutputStream.writeNullFlag();
        }
        if (this.humanPosition != null) {
            bitOutputStream.writeBitsWithExistFlag(this.humanPosition.getValue(), SIZE_POSITION);
        } else if(this.rawHumanPosition != -1) {
            bitOutputStream.writeBitsWithExistFlag(this.rawHumanPosition, SIZE_POSITION);
        }else {
            bitOutputStream.writeNullFlag();
        }
        if(this.myTargetID != null) {
            bitOutputStream.writeBitsWithExistFlag(this.myTargetID.getValue(), SIZE_TARGET);
        } else if(this.rawTargetID != -1) {
            bitOutputStream.writeBitsWithExistFlag(this.rawTargetID, SIZE_TARGET);
        } else {
            bitOutputStream.writeNullFlag();
        }
		bitOutputStream.writeBits(this.myAction, SIZE_ACTION);
		return bitOutputStream;
	}

	public int getHP() { return this.humanHP; }

	public int getBuriedness() { return this.humanBuriedness; }

	public int getDamage() { return this.humanDamage; }

	@Nullable
	public EntityID getPosition() {
		if (this.humanPosition == null) {
		    if(this.rawHumanPosition != -1) this.humanPosition = new EntityID(this.rawHumanPosition);
		}
		return this.humanPosition;
	}

    public boolean isTargetDefined() {
        return (this.myTargetID != null || this.rawTargetID != -1);
    }

    public boolean isHPDefined() {
        return this.humanHP != -1;
    }

    public boolean isBuriednessDefined() {
        return this.humanBuriedness != -1;
    }

    public boolean isDamageDefined() {
        return this.humanDamage != -1;
    }

    public boolean isPositionDefined() {
        return (this.humanPosition != null || this.rawHumanPosition != -1);
    }

	@Override
	@Nonnull
	public String getCheckKey() {
		EntityID tid = this.getTargetID();
		String tidValue = tid == null ? "null" : tid.toString();
		return getClass().getCanonicalName() + " > agent:" + this.getAgentID().getValue() + " target:" + tidValue;
	}
}

