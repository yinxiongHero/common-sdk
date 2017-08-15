package com.hero.commons.db.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hero.commons.db.connection.DBConnection;
import com.hero.commons.exception.DBException;
import com.hero.commons.tool.log.LogFactory;
import com.hero.commons.tool.observer.Observed;
import com.hero.commons.tool.observer.Observer;
import com.hero.commons.tool.observer.impl.GenericObservable;
import com.hero.commons.util.DBUtil;
import com.hero.commons.util.StringUtil;

/**
 * 数据库基础操作类
 * 
 * @author yin.xiong 2017年8月3日
 */
@SuppressWarnings("unused")
public class DBHelper implements DBExecutor {
	// 被观察者实例
	private Observed observable = new GenericObservable();
	// 对java.sql.resultset,dbconnection,statement,parparestatement,的封装
	private ResultSet resultSet = null;
	private DBConnection dbConnection = null;
	private Statement statement = null;

	private int resultSetType = ResultSet.TYPE_FORWARD_ONLY;
	private int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
	private PreparedStatement preparedstatement = null;
	// 标识位
	private boolean isTransaction = false;
	private boolean isBatch = false;
	private boolean printLog = true;
	private int updateCount = 0;
	private int maxRows = 0;
	private int fetchSize;//查询最大list大小

