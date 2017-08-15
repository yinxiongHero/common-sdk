package com.hero.commons.tool.observer;
/**
 * 观察者
 * @author yin.xiong
 * 2017年8月3日
 */
public interface Observer {
	
	/**
	 * 通知方法
	 * @param maxNoticeCount
	 * @param btye
	 * @return
	 */
	 boolean notice(int maxNoticeCount,byte[] btye);
}
