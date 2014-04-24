package my.file.test.netty;

import java.nio.charset.Charset;

public abstract class FAbstractRecevieMsg implements IReceviceNetMsg {
	
	private short msgId;
	private byte[] data;
	private int index = 0;
	private int cap = 0;
	
	public FAbstractRecevieMsg(short msgId){
		this.msgId = msgId;
	}
	
	public void decoder() {
		decoderData();
	}
	
	public abstract void decoderData();
	
	
	@Override
	public void release() {
		data=null;
		index =0;
		cap =0;

	}

	@Override
	public void readData(byte[] data) {
		this.data = data;
		cap = data.length;
	}
	
	
	protected boolean getBoolean(){
		return getByte()!=0;
	}
	
	protected byte getByte(){
		checkIndex(1);
		byte _byte = data[index];
		index ++;
		return _byte;
	}
	
	protected short getShort(){
		checkIndex(2);
		short _short = (short) (data[index] << 8 | data[index + 1] & 0xFF);
		index+=2;
		return _short;
	}
	
	protected int getInt(){
		checkIndex(4);
		int _int = (data[index] & 0xff) << 24 |  (data[index + 1] & 0xff) << 16 |  (data[index + 2] & 0xff) <<  8 | data[index + 3] & 0xff;
		index+=4;
		return _int;
	}
	
	protected char getChar(){
		return (char)getShort();
	}
	
	protected long getLong(){
		checkIndex(8);
		long _long = ((long) data[index]     & 0xff) << 56 |
        ((long) data[index + 1] & 0xff) << 48 |
        ((long) data[index + 2] & 0xff) << 40 |
        ((long) data[index + 3] & 0xff) << 32 |
        ((long) data[index + 4] & 0xff) << 24 |
        ((long) data[index + 5] & 0xff) << 16 |
        ((long) data[index + 6] & 0xff) <<  8 |
         (long) data[index + 7] & 0xff;
		index +=8;
		return _long;
	}
	
	protected float getFloat(){
		return Float.intBitsToFloat(getInt());
	}
	
	protected double getDouble(){
		return Double.longBitsToDouble(getLong());
	}
	
	protected String getString(){
		int size = getInt();
		checkIndex(size);
		byte[] _bytes = new byte[size];
		System.arraycopy(data, index, _bytes, 0, size);
		index+=size;
		return  new String(_bytes,Charset.forName("UTF-8"));
		
	}
	
	private void checkIndex(int minimumReadableBytes){
		if(index + minimumReadableBytes > cap)
			throw new RuntimeException("超过可读取的内容！！！！");
	}

	@Override
	public short getMsgId() {
		return msgId;
	}

	@Override
	public void setMsgId(short msgId) {
		this.msgId = msgId;		
	}
	
}
