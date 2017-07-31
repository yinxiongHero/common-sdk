package com.hero.commons.exception;

/**
 * Exception基类
 *
 * @author sun.jun
 */
public class BaseException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4251082858984384147L;

	public BaseException() {
        super();
    }

    public BaseException(String errCode, String message) {
        super(errCode + " : " + message);
        this.errCode = errCode;
    }

    public BaseException(String errCode, String message, Throwable cause) {
        super(errCode + " : " + message, cause);
        this.errCode = errCode;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * @return error code
     */
    public String getErrorCode() {
        return errCode;
    }

    /**
     * error code
     */
    private String errCode;

}