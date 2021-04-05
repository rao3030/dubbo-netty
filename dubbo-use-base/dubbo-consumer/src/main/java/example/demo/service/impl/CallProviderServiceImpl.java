package example.demo.service.impl;

//import com.alibaba.dubbo.config.annotation.Reference;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericService;
import example.demo.entity.ConsumerDetails;
import example.demo.entity.ProviderDetails;
import example.demo.service.CallProviderService;
import example.demo.service.ProviderDetailsService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Future;

@Service
public class CallProviderServiceImpl implements CallProviderService, ApplicationContextAware {

    //com.alibaba.dubbo.common.bytecode.proxy0@5a24217d
//    @Reference
//    @Autowired
    @Resource(name = "providerDetailsService2")
    ProviderDetailsService providerDetailsService2;

    @Resource(name = "providerDetailsService3")
    ProviderDetailsService providerDetailsService3;
    @Autowired
    Environment environment;
    //    @Value("${dubbo.registry.address}")
    private String address;

    /**
     * <? 思考 consumer服务是怎么调用provider服务的ProviderDetailsService的实现类的method>
     * <todo 调用一个非static的method，是需要目标对象，调用的方法，该方法的参数>
     * consumer 可以传递一个Map  {"target":"bean的id" , "methodName","returnProviderDetails" ,"args","对应参数匹配类型的value"}
     * 将写个东西编码，通过网络传输到provider，provider拿到数据，进行解码，获取target去反射调用，将方法执行的结果，进行编码，通过网络传递给consumer
     * consumer在进行解码。
     *
     * @param consumerDetails
     * @return
     */
    @Override
    public ProviderDetails getProviderDetails(ConsumerDetails consumerDetails) {

        //com.alibaba.dubbo.common.bytecode.proxy0@7a064a65class
        // java.lang.Object
        // [interface com.alibaba.dubbo.common.bytecode.ClassGenerator$DC,
        // interface com.alibaba.dubbo.rpc.service.EchoService,
        // interface example.demo.service.ProviderDetailsService]
        System.out.println(address + "\t" + providerDetailsService2.toString()
                + providerDetailsService2.getClass().getSuperclass()
                + Arrays.toString(providerDetailsService2.getClass().getInterfaces()));
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return providerDetailsService2.returnProviderDetails(consumerDetails);
    }

    @Override
    public ProviderDetails async(String name) {
        ProviderDetails details = providerDetailsService2.returnASync(name);
        System.out.println("异步调用，没获取结果--------" + details);
        //ThreadLocal<Future>，多个异步调用后面的会覆盖前面的Future，所以先get完之后，再去异步调用其他服务接口。
        // (这个算异步调用?，这个异步调用不是很好,对于不用等待调用结果的异步调用就很好，对于要等待结果的就不是很好)
        Future<ProviderDetails> future = RpcContext.getContext().getFuture();
//        System.out.println(details);
        try {
            details = future.get();
            System.out.println("异步调用，获取结果--------" + details);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }

    @Override
    public String callbackMethodTest(Date date, int number) {
        return providerDetailsService3.callbackMethod(date, number);
    }

    //    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //这种方式肯定比Autowired
        this.applicationContext = applicationContext;
    }

    /**
     * todo 演示繁华调用，繁华调用可以不需要这个类，在这个服务里面
     *
     * @param name
     * @return
     */
    @Override
    public String unKnow(String name) {
        //泛化配置
        //<dubbo:reference id="unkonwnServie" interface="example.demo.service.UnKnowService" generic="true" version="1.0"/>
        //获取target
        GenericService service = (GenericService) applicationContext.getBean("unkonwnServie");
        //通过target调用目标方法。
        Object o = service.$invoke("unKnowMethod", new String[]{"java.lang.String"}, new Object[]{name});
        return o.toString();
    }


}
