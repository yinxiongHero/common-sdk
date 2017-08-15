package com.hero.commons.db.ds;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.hero.commons.common.Configurable;
import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.exception.BaseException;
import com.hero.commons.exception.DBException;

/**
 * 数据源抽象类
 * 
 * @author yin.xiong 2017年8月4日
 */
public abstract class PoolDataSource implements DataSource, Configurable {
	private volatile DataSource dataSource;
	private String dataSourceName;
	private volatile boolean actived = false;

	@Override
	public abstract void init(Factory factory) throws BaseException;

	protected boolean isActive() {
		return actived;
	}

	protected void toActive() {
		actived = true;
	}

	public void close() throws DBException {
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	protected void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	protected DataSource getDataSource() {
		return dataSource;
	}

	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public Connection getConnection(String username, String password) throws SQLException {
		return getDataSource().getConnection(username, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		getDataSource().setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		getDataSource().setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return getDataSource().unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return getDataSource().isWrapperFor(iface);
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public String toString() {
		return dataSourceName;
	}

}
