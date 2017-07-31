package com.hero.commons.common.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.hero.commons.common.resource.impl.ResourceFactory;


/**
 * 文件资源加载器
 * 
 * map容器中维护的是一个个xmlResource 
 * @author yin.xiong
 * 2017年7月14日
 */
public  abstract class ResourceLoader {
	private Map<String, ResourceFactory> resourceMap=new HashMap<String, ResourceFactory>();
	
    public void addResource(String name, ResourceFactory resource) {
    	ResourceFactory factoryResource = resourceMap.get(name);
    	if (factoryResource!=null) {
    		for(ResourceFactory child:factoryResource.child()){
    			resource.addItemResource(child);
    		}
		}
    	resourceMap.put(name, resource);
    }
    
    public ResourceFactory[] getResouorce() {
    	Collection<ResourceFactory> values = resourceMap.values();
    	ResourceFactory[] result=new ResourceFactory[values.size()];
    	return values.toArray(result);
    }
    
    /**
     *
     * @param vClass
     * @return
     */
    public ResourceFactory getResource(Class vClass) {
    	return resourceMap.get(vClass.getName());
    }
    
    public ResourceFactory getResource(String vClassName) {
    	return resourceMap.get(vClassName);
    }
    
    public Set<String> getFactoryNames() {
    	return resourceMap.keySet();
    }
    
    public boolean containsResource(String vClassName) {
    	return resourceMap.containsKey(vClassName);
    }
    
    
}
