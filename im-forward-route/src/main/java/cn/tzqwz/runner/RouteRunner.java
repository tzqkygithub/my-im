package cn.tzqwz.runner;


import cn.tzqwz.config.ApplicationConfig;
import cn.tzqwz.zk.ZKUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RouteRunner implements ApplicationRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(RouteRunner.class);

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private ZKUtils zkUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String zkRootNode = applicationConfig.getZkRootNode();
        //给根节点注册监听事件
        LOGGER.info("根节点[{}]注册监听事件",zkRootNode);
        zkUtils.subscribeEvent(zkRootNode);
        LOGGER.info("根节点[{}]注册监听事件成功");
    }
}
