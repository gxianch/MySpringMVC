package javanio.tutorials;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {
	public static void main(String[] args) throws IOException{
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("localhost", 9999));

		while(! socketChannel.finishConnect() ){
		    //wait, or do something else...
			String newData = "New String to write to file..." + System.currentTimeMillis();

			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			buf.put(newData.getBytes());

			buf.flip();

			while(buf.hasRemaining()) {
				socketChannel.write(buf);
			}
		}
	}
}
