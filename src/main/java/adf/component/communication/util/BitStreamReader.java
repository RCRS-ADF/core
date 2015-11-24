package adf.component.communication.util;

public class BitStreamReader {

	private byte[] stream;
	private int index;

	@SuppressWarnings("unused")
	private BitStreamReader() {
	}

    public BitStreamReader(byte[] stream) {
		this.stream = stream;
		this.index = 0;
	}

	//先頭から指定されたビット数を取り出す
	public synchronized int getBits(int len) throws ArrayIndexOutOfBoundsException {
		//ストリーム長を超える読み取りかどうか
//		System.out.println(stream.length +"/"+ index +"/"+ len);
        if (stream.length * 8 < index + len) {
			throw new ArrayIndexOutOfBoundsException();
		}

		int val = 0;
		for (int i = len - 1; i >= 0; i--) {
			int num = stream[index / 8] >>> 7 - index % 8 & 0x1;
			val = val << 1 | num;
			index++;
		}
		return val;
	}

	//書き戻し用メソッド　引数分だけ書き戻す
	public synchronized void writeBack(int len) {
		index -= len;
	}

	public synchronized void writeForward(int len) {
		index += len;
	}

	public final int index() {
		return index;
	}

	public int getRemainBuffer() {
		return this.stream.length * 8 - index;
	}
}
