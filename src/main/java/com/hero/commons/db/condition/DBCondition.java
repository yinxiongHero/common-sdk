package com.hero.commons.db.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hero.commons.util.StringUtil;

/**
 * 数据库查询条件 添加条件 组装bulid sql
 * 
 * @author yin.xiong 2017年8月7日
 */
@SuppressWarnings("unused")
public class DBCondition {

	private Map<String, DBColumn> columnMap = new HashMap<String, DBCondition.DBColumn>();

	private String sql;
	private Object[] params;
	private String extCondition;
	private String orderBy;

	protected void add(String key, String value) {
		add(key, value, "=");
	}

	public void add(String key, String value, String string) {
		String keyT = key + "|" + value;
		DBColumn dbColumn = columnMap.get(keyT);
		if (dbColumn == null) {
			synchronized (this) {
				dbColumn = new DBColumn(keyT, columnMap.size() + 1);
				columnMap.put(keyT, dbColumn);
			}
		}
		dbColumn.addValue(value);
	}

	/**
	 * 创造sql
	 * 
	 * @return
	 */
	public String build() {
		DBColumn[] columnArr = getColumnArray();
		StringBuffer sb = new StringBuffer("1==1");
		List<Object> paramList = new ArrayList<Object>();
		for (DBColumn column : columnArr) {
			if (column.getValueList().size() == 0) {
				continue;
			} else if (column.getValueList().size() == 1) {
				sb.append(" AND ").append(column.getKey()).append(" ").append(column.getCondition()).append(" ? ");
			} else {
				sb.append(" AND ( ");
				for (int i = 0; i < column.getValueList().size(); i++) {
					if (i != 0) {
						sb.append(" OR ");
					}
					sb.append(column.getKey()).append(" ").append(column.getCondition()).append("?");
				}
				sb.append(" ) ");
			}
			paramList.addAll(column.getValueList());
		}
		params = new Object[paramList.size()];
		paramList.toArray(params);
		if (StringUtil.isNotEmpty(extCondition)) {
			sb.append(" AND " + extCondition);
		}
		if (StringUtil.isNotEmpty(orderBy)) {
			sb.append(" ORDER BY " + orderBy);
		}
		return sb.toString();
	}

	public String getSql(){
		return sql;
	}
	
	public Object[] getParams(){
		return params;
	}
	
	public static DBCondition newInstance() {
		return new DBCondition();
	}
	
	/**
	 * 返回排序后的列数组
	 * 
	 * @return
	 */
	private DBColumn[] getColumnArray() {
		Collection<DBColumn> valueList = columnMap.values();
		DBColumn[] result = new DBColumn[valueList.size()];
		valueList.toArray(result);
		Arrays.sort(result);
		return result;
	}

	/**
	 * 内部数据行 类
	 * 
	 * @author yin.xiong 2017年8月7日
	 */

	private static class DBColumn implements Comparable<DBColumn> {
		private int index;
		private String key;
		private String condition;
		private List<Object> valueList = new ArrayList<Object>();

		@Override
		public int compareTo(DBColumn o) {
			if (o == null) {
				return -1;
			}
			return this.index - o.index;
		}

		public DBColumn(String key, int index) {
			this.index = index;
			this.key = key;
			this.condition = "=";
		}

		public DBColumn(String key, int index, String conditoion) {
			this.index = index;
			this.key = key;
			this.condition = conditoion;
		}

		public int getIndex() {
			return index;
		}

		public String getKey() {
			return key;
		}

		public String getCondition() {
			return condition;
		}

		public List<Object> getValueList() {
			return valueList;
		}

		public void addValue(Object value) {
			valueList.add(value);
		}

	}
}
