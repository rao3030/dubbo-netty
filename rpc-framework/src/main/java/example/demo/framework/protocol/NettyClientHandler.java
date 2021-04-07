package example.demo.framework.protocol;

import example.demo.api.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private Invocation invocation;
    private String result;
    private ChannelHandlerContext context;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        synchronized (this) {
            notify();
        }
    }

    public  Object call() throws Exception {
        context.writeAndFlush(invocation);
        synchronized (this) {
            wait();
        }
        return result;
    }

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }
}
