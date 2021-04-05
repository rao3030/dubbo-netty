package example.demo.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import example.demo.service.SpiService;

public class DubboSpi {
    public static void main(String[] args) {
        ExtensionLoader<SpiService> loader = ExtensionLoader.getExtensionLoader(SpiService.class);
        SpiService a = loader.getExtension("a");
        String res = a.spiMethod("wahaha");
        System.out.println(res);
        /*  @SPI("b")
            public interface SpiService {...}*/
        /*SpiService defaultExtension = loader.getDefaultExtension();
        String spiMethod = defaultExtension.spiMethod("---");
        System.out.println(spiMethod);
*/
        System.out.println(loader.getAdaptiveExtension());

    }
}
