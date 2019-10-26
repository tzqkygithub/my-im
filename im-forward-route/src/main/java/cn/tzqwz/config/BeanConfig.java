package cn.tzqwz.config;

import cn.tzqwz.common.route.algorithm.RouteAlgorithmHandle;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

    /**
     * 创建redisTemplate模板对象
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 创建路由算法对象
     * @return
     */
    @Bean
    public RouteAlgorithmHandle getRouteAlgorithmHandle(){
        Object routeAlgorithmObj = null;
        try {
            Class<?> routeObjClass = Class.forName(applicationConfig.getRouteAlgorithmWay());
           routeAlgorithmObj = routeObjClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return (RouteAlgorithmHandle) routeAlgorithmObj;
    }


}
