package adf.agent.communication.standard.bundle.information;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.standard.entities.Blockade;
import rescuecore2.standard.entities.Road;
import rescuecore2.worldmodel.EntityID;

public class MessageRoad extends StandardMessage
{
	private static final int SIZE_ROADID = 32;
	private static final int SIZE_BLOCKADEID = 32;
	private static final int SIZE_COST = 32;
	private static final int SIZE_PASSABLE = 1;
	private static final int SIZE_X = 32;
	private static final int SIZE_Y = 32;

	protected int rawRoadID;
	protected int rawBlockadeID;
	protected EntityID roadID;
	protected EntityID roadBlockadeID;
	protected int blockadeRepairCost;
	protected boolean roadPassable;
	protected Integer blockadeX;
	protected Integer blockadeY;
    protected boolean sendLocation;

	public MessageRoad(boolean isRadio, Road road, Blockade blockade, boolean isPassable) {
		this(isRadio, road, blockade, isPassable, false);
	}

	public MessageRoad(boolean isRadio, Road road, Blockade blockade, boolean isPassable, boolean sendBlockadeLocation) {
		super(isRadio);
		this.roadID = road.getID();
		if (blockade != null) {
			this.roadBlockadeID = blockade.getID();
			this.blockadeRepairCost = blockade.isRepairCostDefined() ? blockade.getRepairCost() : -1;
			this.blockadeX = (sendBlockadeLocation && blockade.isXDefined()) ? blockade.getX() : null;
			this.blockadeY = (sendBlockadeLocation && blockade.isYDefined()) ? blockade.getY() : null;
		} else {
			this.roadBlockadeID = null;
			this.rawBlockadeID = -1;
			this.blockadeRepairCost = -1;
			this.blockadeX = null;
			this.blockadeY = null;
		}
		this.roadPassable = isPassable;
        this.sendLocation = sendBlockadeLocation;
	}

	public MessageRoad(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader)
	{
		super(isRadio, from, ttl, bitStreamReader);
		this.rawRoadID = bitStreamReader.getBits(SIZE_ROADID);
		this.rawBlockadeID = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_BLOCKADEID) : -1;
		this.blockadeRepairCost = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_COST) : -1;
		this.blockadeX = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_X) : null;
		this.blockadeY = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_Y) : null;
		this.roadPassable = (0 != bitStreamReader.getBits(SIZE_PASSABLE));
	}

	public EntityID getRoadID() {
		if (this.roadID == null) {
			this.roadID = new EntityID(this.rawRoadID);
		}
		return this.roadID;
	}

	public EntityID getBlockadeID() {
		if (this.roadBlockadeID == null) {
			if(this.rawBlockadeID != -1) this.roadBlockadeID = new EntityID(this.rawBlockadeID);
		}
		return this.roadBlockadeID;
	}

	public int getRepairCost()
	{
		return this.blockadeRepairCost;
	}

	public boolean isPassable()
	{
		return this.roadPassable;
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
		bitOutputStream.writeBits(this.roadID.getValue(), SIZE_ROADID);
        if(this.roadBlockadeID != null) {
            bitOutputStream.writeBitsWithExistFlag(this.roadBlockadeID.getValue(), SIZE_BLOCKADEID);
        } else if(this.rawBlockadeID != -1) {
            bitOutputStream.writeBitsWithExistFlag(this.rawBlockadeID, SIZE_BLOCKADEID);
        } else {
            bitOutputStream.writeNullFlag();
        }
        if (this.blockadeRepairCost != -1) {
            bitOutputStream.writeBits(this.blockadeRepairCost, SIZE_COST);
        } else {
            bitOutputStream.writeNullFlag();
        }
        if(this.sendLocation) {
            if (this.blockadeX != null) {
                bitOutputStream.writeBits(this.blockadeX, SIZE_X);
            } else {
                bitOutputStream.writeNullFlag();
            }
            if (this.blockadeY != null) {
                bitOutputStream.writeBits(this.blockadeY, SIZE_Y);
            } else {
                bitOutputStream.writeNullFlag();
            }
        } else {
            bitOutputStream.writeNullFlag(); // blockade x
            bitOutputStream.writeNullFlag(); // blockade y
        }
		bitOutputStream.writeBits((this.roadPassable?1:0), SIZE_PASSABLE);
		return bitOutputStream;
	}

	public Integer getBlockadeX() {
		return this.blockadeX;
	}

	public Integer getBlockadeY() {
		return this.blockadeY;
	}

	public boolean isRepairCostDefined() {
		return this.blockadeRepairCost != -1;
	}
	public boolean isXDefined() {
		return this.blockadeX != null;
	}
	public boolean isYDefined() {
		return this.blockadeY != null;
	}

	@Override
	public String getCheckKey() {
		return getClass().getCanonicalName() + " > road:" + this.getRoadID().getValue();
	}
}

