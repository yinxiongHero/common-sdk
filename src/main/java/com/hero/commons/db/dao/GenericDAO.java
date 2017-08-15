package com.hero.commons.db.dao;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.hero.commons.db.bean.ArrayBean;
import com.hero.commons.db.bean.BaseBean;
import com.hero.commons.db.bean.BeanFactory;
import com.hero.commons.db.sql.Database;
import com.hero.commons.exception.DBException;
import com.hero.commons.tool.log.LogFactory;
import com.hero.commons.util.ClassUtil;

/**
 * DAO 基类
 * 
 * @author yin.xiong 2017年8月7日
 */
public abstract class GenericDAO<E extends BaseBean<E>> {
	private Database database;
	private int maxQuerySize = 0;
	private boolean printLog = true;

	/**
	 * 需要对Bean进行插入更新操作时须实现此方法
	 *
	 * @param startPos
	 *            PreparedStatement 参数开始游标
	 * @param bean
	 *            DAO对应的Bean对象
	 * @return PreparedStatement 参数游标+1
	 * @throws com.changingpay.commons.exception.DBException
	 *             未设置参数
	 * @throws java.sql.SQLException
	 *             设置参数失败
	 */
	protected abstract int setStatement(int startPos, E bean) throws DBException, SQLException;

	/**
	 * 子类具体的dao 实现返回具体表名
	 * 
	 * @return
	 */
	protected abstract String getTableName();

	/**
	 * 子类具体的dao 实现返回具体表字段数组
	 * 
	 * @return
	 */
	protected abstract String[] getDBColumnArray();

	protected E createBean() {
		Type type = ClassUtil.getTypes(this)[0];
		@SuppressWarnings("unchecked")
		Class<E> c = (Class<E>) type;
		return BeanFactory.getBeanFactory(getDBName()).createBean(c);
	}

	/**
	 * 分页查询
	 *
	 * @param sql
	 *            SQL语句
	 * @param conditionParams
	 *            查询条件
	 * @param startPos
	 *            开始位置
	 * @param endPos
	 *            结束位置
	 * @return 当前页对象列表
	 * @throws DBException
	 *             查询失败抛出异常
	 */
	public List<E> queryList(String sql, Object[] conditionParams) throws DBException {
		return queryList(sql, createBean(), conditionParams);
	}

	/**
	 * 查询操作
	 *
	 * @param sql
	 *            SQL语句
	 * @param conditionParams
	 *            查询参数值列表
	 * @return 结果集对象
	 * @throws DBException
	 *             查询失败抛出异常
	 */
	public ResultSet query(String sql, Object[] conditionParams) throws DBException {
		Database database = getDatabase();
		if (!database.isOpen()) {
			throw new DBException("S04", "Database  not open");
		}
		database.setPreparedStatement(sql);
		setPreparedStatement(1, conditionParams);
		database.queryPreparedStatement();
		return database.getResultSet();
	}

	private void setPreparedStatement(int index, Object[] conditionParams) throws DBException {
		if (conditionParams != null) {
			Database database = getDatabase();
			try {
				PreparedStatement preparedStatement = database.getPreparedStatement();

				if (printLog) {
					LogFactory.getLog().info(this, "条件参数=" + Arrays.asList(conditionParams));
				}
				for (Object obj : conditionParams) {
					index++;
					if (obj == null) {
						preparedStatement.setString(index, null);
					} else {
						preparedStatement.setObject(index, obj);
					}
				}
			} catch (Exception e) {
				throw new DBException("S04", "get preparedStatement exception", e);
			}
		}
	}

	/**
	 * 查询操作
	 *
	 * @param sql
	 *            SQL语句
	 * @param bean
	 *            返回Bean类型
	 * @param conditionParams
	 *            查询参数值列表
	 * @return 查询结果集
	 * @throws DBException
	 *             查询失败抛出异常
	 */
	protected List<E> queryList(String sql, E bean, Object[] conditionParams, long startPos, long endPos)
			throws DBException {
		try {
			Database database = getDatabase();
			if (!database.isOpen()) {
				database.open();
			}
			database.setPreparedStatement(sql);
			setPreparedStatement(1, conditionParams);
			database.queryPreparedStatement();
			return database.getResultList(bean, startPos, endPos);
		} catch (Exception e) {
			throw new DBException("S04", "queryList user startpos and endpos fail", e);
		}
	}

