package com.huasisoft.search.admin.dict.execption;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 9:38
 * @Description 热词处理异常
 * @Version 2.0.0
 */
public class HotWordException extends RuntimeException{
    private static final long serialVersionUID = -9031343730387947391L;

    public HotWordException(String message){
        super(message);
    }

    public HotWordException(Throwable cause){
        super(cause);
    }

    public HotWordException(String message, Throwable cause){
        super(message,cause);
    }

}
