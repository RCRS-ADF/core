package adf.agent.communication.standard.bundle.information;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;
import rescuecore2.standard.entities.Building;
import rescuecore2.worldmodel.EntityID;


public class MessageBuilding extends StandardMessage
{
	private static final int SIZE_ID = 32;
	private static final int SIZE_BROKENNESS = 32;
	private static final int SIZE_FIERYNESS = 32;
	private static final int SIZE_TEMPERATURE = 32;

	protected int rawBuildingID;
	protected EntityID buildingID;
	protected int buildingBrokenness;
	protected int buildingFieryness;
	protected int buildingTemperature;

	public MessageBuilding(boolean isRadio, Building building)
	{
		super(isRadio);
		this.buildingID = building.getID();
		this.buildingBrokenness = building.isBrokennessDefined() ? building.getBrokenness() : -1;
		this.buildingFieryness = building.isFierynessDefined() ? building.getFieryness() : -1;
		this.buildingTemperature = building.isTemperatureDefined() ? building.getTemperature() : -1;
	}

	public MessageBuilding(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader) {
		super(isRadio, from, ttl, bitStreamReader);
		this.rawBuildingID = bitStreamReader.getBits(SIZE_ID);
		this.buildingBrokenness = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_BROKENNESS) : -1;
		this.buildingFieryness = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_FIERYNESS) : -1;
		this.buildingTemperature = (bitStreamReader.getBits(1) == 1) ? bitStreamReader.getBits(SIZE_TEMPERATURE) : -1;
	}

	public EntityID getBuildingID() {
		if (this.buildingID == null) {
            this.buildingID = new EntityID(this.rawBuildingID);
        }
		return this.buildingID;
	}

	public int getBrokenness() {
        return this.buildingBrokenness;
    }

	public int getFieryness() {
		return this.buildingFieryness;
	}

	public int getTemperature() {
        return this.buildingTemperature;
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
	public BitOutputStream toBitOutputStream() {
		BitOutputStream bitOutputStream = new BitOutputStream();
		bitOutputStream.writeBits(this.buildingID.getValue(), SIZE_ID);
		if (this.buildingBrokenness != -1) {
			bitOutputStream.writeBitsWithExistFlag(this.buildingBrokenness, SIZE_BROKENNESS);
		} else {
			bitOutputStream.writeNullFlag();
		}
		if (this.buildingFieryness != -1) {
			bitOutputStream.writeBitsWithExistFlag(buildingFieryness, SIZE_FIERYNESS);
		} else {
			bitOutputStream.writeNullFlag();
		}
		if (this.buildingTemperature != -1) {
			bitOutputStream.writeBitsWithExistFlag(buildingTemperature, SIZE_TEMPERATURE);
		} else {
			bitOutputStream.writeNullFlag();
		}
		return bitOutputStream;
	}

	public boolean isBrokennessDefined() {
		return this.buildingBrokenness != -1;
	}
	public boolean isFierynessDefined() {
		return this.buildingFieryness != -1;
	}
	public boolean isTemperatureDefined() {
		return this.buildingTemperature != -1;
	}
}

