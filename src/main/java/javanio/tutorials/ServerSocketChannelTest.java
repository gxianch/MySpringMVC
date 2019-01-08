package javanio.tutorials;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelTest {
	public static void main(String[] args) throws Exception{
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
		serverSocketChannel.configureBlocking(false);

		while(true){
		    SocketChannel socketChannel = serverSocketChannel.accept();

		    if(socketChannel != null){
		        //do something with socketChannel...
		    	ByteBuffer buf = ByteBuffer.allocate(48);
		    	int bytesRead = socketChannel.read(buf);
		    	while (bytesRead != -1) {

		    		  buf.flip();  //make buffer ready for read

		    		  while(buf.hasRemaining()){
		    		      System.out.print((char) buf.get()); // read 1 byte at a time
		    		  }

		    		  buf.clear(); //make buffer ready for writing
		    		  bytesRead = socketChannel.read(buf);
		    		}
		    }
		}
	}
}
