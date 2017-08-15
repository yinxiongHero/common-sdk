package com.hero.commons.db.ds;

import java.beans.PropertyVetoException;

import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.db.connection.DBConnection;
import com.hero.commons.exception.BaseException;
import com.hero.commons.tool.log.LogFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * c3p0连接池
 * 
 * @author yin.xiong 2017年8月4日
 */
public class C3P0DataSource extends PoolDataSource {

	@Override
	public void init(Factory factory) throws BaseException {
		try {
			if (!isActive()) {
				synchronized (this) {
					ComboPooledDataSource dataSource = new ComboPooledDataSource();
					String driverName = factory.getString(DBConnection.PARAM_JAVA_SQL_DRIVER, null);
					String url = factory.getString(DBConnection.PARAM_URL, null);
					String user = factory.getString(DBConnection.PARAM_USER, null);
					dataSource.setDriverClass(driverName);
					dataSource.setJdbcUrl(url);
					dataSource.setUser(user);
					dataSource.setPassword(factory.getString(DBConnection.PARAM_PASSWORD, null));
					dataSource.setInitialPoolSize(factory.getInteger("InitialSize", 10));
					dataSource.setMaxPoolSize(factory.getInteger("MaxActive", 20));
					dataSource.setMinPoolSize(factory.getInteger("MinIdle", 2));
					dataSource.setMaxIdleTime(factory.getInteger("MaxWait", 10000));
					dataSource.setCheckoutTimeout(factory.getInteger("CheckoutTimeout", 30000));
					setDataSource(dataSource);
					toActive();
					LogFactory.getLog().info(
							new StringBuffer("C3P0 DataSource init,driverName=[").append(driverName).append("];url=[")
									.append(url).append("];user=[").append(user).append("];").toString());
				}
			}
		} catch (PropertyVetoException e) {
			throw new BaseException();
		}
	}

}
