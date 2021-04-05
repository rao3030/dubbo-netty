package example.demo.dubbo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

import java.util.List;

/**
 * 使用dubbo的spi
 * 一个是条件激活 Active 使用group/value方式来过滤实现类
 * 一个是Adaptive指定一个实现类
 */
//https://dubbo.apache.org/zh/docs/v2.7/dev/source/dubbo-spi/ 官网关于spi的解析
public class DubboSpiTest {
    public static void main(String[] args) {
        ExtensionLoader<SpiFilter> loader = ExtensionLoader.getExtensionLoader(SpiFilter.class);
        URL url = URL.valueOf("test://localhost/test");
//        url = url.addParameter("groupId", 123);
        url = url.addParameter("use", "A,-B");//等同于"+A,-B" 虽然A，B都会反射实例化，但是getActivateExtension不会获取group有B的
        //todo 获取一个同组的实例对象
        List<SpiFilter> filters = loader.getActivateExtension(url, "use", "A");
        for (SpiFilter filter : filters) {
            String res = filter.filter("http://123456.000");
            System.out.println(res+filter);
        }
        //@Adaptive 只能标识一个
        //public class BSpiFilterImpl implements SpiFilter {...}
        //@SPI("C") 如果没有@Adaptive 就看接口的@SPI
        //public interface SpiFilter {}
        SpiFilter spiFilter = loader.getAdaptiveExtension();
        String result = spiFilter.filter("哇嘎嘎嘎");
        System.out.println(result+spiFilter.toString());

    }
}
