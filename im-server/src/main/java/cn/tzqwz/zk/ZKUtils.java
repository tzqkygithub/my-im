package cn.tzqwz.zk;

import cn.tzqwz.config.ApplicationConfig;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * zookeeper工具类
 */
@Component
public class ZKUtils {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 创建根节点
     */
    public String createRootNode(){
        //判断根节点是否存在
        boolean exists = zkClient.exists(applicationConfig.getZkRootNode());
        if(exists){
            //根节点存在不需要重新创建
            return applicationConfig.getZkRootNode();
        }
        //不存在根节点则创建根节点
        zkClient.createPersistent(applicationConfig.getZkRootNode()) ;
        return applicationConfig.getZkRootNode();
    }

    /**
     * 注册本地服务信息,临时目录
     * @return
     */
    public void registerServer(String imServerNode){
        zkClient.createEphemeral(imServerNode);
    }


}