	public DBHelper(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public void setDbConnection(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	/** observed 方法装饰 **/

	public void addObserver(Observer observer) {
		observable.addObserver(observer);
	}

	public void deleteObserver(Observer observer) {
		observable.delObserver(observer);
	}

	public boolean notifyObservers(int maxNoticeSize, byte[] byteArray) {
		return observable.noticeObserver(maxNoticeSize, byteArray);
	}

	public int countObservers() {
		return observable.countObservers();
	}
	
	protected void closeResutSetAndStatement() {
        DBUtil.close(resultSet);
        DBUtil.close(statement);
        DBUtil.close(preparedstatement);
    }
	/** observed 方法装饰 **/

	/**
	 * java.sql.connection
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return dbConnection.getConnection();
	}

	/** =========user Preparedstatement ========= */
	public void setPreparedStatement(String sql) {
		try {
			preparedstatement = getConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
			preparedstatement.clearBatch();
		} catch (SQLException e) {
			LogFactory.getLog().error(this, e);
		}
	}

	/**
	 * 调用此方法一般先调用setPreparedstatement
	 * 
	 * @throws DBException
	 */
	public void queryPreparedStatement() throws DBException {
		if (!isOpen()) {
			throw new DBException("database is not open");
		}
		try {
			if (preparedstatement != null) {
				preparedstatement.setMaxRows(maxRows);
				preparedstatement.setFetchSize(fetchSize);
				preparedstatement.executeQuery();
				resultSet = preparedstatement.getResultSet();
			} else {
				throw new DBException("preparedstatement is null ,please init preparedstatement first");
			}
		} catch (SQLException e) {
			throw new DBException("S05", "use preparedstatement query  database fail");
		}
	}

	@Override
	public int executePrepared() throws DBException {
		if (!isOpen()) {
			throw new DBException("database is not open");
		}
		try {
			if (preparedstatement != null) {
				preparedstatement.execute();
				updateCount = preparedstatement.getUpdateCount();
				LogFactory.getLog().debug("update count =" + updateCount);
				return updateCount;
			} else {
				throw new DBException("preparedstatement is null ,please init preparedstatement first");
			}
		} catch (SQLException e) {
			throw new DBException("S05", "executePrepared query database fail");
		}
	}

	/** ==========user Preparedstatement========== */

	/** =========DBExecutor 接口的方法实现 ========= */
	@Override
	public void addBatch() throws DBException {
		if (!isOpen()) {
			throw new DBException("database is not open");
		}
		try {
			preparedstatement.addBatch();
		} catch (SQLException e) {
			throw new DBException("s05", "preparedstatement addBatch fail!", e);
		}
	}

	@Override
	public void addBatch(String[] sql) throws DBException {
		if (!isOpen()) {
			throw new DBException("database is not open");
		}
		Statement st = null;
		try {
			st = getConnection().createStatement();
			st.clearBatch();
			for (String sqlStr : sql) {
				st.addBatch(sqlStr);
			}
			int[] len = st.executeBatch();
			for (int i = 0; i < len.length; i++) {
				updateCount += len[i];
			}
		} catch (SQLException e) {
			throw new DBException("s05", "preparedstatement addBatch[sql] fail!", e);
		} finally {
			DBUtil.close(st);
		}
	}

	@Override
	public void transationBegin() throws DBException {
		if (!isOpen()) {
			throw new DBException("database is not open");
		}
		DBUtil.close(preparedstatement);
		DBUtil.close(statement);
		try {
			getConnection().setAutoCommit(false);
			isTransaction = true;
		} catch (SQLException e) {
			throw new DBException("S04", "set transationBegin fail!", e);
		}
	}

	@Override
	public void transationCommit(boolean comit) throws DBException {
		if (!isOpen()) {
			throw new DBException("S04", "database not open");
		}
		try {
			if (isBatch) {
				int[] counts = preparedstatement.executeBatch();
				for (int n : counts) {
					updateCount += n;
				}
			}
			getConnection().commit();
			getConnection().setAutoCommit(comit);
			LogFactory.getLog().debug("update count =" + updateCount);
			// 通知所有关联的对象进行更新
			this.notifyObservers(1, null);
			isTransaction = false;
		} catch (SQLException e) {
			throw new DBException("S04", "COMMIT TRANSACTION FAIL", e);
		}
	}

	@Override
	public void rollBack() {
		if (!isTransaction) {
			LogFactory.getLog().warn(this, "rollback invalid without transaction!");
		} else {
			try {
				getConnection().rollback();
			} catch (SQLException e) {
				LogFactory.getLog().error(this, e);
			}
		}

	}

	/**
	 * 不设置返回结果集，暂未使用
	 */
	@Override
	public void execute(String sql) throws DBException {
		if (StringUtil.isEmpty(sql)) {
			throw new DBException("sql is null,can not be executed");
		}
		if (!isOpen()) {
			throw new DBException("database is not open");
		}
		Statement st = null;
		try {
			st = getConnection().createStatement();
			st.execute(sql);
			if (!sql.startsWith("select") && !sql.startsWith("SELECT")) {
				if (isTransaction) {
					updateCount += st.getUpdateCount();
				} else {
					updateCount = st.getUpdateCount();
				}
				if (printLog) {
					LogFactory.getLog().info("update count [" + updateCount + "]");
				}
			}
		} catch (SQLException e) {
			throw new DBException("s04", "execute sql exception", e);
		} finally {
			DBUtil.close(st);
		}

	}

	/**
	 * 使用resultSet非预编译结果执行sql
	 */
	@Override
	public void query(String sql) throws DBException {
		if (StringUtil.isEmpty(sql)) {
			throw new DBException("sql is null,can not be executed");
		}
		if (!isOpen()) {
			throw new DBException("database is not open");
		}
		try {
			statement = getConnection().createStatement(resultSetType, resultSetConcurrency);
			statement.setMaxRows(maxRows);
			statement.setFetchSize(fetchSize);
			statement.execute(sql);
			resultSet = statement.getResultSet();
		} catch (SQLException e) {
			throw new DBException("S05", "execute sql query fail", e);
		}

	}

	// 设置事务隔离级别
	public void setTransactionIsolation(int level) throws DBException {
		try {
			getConnection().setTransactionIsolation(level);
		} catch (SQLException e) {
			throw new DBException("S04", "SET TRANSACTION ISOLATION FAIL", e);
		}
	}

	public int getTransactionIsolation() throws DBException {
		try {
			return getConnection().getTransactionIsolation();
		} catch (SQLException e) {
			throw new DBException("S04", "SET TRANSACTION ISOLATION FAIL", e);
		}
	}

	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	// init and set

	@Override
	public void setResultSetType(int resultSetType) {
		this.resultSetType = resultSetType;
	}

	@Override
	public PreparedStatement getPreparedStatement() throws DBException {
		return preparedstatement;
	}

	@Override
	public void setResultSetConcurrency(int resultSetConcurrency) {
		this.resultSetConcurrency = resultSetConcurrency;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	@Override
	public int getUpdateCount() {
		return updateCount;
	}

	@Override
	public ResultSet getResultSet() {
		return resultSet;
	}

	@Override
	public boolean isTransation() {
		return isTransaction;
	}

	@Override
	public void open() throws DBException {
		this.dbConnection.open();
	}

	@Override
	public void close() {
		this.dbConnection.close();

	}

	@Override
	public boolean isOpen() {
		return dbConnection.isOpen();
	}

	/**
	 * @return the printLog
	 */
	public boolean isPrintLog() {
		return printLog;
	}

	/**
	 * @param printLog
	 *            the printLog to set
	 */
	public void setPrintLog(boolean printLog) {
		this.printLog = printLog;
	}
	
	public int getMaxRows() {
		return maxRows;
	}
	
	public int getFetchSize() {
		return fetchSize;
	}
	
}

