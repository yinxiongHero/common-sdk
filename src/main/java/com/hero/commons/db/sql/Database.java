package com.hero.commons.db.sql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.common.resource.factory.SystemConfigFactory;
import com.hero.commons.db.bean.BaseBean;
import com.hero.commons.db.connection.DBConnection;
import com.hero.commons.exception.DBException;
import com.hero.commons.tool.log.LogFactory;
import com.hero.commons.util.DBUtil;

/**
 * 数据库操作类
 * 
 * @author yin.xiong 2017年8月4日
 */
public class Database extends DBHelper {
	public static final String DEFAULT_NAME = "database";
	private final String name;
	private Boolean checkValidate;
	
	/**
	 * 构造database
	 * 
	 * @param dbConnection
	 */
	public Database(String dbName) {
		super(getDBConnection(dbName));
		this.name = dbName;
		Factory factory = SystemConfigFactory.getSystemFactory(Database.class).getItemFactory(name);
		if (factory!=null) {
			setFetchSize(factory.getInteger("MaxQuerySize", 0));
			setCheckValidate(factory.getBoolean("checkValidate",false));
		}
		
	}

	public Database() {
		this(DEFAULT_NAME);
	}

	private static DBConnection getDBConnection(String dataBaseName) {
		Factory factory = SystemConfigFactory.getSystemFactory(Database.class);
		if (factory == null) {
			LogFactory.getLog().error("<config name=\"com.changingpay.commons.db.sql.Database\" .. not found!");
			return null;
		}
		Factory itemFactory = factory.getItemFactory("dataBaseName");
		DBConnection bean = itemFactory.getBean(DBConnection.class);
		if (bean == null) {
			LogFactory.getLog().error("<config name=\"com.hero.commons.db.connection.DBConnection\" .. not found!");
			return null;
		}
		bean.init(itemFactory);
		return bean;
	}

	/**
	 * 分页查询转换结果集
	 * @param bean
	 * @param startPos
	 * @param endPos
	 * @return
	 * @throws DBException
	 */
	public <T extends BaseBean<T>> List<T> getResultList(T bean, long startPos, long endPos) throws DBException {
		try {
			List<T> list=new ArrayList<T>();
			ResultSet resultSet=getResultSet();
			int recordPos=0;
			while(resultSet.next()){
				if(recordPos<startPos) continue;
				if (recordPos>endPos) break;
				if (isOutofRange(recordPos-startPos+1)) break;
				T t=DBUtil.fetchObject(resultSet,bean,checkValidate);
				if (t!=null) {
					list.add(t);
				}
			}
			return list;
		} catch (Exception e) {
			throw new DBException("S04", "FETCH DATA FAIL", e);
		}finally{
			closeResutSetAndStatement();
		}
	}
	
	/**
	 * 结果集转换成集合
	 * @param bean
	 * @return
	 * @throws DBException
	 */
	public <T extends BaseBean<T>> List<T> getResultList(T bean) throws DBException{
		try {
			ResultSet resultSet = getResultSet();
			int count=0;
			List<T> list=new ArrayList<T>();
			while(resultSet.next()){
				count++;
				if (isOutofRange(count)) {
					LogFactory.getLog().error(this,"getReulstList the curSize=["+count+"] is out of range,maxSige=["+getFetchSize()+"]");
					break;
				}
				//结果集转换为T
				T obj = DBUtil.fetchObject(resultSet, bean, checkValidate);
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			throw new DBException("504","getresultlist fail",e);
		}finally{
			closeResutSetAndStatement();
		}
	}
	
	/**
	 * 查询list 最大查询数范围判断
	 * @param count 查出的总记录数
	 * @return
	 */
	private boolean isOutofRange(long count){
		return getFetchSize()!=0&&count>getFetchSize();
	}
	
	public String getName() {
		return name;
	}
	
	public void setCheckValidate(Boolean checkValidate) {
		this.checkValidate = checkValidate;
	}

}
