package com.licong.util;

/**
 * 断言失败异常
 * 
 * @author James-li
 *
 * 2013-2-19
 */
@SuppressWarnings("serial")
public class AssertionFailtureException extends RuntimeException {

	public AssertionFailtureException() {
	}
	
	public AssertionFailtureException(String msg) {
		super(msg);
	}
}
