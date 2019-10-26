package cn.tzqwz.common.route.algorithm;

import java.util.List;

/**
 * 路由算法接口
 */
public interface RouteAlgorithmHandle {

    /**
     * 对客户端连接服务器进行路由
     * @param serverList
     * @param clientId
     * @return
     */
    String routeServer(List<String> serverList,String clientId);
}
