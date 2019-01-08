package javanio.tutorials;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Select {
	public static void main(String[] args) throws Exception{
//创建一个Selector可以通过Selector.open()方法：
		Selector selector = Selector.open();
		
		//注册Channel到Selector上(Registering Channels with the Selector)
		//Channel必须是非阻塞的。所以FileChannel不适用Selector，
		//因为FileChannel不能切换为非阻塞模式。Socket channel可以正常使用。
//		注意register的第二个参数，这个参数是一个“关注集合”，代表我们关注的channel状态，有四种基础类型可供监听：
//		Connect
//		Accept
//		Read
//		Write
//		一个channel触发了一个事件也可视作该事件处于就绪状态。因此当channel与server连接成功后，那么就是“连接就绪”状态。server channel接收请求连接时处于“可连接就绪”状态。channel有数据可读时处于“读就绪”状态。channel可以进行数据写入时处于“写就绪”状态。
//
//		上述的四种就绪状态用SelectionKey中的常量表示如下：
//		SelectionKey.OP_CONNECT
//		SelectionKey.OP_ACCEPT
//		SelectionKey.OP_READ
//		SelectionKey.OP_WRITE
//		如果对多个事件感兴趣可利用位的或运算结合多个常量，比如：
//		int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
		
		SocketChannel channel = SocketChannel.open();
		channel.connect(new InetSocketAddress("http://jenkov.com", 80));
		channel.configureBlocking(false);
		SelectionKey key = channel.register(selector, SelectionKey.OP_READ);


		while(true) {

		  int readyChannels = selector.select();

		  if(readyChannels == 0) continue;


		  Set<SelectionKey> selectedKeys = selector.selectedKeys();

		  Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

		  while(keyIterator.hasNext()) {

		     key = keyIterator.next();

		    if(key.isAcceptable()) {
		        // a connection was accepted by a ServerSocketChannel.
		    	System.out.println("accepted");
		    } else if (key.isConnectable()) {
		        // a connection was established with a remote server.
		    	System.out.println("established");

		    } else if (key.isReadable()) {
		        // a channel is ready for reading
		    	System.out.println("ready");

		    } else if (key.isWritable()) {
		        // a channel is ready for writing
		    	System.out.println("a channel is ready for writing");
		    }

		    keyIterator.remove();
		  }
		}
	}
}
