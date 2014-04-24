package my.file.test.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<IReceviceNetMsg> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 	throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, IReceviceNetMsg msg) throws Exception {
		
	}

}
