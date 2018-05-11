package com.huasisoft.search.common.util;

import java.io.Serializable;

/**
 * 统一返回类
 * 封装统一的json格式
 *
 */
public class JsonResult<T> implements Serializable{
	
/** --------------------------------------------------- serialVersionUID 序列化ID ------------------------------------------------------- */

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1139719898575567777L;
	
/** ----------------------------------------------------- static final properties 静态变量 ------------------------------------------------------------- */
	
	private static final Integer success = 1 ;
	private static final Integer fail = 0 ;
	
/** ----------------------------------------------------- properties 属性字段 ------------------------------------------------------------- */	

	private Integer exchangeStatus = success ; 
	private Integer errorStatus ;
	private String message = "success";
	private T data ;
	
/** -------------------------------------------- getters and setters 属性获取和设值方法  ------------------------------------------------- */
	
	public Integer getExchangeStatus() {
		return exchangeStatus;
	}
	public void setExchangeStatus(Integer exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}
	
	public Integer getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(Integer errorStatus) {
		this.errorStatus = errorStatus;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
/** -------------------------------------------- constructors 构造方法  ------------------------------------------------- */

	//成功时的构造方法
	public JsonResult(T data) {
		super();
		this.exchangeStatus = success;
		this.data = data;
	}
	
	//失败时的构造方法
	public JsonResult(String errorMessage,Integer errorStatus) {
		super();
		this.message = errorMessage;
		this.exchangeStatus = fail;
		this.errorStatus = errorStatus;
	}
	
}
