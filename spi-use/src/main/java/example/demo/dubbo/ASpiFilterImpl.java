package example.demo.dubbo;

import com.alibaba.dubbo.common.extension.Activate;

import java.net.URL;

@Activate(group = {"A", "B"}, order = 1)
public class ASpiFilterImpl implements SpiFilter {
    public ASpiFilterImpl() {
        System.out.println("===ASpiFilterImpl===");
    }

    @Override
    public String filter(String url) {
        return "protocol==>" + url;
    }
}
