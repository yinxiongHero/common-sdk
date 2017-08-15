package com.hero.commons.db.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hero.commons.exception.DBException;

/**
 * 数据库操作接口
 * 
 * @author yin.xiong 2017年8月3日
 */
public interface DBExecutor extends DBBatch, DBTransaction {
	void open() throws DBException;

	void close();

	boolean isOpen();

	void execute(String sql) throws DBException;

	void query(String sql) throws DBException;

	ResultSet getResultSet();

	void setResultSetType(int resultSetType);

	PreparedStatement getPreparedStatement() throws DBException;

	void setPreparedStatement(String sql) throws DBException;

	int executePrepared() throws DBException;

	int getUpdateCount();

	void setResultSetConcurrency(int resultSetConcurrency);

}
