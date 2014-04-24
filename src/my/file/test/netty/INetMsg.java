package my.file.test.netty;

public interface INetMsg {
	
	/**
	public byte[] getData();**/
	
	public short getMsgId();
	
	public void setMsgId(short msgId);
	
	public void release();

}
