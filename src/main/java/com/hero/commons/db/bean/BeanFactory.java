package com.hero.commons.db.bean;

import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.common.resource.factory.SystemConfigFactory;
import com.hero.commons.db.sql.Database;
import com.hero.commons.tool.log.LogFactory;

/**
 * bean简单工厂类
 * 
 * @author yin.xiong 2017年8月7日
 */
public class BeanFactory {
	private final String dbName;
	private Factory factory;

	public BeanFactory(String dbName) {
		this.dbName = dbName;
		Factory dbNode = SystemConfigFactory.getSystemFactory(Database.class);
		if (dbNode != null) {
			Factory factory = dbNode.getItemFactory(dbName);
			if (factory != null) {
				this.factory = factory.getItemFactory(BeanFactory.class);
			} else {
				LogFactory.getLog().error("database [" + dbName + "] not found!");
			}
		} else {
			LogFactory.getLog().error("database node not found!");
		}
	}
	
	public static BeanFactory getBeanFactory(String dbName){
		return new BeanFactory(dbName);
	}
	
	public static BeanFactory getBeanFactory(){
		return new BeanFactory(Database.DEFAULT_NAME);
	}
	
	public <T extends BaseBean<T>> T createBean(Class<T> beanClass){
		if (factory==null) {
			LogFactory.getLog().error("database["+dbName+"] BeanFactory not found!");
			return null;
		}
		T bean=factory.getBean(beanClass);
		bean.init(factory);
		return bean.newInstance();
	}
	
	public String getDbName() {
		return dbName;
	}
	
	
	
}
