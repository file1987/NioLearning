package my.file.test.mina;

import my.file.test.netty.FReceviceMsgFactory;
import my.file.test.netty.IReceviceNetMsg;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaBytesDecoder extends IoFilterAdapter {

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,Object message) throws Exception {
		//super.messageReceived(nextFilter, session, message);
		if(message instanceof IoBuffer){
			IoBuffer buff = (IoBuffer)message;
			while(buff.remaining()>= 4 ){
				//记录当前缓存区索引位置
				buff.mark();
				
				//2 (msg+data)
				short len = buff.getShort();
				//2 
				short msgId = buff.getShort();
				
				if(buff.remaining() >= len -2 ){
					byte[] data = new byte[len-2];
					buff.get(data);
					IReceviceNetMsg revMsg = FReceviceMsgFactory.getReceviceMsg(msgId);
					//读取bytes
					revMsg.readData(data);
					//消息解码 
					revMsg.decoder();
					nextFilter.messageReceived(session, revMsg);				
				}else{ //数据不够一条消息  重新设置等待下次读取
					buff.reset();
					break;
				}
			}
			
		}else{
			nextFilter.messageReceived(session, message);
		}
	}

}
