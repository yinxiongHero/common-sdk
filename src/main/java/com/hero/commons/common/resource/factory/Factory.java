package com.hero.commons.common.resource.factory;

import java.util.List;

import com.hero.commons.common.Configurable;
import com.hero.commons.common.resource.Resource;
import com.hero.commons.common.resource.impl.ResourceFactory;


/**
 * 工厂接口定义
 *
 * @author sun.jun
 */
public interface Factory extends Resource {

    <T> T getBean(Class<T> vClass);

    <T extends Configurable> T getBean(Class<T> vClass,boolean initial);

    <T> Class<T> getObjectClass(Class<T> vClass);

    <T> List<T> getBeanList(Class<T> vClass);

    <T extends Configurable> List<T> getBeanList(Class<T> vClass,boolean initial);

    Factory getParent();

    Factory getItemFactory(String name);

    Factory getItemFactory(Class vClass);

    Factory[] getItemFactoryArray();

    void addItemResource(ResourceFactory resource);

    String getName();
}
