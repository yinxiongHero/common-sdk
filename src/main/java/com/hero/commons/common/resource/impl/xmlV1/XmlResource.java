package com.hero.commons.common.resource.impl.xmlV1;

import com.hero.commons.common.resource.impl.ResourceFactory;
import com.hero.commons.tool.xml.Config;
import com.hero.commons.tool.xml.Property;

/**
 * xml资源解析   版本V1.0
 * @author yin.xiong
 * 2017年7月14日
 */
public class XmlResource extends ResourceFactory{
	
	public XmlResource(Config config){
		setName(config.getName());//FactoryResource 维护的name
		 //初始 property 参数
		for(Property property:config.getProperty()){
			if ("object".equals(property.getType())) {
                addClass(property.getName(), property.getValue());
            } else if ("singleton".equals(property.getType())) {
                addSingleton(property.getName(), property.getValue());
            } else {
                add(property.getName(), property.getValue());
            }
		}
		//添加子节点
		for(Config subConfig:config.getConfig()){
			addItemResource(new XmlResource(subConfig));
		}
	}
	
}
