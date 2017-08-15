package com.hero.commons.db.dao;

import java.sql.SQLException;

import com.hero.commons.db.bean.BaseBean;
import com.hero.commons.db.bean.BeanFactory;
import com.hero.commons.db.sql.Database;
import com.hero.commons.exception.DBException;

public abstract class BaseDAO<E extends BaseBean<E>> extends GenericDAO<E> {
	
	public BaseDAO(String dbName) {
		super(dbName);
	}
	
	public BaseDAO(Database database) {
		super(database);
	}

	@Override
	protected abstract int setStatement(int startPos, E bean) throws DBException, SQLException ;

	@Override
	protected abstract String getTableName();

	@Override
	protected abstract String[] getDBColumnArray();
	
	protected <T extends BaseBean<T>> T createBean(Class<T> tClass){
		return getResourceFactory().createBean(tClass);
	}
	
	protected BeanFactory getResourceFactory(){
		return BeanFactory.getBeanFactory(getDBName());
	}
	
}
