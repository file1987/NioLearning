package my.file.test.netty;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelConnctionManager {
	
	public static final Map<Channel, IConnection> channelKeys = new ConcurrentHashMap<Channel, IConnection>();
	public static final Map<IConnection,Channel> conKeys = new ConcurrentHashMap<IConnection, Channel>();
	
	public void attachChannel(Channel channel,IConnection con){
		channelKeys.put(channel, con);
		conKeys.put(con, channel);
	}
	
	public void removeChannel(Channel channel){
		IConnection con = channelKeys.get(channel);
		channelKeys.remove(channel);
		conKeys.remove(con);
	}

}
