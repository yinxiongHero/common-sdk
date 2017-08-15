package com.hero.commons.db.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.exception.BaseException;
/**
 * arrayBean 
 * @author yin.xiong
 * 2017年8月7日
 */
public final class ArrayBean implements BaseBean<ArrayBean> {

	private Vector<String> nameVector = new Vector<String>();
	private Map<String, Object> values = new HashMap<String, Object>();

	@Override
	public ArrayBean newInstance() {
		return new ArrayBean();
	}

	@Override
	public void encape(String filedName, Object fileValue) {
		nameVector.add(filedName);
		values.put(filedName, fileValue);
	}

	@Override
	public void init(Factory factory){

	}

	@Override
	public boolean validate() {
		return true;
	}

	/**
	 * @return column name list
	 */
	public List listNames() {
		return nameVector;
	}

	/**
	 * get value from index =i
	 *
	 * @param i
	 *            index
	 * @return column value
	 */
	public Object get(int i) {
		return values.get(nameVector.get(i));
	}

	/**
	 * @param name
	 *            column name
	 * @return column value
	 */
	public Object get(String name) {
		return values.get(name);
	}

}
