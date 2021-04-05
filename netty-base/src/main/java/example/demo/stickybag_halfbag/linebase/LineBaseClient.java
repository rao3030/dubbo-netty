package example.demo.stickybag_halfbag.linebase;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import example.demo.stickybag_halfbag.linebase.clienthandler.InfoClientHandler;
import example.demo.stickybag_halfbag.linebase.clienthandler.LineBaseClientHandler;

import java.net.InetSocketAddress;


public class LineBaseClient {

    private final String host;

    public LineBaseClient(String host) {
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            final Bootstrap b = new Bootstrap();
            /*客户端启动必须*/
            b.group(group)/*将线程组传入*/
                    .channel(NioSocketChannel.class)/*指定使用NIO进行网络传输*/
                    .remoteAddress(new InetSocketAddress(host, LineBaseServer.PORT))/*配置要连接服务器的ip地址和端口*/
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
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024))
                    .addLast(new InfoClientHandler())
                    .addLast(new LineBaseClientHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new LineBaseClient("127.0.0.1").start();
    }
}
