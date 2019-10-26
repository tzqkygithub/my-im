package cn.tzqwz.common.route.algorithm.loop;

import cn.tzqwz.common.route.algorithm.RouteAlgorithmHandle;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 轮询进行路由
 */
public class LoopRouteHandle implements RouteAlgorithmHandle {

    AtomicLong atomicLong = new AtomicLong();//原子类，为了保证线程安全问题


    /**
     * 轮询进行路由
     * @param serverList
     * @param clientId
     * @return
     */
    public String routeServer(List<String> serverList, String clientId) {
        //1.判断服务器列表是否为空
        if(serverList.size()==0){
            throw new RuntimeException("IM服务器列表不能为空");
        }
        //2.算出索引
        Long index = atomicLong.incrementAndGet() % serverList.size();
        if(index<0l){
           index = 0l;
        }
        return serverList.get(index.intValue());
    }
}
