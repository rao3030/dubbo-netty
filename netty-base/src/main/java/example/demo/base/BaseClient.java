package example.demo.base;

import example.demo.base.clientHandler.ClientInHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 类说明：基于Netty的客户端
 */
public class BaseClient {
    public static void main(String[] args) throws Exception {
        /*线程组*/
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            /*客户端启动必备*/
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 9999))
                    .handler(new ClientInHandler())
                    /*.handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientInHandler());
                        }
                    })*/
            ;
            /*异步连接到服务器，sync()会阻塞到完成*/
            ChannelFuture future = bootstrap.connect().sync();
            System.out.println("连接建立succeed");
            /*阻塞当前线程，直到客户端的Channel被关闭*/
            future.channel().closeFuture().sync();
            System.out.println("连接关闭succeed");
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
