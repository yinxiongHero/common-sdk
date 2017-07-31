package com.hero.commons.exception;

/**
 * 文件异常
 *
 * @author sun.jun
 */
public class FileException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6704181917489518761L;

	public FileException() {
    }

    public FileException(String code, String message) {
        super(code, message);
    }

    public FileException(String code, String message, Throwable t) {
        super(code, message, t);
    }

    public FileException(Throwable t) {
        super(t);
    }
}
