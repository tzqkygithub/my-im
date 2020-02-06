package cn.tzqwz.service.impl;

import cn.tzqwz.common.utils.RedisUtils;
import cn.tzqwz.entity.IMAcountEntity;
import cn.tzqwz.service.UserInfoCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户相关操作业务类
 */
@Service
public class UserInfoCacheServiceImpl implements UserInfoCacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Map<String ,IMAcountEntity> accountCacheMap = new ConcurrentHashMap<String,IMAcountEntity>();

    /**
     * 根据用户id获取账户实体类相关的操作类
     * @param userId
     * @return
     */
    public IMAcountEntity getUserIdByAccount(String userId) {
        //先从JVM缓存中去读取数据
        IMAcountEntity cacheAccount = accountCacheMap.get(userId);
        //如果没有再从redis中去读取数据
        if(cacheAccount==null) {
            String userName = stringRedisTemplate.opsForValue().get("im-account-" + userId);
            if (userName != null) {
                IMAcountEntity imAcountEntity = new IMAcountEntity();
                imAcountEntity.setUserId(userId);
                imAcountEntity.setUserName(userName);
                return imAcountEntity;
            }
            throw new RuntimeException("没有找到用户:"+userId+"的信息");
        }

        return cacheAccount;
    }
}
