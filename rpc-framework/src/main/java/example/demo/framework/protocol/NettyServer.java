package example.demo.framework.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;

public class NettyServer {
    public static void main(String[] args) throws Exception {

        NioEventLoopGroup boss = new NioEventLoopGroup(1, new DefaultThreadFactory("boss", true));
        NioEventLoopGroup worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2, new DefaultThreadFactory("worker", true));
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(9999))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //ObjectDecoder extends LengthFieldBasedFrameDecoder，所以自带了tcp的粘包半包处理机制
                            pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(NettyServer.class.getClassLoader())))
                                    .addLast("encoder", new ObjectEncoder())
                                    .addLast("handler", new NettyServerHandler())
                            ;
                        }
                    });
            /*异步绑定到服务器，sync()会阻塞到完成*/
            ChannelFuture future = bootstrap.bind().sync();
            /*阻塞当前线程，直到服务器的ServerChannel被关闭*/
            future.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }
}
