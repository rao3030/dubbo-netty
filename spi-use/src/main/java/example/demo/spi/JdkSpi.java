package example.demo.spi;

import example.demo.service.SpiService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * spi的本质就是反射罢了
 */
public class JdkSpi {
    public static void main(String[] args) {
        ServiceLoader<SpiService> services = ServiceLoader.load(SpiService.class);
//        Iterator<SpiService> iterator = services.iterator();
        for (SpiService service : services) {
            String result = service.spiMethod("哇哈哈");
            System.out.println(result);
        }
    }
}
