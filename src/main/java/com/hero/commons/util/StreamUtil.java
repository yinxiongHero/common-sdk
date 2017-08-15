package com.hero.commons.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import com.hero.commons.tool.log.LogFactory;

public class StreamUtil {
	public static void close(Reader reader) {
		try {
			if (reader == null) {
				throw new Exception("reader is null");
			}
			reader.close();
		} catch (Exception e) {
			LogFactory.getLog().error(StreamUtil.class,e);
		}
	}
	
	public static void close(InputStream inputStream) {
		try {
			if (inputStream == null) {
				throw new Exception("reader is null");
			}
			inputStream.close();
		} catch (Exception e) {
			LogFactory.getLog().error(StreamUtil.class,e);
		}
	}
	
	public static void close(OutputStream outputStream) {
		try {
			if (outputStream == null) {
				throw new Exception("reader is null");
			}
			outputStream.close();
		} catch (Exception e) {
			LogFactory.getLog().error(StreamUtil.class,e);
		}
	}
}
