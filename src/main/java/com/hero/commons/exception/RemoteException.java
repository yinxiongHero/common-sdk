package com.hero.commons.exception;

/**
 * 远程异常
 *
 * @author sun.jun
 */
public class RemoteException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6296288756868819727L;

	public RemoteException() {
    }

    public RemoteException(String code, String message) {
        super(code, message);
    }

    public RemoteException(String code, String message, Throwable t) {
        super(code, message, t);
    }

    public RemoteException(Throwable t) {
        super(t);
    }
}
