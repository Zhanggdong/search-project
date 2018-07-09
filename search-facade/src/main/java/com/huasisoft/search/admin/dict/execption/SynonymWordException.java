package com.huasisoft.search.admin.dict.execption;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 9:38
 * @Description 同义词处理异常
 * @Version 2.0.0
 */
public class SynonymWordException extends RuntimeException{
    private static final long serialVersionUID = 1066410224355851001L;

    public SynonymWordException(String message){
        super(message);
    }

    public SynonymWordException(Throwable cause){
        super(cause);
    }

    public SynonymWordException(String message, Throwable cause){
        super(message,cause);
    }

}
