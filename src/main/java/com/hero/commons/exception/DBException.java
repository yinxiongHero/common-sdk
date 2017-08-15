package com.hero.commons.exception;

import java.sql.SQLException;

/**
 * 数据库异常
 *
 * @author sun.jun
 */
public class DBException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1318044864920664553L;

	public DBException() {
		super();
	}

	public DBException(String errCode, String message) {
		super(errCode, message);
	}

	public DBException(String message) {
		super(message);
	}

	public DBException(String errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

	public DBException(Throwable cause) {
		super(cause);
	}

	public DBException(SQLException e) {
		this(String.valueOf(e.getErrorCode()), e.getMessage(), e);
	}
}
