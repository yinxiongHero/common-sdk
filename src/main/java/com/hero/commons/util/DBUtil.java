package com.hero.commons.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.hero.commons.db.bean.BaseBean;
import com.hero.commons.db.connection.DBConnection;
import com.hero.commons.exception.DBException;
import com.hero.commons.tool.log.LogFactory;

/**
 * 数据操作类
 * 
 * @author yin.xiong 2017年8月4日
 */
public class DBUtil {
	
	/**
	 * 从resultSet 结果集中组装一个实体bean返回
	 * @param resultSet
	 * @param bean
	 * @param checkValidate
	 * @return
	 * @throws DBException
	 */
	public static <T extends BaseBean<T>> T fetchObject(ResultSet resultSet, T bean, boolean checkValidate)
			throws DBException {
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			T t = bean.newInstance();
			for (int i = 0; i < columnCount; i++) {
				t.encape(metaData.getColumnName(i), resultSet.getObject(i));
			}
			if (checkValidate) {
				if (!t.validate()) {
					LogFactory.getLog().error("Record validate fail ,Class =[" + t.getClass().getName() + "]");
				}
			}
			return t;
		} catch (SQLException e) {
			throw new DBException("S04", "FETCH DATA FROM DATABASE FAIL", e);
		}
	}

	public static void close(DBConnection con) {
		if (con != null) {
			con.close();
			con = null;
		}
	}

	public static void close(Connection con) {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			LogFactory.getLog().fatal(e);
		}
	}

	public static void close(Statement s) {
		try {
			if (s != null) {
				s.close();
				s = null;
			}
		} catch (SQLException e) {
			LogFactory.getLog().fatal(e);
		}
	}

	public static void close(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException e) {
			LogFactory.getLog().fatal(e);
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			LogFactory.getLog().fatal(e);
		}
	}
}
