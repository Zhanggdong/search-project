package com.huasisoft.search.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 断言工具
 * @author bing.yi
 *
 */
public class Assert extends org.springframework.util.Assert{
	
	Assert() {
		super() ;
	}
	public static void isEmpty(String value) {
		if(StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("[Assertion failed] - this String must be not null") ;
		}
	}	
	public static void isEmpty(String value , String message) {
		if(StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException(message) ;
		}
	}
	public static void isNotEmpty(String value) {
		if(StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("[Assertion failed] - this String must be null") ;
		}
	}	
	public static void isNotEmpty(String value , String message) {
		if(StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException(message) ;
		}
	}
	public static void isEmpty(byte[] valueArray) {
		if(ArrayUtils.isEmpty(valueArray)) {
			throw new IllegalArgumentException("[Assertion failed] - this String must be not null") ;
		}
	}	
	public static void isEmpty(byte[] valueArray , String message) {
		if(ArrayUtils.isEmpty(valueArray)) {
			throw new IllegalArgumentException(message) ;
		}
	}
	public static void isNotEmpty(byte[] valueArray) {
		if(ArrayUtils.isEmpty(valueArray)) {
			throw new IllegalArgumentException("[Assertion failed] - this String must be null") ;
		}
	}	
	public static void isNotEmpty(byte[] valueArray , String message) {
		if(ArrayUtils.isEmpty(valueArray)) {
			throw new IllegalArgumentException(message) ;
		}
	}
	public static void isNull(Object o) {
		if(o == null) {
			throw new IllegalArgumentException("[Assertion failed] - this Object must be null") ;
		}
	}
	public static void isNull(Object o, String message) {
		if(o == null) {
			throw new IllegalArgumentException(message) ;
		}
	}
}
