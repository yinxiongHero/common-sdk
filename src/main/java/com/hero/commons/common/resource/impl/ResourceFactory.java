package com.hero.commons.common.resource.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.hero.commons.tool.log.LogFactory;
import com.hero.commons.util.ClassUtil;

/**
 * 资源工厂 
 * 
 * 资源文件解析类
 * 
 * @author yin.xiong 2017年7月13日
 */
@SuppressWarnings("unused")
public class ResourceFactory extends AbstractResource {
	// 文件key
	private String name;

	// 内嵌式资源容器
	private Map<String, ResourceFactory> resourceMap = new HashMap<String, ResourceFactory>();

	// 资源容器集合
	private List<ResourceFactory> itemResourceList = new ArrayList<ResourceFactory>();

	private final Map<Class, List<Class>> singletonClassMap = new HashMap<Class, List<Class>>();

	private final Map<Class, List<Object>> singletonObjectMap = new HashMap<Class, List<Object>>();

	private final Map<Class, List<Class>> instanceMap = new HashMap<Class, List<Class>>();
	
	
	
	/**
	 * 存放在初始instanceMap中
	 * 
	 * @param classType
	 *            接口全类名
	 * @param classImpl
	 *            实现类全类名
	 */
	protected synchronized final void addClass(String classType, String classImpl) {
		Class key = ClassUtil.getClass(classType);
		Class value = ClassUtil.getClass(classImpl);
		List<Class> list = instanceMap.get(key);
		if (list == null) {
			list = new ArrayList<Class>();
			instanceMap.put(key, list);
		}
		list.add(value);
	}

	protected synchronized final void addSingleton(String classType, String classImpl) {
		Class key = ClassUtil.getClass(classType);
		Class value = ClassUtil.getClass(classImpl);
		List<Class> list = singletonClassMap.get(key);
		if (list == null) {
			list = new ArrayList<Class>();
			singletonClassMap.put(key, list);
		}
		list.add(value);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void addItemResource(ResourceFactory resourceFactory) {
		if (resourceMap.containsValue(resourceFactory)) {
			return;
		}
		itemResourceList.add(resourceFactory);
		resourceMap.put(resourceFactory.getName(), resourceFactory);
	}

	public ResourceFactory child(String name) {
		return resourceMap.get(name);
	}

	public ResourceFactory[] child() {
		ResourceFactory[] result = new ResourceFactory[itemResourceList.size()];
		return itemResourceList.toArray(result);
	}

	private void checkSingleton(Class vClass) {
		try {
			List<Class> listSingletonClass = singletonClassMap.get(vClass);
			if (listSingletonClass != null) {
				synchronized (vClass) {
					listSingletonClass = singletonClassMap.get(vClass);
					if (listSingletonClass != null) {
						List<Object> listobj = new ArrayList<Object>();
						for (Class implClass : listSingletonClass) {
							listobj.add(implClass.newInstance());
						}
						singletonObjectMap.put(vClass, listobj);
						singletonClassMap.remove(vClass);
					}
				}
			}
		} catch (Exception e) {
		}
	}
	
	
	 /**
     * get object class
     *
     * @param vClass key name
     * @return Object
     */
    public <T> Class<T> getObjectClass(Class<T> vClass) {
        try {
            checkSingleton(vClass);
            List<Class> listClass = instanceMap.get(vClass);
            if (listClass != null && listClass.size() > 0) {
                @SuppressWarnings("unchecked")
                Class<T> obj = (Class<T>) listClass.get(0);
                LogFactory.getLog().debug("get object class:" + obj);
                return obj;
            }
        } catch (Exception e) {
            LogFactory.getLog().fatal(e);
        }
        return null;
    }
	
	/**
	 * 通过一个class类 查出对应的全部资源文件类
	 * @param vClass
	 * @return
	 */
	public  <E> List<E>  getBeanList(Class<E> vClass){
		List<Object> classList = singletonObjectMap.get(vClass);
		if (classList!=null) {
			List<E>  result=(List<E>) classList;
			return result;
		}
		List<Class> list = singletonClassMap.get(vClass);
		List<E> result=new ArrayList<E>();
		if (list!=null) {
			for(Class c:list){
				E newInstance;
				try {
					newInstance = (E) c.newInstance();
					result.add(newInstance);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	
	 /**
     * get as object
     *
     * @param vClass key name
     * @return Object
     */
    public <T> T getBean(Class<T> vClass) {
        try {
            checkSingleton(vClass);
            List<Object> listObj = singletonObjectMap.get(vClass);
            if (listObj != null && listObj.size() > 0) {
                @SuppressWarnings("unchecked")
                T o = (T) listObj.get(0);
                return o;
            }
            List<Class> listClass = instanceMap.get(vClass);
            if (listClass != null && listClass.size() > 0) {
                @SuppressWarnings("unchecked")
                Class<T> vCls = (Class<T>) listClass.get(0);
                return vCls.newInstance();
            }
        } catch (Exception e) {
            LogFactory.getLog().fatal(e);
        }
        if (resourceMap.containsKey(vClass.getName())) {
            LogFactory.getLog().warn("[property]/[type] for param [" + vClass.getName() + "] is empty,get object fail!");
        }
        return null;
    }

}
