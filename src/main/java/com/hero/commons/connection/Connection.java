package com.hero.commons.connection;

import com.hero.commons.exception.RemoteException;

/**
 * 连接接口
 * @author yin.xiong
 * 2017年8月1日
 */
public interface Connection {
	void connect() throws RemoteException;
	
	void disconnect();
}
