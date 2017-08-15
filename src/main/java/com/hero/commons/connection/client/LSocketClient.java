package com.hero.commons.connection.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.connection.Sender;
import com.hero.commons.exception.RemoteException;
import com.hero.commons.tool.log.LogFactory;
import com.hero.commons.util.StreamUtil;

/**
 * 客户端长连接模式
 * 
 * @author yin.xiong 2017年8月1日
 */
@SuppressWarnings("unused")
public class LSocketClient implements Sender {

	private volatile boolean isShutDown = false;// 连接是否关闭
	private volatile boolean initial = false;

	private boolean isInCluster = false;// 是否重连
	private volatile OutputStream outputSteam;// 往服务端发送数据的数据流
	private volatile Socket socket;// 客户端的socke连接
	private String clientName;
	private String ip;
	private int port;
	private int timeout;// 超时时间
	private long heartTime;// 心跳时间
	private long rebindingTime;// 重连间隔时间
	private int packLenLen;// 报文长度标识位

	@Override
	public void init(Factory factory) {
		if (isInitial())
			return;
		String address = factory.getString("address", null);
		String[] addr = address.split("[:]");
		setClientName(factory.getName());
		setIp(addr[0]);
		setPort(Integer.parseInt(addr[1], 10));
		setTimeout(factory.getInteger("timeout", 0));
		setHeartTime(factory.getLong("heartTime", 0));
		setPackLenLen(factory.getInteger("packLenLen", 0));
		setRebindingTime(factory.getLong("rebindingTime", 5000));
		connect();
	}

	/**
	 * 初始化参数，同时建立连接
	 */
	@Override
	public synchronized void connect() {
		if (initial)
			return;
		if (bind()) {
			LogFactory.getLog().info(
					this,
					new StringBuilder("LSocketClient[").append(clientName).append("/").append(socket.getLocalPort())
							.append("]向 [").append(ip).append(":").append(port).append("]建立链接成功").toString());
		} else {
			LogFactory.getLog().info(
					this,
					new StringBuilder("LSocketClient[").append(clientName).append("/").append(socket.getLocalPort())
							.append("]向 [").append(ip).append(":").append(port).append("]建立链接失败").toString());
		}
		//心跳检测
		Thread heard=new Thread(new Monitor());
		heard.start();
	}

	private boolean bind() {
		try {
			LogFactory.getLog().info(
					this,
					new StringBuilder("LSocketClient[").append(clientName).append("]向[").append(ip).append(":")
							.append(port).append("]开始尝试建立链接....").toString());
			socket = new Socket(ip, port);
			outputSteam = socket.getOutputStream();
			return socket.isConnected();
		} catch (IOException e) {
			LogFactory.getLog().error(this, e);
			return false;
		}
	}

	@Override
	public void send(byte[] data) throws RemoteException {
		if (!initial || isShutDown) {
			throw new RemoteException("S05", "LSocketClient[" + clientName + "] not connect.");
		}
		try {
			outputSteam.write(data);
			outputSteam.flush();
		} catch (Exception e) {
			LogFactory.getLog().error(this, e);
			if (!isShutDown) {
				// 未关闭,尝试重发操作
				try {
					LogFactory.getLog().error("LSocketClient[" + clientName + "] rebinding...！");
					outputSteam.close();
					socket.close();
					while (!bind()) {
						if (!isInCluster) {
							break;
						}
						Thread.sleep(rebindingTime);
					}
					if (isConnected()) {
						// 重发
						LogFactory.getLog().info("LSocketClient[" + clientName + "] rebind success");
						outputSteam.write(data);
						outputSteam.flush();
					} else {
						throw new RemoteException("S05", "LSocketClient[" + clientName + "] is shutdown..");
					}
				} catch (Exception e1) {
					LogFactory.getLog().error(this, e1);
					throw new RemoteException("S05", "LSocketClient,[" + clientName + "]is  exception");
				}
			} else {
				throw new RemoteException("S05", "LSocketClient,[" + clientName + "]is  shutDown");
			}
		}
	}

	@Override
	public byte[] getRcvData() throws RemoteException {
		try {
			ByteArrayOutputStream write = new ByteArrayOutputStream();
			InputStream inputStream = socket.getInputStream();
			int read = inputStream.read();
			int len = -1;
			byte[] b = new byte[1024];
			while ((len = inputStream.read(b)) != -1) {
				write.write(b, 0, len);
			}
			return write.toByteArray();
		} catch (Exception e) {
			LogFactory.getLog().error(this, e);
			throw new RemoteException("S503", "getRcvData exception");
		}
	}

	@Override
	public void disconnect() {
		try {
			isShutDown = true;
			initial = false;
			StreamUtil.close(outputSteam);
			socket.close();
		} catch (IOException e) {
			LogFactory.getLog().error(this, e);
		}
	}

	@Override
	public void setMaxDataSize(int maxDataSize) {
	}

	@Override
	public void setPackLenLen(int packLenLen) {

	}

	public boolean isConnected() {
		return !isShutDown && initial && socket.isConnected() && !socket.isClosed();
	}

	// 内部类
	private final class Monitor implements Runnable {
		@Override
		public void run() {
			// 轮询心跳检测
			while (!isShutDown) {
				try {
					Thread.sleep(heartTime);
					send(new byte[0]);
				} catch (Exception e) {
					LogFactory.getLog().error("LSocketClient[" + clientName + "] send heart message failed!");
					LogFactory.getLog().error(this, e);
				}

			}
		}

	}

	// get and set
	public boolean isInCluster() {
		return isInCluster;
	}

	public boolean isInitial() {
		return initial;
	}

	public void addInCluster() {
		this.isInCluster = true;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setHeartTime(long heartTime) {
		this.heartTime = heartTime;
	}

	public void setRebindingTime(long rebindingTime) {
		this.rebindingTime = rebindingTime;
	}

}
