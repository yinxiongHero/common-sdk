package com.hero.commons.db.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.hero.commons.tool.log.LogFactory;


/**
 * 抽象数据库连接类 
 * 
 * 维护子类的connection
 * @author yin.xiong
 * 2017年8月3日
 */
public abstract class AbstratDBConnection implements DBConnection{
	
	private Connection connection=null;
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public boolean isOpen(){
		try {
			return connection!=null&&!connection.isClosed();
		} catch (SQLException e) {
			LogFactory.getLog().error(this,e);
			return false;
		}
		
	}
	
	public void close(){
		if (connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LogFactory.getLog().error(this,e);
			}
		}
	}
	
	
	

}
