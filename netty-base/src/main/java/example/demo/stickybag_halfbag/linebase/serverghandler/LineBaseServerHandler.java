package example.demo.stickybag_halfbag.linebase.serverghandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;


@ChannelHandler.Sharable
public class LineBaseServerHandler extends ChannelInboundHandlerAdapter {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端ip地址与port：[" + ctx.channel().remoteAddress() + "]已连接.........");
    }

    /*** 服务端读取到网络数据后的处理*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf) msg;
        String request = in.toString(CharsetUtil.UTF_8);

        System.out.println("the counter is:" + counter.incrementAndGet() + "\tServer Accept[" + request + "] and ");
        String resp = "Hello," + request + ". Welcome to Netty World!" + System.getProperty("line.separator");
        ctx.writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
    }

    /*** 服务端读取完成网络数据后的处理*/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-channelReadComplete-");
        //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "即将关闭...");
    }
}
