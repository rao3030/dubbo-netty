package example.demo.consumer;

import example.demo.api.Invocation;
import example.demo.api.service.HelloService;
import example.demo.framework.protocol.NettyClient;

import java.util.Date;

public class ConsumerApp {
    public static void main(String[] args) throws Exception {
        Invocation invocation = new Invocation();
        invocation.setInterfaceName(HelloService.class.getName());
        invocation.setMethodName("sayHello");
        invocation.setParamTypes(new Class[]{java.lang.String.class, java.util.Date.class});
        invocation.setParams(new Object[]{"哇哈哈", new Date(System.currentTimeMillis() - 10000)});
        NettyClient nettyClient = new NettyClient();
        nettyClient.start("127.0.0.1", 9999);
        System.out.println(nettyClient.send(null, 0, invocation));
        System.in.read();
    }
}
