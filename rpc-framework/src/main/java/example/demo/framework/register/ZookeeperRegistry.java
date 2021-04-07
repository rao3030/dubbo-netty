package example.demo.framework.register;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class ZookeeperRegistry {
    static ZooKeeper zooKeeper;
    //本地缓存
    private static final ConcurrentHashMap<String, Class<?>> services = new ConcurrentHashMap<>();

    static {
        connectZookeeper();
    }

    public static void connectZookeeper() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    latch.countDown();
                }
            });
            latch.await();
            System.out.println(Thread.currentThread().getName() + " connect zookeeper succeed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registry(String interfaceName, Class clazz) {
        services.put(interfaceName, clazz);
    }

    public static void getInterfaceClass(String interfaceName) {
        if (!services.containsKey(interfaceName)) {
            try {
                List<String> children = zooKeeper.getChildren("/rpc", false);
                if (children.contains(interfaceName)) {

                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void unRegistry(String interfaceName) {
        Class<?> remove = services.remove(interfaceName);
        remove = null;//help gc
    }

    public static void main(String[] args) {

    }
}
