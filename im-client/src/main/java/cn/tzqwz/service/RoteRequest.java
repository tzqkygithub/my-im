package cn.tzqwz.service;

import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.base.res.NULLResData;
import cn.tzqwz.common.dto.input.RegisterIMInDTO;

/**
 * 请求路由接口
 */
public interface RoteRequest {

    /**
     * 注册IM账户
     * @param registerIMInDTO
     */
    BaseResponse<NULLResData> registerIM(RegisterIMInDTO registerIMInDTO);
}
