package com.licong.log;

import static com.licong.util.ObjectUtils.isNull;

/**
 * 系统日志
 * 
 * @author James-li
 *
 * 2013-6-26
 */
public class SystemLogger implements ILogger {

	private Class<?> clazz;
	
	public SystemLogger(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void info(String info) {
		StringBuilder sb = new StringBuilder();
		if(isNull(clazz)) {
			System.out.println(sb.append("[INFO]").append(info));
		}else {
			System.out.println(sb.append("[INFO]").append("[").append(clazz.getName()).append("]").append(info));
		}
	}

	public void error(String info) {
		StringBuilder sb = new StringBuilder();
		if(isNull(clazz)) {
			System.out.println(sb.append("[ERROR]").append(info));
		}else {
			System.out.println(sb.append("[ERROR]").append("[").append(clazz.getName()).append("]").append(info));
		}
	}

	public void error(String info, Throwable exception) {
		StringBuilder sb = new StringBuilder();
		if(isNull(clazz)) {
			sb.append("[ERROR]").append(info).append("\n");
		}else {
			sb.append("[ERROR]").append("[").append(clazz.getName()).append("]").append(info).append("\n");
		}
		System.out.println(sb.toString());
		exception.printStackTrace();
	}

	public void debug(String info) {
		StringBuilder sb = new StringBuilder();
		if(isNull(clazz)) {
			System.out.println(sb.append("[DEBUG]").append(info));
		}else {
			System.out.println(sb.append("[DEBUG]").append("[").append(clazz.getName()).append("]").append(info));
		}
	}

}
