package com.hero.commons.common.impl;

import java.io.Serializable;

import com.hero.commons.common.ID;

/**
 * 
 * @author yin.xiong
 * 2017年7月13日
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public class IDImpl<E extends IDImpl> implements ID, Serializable, Comparable<E> {

	private static final long serialVersionUID = 1L;

	private String id;
	private boolean valid;

	public boolean equals(E o) {
		return o != null && this.id.equals(o.getId());
	}
	
	protected IDImpl(String id){
		this.id=id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		//同一个class类型
		if (!obj.getClass().equals(getClass())) {
			return false;
		}
		//最后比较id
		IDImpl impl=(IDImpl) obj;
		return impl.getId().equals(this.getId());
	}

	@Override
	public String getId() {
		return id;
	}

	public int hashCode() {
		return id.hashCode();
	}

	protected boolean validate() {
		return true;
	}

	public boolean isValid() {
		return valid;
	}

	@Override
	public int compareTo(E o) {
		return this.id.compareTo(o.getId());
	}

}
