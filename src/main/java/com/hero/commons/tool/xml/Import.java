package com.hero.commons.tool.xml;
/**
 * import节点
 * <import ref="aa.xml"/>
 * @author yin.xiong
 * 2017年7月27日
 */
public class Import implements XmlNode{
	private String ref;
	
	public Import(){
		
	}
	
	public Import(String ref){
		this.ref=ref;
	}
	
	public final String getRef() {
		return ref;
	}

	public final void setRef(String ref) {
		this.ref = ref;
	}
	
	
}
