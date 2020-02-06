package cn.tzqwz.common.base.controller;

import cn.tzqwz.common.base.constants.BaseConstants;
import cn.tzqwz.common.base.res.BaseResponse;
import org.springframework.stereotype.Component;

/**
 * 定义响应数据模板
 */
@Component
public class BaseController<T> {

    public BaseResponse<T> setResultError(Integer code, String msg,String reqUserId) {
        return setResult(code, msg, null,reqUserId);
    }


    public BaseResponse<T> setResultError(Integer code, String msg,Object data,String reqUserId) {
        return setResult(code, msg, data,reqUserId);
    }

    // 返回错误，可以传msg
    public BaseResponse<T> setResultError(String msg,String reqUserId) {
        return setResult(BaseConstants.HTTP_RES_CODE_500, msg, null,reqUserId);
    }

    // 返回成功，可以传data值
    public BaseResponse<T> setResultSuccess(Object data,String reqUserId) {
        return setResult(BaseConstants.HTTP_RES_CODE_200, BaseConstants.HTTP_RES_CODE_200_VALUE, data,reqUserId);
    }

    // 返回成功，沒有data值
    public BaseResponse<T> setResultSuccess(String reqUserId) {
        return setResult(BaseConstants.HTTP_RES_CODE_200, BaseConstants.HTTP_RES_CODE_200_VALUE, null,reqUserId);
    }


    // 返回成功，沒有data值
    public BaseResponse<T> setResultSuccess(String msg,String reqUserId) {
        return setResult(BaseConstants.HTTP_RES_CODE_200, msg, null,reqUserId);
    }

    // 通用封装
    public BaseResponse<T> setResult(Integer code, String msg, Object data,String reqUserId) {

        return new BaseResponse(code, msg, data,reqUserId);
    }
}
