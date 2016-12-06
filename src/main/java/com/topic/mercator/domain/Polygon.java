package com.topic.mercator.domain;

/**
 * 多边形区域 Copyright (c) 2015 www.imdada.cn All rights reserved. 版权归属 New Dada
 * 所有,未经许可不得任意复制与传播.
 * 
 * @author cdzhangwei
 * @since 2016年11月30日
 */
@SuppressWarnings("serial")
public class Polygon extends GeoGraph {

	private Coordinate[] paths;
	private Rectangular rectangular;

	public Polygon(Coordinate[] paths) {
		this.paths = paths;
		rectangular = getRectangular(paths);
	}

	public Polygon() {
	}

	private Rectangular getRectangular(Coordinate[] paths) {
		double minLongtitude = 180.0f;
		double maxLongtitude = -180.0f;
		double minLatitude = 90.0f;
		double maxLatitude = -90.0f;
		
		for (Coordinate coordinate : paths) {
			minLongtitude = minLongtitude > coordinate.getLongtitude() ? coordinate.getLongtitude() : minLongtitude;
			maxLongtitude = maxLongtitude < coordinate.getLongtitude() ? coordinate.getLongtitude() : maxLongtitude;
			minLatitude = minLatitude > coordinate.getLatitude() ? coordinate.getLatitude() : minLatitude;
			maxLatitude = maxLatitude < coordinate.getLatitude() ? coordinate.getLatitude() : maxLatitude;
		}
		return new Rectangular(maxLongtitude, minLongtitude, maxLatitude,minLatitude);
	}

	public Coordinate[] getPaths() {
		return paths;
	}

	/**
	 * 返回边界 最小经度，最大经度，最小纬度，最大纬度
	 * 
	 * @return
	 */
	public Rectangular getRectangular() {
		return rectangular;
	}
}
