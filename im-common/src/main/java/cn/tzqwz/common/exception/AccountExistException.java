package cn.tzqwz.common.exception;

/**
 * 账户存在异常
 */
public class  AccountExistException extends RuntimeException{


    public AccountExistException(){
        super();
    }

    public AccountExistException(String message){
        super((message));
    }
}
