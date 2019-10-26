package cn.tzqwz.common.exception;

/**
 * 账户重复登录异常
 */
public class AccountRepeatLoginException extends RuntimeException {

    public AccountRepeatLoginException(){
        super();
    }

    public AccountRepeatLoginException(String message){
        super(message);
    }
}
