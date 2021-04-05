package example.demo.service;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * todo jdk使用spi的话子类必须要有无参构造
 * resources/META-INF/services/example.demo.service.SpiService
 */
@SPI
public interface SpiService {
    String spiMethod(String name);
}
