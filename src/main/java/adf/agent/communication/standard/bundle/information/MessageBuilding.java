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
		this.buildingBrokenness  = building.getBrokenness();
		this.buildingFieryness  = building.getFieryness();
		this.buildingTemperature  = building.getTemperature();
	}

	public MessageBuilding(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader)
	{
		super(isRadio, from, ttl, bitStreamReader);
		rawBuildingID = bitStreamReader.getBits(SIZE_ID);
		buildingBrokenness = bitStreamReader.getBits(SIZE_BROKENNESS);
		buildingFieryness = bitStreamReader.getBits(SIZE_FIERYNESS);
		buildingTemperature = bitStreamReader.getBits(SIZE_TEMPERATURE);
	}

	public EntityID getBuildingID()
	{
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
	public int getByteArraySize() {
		return SIZE_ID + SIZE_BROKENNESS + SIZE_FIERYNESS + SIZE_TEMPERATURE;
	}

	@Override
	public byte[] toByteArray() {
		BitOutputStream bitOutputStream = new BitOutputStream();
		bitOutputStream.writeBits(buildingID.getValue(), SIZE_ID);
		bitOutputStream.writeBits(buildingBrokenness, SIZE_BROKENNESS);
		bitOutputStream.writeBits(buildingFieryness, SIZE_FIERYNESS);
		bitOutputStream.writeBits(buildingTemperature, SIZE_TEMPERATURE);
		return bitOutputStream.toByteArray();
	}
}

