package my.file.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyMsgEncoder extends MessageToByteEncoder<ISendNetMsg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ISendNetMsg msg, ByteBuf out) throws Exception {
		//消息编码
		msg.encoder();
		//写入bytebuf
		out.writeBytes(msg.getData());
		msg.release();
			
	}

}
