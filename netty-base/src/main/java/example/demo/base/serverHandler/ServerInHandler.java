package example.demo.base.serverHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

@ChannelHandler.Sharable
public class ServerInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(byteBuf.toString(StandardCharsets.UTF_8));
        byteBuf.release();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            builder.append(i).append("生活在迷惘当中，不知哪里才是我想要的彼岸，只能在人生的道路上，撞撞跌跌，深陷那无尽de黑夜，而无光所寻。\n");
        }
        ctx.writeAndFlush(Unpooled.copiedBuffer((builder.toString()).getBytes(StandardCharsets.UTF_8)));
    }
}
