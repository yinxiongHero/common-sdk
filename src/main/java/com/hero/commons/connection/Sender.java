package com.hero.commons.connection;

import com.hero.commons.common.Configurable;
import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.exception.RemoteException;
/**
 * 发送端接口
 * @author yin.xiong
 * 2017年8月1日
 */
public interface Sender extends Configurable,Connection{
	
	void init(Factory factory);
    
    /**
     * @param maxDataSize max data size of inputstream
     */
    void setMaxDataSize(int maxDataSize);

    /**
     * @param packLenLen message pack len
     */
    void setPackLenLen(int packLenLen);
    
    void send(byte[] data) throws RemoteException;

    byte[] getRcvData() throws RemoteException;
}
