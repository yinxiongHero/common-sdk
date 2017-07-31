package com.hero.commons.tool.xml;

import java.util.Vector;

/**
 * xml config 配置信息
 * @author yin.xiong
 * 2017年7月14日
 */
public class Config {
	private String name;
	private String type;
	private Vector<Property> propertyList;
	private Vector<Bean> beanList;
	private Vector<Config> configList;
	
	public Config() {
        propertyList = new Vector<Property>();
        beanList = new Vector<Bean>();
        configList = new Vector<Config>();
    }

    public Config(String name,String type){
        this.name =name;
        this.type =type;
        propertyList = new Vector<Property>();
        beanList = new Vector<Bean>();
        configList = new Vector<Config>();
    }

    public void addProperty(Property vProperty) {
        propertyList.addElement(vProperty);
    }

    public void addBean(Bean vBean) {
        beanList.addElement(vBean);
    }

    public void addConfig(Config vConfig) {
        configList.addElement(vConfig);
    }

    public java.lang.String getName() {
        return this.name;
    }

    public java.lang.String getType() {
        return this.type;
    }

    public Property getProperty(int index) {
        return propertyList.elementAt(index);
    }

    public Property[] getProperty() {
        int size = propertyList.size();
        Property[] mArray = new Property[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = propertyList.elementAt(index);
        }
        return mArray;
    }

    public int getPropertyCount() {
        return propertyList.size();
    }

    public Bean getBean(int index) {
        return beanList.elementAt(index);
    }

    public Bean[] getBean() {
        int size = beanList.size();
        Bean[] mArray = new Bean[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = beanList.elementAt(index);
        }
        return mArray;
    }

    public int getBeanCount() {
        return beanList.size();
    }

    public Config getConfig(int index) {
        return configList.elementAt(index);
    }

    public Config[] getConfig() {
        int size = configList.size();
        Config[] mArray = new Config[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = configList.elementAt(index);
        }
        return mArray;
    }

    public int getConfigCount() {
        return configList.size();
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    public void setProperty(Property[] propertyArray) {
        propertyList.removeAllElements();
        for (Property vProperty : propertyArray) {
            propertyList.addElement(vProperty);
        }
    }

    public void setBean(Bean[] beanArray) {
        beanList.removeAllElements();
        for (Bean vBean : beanArray) {
            beanList.addElement(vBean);
        }
    }

    public void setConfig(Config[] configArray) {
        configList.removeAllElements();
        for (Config vConfig : configArray) {
            configList.addElement(vConfig);
        }
    }
	
}
