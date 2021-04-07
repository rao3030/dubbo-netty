package example.demo.framework.protocol;

import example.demo.api.Invocation;
import example.demo.consumer.ConsumerApp;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;

/**
 * 类说明：基于Netty的客户端
 */
public class NettyClient {

    public NettyClientHandler clientHandler;

    public void start(String hostname, int port) throws InterruptedException {
        /*线程组*/
        EventLoopGroup group = new NioEventLoopGroup();
        clientHandler = new NettyClientHandler();
        try {
            /*客户端启动必备*/
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .remoteAddress(new InetSocketAddress(hostname, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(NettyServer.class.getClassLoader())))
                                    .addLast("encoder", new ObjectEncoder())
                                    .addLast("handler", clientHandler)
                            ;
                        }
                    })
            ;
            ChannelFuture future = bootstrap.connect().sync();
            System.out.println("111");
//            future.channel().closeFuture().sync();
        } finally {
//            group.shutdownGracefully().sync();
        }
    }

    public Object send(String hostname, int port, Invocation invocation) throws Exception {
        clientHandler.setInvocation(invocation);
        return clientHandler.call();
    }
}
