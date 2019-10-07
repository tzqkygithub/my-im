package cn.tzqwz.zk;

import cn.tzqwz.common.utils.SpringUtils;
import cn.tzqwz.config.ApplicationConfig;
import cn.tzqwz.im.runner.IMServerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 服务注册
 */
@Component
public class IMServerRegistration {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMServerRegistration.class);

    private String hostAddress;
    private Integer imServerPort;
    private Integer webServerPort;

    private ZKUtils zkUtils;

    @Autowired
    private ApplicationConfig applicationConfig;


    /**
     * 进行服务注册
     */
    public void serverRegister() {
        LOGGER.info("开始注册服务信息");
        ZKUtils zkUtils = SpringUtils.getBean(ZKUtils.class);
        //获取本地IP地址
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Integer imServerPort = applicationConfig.getImServerPort();
        Integer webServerPort = applicationConfig.getWebServerPort();
        //创建头结点
        String rootNode = zkUtils.createRootNode();
        //将本地服务信息注册到zk上
        String imServerNode = rootNode+"/ip-"+""+hostAddress+":"+imServerPort+":"+webServerPort;
        zkUtils.registerServer( imServerNode);
        LOGGER.info("服务节点注册到Zookeeper成功({})",imServerPort);
    }
}
