package my.file.test.mina;

import my.file.test.netty.ISendNetMsg;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaMsgEncoder extends IoFilterAdapter {

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,Object message) throws Exception {
		//super.messageReceived(nextFilter, session, message);
		if(message instanceof ISendNetMsg){
			ISendNetMsg msg =(ISendNetMsg) message;
			//消息编码
			msg.encoder();
			//写入bytebuf
			byte[] data = msg.getData();
			IoBuffer buf = IoBuffer.allocate(data.length).setAutoExpand(true);
			buf.put(data);
			msg.release();
			nextFilter.messageReceived(session, buf);
		}else{
			nextFilter.messageReceived(session, message);
		}
		
	}

}
