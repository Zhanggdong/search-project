package com.huasisoft.search.common.exception;

/**
 * 统一的异常状态码
 * 系统级别：用1-100表示
 * 		     1:代码运行出错 
 * 模块业务异常: 从101 开始，每个模块分配一个段，每段长度为200 
 * 			例如：xx模块   101-300 
 *
 */
public enum ExcepCodeConst {
	
	EXCHANGE_SYS_EXCEP("后台运行出错", 1);
	
/** ----------------------------------------------------- properties 属性字段 ------------------------------------------------------------- */
	
    // 成员变量  
    private String message;  
    private int state;  
    
/** -------------------------------------------- getters and setters 属性获取和设值方法  ------------------------------------------------- */
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	
/** -------------------------------------------- constructors 构造方法  ------------------------------------------------- */
	
	 // 构造方法  
    private ExcepCodeConst(String message, int state) {  
        this.message = message;  
        this.state = state;  
    }
    
/** -------------------------------------------- methods 成员方法  ------------------------------------------------- */
  
    /**
     * 根据状态码获取说明信息
     * @param state
     * @return
     */
    public static String getMessage(int state) {  
        for (ExcepCodeConst d : ExcepCodeConst.values()) {  
            if (d.getState() == state) {  
                return d.message;  
            }  
        }  
        return null;  
    } 
}
