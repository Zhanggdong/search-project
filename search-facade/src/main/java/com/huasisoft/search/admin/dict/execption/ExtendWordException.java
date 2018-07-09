package com.huasisoft.search.admin.dict.execption;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 9:38
 * @Description 扩展词处理异常
 * @Version 2.0.0
 */
public class ExtendWordException extends RuntimeException{

    private static final long serialVersionUID = 4235612935718674592L;
    public ExtendWordException(String message){
        super(message);
    }

    public ExtendWordException(Throwable cause){
        super(cause);
    }

    public ExtendWordException(String message, Throwable cause){
        super(message,cause);
    }

}
