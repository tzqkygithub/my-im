package cn.tzqwz.controller;

import cn.tzqwz.cache.IMServerCache;
import cn.tzqwz.common.base.constants.BaseConstants;
import cn.tzqwz.common.base.controller.BaseController;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.base.res.NULLResData;
import cn.tzqwz.common.dto.input.LoginImInDTO;
import cn.tzqwz.common.dto.input.RegisterIMInDTO;
import cn.tzqwz.common.exception.AccountExistException;
import cn.tzqwz.common.exception.AccountPasswordException;
import cn.tzqwz.common.exception.AccountRepeatLoginException;
import cn.tzqwz.common.route.algorithm.RouteAlgorithmHandle;
import cn.tzqwz.config.ApplicationConfig;
import cn.tzqwz.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *路由操作相关API
 */
@RestController
@RequestMapping("/")
public class RouteController extends BaseController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private IMServerCache imServerCache;

    @Autowired
    private RouteAlgorithmHandle routeAlgorithmHandle;

    /**
     * 注册IM账户
     * @return
     */
    @PostMapping(value = "/registerImAmcount")
    public BaseResponse<String> registerIMAmcount(@RequestBody RegisterIMInDTO registerIMInDTO){
        //校验参数
        String userName = registerIMInDTO.getUserName();
        if(StringUtils.isEmpty(userName)){
            return setResultError("输入的账户名称不能为空",null);
        }
        String password = registerIMInDTO.getPassword();
        if(StringUtils.isEmpty(password)){
            return setResultError("输入的密码不能为空",null);
        }
        //校验通过调用账户业务方法完成注册操作
        try{
            String userId = accountService.registerIM(userName, password);
            return setResultSuccess(userId);
        }catch (AccountExistException e){
            return setResultError(BaseConstants.HTTP_RES_CODE_203,"账户已经存在了",e.getMessage());
        }

    }

    /**
     * 登录IM账户并获取IMServer服务器
     * @param loginImInDTO
     * @return
     */
    @PostMapping("/getIMServer")
    public BaseResponse<String> getIMServer(@RequestBody LoginImInDTO loginImInDTO){
        //校验参数
        String userName = loginImInDTO.getUserName();
        if(StringUtils.isEmpty(userName)){
            return setResultError("输入的账户名称不能为空",null);
        }
        String password = loginImInDTO.getPassword();
        if(StringUtils.isEmpty(password)){
            return setResultError("输入的密码不能为空",null);
        }
        String userId = null;
        //完成登录操作
        try {
           userId = accountService.loginIM(userName, password);
        }catch (AccountExistException existException){
            //账户不存在
            return setResultError(BaseConstants.HTTP_RES_CODE_204,existException.getMessage(),null);
        }catch (AccountPasswordException passwordException){
            //账户密码操作
            return setResultError(BaseConstants.HTTP_RES_CODE_204,"账户密码错误",passwordException.getMessage());
        }catch(AccountRepeatLoginException loginException){
            //账户重复登录
            return setResultError(BaseConstants.HTTP_RES_CODE_206,"账户重复登录",loginException.getMessage());
        }
        //获取服务器列表
        List<String> imServerList = imServerCache.findZKServerNode(applicationConfig.getZkRootNode());
        if(imServerList.size()==0){
            return setResultError("IM服务器列表不能为空",userId);
        }
        String serverAddress = routeAlgorithmHandle.routeServer(imServerList, userId);
        return setResultSuccess(serverAddress,userId);
    }
}
