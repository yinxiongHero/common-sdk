package com.hero.commons.db.connection;


import javax.sql.DataSource;

import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.db.ds.PoolDataSource;
import com.hero.commons.exception.DBException;

/**
 * 数据源连接池
 * 
 * @author yin.xiong 2017年8月4日
 */
public class DataSourcePoolConnection extends AbstratDBConnection {
	private Factory factory;

	@Override
	public void init(Factory factory) {
		this.factory = factory;
	}

	@Override
	public void open() throws DBException {
		PoolDataSource poolDataSource = (PoolDataSource) factory.getBean(DataSource.class);
		try {
			poolDataSource.init(factory);
			setConnection(poolDataSource.getConnection());
		} catch (Exception e) {
			throw new DBException("S05", "open connection  ,init datasource fail ");
		}

	}

}
