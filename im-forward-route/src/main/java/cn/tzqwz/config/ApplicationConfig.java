package cn.tzqwz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 路由服务的基本配置信息
 */
@Component
public class ApplicationConfig {

    @Value("${zk.connect.address}")
    private String zkAddress;

    @Value("${server.port}")
    private Integer webServerPort;

    @Value("${zk.connect.timeout}")
    private Integer zkTimeOut;


    @Value("${zk.root.node}")
    private String zkRootNode;

    @Value("${route.algorithm.way}")
    private String routeAlgorithmWay;

    @Value("${threadPool.corePoolSize}")
    private Integer threadCorePoolSize; //线程池核心线程数

    @Value("${threadPool.maxPoolSize}")
    private Integer threadMaxPoolSize; //线程池最大线程数

    @Value("${threadPool.queueCapacity}")
    private Integer threadQueueCapacity; //线程池消息队列容量

    public Integer getZkTimeOut() {
        return zkTimeOut;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public String getZkRootNode() {
        return zkRootNode;
    }

    public Integer getThreadCorePoolSize() {
        return threadCorePoolSize;
    }

    public Integer getThreadMaxPoolSize() {
        return threadMaxPoolSize;
    }

    public Integer getThreadQueueCapacity() {
        return threadQueueCapacity;
    }

    public String getRouteAlgorithmWay() {
        return routeAlgorithmWay;
    }

    public Integer getWebServerPort() {
        return webServerPort;
    }

}
