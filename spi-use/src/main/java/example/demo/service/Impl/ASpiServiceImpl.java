package example.demo.service.Impl;

import com.alibaba.dubbo.common.extension.SPI;
import example.demo.service.SpiService;

public class ASpiServiceImpl implements SpiService {
    public ASpiServiceImpl() {
        System.out.println("---ASpiServiceImpl---");
    }

    @Override
    public String spiMethod(String name) {
        return "---A-SpiServiceImpl implements SpiService---";
    }
}
