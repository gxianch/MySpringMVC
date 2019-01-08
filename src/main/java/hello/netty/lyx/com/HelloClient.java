package hello.netty.lyx.com;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 1、Client向Server发送消息：Are you ok?
 * 2、Server接收客户端发送的消息，并打印出来。
 * 3、Server端向客户端发送消息：I am ok!
 * 4、Client接收Server端发送的消息，并打印出来，通讯结束。
 */

public class HelloClient {
	public void connect(String host, int port)  {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new HelloClientIntHandler());
				}
			});

			// Start the client.
			/**
			 * wait()方法：Waits for this future to be completed.
			 * Waits for this future until it is done, and rethrows the cause of the failure if this future
			 * failed.
			 */
			long t1 = System.currentTimeMillis();
			ChannelFuture f = b.connect(host, port).await();
			// Wait until the connection is closed.
			f.channel().closeFuture().await();    //closeFuture方法返回通道关闭的结果
			long t2 = System.currentTimeMillis();
			System.out.print("diff in seconds:" + (t2 - t1) / 1000 + "\n");

		} catch (InterruptedException e) {
			System.out.println("helloclient服务异常");
			e.printStackTrace();

			/**
			 * 给通道的关闭Future注册了监听事件，监听事件等这个关闭Future完成后打印了字符串，而客户端没有读取服务器的数据。
			 */
			/*
            long t1 = System.currentTimeMillis();
            ChannelFuture f = b.connect(host, port).await();
            f = f.channel().closeFuture();
            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("success complete!!ok!!");
                }
            });
            long t2 = System.currentTimeMillis();
            System.out.print("diff in seconds:" + (t2 - t1) / 1000 + "\n");
			 */

			/*      long t1 = System.currentTimeMillis();
            ChannelFuture f = b.connect(host, port).await();
            f = f.channel().closeFuture().await();
//            f.addListener(new ChannelFutureListener() {
//                public void operationComplete(ChannelFuture future) throws Exception {
//                    System.out.println("success complete!!ok!!");
//                }
//            });
            long t2 = System.currentTimeMillis();
            System.out.print("diff in seconds:" + (t2 - t1) / 1000 + "\n");
			 */
		} finally {
			workerGroup.shutdownGracefully();
		}

	}

	public static void main(String[] args)  {
		HelloClient client = new HelloClient();
		client.connect("127.0.0.1", 28100);
	}
}