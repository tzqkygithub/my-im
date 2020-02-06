package cn.tzqwz.common.utils;


import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * 封装OKHttp的常用操作
 */
@Component
public class OKHttpUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(OKHttpUtils.class);

    @Autowired
    private OkHttpClient okHttpClient;

    private MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

    /**
     * POST请求发送JSON数据
     */
    public String createPostJSONReq(String reqUrl, JSONObject postJSONParam){
        RequestBody requestBody = RequestBody.create(mediaType,postJSONParam.toJSONString());
        Request request = new Request.Builder()
                .url(reqUrl)
                .post(requestBody)
                .build();
        return execNewCall(request);
    }

    /**
     * GET请求发送Map集合数据
     * @param reqUrl
     * @return
     */
    public String createGetReq(String reqUrl, Map<String,String> mapParam){
        StringBuffer reqParam = getQueryString(reqUrl, mapParam);
        Request request = new Request.Builder()
                .url(reqParam.toString())
                .get()
                .build();
        return execNewCall(request);
    }


    /**
     * 调用okhttp的newCall方法pu
     * @param request
     * @return
     */
    private  String execNewCall(Request request){
        Response response = null;
        try {

            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                return body.string();
            }
        } catch (Exception e) {
            LOGGER.error("okhttp3 put error >> ex = {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    /**
     * 根据map获取get请求参数
     * @param queries
     * @return
     */
    public static StringBuffer getQueryString(String url,Map<String,String> queries){
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        return sb;
    }


}
