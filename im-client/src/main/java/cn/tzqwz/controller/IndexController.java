package cn.tzqwz.controller;

import cn.tzqwz.common.base.controller.BaseController;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.base.res.NULLResData;
import cn.tzqwz.common.dto.input.RegisterIMInDTO;
import cn.tzqwz.service.RoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端的相关操作
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {

    @Autowired
    private RoteRequest roteRequest;

    /**
     * 注册IM账户
     * @return
     */
    @PostMapping(value = "/registerIMAmcount")
    public BaseResponse<NULLResData> registerIMAmcount(@RequestBody RegisterIMInDTO registerIMInDTO){
        BaseResponse<NULLResData> response = roteRequest.registerIM(registerIMInDTO);
        return response;

    }



}
