package my.file.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class Server {
	
	
	
	private Selector selector = Selector.open();
	
	
	
	
	
	
	
	private ServerSocketChannel server;
	private boolean isOpen = false;
	public Server() throws IOException{
		this(9000);
	}
	
	public Server(int port) throws IOException{
		server = ServerSocketChannel.open(); 
		server.socket().bind(new InetSocketAddress(port));
		server.configureBlocking(false);
		server.register(selector, SelectionKey.OP_ACCEPT);	
		isOpen = true;
	}
	
	public void listenerAccept() throws IOException{
		Thread acceptThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (isOpen) {
					try {
						selector.select();
						Iterator<SelectionKey> keyIt = selector.selectedKeys().iterator();
						while (keyIt.hasNext()) {
							SelectionKey sk = keyIt.next();
							switch (sk.interestOps()) {
								case SelectionKey.OP_ACCEPT:
									SocketChannel cs = server.accept();
									cs.configureBlocking(false);
									cs.register(selector, SelectionKey.OP_READ);
									break;
								case SelectionKey.OP_CONNECT:
									break;
								case SelectionKey.OP_READ:
									read(sk);
									break;
								case SelectionKey.OP_WRITE:
									break;
								default:
									break;
							}
							keyIt.remove();		
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
			}
		});
		acceptThread.start();
		
	}
	
	public byte[] read(SelectionKey sk) throws IOException{
		return read((SocketChannel)sk.channel()); 
	}
	
	
	
	public byte[] read(SocketChannel sc) throws IOException{
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		int count = sc.read(byteBuffer);
		if(count==-1)
			return null;
		
		byteBuffer.flip();
		//2
		short msgId = byteBuffer.getShort();
		System.out.println("msgId:"+ msgId);
		//4
		int len = byteBuffer.getInt();
		System.out.println("len:"+ len);
		//一条消息
		if(byteBuffer.remaining() >= len){
			byte[] inBytes = new byte[len];
			for(int i=0;i<len;i++){
			   inBytes[i] = byteBuffer.get();
			}
			System.out.println("xxxx:"+ new String(inBytes,Charset.forName("UTF-8")));
			byteBuffer.compact();
		    return inBytes;		
		}else{
			
		}
		
		
		
		return null;
	}
	
	public void write(String data,SocketChannel sc) throws IOException{
		int len = data.length();
		ByteBuffer buffer = ByteBuffer.allocate(len + 8);
		buffer.clear();
		buffer.putInt(len);
		buffer.put(data.getBytes());
		buffer.flip();		
		sc.write(buffer);
	}
	
	public void close() throws IOException{
		isOpen = false;
		selector.close();
		server.close();
	}
	
	
	public static void main(String[] args) throws IOException{
		Server server = new Server();
		server.listenerAccept();
	}
	
	
	
	

}
