package com.hero.commons.common.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;

import com.hero.commons.exception.BaseException;
import com.hero.commons.exception.FileException;
import com.hero.commons.exception.XmlException;
import com.hero.commons.tool.log.LogFactory;
import com.hero.commons.tool.xml.Config;
import com.hero.commons.tool.xml.Import;
import com.hero.commons.tool.xml.Root;
import com.hero.commons.util.MappingUtil;
import com.hero.commons.util.StreamUtil;

/**
 *xml文件类 加载器  
 * 容器维护的是xml文件名
 */
public class XmlResourceLoader extends ResourceLoader{
	
	private  List<String> fileList=new ArrayList<String>();
	
	public XmlResourceLoader() {
	}
	
	/**
	 * 维护所有xml的资源名称
	 */
	public XmlResourceLoader(String[] fileArray) {
		for(String fileStr:fileArray){
			load(fileStr);
		}
	}
	
	/**
	 * 
	 * @param fileArray 资源名称数组
	 */
	public XmlResourceLoader(String file) {
		load(file);
	}
	
	public void load(String file) {
		fileList.add(file);
		initConfig(file);
	}
	
	/**
	 * 加载每个资源文件
	 * @param filePath 资源名称  绝对路径
	 */
	private void initConfig(String filePath) {
		try {
			LogFactory.getLog().info("loading config [" + filePath + "]....");
			Root root=reload(filePath);
			//根据version加载当前xml的config
			String version = root.getVersion();
			List<Config> configList = root.getConfigList();
			for(Config config:configList){
				if ("1.0".equals(version)) {
					addResource(config.getName(), new com.hero.commons.common.resource.impl.xmlV1.XmlResource(config));
				}else {
					LogFactory.getLog().error(this,"not supported xml version");
					continue;
				}
			}
			//加载当前xml导入的文件
			List<Import> importList = root.getImportList();
			for(Import importClass:importList){
				LogFactory.getLog().info("loading config [" + filePath + "],its ref import ,name="+importClass.getRef()+"");
				initConfig(importClass.getRef());
			}
		} catch (Exception e) {
			LogFactory.getLog().error(this,"xml加载失败,filePath="+filePath);
			LogFactory.getLog().error(this,e);
		}
	}

	private Root reload(String file) throws IOException, XmlException, FileException {
		InputStream is = null;
		Reader reader = null;
		try {
			long t = System.currentTimeMillis();
			is = XmlResourceLoader.class.getResourceAsStream(file);
			if (is == null) {
				throw new FileException("404", "file [" + file + "] not found..");
			}
			reader = new InputStreamReader(is, "UTF-8");
			Unmarshaller unmarshaller = new Unmarshaller(Root.class);
			unmarshaller.setMapping(getMapping());
			Root xmlObject = (Root) unmarshaller.unmarshal(reader);
			return xmlObject;
		} catch (FileException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new XmlException("S13", "READ XML FAIL", e);
		} finally {
			StreamUtil.close(reader);
			StreamUtil.close(is);
		}
	}

	public static Mapping getMapping() throws BaseException {
		return MappingUtil.getMapping("/commons-config-mapping.xml");
	}
	
	
	
}
