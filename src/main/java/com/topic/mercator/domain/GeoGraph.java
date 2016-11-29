package com.topic.mercator.domain;

import java.io.Serializable;

/**
 * geo graph
 * Copyright (c) 2015 www.imdada.cn All rights reserved.
 * 版权归属 New Dada 所有,未经许可不得任意复制与传播.
 * @author cdzhangwei
 * @since 2016年11月28日
 */
public class GeoGraph implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2376989270661658033L;

	private String targ;
	private Coordinate targCoordinate;
	
	public String getTarg() {
		return targ;
	}
	public void setTarg(String targ) {
		this.targ = targ;
	}
	public Coordinate getTargCoordinate() {
		return targCoordinate;
	}
	public void setTargCoordinate(Coordinate targCoordinate) {
		this.targCoordinate = targCoordinate;
	}
	
}
