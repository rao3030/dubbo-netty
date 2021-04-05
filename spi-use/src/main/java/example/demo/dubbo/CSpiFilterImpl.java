package example.demo.dubbo;

import com.alibaba.dubbo.common.extension.Activate;

import java.net.URL;

@Activate(group = {"C"}, order = 1)
public class CSpiFilterImpl implements SpiFilter {
    public CSpiFilterImpl() {
        System.out.println("===CSpiFilterImpl===");
    }

    @Override
    public String filter(String url) {
        return "path==>" + url;
    }
}
