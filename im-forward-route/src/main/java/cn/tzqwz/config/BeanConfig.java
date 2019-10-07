package cn.tzqwz.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bean注入到spring容器当中
 */
@Configuration
public class BeanConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 创建zk连接对象
     * @return
     */
    @Bean
    public ZkClient zkClient(){
      return new ZkClient(applicationConfig.getZkAddress(),applicationConfig.getZkTimeOut());
    }

    /**
     * 创建缓存对象
     * @return
     */
    @Bean
    public LoadingCache loadingCache(){
        return CacheBuilder.newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return null;
                    }
                });
    }
}
