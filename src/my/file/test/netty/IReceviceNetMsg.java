package my.file.test.netty;

public interface IReceviceNetMsg extends INetMsg {
	
	public void readData(byte[] data);
	
	public void decoder();
	
}
