package com.hero.commons.db.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.exception.DBException;
import com.hero.commons.util.StringUtil;

/**
 * JDBC 数据库连接
 * 
 * @author yin.xiong 2017年8月4日
 */
public class JDBCConnection extends AbstratDBConnection {

	private String driveName;
	private String url;
	private String user;
	private String password;

	@Override
	public void init(Factory factory) {
		driveName = factory.getString("java.sql.Driver", null);
		url = factory.getString(DBConnection.PARAM_URL, null);
		user = factory.getString(DBConnection.PARAM_USER, null);
		password = factory.getString(DBConnection.PARAM_PASSWORD, null);
	}

	@Override
	public void open() throws DBException {
		if (isOpen())
			return;
		if (driveName == null) {
			throw new DBException("S04", "driveName is null");
		} else if (url == null) {
			throw new DBException("S04", "url is null");
		}
		try {
			Class.forName(driveName);
		} catch (ClassNotFoundException e) {
			throw new DBException("S04", "driveName is not found");
		}
		try {
			if (StringUtil.isEmpty(user) || StringUtil.isEmpty(password)) {
				setConnection(DriverManager.getConnection(url));
			} else {
				setConnection(DriverManager.getConnection(url, user, password));
			}
		} catch (SQLException e) {
			throw new DBException("S06","getConnection driveName=["+driveName+"] fail");
		}

	}

	// set
	public void setDriveName(String driveName) {
		this.driveName = driveName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
