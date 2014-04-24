package my.file.test;

import my.file.test.mina.MinaServer;
import my.file.test.netty.FAbstractRecevieMsg;
import my.file.test.netty.FReceviceMsgFactory;
import my.file.test.netty.NettyServer;

public class Main {
	public static void main(String[] args) throws Exception{
		
		FReceviceMsgFactory.registerReceviceMsg((short)111, TestMsg.class);
		
		//NettyServer server = new NettyServer();
		MinaServer server = new MinaServer(); 
		
		server.startUp();
		
		
	}
	
	
	public static class TestMsg extends FAbstractRecevieMsg{

		public TestMsg(short msgId) {
			super(msgId);
		}

		@Override
		public void decoderData() {
			
			String data = getString();
			System.out.println("data:" +data);
			
		}
		
		
		
	}
	
	
	
	
	
}
