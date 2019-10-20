package cn.tzqwz.controller;

import cn.tzqwz.common.base.constants.BaseConstants;
import cn.tzqwz.common.base.controller.BaseController;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.base.res.NULLResData;
import cn.tzqwz.common.dto.input.RegisterIMInDTO;
import cn.tzqwz.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *路由操作相关API
 */
@RestController
@RequestMapping("/")
public class RouteController extends BaseController {

    @Autowired
    private AccountService accountService;

    /**
     * 注册IM账户
     * @return
     */
    @PostMapping("/registerImAmcount")
    public BaseResponse<NULLResData> registerImAmcount(@RequestBody RegisterIMInDTO registerIMInDTO){
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
        }catch (RuntimeException e){
            return setResultError(BaseConstants.HTTP_RES_CODE_203,"账户已经存在了",NULLResData.createNULLRespData(),e.getMessage());
        }

    }
}
