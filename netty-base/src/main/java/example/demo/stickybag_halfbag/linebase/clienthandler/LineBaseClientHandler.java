package example.demo.stickybag_halfbag.linebase.clienthandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import example.demo.stickybag_halfbag.Constants;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * SimpleChannelInboundHandler-->channelRead0 模板设计模式-channelRead0是一个钩子方法.
 * 如果这个SimpleChannelInboundHandler实现类作用了，内部就会调用ReferenceCountUtil.release(msg);
 * 所以后面的ChannelInboundHandler，就拿不到了msg了
 */
public class LineBaseClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final Random random = new Random();

    /*** 客户端读取到网络数据后的处理*/
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("LineBaseClientHandler Accept[" + msg.toString(CharsetUtil.UTF_8)
                + "] and the counter is:" + counter.incrementAndGet());
        //一旦调用这个ctx.close()会触发一个事件--这个f.channel().closeFuture().sync();就会监听到这个事件，不阻塞当前线程，线程会玩下走了
//        ctx.close();
    }

    /*** 客户端被通知channel活跃后，做事*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            builder.append(i)
                    .append("生活在迷惘当中，不知哪里才是我想要的彼岸，只能在人生的道路上，撞撞跌跌，深陷那无尽de黑夜，而无光所寻。")
                    .append(Constants.values()[random.nextInt(Constants.values().length)].getSrc())
                    .append(System.lineSeparator());
        }
        ctx.writeAndFlush(Unpooled.copiedBuffer((builder.toString()).getBytes(StandardCharsets.UTF_8)));
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
