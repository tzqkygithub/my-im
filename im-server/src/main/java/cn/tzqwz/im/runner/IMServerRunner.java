package cn.tzqwz.im.runner;

import cn.tzqwz.config.ApplicationConfig;
import cn.tzqwz.im.server.IMServer;
import cn.tzqwz.zk.IMServerRegistration;
import cn.tzqwz.zk.ZKUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.rmi.registry.Registry;

/**
 * 项目启动执行类
 */
@Component
public class IMServerRunner implements ApplicationRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMServerRunner.class);


    @Autowired
    private IMServer imServer;

    /**
     *项目启动完成后,将服务信息注册到zookeeper上面
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //启动IM服务
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    imServer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("服务器启动失败");
                }
            }
        }).start();

    }
}
