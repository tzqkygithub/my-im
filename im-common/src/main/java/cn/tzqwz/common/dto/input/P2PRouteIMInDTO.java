package cn.tzqwz.common.dto.input;

/**
 * 私聊的实体类
 */
public class P2PRouteIMInDTO {

    private String userId;//消息的发送者

    private String receiverUserId;//消息的接受者

    private String msg;//具体消息

    public String getUserId() {
        return userId;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
