package com.ferret.exception;

/**细化运行时异常,自己定义一个业务异常*/
public class ServiceException extends RuntimeException {

	public ServiceException(String message) {
		super(message);
	}

}
