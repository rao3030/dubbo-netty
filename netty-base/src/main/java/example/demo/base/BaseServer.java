package example.demo.base;

import example.demo.base.serverHandler.ServerInHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 类说明：基于Netty的服务器
 */
public class BaseServer {
    public static void main(String[] args) throws Exception {
        /*线程组*/
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        final ServerInHandler serverHandler = new ServerInHandler();

        try {
            /*服务端启动必备*/
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    /*指定使用NIO的通信模式*/
                    .channel(NioServerSocketChannel.class)
                    /*指定监听端口*/
                    .localAddress(new InetSocketAddress(9999))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    })
            ;
            /*异步绑定到服务器，sync()会阻塞到完成*/
            ChannelFuture future = bootstrap.bind().sync();
            /*阻塞当前线程，直到服务器的ServerChannel被关闭*/
            future.channel().closeFuture().sync();
        } finally {
            /*线程组关闭*/
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();

        }
    }
}
