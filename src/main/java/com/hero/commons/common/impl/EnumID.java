package com.hero.commons.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.hero.commons.common.ID;
import com.hero.commons.common.impl.IDImpl;

/**
 * 节点类
 * 
 * @author yin.xiong 2017年7月13日
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public class EnumID<E extends EnumID> extends IDImpl<E> implements ID {
	private static final long serialVersionUID = 1L;

	private static final Map<Class, HashSet<EnumID>> values = new HashMap<Class, HashSet<EnumID>>();

	protected EnumID(String id) {
		this(id, false);
	}

	protected EnumID(String id, boolean isInitCurClass) {
		super(id);
		if (isInitCurClass) {
			HashSet<EnumID> hashSet = values.get(getClass());
			if (hashSet==null) {
				hashSet=new HashSet<EnumID>();
			}
			hashSet.add(this);
			values.put(getClass(), hashSet);
		}
	}

	/**
	 * 获取容器中所有当前类E 的全部节点
	 * 把当前Class下所有set<EnumID> 全部以list返回
	 * 
	 * @return
	 */
	public List<E> getAllBrother() {
		HashSet<E> hashSet = (HashSet<E>) values.get(getClass());
		if (hashSet==null) {
			return null;
		}
		List<E> list=new ArrayList<E>();
		list.addAll(hashSet);
		return list;
	}
	
	/**
	 * 静态内部类 
	 */
	private  static class A extends EnumID{
		protected A(String id, boolean isInitCurClass) {
			super(id, isInitCurClass);
		}
	}
	
	public static void main(String[] args) {
		EnumID<A> enumID = new EnumID<A>("1");
	}
}
