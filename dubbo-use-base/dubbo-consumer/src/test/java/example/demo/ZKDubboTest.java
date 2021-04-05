package example.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class ZKDubboTest {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("---connected succeed---");
                latch.countDown();
            }
        });
        latch.await();
        final String dubboNodeName = "/dubbo";
        List<String> interfaceNames = zooKeeper.getChildren(dubboNodeName, false);
        if (interfaceNames != null && interfaceNames.size() != 0) {
            for (String interfaceName : interfaceNames) {
                // /dubbo/XXXService
                String interfaceNodeName = dubboNodeName + "/" + interfaceName;
                List<String> children = zooKeeper.getChildren(interfaceNodeName, false);
                String consumers = children.get(0);
                String providers = children.get(3);
                zooKeeper.getChildren(interfaceNodeName + "/" + consumers, false).stream().map(s -> interfaceName+"的调用方--"+s).forEach(System.out::println);
                zooKeeper.getChildren(interfaceNodeName + "/" + providers, false).stream().map(s -> interfaceName+"的提供方--"+s).forEach(System.out::println);
            }
        }
    }

}
