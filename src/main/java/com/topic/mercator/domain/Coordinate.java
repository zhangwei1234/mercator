package com.topic.mercator.domain;

import java.io.Serializable;

import com.topic.mercator.enums.CoordinateType;

/**
 * 坐标信息
 * Copyright (c) 2015 www.imdada.cn All rights reserved.
 * 版权归属 New Dada 所有,未经许可不得任意复制与传播.
 * @author cdzhangwei
 * @since 2016年11月28日
 */
public class Coordinate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2605780905198799540L;

	/**
	 * 经度
	 */
	private double longtitude;
	
	/**
	 * 纬度
	 */
	private double latitude;
	
	/**
	 * 经纬度坐标类型
	 */
	private CoordinateType coordinateType;

	public Coordinate(){
		
	}
	
	/**
	 * default coordinateType.qq
	 * @param longtitude
	 * @param latitude
	 */
	public Coordinate(double longtitude, double latitude){
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.coordinateType = CoordinateType.QQ;
	}
	
	public Coordinate(double longtitude, double latitude, CoordinateType type){
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.coordinateType = type;
	}
	
	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public CoordinateType getCoordinateType() {
		return coordinateType;
	}

	public void setCoordinateType(CoordinateType coordinateType) {
		this.coordinateType = coordinateType;
	}
	
}
