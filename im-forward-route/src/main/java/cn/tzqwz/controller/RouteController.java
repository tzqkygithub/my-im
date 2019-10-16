package cn.tzqwz.controller;

import cn.tzqwz.common.base.controller.BaseController;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.base.res.NULLResData;
import cn.tzqwz.common.dto.input.RegisterIMInDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *路由操作相关API
 */
@RestController
@RequestMapping("/")
public class RouteController extends BaseController {

    /**
     * 注册IM账户
     * @return
     */
    public BaseResponse<NULLResData> registerImAmcount(@RequestBody RegisterIMInDTO registerIMInDTO){
        return setResultSuccess(null,null);
    }
}
