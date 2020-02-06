package cn.tzqwz.service.impl;

import cn.tzqwz.common.base.constants.RoteConstants;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.base.res.NULLResData;
import cn.tzqwz.common.dto.input.LoginImInDTO;
import cn.tzqwz.common.dto.input.RegisterIMInDTO;
import cn.tzqwz.common.utils.OKHttpUtils;
import cn.tzqwz.service.RoteRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RoteRequestImpl implements RoteRequest {

    @Value("${route.web.server.url}")
    private String roteWebServerUrl;

    @Autowired
    private OKHttpUtils okHttpUtils;

    /**
     * 请求路由服务的注册IM方法
     * @param registerIMInDTO
     */
    public BaseResponse<NULLResData> registerIM(RegisterIMInDTO registerIMInDTO) {
        String requestRegisterUrl = roteWebServerUrl+ RoteConstants.ROTE_REGISTER_IM_URL;
        String jsonString = JSONObject.toJSONString(registerIMInDTO);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String responseBodyStr = okHttpUtils.createPostJSONReq(requestRegisterUrl, jsonObject);
        BaseResponse baseResponse = JSONObject.parseObject(responseBodyStr, BaseResponse.class);
        return baseResponse;
    }

    /**
     * 请求路由服务获取IMServer服务器地址方法
     * @param loginImInDTO
     * @return
     */
    public BaseResponse<String> getIMServer(LoginImInDTO loginImInDTO) {
        String requestLoginUrl = roteWebServerUrl + RoteConstants.ROTE_GET_IMSERVER_URL;
        String requestJsonStr = JSONObject.toJSONString(loginImInDTO);
        JSONObject reqJson = JSONObject.parseObject(requestJsonStr);
        String resString = okHttpUtils.createPostJSONReq(requestLoginUrl, reqJson);
        BaseResponse baseResponse = JSONObject.parseObject(resString, BaseResponse.class);
        return baseResponse;
    }
}
