package example.demo.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

public class JdkProxy {
    public static void main(String[] args) {
        JumpService jumpService = new JumpServiceImpl();
        Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{JumpService.class}, new InvocationHandler() {
            //method.invoke的第一个入参是Proxy.newProxyInstance的第二个入参的一个实例对象
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(Thread.currentThread().getName() + "\t" + method.getName());
//                return method.invoke(jumpService, args);
//                return method.invoke(JumpService.class.newInstance(),args);//失败的，接口不能实例化
                return method.invoke(new JumpService() {
                    @Override
                    public String jumpName(String name, int age) {
                        return "name:[" + name + "]\tage:[" + age + "]";
                    }

                    @Override
                    public void print(Date date) {
                        System.out.println(date);
                    }
                }, args);
            }
        });
        JumpService proxy = (JumpService) proxyInstance;
        String name = proxy.jumpName("name", 18);
        System.out.println(name);
    }
}
