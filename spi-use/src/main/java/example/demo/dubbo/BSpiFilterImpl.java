package example.demo.dubbo;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.extension.Adaptive;

import java.net.URL;

@Activate(group = {"A"}, order = 2)
@Adaptive
public class BSpiFilterImpl implements SpiFilter {
    public BSpiFilterImpl() {
        System.out.println("===BSpiFilterImpl===");
    }

    @Override
    public String filter(String url) {
        return "host==>" + url;
    }
}
