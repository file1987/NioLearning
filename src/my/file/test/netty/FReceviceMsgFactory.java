package my.file.test.netty;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FReceviceMsgFactory {
	
	
	private final static Map<Short,Class<? extends IReceviceNetMsg> > revMsgs = new ConcurrentHashMap<Short,Class<? extends IReceviceNetMsg>>();
	
	
	public static IReceviceNetMsg getReceviceMsg(short msgId){
		Class<? extends IReceviceNetMsg>  instanceClass = revMsgs.get(msgId);
		if(instanceClass==null)
			throw new RuntimeException("根据消息id找不到相应的消息类。msgId："+msgId);
		try {
			Constructor<? extends IReceviceNetMsg> c = instanceClass.getConstructor(short.class);
			if(c==null)
				return instanceClass.newInstance();
			return c.newInstance(msgId);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public static Class<? extends IReceviceNetMsg> registerReceviceMsg(short msgId,Class<? extends IReceviceNetMsg> revMsg){
		
		if(revMsgs.containsKey(msgId)){
			throw new RuntimeException("消息id已被注册，请重新分配消息id。 msgId："+msgId);
		}
		
		return revMsgs.put(msgId, revMsg);
		
	}
	
}