	/**
	 * 查询操作
	 * 
	 * @param sql
	 * @param bean
	 * @param conditionParams
	 * @return
	 * @throws DBException
	 */
	protected List<E> queryList(String sql, E bean, Object[] conditionParams) throws DBException {
		try {
			Database database = getDatabase();
			if (!database.isOpen()) {
				database.open();
			}
			database.setPreparedStatement(sql);
			setPreparedStatement(1, conditionParams);
			database.queryPreparedStatement();
			return database.getResultList(bean);
		} catch (Exception e) {
			throw new DBException("S04", "queryList user startpos and endpos fail", e);
		}
	}

	/**
	 * 单一查询
	 *
	 * @param sql
	 *            SQL语句
	 * @param bean
	 *            结果集Bean对象
	 * @param conditionParams
	 *            参数列表
	 * @return Bean对象
	 * @throws DBException
	 *             查询失败抛出异常
	 */
	protected E queryBean(String sql, E bean, Object[] conditionParams) throws DBException {
		List<E> queryList = queryList(sql, bean, conditionParams);
		if (queryList == null || queryList.size() == 0) {
			return null;
		}
		return queryList.get(0);
	}

	/**
	 * 单一查询
	 *
	 * @param sql
	 *            SQL语句
	 * @param bean
	 *            结果集Bean对象
	 * @param conditionParams
	 *            参数列表
	 * @return Bean对象
	 * @throws DBException
	 *             查询失败抛出异常
	 */
	protected E queryBean(String sql, Object[] conditionParams) throws DBException {
		List<E> queryList = queryList(sql, conditionParams);
		if (queryList == null || queryList.size() == 0) {
			return null;
		}
		return queryList.get(0);
	}

	// 增删改方法
	/**
	 * 预编译批处理语句(批量插入)
	 *
	 * @param sql
	 *            SQL语句
	 * @param conditionParamsList
	 *            参数列表
	 * @return 批处理结果
	 * @throws DBException
	 *             批处理失败抛出异常
	 */
	protected int batchInsert(String sql, List<E> conditionParamsList) throws DBException {
		Database database = getDatabase();
		if (!database.isOpen()) {
			database.open();
		}
		// 批量插入强制开启事务
		database.transationBegin();
		try {
			for (E bean : conditionParamsList) {
				if (printLog) {
					LogFactory.getLog().info(this, "参数{" + bean.toString() + "}");
				}
				// 子类自行实现PreparedStatement set值
				setStatement(1, bean);
				// 添加操作
				database.addBatch();
			}
			database.transationCommit(false);
			return database.getUpdateCount();
		} catch (SQLException e) {
			throw new DBException("S05", "batchInset exception", e);
		}

	}

	/**
	 * 插入操作
	 *
	 * @param sql
	 *            SQL语句
	 * @param bean
	 *            插入的Bean 对象
	 * @throws DBException
	 *             插入数据库失败抛出异常
	 */
	protected void insert(String sql, E bean) throws DBException {
		if (!database.isOpen()) {
			database.open();
		}
		database.setPreparedStatement(sql);
		try {
			setStatement(1, bean);
			database.executePrepared();
		} catch (SQLException e) {
			throw new DBException("s05", "single insert fail", e);
		} finally {
			if (database.isOpen()) {
				database.close();
			}

		}
	}

