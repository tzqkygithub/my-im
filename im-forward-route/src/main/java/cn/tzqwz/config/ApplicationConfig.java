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

    public Integer getZkTimeOut() {
        return zkTimeOut;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public String getZkRootNode() {
        return zkRootNode;
    }

    public String getRouteAlgorithmWay() {
        return routeAlgorithmWay;
    }

    public Integer getWebServerPort() {
        return webServerPort;
    }

}
