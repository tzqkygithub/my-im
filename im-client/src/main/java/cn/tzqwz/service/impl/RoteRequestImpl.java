package cn.tzqwz.service.impl;

import cn.tzqwz.common.base.constants.RoteConstants;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.base.res.NULLResData;
import cn.tzqwz.common.dto.input.RegisterIMInDTO;
import cn.tzqwz.service.RoteRequest;
import cn.tzqwz.utils.OKHttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RoteRequestImpl implements RoteRequest {

    @Value("${rote.web.server.url}")
    private String roteWebServerUrl;

    @Autowired
    private OKHttpUtils okHttpUtils;

    /**
     * 请求路由服务的注册IM方法
     * @param registerIMInDTO
     */
    public BaseResponse<NULLResData> registerIM(RegisterIMInDTO registerIMInDTO) {
        String requestRegiJsterUrl = roteWebServerUrl+ RoteConstants.ROTE_REGISTER_IM_URL;
        String jsonString = JSONObject.toJSONString(registerIMInDTO);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String responseBodyStr = okHttpUtils.createPostJSONReq(requestRegiJsterUrl, jsonObject);
        BaseResponse baseResponse = JSONObject.parseObject(responseBodyStr, BaseResponse.class);
        return baseResponse;
    }
}
