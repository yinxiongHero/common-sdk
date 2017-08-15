package com.hero.commons.common.impl;
/**
 * 实体节点   
 * @author yin.xiong
 * 2017年7月13日
 */
public class EntityID extends EnumID<EntityID>{

	private static final long serialVersionUID = 1L;
	private String desc;
	
	/**
	 * 此处使用了类似装饰器模式   每一个父类维护一个状态值
	 * @param id
	 * @param desc
	 */
	public EntityID(String id,String desc) {
		super(id);
		this.desc=desc;
	}
	
	/**
	 * 此处使用了类似装饰器模式   EntityID 维护desc描述,EnumID维护id，父类中有一个容易维护着所有的实例
	 * @param id
	 * @param desc  
	 * @param isInitCurClass 是否初始化自身
	 */
	public EntityID(String id,String desc,Boolean isInitCurClass) {
		super(id, isInitCurClass);
		this.desc=desc;
	}
	
	public final String getDesc() {
		return desc;
	}
	public final void setDesc(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return getId()+":"+desc;
	}
	
}
