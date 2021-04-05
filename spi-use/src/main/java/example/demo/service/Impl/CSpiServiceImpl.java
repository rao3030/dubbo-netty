package example.demo.service.Impl;

import com.alibaba.dubbo.common.extension.SPI;
import example.demo.service.SpiService;
import lombok.NoArgsConstructor;

public class CSpiServiceImpl implements SpiService {
    public CSpiServiceImpl() {
        System.out.println("---CSpiServiceImpl---");
    }

    @Override
    public String spiMethod(String name) {
        return "---C-SpiServiceImpl implements SpiService---";
    }
}
