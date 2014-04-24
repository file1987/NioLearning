package my.file.test.netty;


public interface IConnection {
	
  public long  getPlayerId();
  
  //public void  sendData(byte[] data);
  
  public void  close();
  
  public void  write(ISendNetMsg msg);
  
  public String getIp();
  
   
  

}
