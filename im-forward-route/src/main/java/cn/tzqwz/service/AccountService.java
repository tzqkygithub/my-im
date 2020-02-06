package cn.tzqwz.service;


import cn.tzqwz.cache.IMServerCache;
import cn.tzqwz.common.entity.IMServerAddressEntity;
import cn.tzqwz.entity.ChatReqEntity;

import java.util.List;
import java.util.Map;

/**
 * 账户相关操作业务层
 */
public interface AccountService {

    /**
     * 注册IM账户
     * @param username
     * @param password
     * @return
     */
     String registerIM(String username,String password);

    /**
     * 登录IM账户
     * @param username
     * @param password
     * @return
     */
     String loginIM(String username,String password);

    /**
     * 向服务器端发送消息
     * @param serverUrl
     * @param userId
     * @param chatReqEntity
     */
    void sendPush(String serverUrl, String userId, ChatReqEntity chatReqEntity);

    /**
     * 获取所有在线列表的路由关系
     * @return
     */
    Map<String, IMServerAddressEntity> loadRouteRelated();
}
