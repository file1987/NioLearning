package my.file.test.netty;

import java.nio.charset.Charset;

public abstract class FAbstractSendMsg implements ISendNetMsg {
	
	private byte[] data = new byte[128];
	private int index = 0;
	private int cap = 128;
	private short msgId;
	
	public FAbstractSendMsg(short msgId){
		this.msgId = msgId;
	}
	
	
	public void encoder(){
		putShort(0);
		if(msgId==0)
			throw new RuntimeException("消息id木有注册进来");
		putShort(msgId);
		encoderData();
		//把自己长度减掉
		putShort(0, (short)(index-2));
		
	}
	
	public abstract void encoderData(); 
		
	@Override
	public short getMsgId() {
		return msgId;
	}

	@Override
	public void setMsgId(short msgId) {
		this.msgId = msgId;
	}

	@Override
	public void release() {
		data= null;
		index =0;
		cap = 0;
		msgId = 0;
	}

	@Override
	public byte[] getData() {
		byte[] _data = new byte[index];
		System.arraycopy(data, 0, _data, 0, index);
		return _data;
	}
	
	protected void putBoolean(boolean b){
		putByte(b?1:0);
	}
	
	protected void putByte(byte b){
		checkIndex(1);
		data[index] = b;
		index++;
	}
		
	protected void putByte(int b){
		putByte((byte)b);
	}
	
	protected void putShort(int b){
		putShort((short)b);
	}
	
	protected void putShort(short b){
		checkIndex(2);
		putShort(index, b);
        index +=2;
	}
	
	private void putShort(int index,short b){
		data[index]     = (byte) (b >>> 8);
        data[index + 1] = (byte) b;
	}
	
	protected void putInt(int value){
		checkIndex(4);
		data[index]     = (byte) (value >>> 24);
        data[index + 1] = (byte) (value >>> 16);
        data[index + 2] = (byte) (value >>> 8);
        data[index + 3] = (byte) value;
        index +=4;
	}
	
	protected void putLong(long value){
		checkIndex(8);
		data[index]     = (byte) (value >>> 56);
		data[index + 1] = (byte) (value >>> 48);
		data[index + 2] = (byte) (value >>> 40);
		data[index + 3] = (byte) (value >>> 32);
		data[index + 4] = (byte) (value >>> 24);
		data[index + 5] = (byte) (value >>> 16);
		data[index + 6] = (byte) (value >>> 8);
		data[index + 7] = (byte) value;
		index+=8;
	}
	
	protected void putFloat(float value){
		putInt(Float.floatToRawIntBits(value));
	}
	
	protected void putDouble(double value){
		 putLong(Double.doubleToRawLongBits(value));
	}
	
	protected void putChar(char value){
		putShort((byte)value);
	}
	
	protected void putChar(int value){
		putShort((short)value);
	}
	
	protected void putString(String value){
		byte[] _data = value.getBytes(Charset.forName("UTF-8"));
		int len = _data.length;
		putInt(len);
		checkIndex(len);
		System.arraycopy(_data, 0, data, index, len);
		index+=len;
	}
	
	protected void put(byte value) {
		putByte(value);
	}

	protected void put(short value) {
		putShort(value);
	}

	protected void put(char value) {
		putChar(value);
	}

	protected void put(int value) {
		putInt(cap);
	}

	protected void put(boolean value) {
		putBoolean(value);
	}

	protected void put(long value) {
       putLong(value);
	}

	protected void put(float value) {
        putFloat(value);
	}

	protected void put(double value) {
		putDouble(value);
	}
	protected void put(String value) {
		putString(value);
	}
	
	private void checkIndex(int writeByteCounts){
		
		int _cap = cap;
		while((index+writeByteCounts) > _cap){
			_cap +=128; 
		}
		if(cap!=_cap){
			cap = _cap;
			byte[] _data = new byte[cap];
			System.arraycopy(data, 0, _data, 0, index);
			data = _data;
		}
	}
	

}
