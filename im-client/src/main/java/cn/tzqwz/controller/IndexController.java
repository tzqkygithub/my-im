package cn.tzqwz.controller;

import cn.tzqwz.common.base.controller.BaseController;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.base.res.NULLResData;
import cn.tzqwz.common.dto.input.RegisterIMInDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端的相关操作
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController<NULLResData> {

    /**
     * 注册IM账户
     * @return
     */
    @PostMapping(value = "/registerIMAmcount")
    public BaseResponse<NULLResData> registerIMAmcount(@RequestBody RegisterIMInDTO registerIMInDTO){
        return setResultSuccess(null);
    }
}
