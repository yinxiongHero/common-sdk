package com.hero.commons.common.resource.factory;

import com.hero.commons.common.resource.ResourceLoader;
import com.hero.commons.common.resource.XmlResourceLoader;
import com.hero.commons.common.resource.impl.ResourceFactory;
import com.hero.commons.util.ResourceUtil;

/**
 * 系统配置工厂 对外暴露方法 以简单工厂的方式实现资源切换
 * 
 * @author yin.xiong 2017年7月27日
 */
public class SystemConfigFactory {
	private final static ResourceLoader loader;

	static {
		// 项目根路径下，默认systemconfig.xml 会被加载
		loader = new XmlResourceLoader(ResourceUtil.getPropertyStr("common-sdk-base", "default.config.file"));
	}

	public static FactoryResource factoryResource;

	// query
	public static boolean containsSystemFactory(Class cls) {
		return containsSystemFactory(cls.getName());
	}

	public static boolean containsSystemFactory(String className) {
		return loader.containsResource(className);
	}

	public static Factory getSystemFactory(Class cls) {
		return getSystemFactory(cls.getName());
	}

	public static Factory getSystemFactory(String className) {
		ResourceFactory resource = loader.getResource(className);
		return resource == null ? null : new FactoryResource(null, resource);
	}

	// add
	public static Factory addSystemFactory(String className, ResourceFactory resource) {
		loader.addResource(className, resource);
		return new FactoryResource(null, resource);
	}

	public static Factory addSystemFactory(Class cls, ResourceFactory resource) {
		return addSystemFactory(cls.getName(), resource);
	}
	
	//getFactory
	public static Factory getItemFactory(String... clsName) {
		Factory factory = null;
		for (String className : clsName) {
			if (factory == null) {
				factory = SystemConfigFactory.getItemFactory(className);
			} else {
				factory = factory.getItemFactory(className);
			}
			if (factory == null) {
				return null;
			}
		}
		return factory;
	}

	public static Factory getItemFactory(Class... cls) {
		Factory factory = null;
		for (Class c : cls) {
			if (factory == null) {
				factory = SystemConfigFactory.getItemFactory(c);
			} else {
				factory = factory.getItemFactory(c);
			}
			if (factory == null) {
				return null;
			}
		}
		return factory;
	}
	
	public static ResourceLoader getResourceLoader() {
		return loader;
	}

}
