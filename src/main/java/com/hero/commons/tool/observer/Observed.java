package com.hero.commons.tool.observer;
/**
 * 被观察者
 * @author yin.xiong
 * 2017年8月3日
 */
public interface Observed {
	
	public void addObserver(Observer observer);
	
	public void delObserver(Observer observer);
	
	public boolean noticeObserver(int maxNoticeSize,byte[] byteArray);
	
	/**
	 * 统计观察者数量
	 * @return
	 */
	public int countObservers();
}
