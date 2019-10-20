package cn.tzqwz.service.impl;

import cn.tzqwz.common.base.constants.IMConstants;
import cn.tzqwz.entity.IMAcountEntity;
import cn.tzqwz.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 账户相关操作的实现类
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    /**
     * 注册IM账户
     * @param username
     * @param password
     * @return
     */
    public String registerIM(String username, String password) {
        //2.判断指定账户的Key是否存
        String userid = stringRedisTemplate.opsForValue().get(username);
        //如果不存在，则将账户信息存储到redis中
        if(userid==null){
            //为了方便查询，单独存储账户id和账户名称
            //1.随机生成一个userid
            long userId = System.currentTimeMillis();
            String key = IMConstants.ACCOUNT_PREFIX+userId;
            stringRedisTemplate.opsForValue().set(username,key);
            stringRedisTemplate.opsForValue().set(key,username);
            IMAcountEntity imAcount = new IMAcountEntity();
            imAcount.setUserId(userId+"");
            imAcount.setUserName(username);
            imAcount.setPassword(password);
            redisTemplate.opsForHash().put(IMConstants.ACCOUNT_HASH_KEY_PREFIX,key,imAcount);
            return userId+"";
        }
        throw new RuntimeException(userid);
    }
}
