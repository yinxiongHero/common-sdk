package com.hero.commons.util;

import java.io.IOException;
import java.io.InputStream;

import org.exolab.castor.mapping.Mapping;
import org.xml.sax.InputSource;

import com.hero.commons.exception.BaseException;
import com.hero.commons.exception.XmlException;
import com.hero.commons.tool.CacheFactory;


/**
 * 映射文件解析工具
 * @author yin.xiong
 * 2017年7月27日
 */
public class MappingUtil {

	/**
	 * 获取Mapping
	 *
	 * @param s
	 *            Mapping file path
	 * @return Mapping
	 * @throws BaseException
	 *             Mapping文件转换失败抛出异常
	 */
	static public Mapping getMapping(String s) throws BaseException {
		return mappingFactory.get(s);
	}

	/**
	 * 获取Mapping
	 *
	 * @param is
	 *            Mapping InputStream
	 * @return Mapping
	 * @throws XmlException
	 *             Mapping文件转换失败抛出异常
	 * @throws java.io.IOException
	 *             获取Mapping文件失败抛出异常
	 */
	static public Mapping getMapping(InputStream is) throws XmlException, IOException {
		try {
			Mapping mapping = new Mapping();
			mapping.loadMapping(new InputSource(is));
			return mapping;
		} catch (Exception e) {
			throw new XmlException("S14", "XML MAPPING ERROR", e);
		}
	}

	static private CacheFactory<String, Mapping> mappingFactory = new CacheFactory<String, Mapping>() {

		protected Mapping build(String path) throws BaseException {
			try {
				return getMapping(MappingUtil.class.getResourceAsStream(path));
			} catch (IOException e) {
				throw new XmlException("S14", "READ XML MAPPING FAIL", e);
			}
		}
	};
}
