package javanio.tutorials;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 来源 https://nio.hacktons.cn
 * @author guox
 *
 */
public class BufferTest {
	public static void main(String[] args)  throws Exception{
		RandomAccessFile aFile = new RandomAccessFile("D:\\1.txt", "rw");
		FileChannel inChannel = aFile.getChannel();

		//create buffer with capacity of 48 bytes
		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = inChannel.read(buf); //read into buffer.
		while (bytesRead != -1) {

		  buf.flip();  //make buffer ready for read

		  while(buf.hasRemaining()){
		      System.out.print((char) buf.get()); // read 1 byte at a time
		  }

		  buf.clear(); //make buffer ready for writing
		  bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}
}
