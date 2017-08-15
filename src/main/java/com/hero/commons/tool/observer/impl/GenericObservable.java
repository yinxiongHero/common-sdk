package com.hero.commons.tool.observer.impl;

import java.util.HashSet;
import java.util.Set;

import com.hero.commons.tool.observer.Observed;
import com.hero.commons.tool.observer.Observer;

/**
 * 通用被观察者
 * @author yin.xiong
 * 2017年8月4日
 */
public class GenericObservable implements Observed{
	private Set<Observer> observerSet;
	
	public GenericObservable() {
		observerSet=new HashSet<Observer>();
	}
	@Override
	public void addObserver(Observer observer) {
		observerSet.add(observer);
	}

	@Override
	public void delObserver(Observer observer) {
		observerSet.remove(observer);
	}

	@Override
	public boolean noticeObserver(int maxNoticeSize, byte[] byteArray) {
		for(Observer observer:observerSet){
			observer.notice(maxNoticeSize, byteArray);
		}
		return true;
	}

	@Override
	public int countObservers() {
		return observerSet.size();
	}

}
