package com.hero.commons.exception;

/**
 * 安全性异常
 *
 * @author sun.jun
 */
public class SecretException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2874202548593451309L;

	public SecretException() {
        super();
    }

    public SecretException(String errCode, String message) {
        super(errCode, message);
    }

    public SecretException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public SecretException(Throwable cause) {
        super(cause);
    }
}