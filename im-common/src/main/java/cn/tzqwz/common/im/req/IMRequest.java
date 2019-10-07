package cn.tzqwz.common.im.req;

/**
 * IM请求实体类
 */
public class IMRequest {

    private String requestId;

    private String reqMsg;

    private Integer reqType;

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }

    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getReqMsg() {
        return reqMsg;
    }

    public Integer getReqType() {
        return reqType;
    }
}
