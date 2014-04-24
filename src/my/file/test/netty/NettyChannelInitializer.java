package my.file.test.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel sc) throws Exception {
		sc.pipeline()
		   //解码器
		  .addLast("decoder", new NettyBytesDecoder())
		   //编码器
		  .addLast("encoder", new NettyMsgEncoder())
		   //business logic
		  .addLast("handler",new NettyServerHandler());
		
	}

}
