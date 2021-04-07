package example.demo.framework.protocol;

import example.demo.api.Invocation;
import example.demo.api.service.impl.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;
        System.out.println(invocation);
        Class<?> aClass = Class.forName(invocation.getInterfaceName());
        Method method = aClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
        /*if (method.getModifiers() != Modifier.PUBLIC) {method.setAccessible(true); }*/
        Object invoke = method.invoke(new HelloServiceImpl(), invocation.getParams());
        ctx.writeAndFlush("netty-server: " + invoke);
        ctx.fireChannelRead(msg);
    }
}
