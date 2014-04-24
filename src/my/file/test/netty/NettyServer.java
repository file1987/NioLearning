package my.file.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	
	private final int port;
	private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();
	public NettyServer(){
		this(9000);
	}
	
	public NettyServer(int port){
		this.port = port;
	}

	
	public void startUp() throws Exception{		
		ServerBootstrap boot = new ServerBootstrap();
		boot.group(bossGroup, workerGroup)
		    .channel(NioServerSocketChannel.class)
		    .childHandler(new NettyChannelInitializer());
		boot.bind(port).sync();		
	}
	
	public void close() throws Exception{
		bossGroup.shutdownGracefully().sync();
		workerGroup.shutdownGracefully().sync();
	}
	
	
	
	
	
	
	
}
