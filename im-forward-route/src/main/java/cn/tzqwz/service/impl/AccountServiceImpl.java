package cn.tzqwz.service.impl;

import cn.tzqwz.cache.IMServerCache;
import cn.tzqwz.common.base.constants.IMConstants;
import cn.tzqwz.common.base.constants.RoteConstants;
import cn.tzqwz.common.exception.AccountExistException;
import cn.tzqwz.common.exception.AccountPasswordException;
import cn.tzqwz.common.exception.AccountRepeatLoginException;
import cn.tzqwz.common.utils.RedisUtils;
import cn.tzqwz.entity.IMAcountEntity;
import cn.tzqwz.service.AccountService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
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
        if(StringUtils.isEmpty(userid)){
            //为了方便查询，单独存储账户id和账户名称
            //1.随机生成一个userid
            long userId = System.currentTimeMillis();
            String key = IMConstants.ACCOUNT_PREFIX+"-"+userId;
            stringRedisTemplate.opsForValue().set(username,userId+"");
            stringRedisTemplate.opsForValue().set(key,username);
            IMAcountEntity imAcount = new IMAcountEntity();
            imAcount.setUserId(userId+"");
            imAcount.setUserName(username);
            imAcount.setPassword(password);
            RedisUtils.putObj(IMConstants.ACCOUNT_HASH_KEY_PREFIX+"-"+userId,imAcount);
            return userId+"";
        }
        throw new AccountExistException(userid);
    }

    /**
     * 登录IM账户
     * @param username
     * @param password
     * @return
     */
    public String loginIM(String username, String password) {
        //1.验证账户信息
        String userId = stringRedisTemplate.opsForValue().get(username);
        if(StringUtils.isEmpty(userId)){
           throw new AccountExistException("账户不存在");
        }
        String objJsonStr = RedisUtils.getObjJsonStr(IMConstants.ACCOUNT_HASH_KEY_PREFIX+"-"+userId);
        IMAcountEntity imAcountEntity = JSONObject.parseObject(objJsonStr, IMAcountEntity.class);
        if (!imAcountEntity.getPassword().equals(password)){
            throw new AccountPasswordException(userId+"");
        }
        //2.账户信息验证通过,保存账户登录状态(Set数据结构元素不能重复)
        boolean isLogin = RedisUtils.saveSet(RoteConstants.LOGIN_STATUS, userId);
        //如果返回false,说明重复登录
        if(!isLogin){
            throw new AccountRepeatLoginException(userId+"");
        }
        return userId;
    }

}
