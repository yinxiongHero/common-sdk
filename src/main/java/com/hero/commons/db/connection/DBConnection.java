package com.hero.commons.db.connection;

import java.sql.Connection;

import com.hero.commons.common.Configurable;
import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.exception.DBException;

/**
 * db 连接接口
 * 
 * @author yin.xiong 2017年8月3日
 */
public interface DBConnection extends Configurable {

	static String PARAM_URL = "URL";
	static String PARAM_USER = "User";
	static String PARAM_JAVA_SQL_DRIVER="java.sql.Driver";
	static String PARAM_PASSWORD = "Password";
	static String PARAM_LOOKUP_NAME = "Lookup";
	static String PARAM_CONTEXT_PARAM = "ContextParam";

	@Override
	void init(Factory factory);

	void open() throws DBException;

	Connection getConnection();

	void close();

	boolean isOpen();
}
