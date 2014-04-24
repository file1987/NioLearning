package my.file.test.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class MinaServer {
	
	private final int port;
	private final IoAcceptor acceptor;
	public MinaServer(){
		this(9000);
	}
	
	public MinaServer(int port){
		this.port = port;
		// Create the acceptor
		acceptor = new NioSocketAcceptor();
	}
	
	public void startUp() throws IOException{
        acceptor.getFilterChain().addLast("decoder", new MinaBytesDecoder());
        acceptor.getFilterChain().addLast("encoder", new MinaMsgEncoder());
        acceptor.setHandler(new MinaServerHandle());
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        acceptor.bind(new InetSocketAddress(port));
	}
	
	public void close(){
		acceptor.dispose();;
	}
	
	

}
