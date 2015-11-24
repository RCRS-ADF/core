package adf.agent.communication.standard.bundle;


import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;

public class MessageDummy extends StandardMessage
{
	final private int SIZE_TEST = 32;
	private int dummyTest;

	public MessageDummy(boolean isRadio, int test)
	{
		super(isRadio);
		dummyTest = test;
	}

	public MessageDummy(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader)
	{
		super(isRadio, from, ttl, bitStreamReader);
		dummyTest = bitStreamReader.getBits(SIZE_TEST);
	}

	public int getValue() { return this.dummyTest; }

	@Override
	public int getByteArraySize()
	{
		return SIZE_TEST;
	}

	@Override
	public byte[] toByteArray()
	{
		BitOutputStream bitOutputStream = new BitOutputStream();
		bitOutputStream.writeBits(dummyTest, SIZE_TEST);
		return bitOutputStream.toByteArray();
	}
}
