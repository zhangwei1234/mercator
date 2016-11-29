package com.topic.mercator.enums;

/**
 * 经纬度坐标类型 Copyright (c) 2015 www.imdada.cn All rights reserved. 版权归属 New Dada
 * 所有,未经许可不得任意复制与传播.
 * 
 * @author cdzhangwei
 * @since 2016年11月28日
 */
public enum CoordinateType {

	PHONE(1, "phone"), 
	QQ(2, "qq"), 
	GOOGLE(3, "google"), 
	BAIDU(4, "baidu");
	
	private int code;
	private String name;
	
	private CoordinateType(Integer code, String name){
		this.code = code;
		this.name = name;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
}
