package com.hero.commons.db.sql;

import com.hero.commons.exception.DBException;

/**
 * 事务操作接口
 * @author yin.xiong
 * 2017年8月3日
 */
public interface DBTransaction {
	
	void transationBegin() throws DBException;
	
	boolean isTransation();
	
	void transationCommit(boolean comit) throws DBException;
	
	void rollBack();
}