	/**
	 * 更新操作
	 *
	 * @param sql
	 *            SQL语句
	 * @param conditionParams
	 *            参数列表
	 * @param bean
	 *            需更新的Bean对象
	 * @return 返回更新的记录数
	 * @throws DBException
	 *             更新失败抛出异常
	 */
	protected int update(String sql, Object[] conditionParams, E bean) throws DBException {
		Database database = getDatabase();
		try {
			if (!database.isOpen()) database.open();
			database.setPreparedStatement(sql);
			if (printLog) {
				LogFactory.getLog().info("参数{" + bean.toString() + "}");
			}
			int index = setStatement(1, bean);
			setPreparedStatement(index, conditionParams);
			return database.executePrepared();
		} catch (SQLException e) {
			throw new DBException("S04", "SET TABLE STATEMENT FAIL", e);
		} catch (DBException e) {
			throw e;
		} catch (Exception e) {
			throw new DBException("S04", "UPDATE FAIL", e);
		} finally {
			if (database.isOpen()) database.close();
		}

	}

	/**
	 * 删除操作
	 *
	 * @param sql
	 *            SQL语句
	 * @param conditionParams
	 *            参数列表
	 * @return 删除的记录数
	 * @throws DBException
	 *             删除失败抛出异常
	 */
	protected int delete(String sql, Object[] conditionParams) throws DBException {
		return execute(sql, conditionParams);
	}

	/**
	 * 获取记录数
	 *
	 * @param sql
	 *            SQL语句
	 * @param conditionParams
	 *            参数列表
	 * @return 记录数
	 * @throws DBException
	 *             查询失败抛出异常
	 */
	public long count(String sql, Object[] conditionParams) throws DBException {
		Database database = getDatabase();
		if (!database.isOpen()) {
			database.open();
		}
		try {
			database.setPreparedStatement(sql);
			database.setFetchSize(getMaxQuerySize());
			database.executePrepared();
			List<ArrayBean> resultList = database.getResultList(new ArrayBean());
			if (resultList==null||resultList.size()==0) {
				return 0;
			}
			return  ((Number) resultList.get(0).get(0)).longValue();
		} catch (Exception e) {
			throw new DBException("S05","get count fail!",e);
		}finally{
			if (database.isOpen()) {
				database.close();
			}
		}
	}

	/**
	 * 获取数据库所有Bean对象Pool
	 * <p/>
	 *
	 * @param sqlCondition
	 *            SQL条件,START WITH 'WHERE'
	 * @param paramList
	 *            参数列表
	 * @return DataPool，里面是Bean对象;
	 */
//	public DataPool<E> getDataPool(final String sqlCondition, final Object[] paramList) {
//	}

	/**
	 * 执行业务处理
	 *
	 * @param sql
	 *            SQL语句
	 * @param conditionParams
	 *            参数列表
	 * @return 执行处理结果
	 * @throws DBException
	 *             执行处理失败抛出异常
	 */
	public int execute(String sql, Object[] conditionParams) throws DBException {
		Database database = getDatabase();
		try {
			if (!database.isOpen()) {
				database.open();
			}
			database.setPreparedStatement(sql);
			setPreparedStatement(1, conditionParams);
			return database.executePrepared();
		} catch (DBException e) {
			throw e;
		} catch (Exception e) {
			throw new DBException("S04", "EXECUTE FAIL", e);
		} finally {
			if (database.isOpen())
				database.close();
		}
	}

	/** ======= 构造方法，set,get====== */
	public GenericDAO(String dbName) {
		this(new Database(dbName));
	}

	/**
	 * 注意调用显示构造器不会改变传入连接状态 如果database已经打开，本类操作不会关闭，事务处理需按照此方法显示调用； 如果database未打开，本类方法将打开连接，使用完会自动关闭。
	 *
	 * @param database
	 *            数据库处理类
	 */
	public GenericDAO(Database database) {
		this.database = database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Database getDatabase() {
		if (database == null) {
			this.database = new Database();
		}
		return database;
	}

	public String getDBName() {
		return getDatabase().getName();
	}

	public void setMaxQuerySize(int maxQuerySize) {
		this.maxQuerySize = maxQuerySize;
	}

	public int getMaxQuerySize() {
		return maxQuerySize;
	}

	public void setPrintLog(boolean printLog) {
		this.printLog = printLog;
	}

	/** ======= 构造方法，set,get====== */

}
