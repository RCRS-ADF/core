package adf.agent.communication.standard.bundle.topdown;

import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitOutputStream;
import adf.component.communication.util.BitStreamReader;

public class MessageReport extends StandardMessage
{
	private static final int SIZE_DONE = 1;
	private boolean reportDone;

	public MessageReport(boolean isRadio, boolean isDone) {
		super(isRadio);
		this.reportDone = isDone;
	}

	public MessageReport(boolean isRadio, int from, int ttl, BitStreamReader bitStreamReader) {
		super(isRadio, from, ttl, bitStreamReader);
		this.reportDone = (0 != bitStreamReader.getBits(SIZE_DONE));
	}

	public boolean isDone()
	{ return this.reportDone; }

	public boolean isFailed()
	{ return !this.reportDone; }

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
		bitOutputStream.writeBits((this.reportDone?1:0), SIZE_DONE);
		return bitOutputStream;
	}

	@Override
	public String getCheckKey() {
		return getClass().getCanonicalName() + " > isDone:" + this.isDone();
	}
}
