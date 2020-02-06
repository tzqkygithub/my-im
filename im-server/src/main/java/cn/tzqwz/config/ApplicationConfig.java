package cn.tzqwz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 基本配置信息
 */
@Component
public class ApplicationConfig {

    @Value("${zk.connect.address}")
    private String zkAddress;

    @Value("${server.port}")
    private Integer webServerPort;

    @Value("${zk.connect.timeout}")
    private Integer zkTimeOut;

    @Value("${im.server.port}")
    private Integer imServerPort;

    @Value("${zk.root.node}")
    private String zkRootNode;





    public Integer getZkTimeOut() {
        return zkTimeOut;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public Integer getImServerPort() {
        return imServerPort;
    }

    public String getZkRootNode() {
        return zkRootNode;
    }

    public Integer getWebServerPort() {
        return webServerPort;
    }
}
