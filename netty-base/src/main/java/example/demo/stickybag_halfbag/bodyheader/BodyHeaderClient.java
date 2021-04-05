package example.demo.stickybag_halfbag.bodyheader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.net.InetSocketAddress;


public class BodyHeaderClient {

    private final String host;

    public BodyHeaderClient(String host) {
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            final Bootstrap b = new Bootstrap();;/*客户端启动必须*/
            b.group(group)/*将线程组传入*/
                    .channel(NioSocketChannel.class)/*指定使用NIO进行网络传输*/
                    .remoteAddress(new InetSocketAddress(host, BodyHeaderServer.PORT))/*配置要连接服务器的ip地址和端口*/
                    .handler(new ChannelInitializerImp());
            ChannelFuture f = b.connect().sync();
            System.out.println("已连接到服务器.....");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            /*粘包半包问题*/
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,
                    0,2,0,
                    2));
            ch.pipeline().addLast(new LengthFieldPrepender(2));
            ch.pipeline().addLast(new BodyHeaderClientHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new BodyHeaderClient("127.0.0.1").start();
    }
}
