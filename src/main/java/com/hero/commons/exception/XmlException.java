package com.hero.commons.exception;

/**
 * XML异常
 *
 * @author sun.jun
 */
public class XmlException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8464294194847499650L;

	public XmlException() {
        super();
    }

    public XmlException(String errCode, String message) {
        super(errCode, message);
    }

    public XmlException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public XmlException(Throwable cause) {
        super(cause);
    }

}