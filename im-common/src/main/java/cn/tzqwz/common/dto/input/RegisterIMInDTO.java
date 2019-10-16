package cn.tzqwz.common.dto.input;

/**
 * 注册IM账户的实体类
 */
public class RegisterIMInDTO {

    private String userName;//账户名称

    private String password;//账户密码

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
