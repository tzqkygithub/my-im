package cn.tzqwz.entity;

/**
 * 消息接受者实体类
 */
public class ChatReqEntity {

    private String receiverUserId;

    private String msg;

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public String getMsg() {
        return msg;
    }
}
