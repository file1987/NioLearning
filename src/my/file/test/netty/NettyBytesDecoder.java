package my.file.test.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NettyBytesDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,List<Object> out) throws Exception {
		
		
		while(in.readableBytes()>=4){
			//记录当前缓存区索引位置
			in.markReaderIndex();
			//2 (msg+data)
			short len = in.readShort();
			//2 
			short msgId = in.readShort();
			
			if(in.readableBytes() >= len -2 ){
				byte[] data = new byte[len-2];
				in.readBytes(data);
				IReceviceNetMsg revMsg = FReceviceMsgFactory.getReceviceMsg(msgId);
				//读取bytes
				revMsg.readData(data);
				//消息解码 
				revMsg.decoder();
				out.add(revMsg);				
			}else{ //数据不够一条消息  重新设置等待下次读取
				in.resetReaderIndex();
				break;
			}
					
		}
		
			
		

	}

}
