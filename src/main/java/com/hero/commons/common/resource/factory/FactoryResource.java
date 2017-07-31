package com.hero.commons.common.resource.factory;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.hero.commons.common.Configurable;
import com.hero.commons.common.KVPair;
import com.hero.commons.common.resource.impl.ResourceFactory;
import com.hero.commons.exception.BaseException;
import com.hero.commons.tool.log.LogFactory;

/**
 * 工厂资源类
 * 
 * @author yin.xiong 2017年7月28日
 */
public class FactoryResource implements Factory {

	private final Factory parnet;
	private final ResourceFactory resourceFactory;

	FactoryResource(Factory parnet, ResourceFactory resourceFactory) {
		this.parnet = parnet;
		this.resourceFactory = resourceFactory;
	}

	@Override
	public String[] getStringArray(String name) {
		return resourceFactory.getStringArray(name);
	}

	@Override
	public String[] getStringArray(String name, String[] vDefault) {
		return resourceFactory.getStringArray(name, vDefault);
	}

	@Override
	public KVPair[] getByPrefix(String prefix) {
		return resourceFactory.getByPrefix(prefix);
	}

	@Override
	public String getString(String name) {
		return resourceFactory.getString(name);
	}

	@Override
	public String getString(String name, String vDefault) {
		return resourceFactory.getString(name, vDefault);
	}

	@Override
	public int getInteger(String name) {
		return resourceFactory.getInteger(name);
	}

	@Override
	public int getInteger(String name, int vDefault) {
		return resourceFactory.getInteger(name, vDefault);
	}

	@Override
	public int getInteger(String name, int digit, int vDefault) {
		return 0;
	}

	@Override
	public long getLong(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(String name, long vDefault) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(String name, int digit, long vDefault) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getBoolean(String name) {
		return resourceFactory.getBoolean(name);
	}

	@Override
	public boolean getBoolean(String name, boolean vDefault) {
		return resourceFactory.getBoolean(name,vDefault);
	}

	@Override
	public BigDecimal getDecimal(String name) {
		return resourceFactory.getDecimal(name);
	}

	@Override
	public BigDecimal getDecimal(String name, BigDecimal vDefault) {
		return resourceFactory.getDecimal(name, vDefault);
	}

	@Override
	public Enumeration<String> getkey() {
		return resourceFactory.getkey();
	}

	@Override
	public Iterator<String> keyIterator() {
		return resourceFactory.keyIterator();
	}

	@Override
	public String[] getKeyArray() {
		return resourceFactory.getKeyArray();
	}

	@Override
	public boolean containsKey(String key) {
		return resourceFactory.containsKey(key);
	}

	@Override
	public <T> T getBean(Class<T> vClass) {
		return resourceFactory.getBean(vClass);
	}

	@Override
	public <T extends Configurable> T getBean(Class<T> vClass, boolean initial) {
		T t = resourceFactory.getBean(vClass);
		try {
			if (initial) {
					t.init(this);
			}
		} catch (BaseException e) {
			LogFactory.getLog().error(this,e);
		}
		return t;
	}

	@Override
	public <T> Class<T> getObjectClass(Class<T> vClass) {
		return resourceFactory.getObjectClass(vClass);
	}

	@Override
	public <T> List<T> getBeanList(Class<T> vClass) {
		return resourceFactory.getBeanList(vClass);
	}

	@Override
	public <T extends Configurable> List<T> getBeanList(Class<T> vClass, boolean initial) {
		List<T> beanList = resourceFactory.getBeanList(vClass);
		try {
			if (initial) {
				for (T t : beanList) {
					t.init(this);
				}
			}
		} catch (BaseException e) {
			LogFactory.getLog().error(this,e);
		}
		return beanList;
	}

	@Override
	public Factory getParent() {
		return parnet;
	}

	@Override
	public Factory getItemFactory(String name) {
		ResourceFactory resouce = resourceFactory.child(name);
		if (resouce == null) {
			return null;
		}
		return new FactoryResource(this, resouce);
	}

	@Override
	public Factory getItemFactory(Class vClass) {
		return getItemFactory(vClass.getName());
	}

	@Override
	public Factory[] getItemFactoryArray() {
		ResourceFactory[] child = resourceFactory.child();
		if (child == null || child.length == 0) {
			return new Factory[0];
		}
		Factory[] factory = new Factory[child.length];
		for (int i = 0; i < child.length; i++) {
			factory[i] = new FactoryResource(this, child[i]);
		}
		return factory;
	}

	@Override
	public void addItemResource(ResourceFactory resource) {
		resourceFactory.addItemResource(resource);
	}

	@Override
	public String getName() {
		return resourceFactory.getName();
	}

}
