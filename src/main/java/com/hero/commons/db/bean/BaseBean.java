package com.hero.commons.db.bean;

import com.hero.commons.common.Configurable;
import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.exception.BaseException;

/**
 * 基础bean接口
 * 
 * @author yin.xiong 2017年8月7日
 * @param <E>
 */
public interface BaseBean<E extends BaseBean> extends Configurable {

	E newInstance();

	void encape(final String filedName, final Object fileValue);

	@Override
	void init(Factory factory);

	boolean validate();

	String toString();
}
