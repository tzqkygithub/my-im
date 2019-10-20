package cn.tzqwz.common.base.res;

/**
 * 定义http响应实体类
 */
public class BaseResponse<T> {

    private Integer retCode;//响应状态码

    private String retMsg;//响应具体信息

    private T retData;//响应数据

    private String reqUserId;//用户请求id

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getRetData() {
        return retData;
    }

    public void setRetData(T retData) {
        this.retData = retData;
    }

    public String getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(String reqUserId) {
        this.reqUserId = reqUserId;
    }

    public BaseResponse(Integer retCode, String retMsg, T retData, String reqUserId) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retData = retData;
        this.reqUserId = reqUserId;
    }

    public BaseResponse(){}
}
