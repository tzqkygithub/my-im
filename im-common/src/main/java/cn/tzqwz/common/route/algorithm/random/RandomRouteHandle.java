package cn.tzqwz.common.route.algorithm.random;

import cn.tzqwz.common.route.algorithm.RouteAlgorithmHandle;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机进行路由
 */
public class RandomRouteHandle implements RouteAlgorithmHandle {

    /**
     * 随机对服务器列表进行路由
     * @param serverList
     * @param clientId
     * @return
     */
    public String routeServer(List<String> serverList, String clientId) {
        if(serverList.size()==0){
            throw new RuntimeException("IM服务器列表不能为空");
        }
        int index = ThreadLocalRandom.current().nextInt(serverList.size());
        return serverList.get(index);
    }
}
