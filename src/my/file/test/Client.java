package my.file.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {
	
	
	
	private SocketChannel  client ;
	
	public Client() throws IOException{
		client = SocketChannel.open();
	}
	
	public  boolean connect(String ip,int port) throws IOException{
		return client.connect(new InetSocketAddress(ip,port));
	}
	
	public boolean finishConnect() throws IOException{
		return client.finishConnect();
	}
	
	public void close() throws IOException{
		client.close();
	}
	
	public ByteBuffer read() throws IOException{
		ByteBuffer buffer = ByteBuffer.allocate(128);
		int count = client.read(buffer);
		if(count==-1)
			return null;
		return buffer;
	}
		
	public void write(String data) throws IOException{
		byte[] datas = data.getBytes(Charset.forName("UTF-8"));
		int len = datas.length;
		ByteBuffer buffer = ByteBuffer.allocate(len + 6);
		buffer.clear();
		buffer.putShort((short)111);
		buffer.putInt(len);
		buffer.put(datas);
		buffer.flip();
		client.write(buffer);
	}

	
	public void write(int i) throws IOException{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.clear();
		buffer.putInt(i);
		buffer.flip();
		client.write(buffer);
	}
	
	public void write(byte[] data) throws IOException{
		
		ByteBuffer buffer = ByteBuffer.allocate(data.length+6);
		buffer.clear();
		buffer.putShort((short)111);
		buffer.putInt(data.length);
		buffer.put(data);
		buffer.flip();
		client.write(buffer);
	}
	
	
	public static void main(String[] args) throws IOException{
		
		Client client = new Client();
		//连接上
	     if(client.connect("127.0.0.1", 9000)){
	    	 while(client.finishConnect()){
	    		 client.write(" testing ....... ");
	    		 client.write("测试中文呀呀呀呀呀呀呀呀");
	    		 /**
	    		 while(true){
		    		 ByteBuffer buffer = client.read();
		    		 StringBuilder builder = new StringBuilder();
		    		 while (buffer.hasRemaining()) {
		    			 char _byte = buffer.getChar();
					
						builder.append(_byte);
					}
		    		 
		    			System.out.println(builder.toString());
		    	 }**/
	    		 break;
	    	 }
	    	 client.close();
	     }
	}
	
	
	
	
	
	
	

}
