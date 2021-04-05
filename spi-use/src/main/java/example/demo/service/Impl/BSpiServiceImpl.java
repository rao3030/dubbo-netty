package example.demo.service.Impl;

import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;
import example.demo.service.SpiService;
import lombok.NoArgsConstructor;

@Adaptive
public class BSpiServiceImpl implements SpiService {
    public BSpiServiceImpl() {
        System.out.println("---BSpiServiceImpl---");
    }

    @Override
    public String spiMethod(String name) {
        return "---B-SpiServiceImpl implements SpiService---";
    }
}
