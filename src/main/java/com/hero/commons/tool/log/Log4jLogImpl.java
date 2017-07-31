package com.hero.commons.tool.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

/**
 * org.apache.log4j.Logger 的封装实现类
 * 
 * @author yin.xiong 2017年7月17日
 */
public class Log4jLogImpl implements Log {
	private Logger logger;
	// 全局唯一的log4j配置文件
	private static Properties log4jProperty = null;

	private static Properties getLog4jProerties() {
		if (log4jProperty == null) {
			ResourceBundle logBundle = ResourceBundle.getBundle("log4j");
			Enumeration enumeration = logBundle.getKeys();
			Properties properties = new Properties();
			while (enumeration.hasMoreElements()) {
				String tKey = (String) enumeration.nextElement();
				if (tKey.startsWith("log4j")) {
					properties.put(tKey, logBundle.getString(tKey));
				}
			}
			log4jProperty = properties;
		}
		return log4jProperty;
	}

	public Log4jLogImpl(String s) {
		try {
			PropertyConfigurator.configure(getLog4jProerties());
			logger = Logger.getLogger(s);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			e.printStackTrace(System.out);
			logger = Logger.getRootLogger();
			PatternLayout pl = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss SSS} %-p - %m%n");
			ConsoleAppender rfa = new ConsoleAppender(pl);
			logger.addAppender(rfa);
		}
	}

	/**
	 * 把错误的堆栈信息转成string输出
	 * 
	 * @param e
	 * @return
	 */
	private String getLogStackTrace(Throwable e) {
		PrintStream pStream = null;
		OutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			pStream = new PrintStream(outputStream);
			e.printStackTrace(pStream);
			return outputStream.toString();

		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (pStream != null) {
				pStream.close();
			}
		}
	}

	public boolean enableTrace() {
		return logger.isEnabledFor(Level.TRACE);
	}

	public boolean enableDebug() {
		return logger.isEnabledFor(Level.DEBUG);
	}

	public boolean enableInfo() {
		return logger.isEnabledFor(Level.INFO);
	}

	public boolean enableWarn() {
		return logger.isEnabledFor(Level.WARN);
	}

	public boolean enableError() {
		return logger.isEnabledFor(Level.ERROR);
	}

	public boolean enableFatal() {
		return logger.isEnabledFor(Level.FATAL);
	}

	public boolean enableALL() {
		return logger.isEnabledFor(Level.ALL);
	}

	public void debug(String message) {
		logger.debug(message);
	}

	public void debug(Object obj, String message) {
		logger.debug("[" + obj.getClass().getName() + "]:" + message);
	}

	public void debug(Class objClass, String message) {
		logger.debug("[" + objClass.getName() + "]:" + message);
	}

	public void debug(Throwable e) {
		debug("Exception", e);
	}

	public void debug(String message, Throwable e) {
		logger.debug(message + ":" + e.getMessage() + getLogStackTrace(e));
	}

	public void info(String message) {
		logger.info(message);
	}

	public void info(Object obj, String message) {
		logger.info("[" + obj.getClass().getName() + "]:" + message);
	}

	public void info(Class objClass, String message) {
		logger.info("[" + objClass.getName() + "]:" + message);
	}

	public void info(Throwable e) {
		info("Exception", e);
	}

	public void info(String message, Throwable e) {
		logger.info(message + ":" + e.getMessage() + getLogStackTrace(e));
	}

	public void warn(String message) {
		logger.warn(message);
	}

	public void warn(Object obj, String message) {
		logger.warn("[" + obj.getClass().getName() + "]:" + message);
	}

	public void warn(Class objClass, String message) {
		logger.warn("[" + objClass.getName() + "]:" + message);
	}

	public void warn(Throwable e) {
		warn("Exception", e);
	}

	public void warn(String message, Throwable e) {
		logger.warn(message + ":" + e.getMessage() + getLogStackTrace(e));
	}

	public void error(String message) {
		logger.error(message);
	}

	public void error(Object obj, String message) {
		logger.error("[" + obj.getClass().getName() + "]:" + message);
	}

	public void error(Class objClass, String message) {
		logger.error("[" + objClass.getName() + "]:" + message);
	}

	public void error(Throwable e) {
		error("Exception", e);
	}

	public void error(String message, Throwable e) {
		logger.error(message + ":" + e.getMessage() + getLogStackTrace(e));
	}

	public void error(Class objClass, Throwable e) {
		logger.error("[" + objClass.getName() + "]:" + e.getMessage() + getLogStackTrace(e));
	}

	public void error(Object obj, Throwable e) {
		logger.error("[" + obj.getClass().getName() + "]:" + e.getMessage() + getLogStackTrace(e));
	}

	public void fatal(String message) {
		logger.fatal(message);
	}

	public void fatal(Object obj, String message) {
		logger.fatal("[" + obj.getClass().getName() + "]:" + message);
	}

	public void fatal(Class objClass, String message) {
		logger.fatal("[" + objClass.getName() + "]:" + message);
	}

	public void fatal(Throwable e) {
		fatal("Exception", e);
	}

	public void fatal(String message, Throwable e) {
		logger.fatal(message + ":" + e.getMessage() + getLogStackTrace(e));
	}

	public void trace(String message) {
		logger.trace(message);
	}

	public void trace(Object obj, String message) {
		logger.trace("[" + obj.getClass().getName() + "]:" + message);
	}

	public void trace(Class objClass, String message) {
		logger.trace("[" + objClass.getName() + "]:" + message);
	}

	public void trace(Throwable e) {
		trace("Exception", e);
	}

	public void trace(String message, Throwable e) {
		logger.trace(message + ":" + e.getMessage() + getLogStackTrace(e));
	}

}
