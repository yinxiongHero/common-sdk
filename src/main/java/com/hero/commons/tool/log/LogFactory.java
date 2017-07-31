package com.hero.commons.tool.log;

import java.util.HashMap;
import java.util.Map;
/**
 * log工厂 维护的一个个静态的org.apache.log4j.Logger log实例  默认rootLog
 * @author yin.xiong
 * 2017年7月17日
 */
public class LogFactory {
	private static Log rootLog = new Log4jLogImpl("rootLog");

	private static Log serverLog = new Log4jLogImpl("serverLog");

	private static Map<String, Log> logMap = new HashMap<String, Log>();

	public static Log getLog(String str) {
		Log log = logMap.get(str);
		if (log == null) {
			synchronized (log) {
				if (log == null) {
					log = new Log4jLogImpl(str);
					logMap.put(str, log);
				}
				return log;
			}
		}
		return log;
	}

	public static Log getLog() {
		return rootLog;
	}

	public static Log getServerLog() {
		return serverLog;
	}

}
