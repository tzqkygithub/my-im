package cn.tzqwz.service;


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

}
