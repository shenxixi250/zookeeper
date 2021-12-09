package org.apache.zookeeper;

import org.apache.yetus.audience.InterfaceAudience;
//import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@InterfaceAudience.Public
public class OriginalZookeeper  implements Watcher{
    private static final Logger LOG = LoggerFactory.getLogger(OriginalZookeeper.class);
//    private static Stat stat = new Stat();
    @Override
    public void process(WatchedEvent event) {
        System.out.println("回调接口");
        System.out.println(event.toString());
    }
    public static void main(String[] args) throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 20000000, new OriginalZookeeper());
//        byte [] data = zooKeeper.getData("/shenxixi",new OriginalZookeeper(),stat);
//        System.out.println(new String(data));
        System.out.println("222");
    }

}