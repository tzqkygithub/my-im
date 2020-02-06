package cn.tzqwz.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 配置Bean信息
 */
@Configuration
public class BeanConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 创建zkClient连接对象
     * @return
     */
    @Bean
    public ZkClient zkClient(){
        return new ZkClient(applicationConfig.getZkAddress(),applicationConfig.getZkTimeOut());
    }


}
