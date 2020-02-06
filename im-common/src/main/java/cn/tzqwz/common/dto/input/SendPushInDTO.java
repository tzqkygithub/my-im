package cn.tzqwz.common.dto.input;


public class SendPushInDTO {

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
