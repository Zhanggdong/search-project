package com.huasisoft.search.admin.dict.execption;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 9:38
 * @Description 停词处理异常
 * @Version 2.0.0
 */
public class StopWordException extends RuntimeException{

    private static final long serialVersionUID = -8235774727968119180L;

    public StopWordException(String message){
        super(message);
    }

    public StopWordException(Throwable cause){
        super(cause);
    }

    public StopWordException(String message,Throwable cause){
        super(message,cause);
    }

}
