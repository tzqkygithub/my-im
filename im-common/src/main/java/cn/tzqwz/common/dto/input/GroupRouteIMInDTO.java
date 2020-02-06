package cn.tzqwz.common.dto.input;

public class GroupRouteIMInDTO {

    private String userId;

    private String msg;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserId() {
        return userId;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "GroupRouteIMInDTO{" +
                "userId='" + userId + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
