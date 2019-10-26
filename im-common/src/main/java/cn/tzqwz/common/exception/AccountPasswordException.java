package cn.tzqwz.common.exception;

/**
 * 账户密码异常
 */
public class AccountPasswordException extends RuntimeException {

    public AccountPasswordException(){
        super();
    }

    public AccountPasswordException(String message){
        super(message);
    }
}
