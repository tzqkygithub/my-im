package cn.tzqwz.service.impl;


import cn.tzqwz.cache.IMServerCache;
import cn.tzqwz.common.base.constants.IMConstants;
import cn.tzqwz.common.base.constants.RoteConstants;
import cn.tzqwz.common.entity.IMServerAddressEntity;
import cn.tzqwz.common.exception.AccountExistException;
import cn.tzqwz.common.exception.AccountPasswordException;
import cn.tzqwz.common.exception.AccountRepeatLoginException;
import cn.tzqwz.common.route.algorithm.RouteAlgorithmHandle;
import cn.tzqwz.common.utils.OKHttpUtils;
import cn.tzqwz.common.utils.RedisUtils;
import cn.tzqwz.config.ApplicationConfig;
import cn.tzqwz.entity.ChatReqEntity;
import cn.tzqwz.entity.IMAcountEntity;
import cn.tzqwz.service.AccountService;
import cn.tzqwz.service.UserInfoCacheService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户相关操作的实现类
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);



    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserInfoCacheService userInfoCacheService;


    @Autowired
    private IMServerCache imServerCache;

    @Autowired
    private OKHttpUtils okHttpUtils;

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

    /**
     * 向服务器端发送消息
     * @param serverUrl
     * @param userId
     * @param chatReqEntity
     */
    @Override
    public void sendPush(String serverUrl, String userId, ChatReqEntity chatReqEntity) {
        //获取消息发送的实体类
        IMAcountEntity userIdByAccount = userInfoCacheService.getUserIdByAccount(userId);
        if(userIdByAccount==null){
             throw new RuntimeException("系统异常");
        }
        //拼接发送消息
        String msg = userIdByAccount.getUserName()+":"+chatReqEntity.getMsg();
        chatReqEntity.setMsg(msg);
        String jsonString = JSONObject.toJSONString(chatReqEntity);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        okHttpUtils.createPostJSONReq(serverUrl,jsonObject);
    }

    /**
     * 获取所有在线列表的路由关系
     * @return
     */
    @Override
    public Map<String, IMServerAddressEntity> loadRouteRelated() {
        //1.获取所有用户的列表
        Map<String,IMServerAddressEntity> routes = new HashMap<>(64);
        RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
        ScanOptions options = ScanOptions.scanOptions()
                .match(RoteConstants.IM_ACCOUNT + "*")
                .build();
        Cursor<byte[]> scan = connection.scan(options);

        while (scan.hasNext()) {
            byte[] next = scan.next();
            String key = new String(next, StandardCharsets.UTF_8);
            LOGGER.info("key={}", key);
            parseServerInfo(routes, key);

        }
        try {
            scan.close();
        } catch (IOException e) {
            LOGGER.error("IOException",e);
        }
        return routes;
    }

    private void parseServerInfo(Map<String, IMServerAddressEntity> routes, String key) {
        String[] split = key.split("-");
        String receiverUserId = split[2];
        String routeServerKey = RoteConstants.ROUTE_SERVER+"-"+receiverUserId;
        String serverAddress = stringRedisTemplate.opsForValue().get(routeServerKey);
        IMServerAddressEntity imServerAddressEntity = new IMServerAddressEntity(serverAddress);
        routes.put(receiverUserId,imServerAddressEntity);
    }


}
