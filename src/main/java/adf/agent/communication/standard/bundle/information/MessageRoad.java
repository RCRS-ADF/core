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
	protected int rawRoadID;
	protected int rawBlockadeID;
	protected EntityID roadID;
	protected EntityID roadBlockadeID;
	protected int blockadeRepairCost;
	protected boolean roadPassable;

	public MessageRoad(boolean isRadio, Road road, Blockade blockade, boolean isPassable)
	{
		super(isRadio);
		this.roadID = road.getID();
		if (blockade == null)
		{
			this.roadBlockadeID = new EntityID(0);
			this.blockadeRepairCost = 0;
		}
		else
		{
			this.roadBlockadeID = blockade.getID();
			this.blockadeRepairCost = blockade.getRepairCost();
		}
		this.roadPassable = isPassable;
	}

	public MessageRoad(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader)
	{
		super(isRadio, from, ttl, bitStreamReader);
		rawRoadID = bitStreamReader.getBits(SIZE_ROADID);
		rawBlockadeID = bitStreamReader.getBits(SIZE_BLOCKADEID);
		blockadeRepairCost = bitStreamReader.getBits(SIZE_COST);
		roadPassable = (0 == bitStreamReader.getBits(SIZE_PASSABLE)?false:true);
	}

	public EntityID getRoadID()
	{
		if (this.roadID == null)
		{ this.roadID = new EntityID(this.rawRoadID); }

		return this.roadID;
	}

	public EntityID getBlockadeID()
	{
		if (this.roadBlockadeID == null)
		{ this.roadBlockadeID = new EntityID(this.rawBlockadeID); }

		return this.roadBlockadeID;
	}

	public int getRepairCost()
	{
		return blockadeRepairCost;
	}

	public boolean isPassable()
	{
		return roadPassable;
	}

	@Override
	public int getByteArraySize() {
		return SIZE_ROADID + SIZE_BLOCKADEID + SIZE_COST + SIZE_PASSABLE;
	}

	@Override
	public byte[] toByteArray() {
		BitOutputStream bitOutputStream = new BitOutputStream();
		bitOutputStream.writeBits(roadID.getValue(), SIZE_ROADID);
		if (roadBlockadeID == null) {
			bitOutputStream.writeBits(0, SIZE_BLOCKADEID);
		}else {
			bitOutputStream.writeBits(roadBlockadeID.getValue(), SIZE_BLOCKADEID);
		}
		bitOutputStream.writeBits(blockadeRepairCost, SIZE_COST);
		bitOutputStream.writeBits((roadPassable?1:0), SIZE_PASSABLE);
		return bitOutputStream.toByteArray();
	}
}

