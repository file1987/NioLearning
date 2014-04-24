package my.file.test.netty;

public interface ISendNetMsg extends INetMsg {
	
	public byte[] getData();
	
	public void encoder();
}
