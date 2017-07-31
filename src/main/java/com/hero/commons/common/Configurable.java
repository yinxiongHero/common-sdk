package com.hero.commons.common;

import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.exception.BaseException;

/**
 * 配置化接口
 * @author yin.xiong
 * 2017年7月27日
 */
public interface Configurable {

    void init(Factory factory) throws BaseException;

}
