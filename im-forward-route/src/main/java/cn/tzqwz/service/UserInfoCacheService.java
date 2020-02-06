package cn.tzqwz.service;

import cn.tzqwz.entity.IMAcountEntity;

/**
 *用户的相关操作
 */
public interface UserInfoCacheService {
    /**
     * 根据用户id获取账户实体信息
     * @param userId
     * @return
     */
    IMAcountEntity getUserIdByAccount(String userId);
}
