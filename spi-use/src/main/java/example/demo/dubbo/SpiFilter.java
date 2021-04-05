package example.demo.dubbo;

import com.alibaba.dubbo.common.extension.SPI;

import java.net.URL;

@SPI("C")
public interface SpiFilter {
    String filter(String url);
}
