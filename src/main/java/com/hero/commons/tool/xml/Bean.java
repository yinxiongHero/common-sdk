package com.hero.commons.tool.xml;

import java.util.Vector;


/**
 * bean 节点
 * @author yin.xiong
 * 2017年7月28日
 */
public class Bean implements XmlNode {

    private java.lang.String name;
    private java.lang.String type;
    private java.lang.String implClass;
    private java.util.Vector<Property> propertyList;
    private java.util.Vector<Bean> beanList;

    public Bean() {
        propertyList = new Vector<Property>();
        beanList = new Vector<Bean>();
    }

    public Bean(String name, String implClass) {
        this.name = name;
        this.implClass = implClass;
        propertyList = new Vector<Property>();
        beanList = new Vector<Bean>();
    }

    public Bean(String name, String type, String implClass) {
        this.name = name;
        this.type = type;
        this.implClass = implClass;
        propertyList = new Vector<Property>();
        beanList = new Vector<Bean>();
    }

    public void addProperty(Property vProperty) {
        propertyList.addElement(vProperty);
    }

    public void addBean(Bean vBean) {
        beanList.addElement(vBean);
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

    public java.lang.String getName() {
        return this.name;
    }

    public java.lang.String getType() {
        return this.type;
    }

    public java.lang.String getImplClass() {
        return this.implClass;
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

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    public void setImplClass(java.lang.String implClass) {
        this.implClass = implClass;
    }

}
