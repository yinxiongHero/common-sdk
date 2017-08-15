package com.hero.commons.connection.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.hero.commons.connection.Connection;
import com.hero.commons.exception.RemoteException;
import com.hero.commons.tool.log.LogFactory;
import com.hero.commons.util.StreamUtil;

/**
 * ftp客户端工具类
 * 
 * @author yin.xiong 2017年8月3日
 */
@SuppressWarnings("unused")
public class FtpClient implements Connection {

	public static final int DEFAULT_DATA_PORT = FTPClient.DEFAULT_DATA_PORT;
	public static final int DEFAULT_PORT = FTPClient.DEFAULT_PORT;

	// file type
	public static final int ASCII_FILE_TYPE = FTPClient.ASCII_FILE_TYPE;
	public static final int EBCDIC_FILE_TYPE = FTPClient.EBCDIC_FILE_TYPE;
	public static final int BINARY_FILE_TYPE = FTPClient.BINARY_FILE_TYPE;
	public static final int LOCAL_FILE_TYPE = FTPClient.LOCAL_FILE_TYPE;

	public static final int NON_PRINT_TEXT_FORMAT = FTPClient.NON_PRINT_TEXT_FORMAT;
	public static final int TELNET_TEXT_FORMAT = FTPClient.TELNET_TEXT_FORMAT;
	public static final int CARRIAGE_CONTROL_TEXT_FORMAT = FTPClient.CARRIAGE_CONTROL_TEXT_FORMAT;

	public static final int FILE_STRUCTURE = FTPClient.FILE_STRUCTURE;
	public static final int RECORD_STRUCTURE = FTPClient.RECORD_STRUCTURE;
	public static final int PAGE_STRUCTURE = FTPClient.PAGE_STRUCTURE;

	public static final int STREAM_TRANSFER_MODE = FTPClient.STREAM_TRANSFER_MODE;
	public static final int BLOCK_TRANSFER_MODE = FTPClient.BLOCK_TRANSFER_MODE;
	public static final int COMPRESSED_TRANSFER_MODE = FTPClient.COMPRESSED_TRANSFER_MODE;

	public static final java.lang.String DEFAULT_CONTROL_ENCODING = FTPClient.DEFAULT_CONTROL_ENCODING;

	// ftp mode
	public final static int ACTIVE_LOCAL_DATA_CONNECTION_MODE = FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE;
	public final static int ACTIVE_REMOTE_DATA_CONNECTION_MODE = FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE;
	public final static int PASSIVE_LOCAL_DATA_CONNECTION_MODE = FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE;
	public final static int PASSIVE_REMOTE_DATA_CONNECTION_MODE = FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE;

	private FTPClient ftpClient;

	private final String hostname;
	private final int hostPort;

	private int fileType = FTPClient.ASCII_FILE_TYPE;
	private String controlEncoding = FTPClient.DEFAULT_CONTROL_ENCODING;
	private String loginId;
	private String password;
	private int ftpMode;// ftp模式

	// 构造方法
	public FtpClient(String hostname, int hostPort) {
		this.hostname = hostname;
		this.hostPort = hostPort;
	}

	public FtpClient(String hostname, int hostPort, String loginId, String password) {
		this.hostname = hostname;
		this.hostPort = hostPort;
		this.loginId = loginId;
		this.password = password;
	}
	
	//常用的封装方法
	public void download(String remoteFileName,String localFileName) throws RemoteException{
		BufferedOutputStream outputStream=null;
		try {
			outputStream=new BufferedOutputStream(new FileOutputStream(localFileName));
			if (ftpClient.retrieveFile(remoteFileName, outputStream)) {
				LogFactory.getLog().debug(this,"download remote path=["+remoteFileName+"] success!");
			}else {
				LogFactory.getLog().error(this,"download remote path=["+remoteFileName+"] fail!");
			}
		} catch (IOException e) {
			LogFactory.getLog().error(this,e);
			throw new RemoteException("S505","download remote path=["+remoteFileName+"] fail!");
		}finally{
			if (outputStream!=null) {
				StreamUtil.close(outputStream);
			}
		}
	}
	
	//上传文件
	
	  public void upload(String remoteFileName, String filepathname, boolean append)
	            throws RemoteException {
		  
		  
	  }
	
	
	//改变文件夹
	
	
	
	
	public void download(String remoteFileName,File localFile) throws RemoteException{
		BufferedOutputStream outputStream=null;
		try {
			outputStream=new BufferedOutputStream(new FileOutputStream(localFile));
			if (ftpClient.retrieveFile(remoteFileName, outputStream)) {
				LogFactory.getLog().debug(this,"download remote path=["+remoteFileName+"] success!");
			}else {
				LogFactory.getLog().error(this,"download remote path=["+remoteFileName+"] fail!");
			}
		} catch (IOException e) {
			LogFactory.getLog().error(this,e);
			throw new RemoteException("S505","download remote path=["+remoteFileName+"] fail!");
		}finally{
			if (outputStream!=null) {
				StreamUtil.close(outputStream);
			}
		}
	}
	

	@Override
	public void connect() throws RemoteException {
		LogFactory.getLog().debug("FTPClient=[" + hostname + ":" + hostPort + "]");
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(hostname, hostPort);
			ftpClient.login(password, password);
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				LogFactory.getLog().debug(this,
						"connect to hostname=[" + hostname + "],hostProt=[" + hostPort + "],success!");
			} else {
				throw new RemoteException("S05", "connect to hostname=[" + hostname + "],hostProt=[" + hostPort
						+ "],fail,replyCode=[" + ftpClient.getReplyCode() + "]!");
			}

		} catch (Exception e) {
			LogFactory.getLog().error(this, "FTPClient=[" + hostname + ":" + hostPort + "],Connect fail");
			throw new RemoteException("S503", "FTPClient=[" + hostname + ":" + hostPort + "],Connect fail");
		}
	}
	
	@Override
	public void disconnect() {
		try {
			if (ftpClient!=null) {
				ftpClient.logout();
			}
		} catch (IOException e1) {
			LogFactory.getLog().error(this,e1);
		}
		try {
			if(ftpClient!=null){
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			LogFactory.getLog().error(this,e);
		}
	}

	// SET

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFtpMode(int ftpMode) {
		this.ftpMode = ftpMode;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public void setControlEncoding(String controlEncoding) {
		this.controlEncoding = controlEncoding;
	}

}
