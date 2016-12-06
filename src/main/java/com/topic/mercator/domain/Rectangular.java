package com.topic.mercator.domain;

/**
 * 地图上的矩形区域 Copyright (c) 2015 www.imdada.cn All rights reserved. 版权归属 New Dada
 * 所有,未经许可不得任意复制与传播.
 * 
 * @author cdzhangwei
 * @since 2016年11月30日
 */
public class Rectangular {

	// 最大经度
	private double maxLng;
	// 最小经度
	private double minLng;
	// 最大纬度
	private double maxLat;
	// 最小纬度
	private double minLat;

	public Rectangular(double maxLng, double minLng, double maxLat,
			double minLat) {

		this.maxLng = maxLng;
		this.minLng = minLng;
		this.maxLat = maxLat;
		this.minLat = minLat;
		if (!(checklat(maxLat) && checklat(minLat) && checkLng(maxLng) && checkLng(minLng))) {
			throw new IllegalArgumentException("构建矩形时，经纬度参数不正确 maxLat="+maxLat+"minLat="+minLat+"maxLng="+maxLng+"minLng="+ minLng);
		}
	}

	public Rectangular() {
	}

	public static boolean checkLng(double lng) {
		return lng <= 180 && lng >= -180;
	}

	public static boolean checklat(double lat) {
		return lat <= 90 && lat >= -90;
	}

	public double getMaxLng() {
		return maxLng;
	}

	public void setMaxLng(double maxLng) {
		this.maxLng = maxLng;
	}

	public double getMinLng() {
		return minLng;
	}

	public void setMinLng(double minLng) {
		this.minLng = minLng;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	public double getMinLat() {
		return minLat;
	}

	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}
}
