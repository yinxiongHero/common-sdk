package com.hero.commons.db.sql;

import com.hero.commons.exception.DBException;

/**
 * 数据路批量操作接口
 * @author yin.xiong
 * 2017年8月3日
 */
public interface DBBatch {
	
	void addBatch() throws DBException;

	void addBatch(String[] sql) throws DBException;
}