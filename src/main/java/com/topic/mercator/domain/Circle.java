package com.topic.mercator.domain;

import com.topic.mercator.util.GeoUtils;

/**
 * 圆形区域 Copyright (c) 2015 www.imdada.cn All rights reserved. 版权归属 New Dada
 * 所有,未经许可不得任意复制与传播.
 * 
 * @author cdzhangwei
 * @since 2016年11月30日
 */
@SuppressWarnings("serial")
public class Circle extends GeoGraph {

	/** 圆形中心点坐标 */
	private Coordinate center;

	/** 圆半径大小-单位米 */
	private Integer radius;

	private Rectangular rectangular;

	public Circle(Coordinate center, int radius) {
		this.radius = radius;
		this.center = center;
		rectangular = getRectangular(center, radius);
	}

	public Circle() {

	}

	public Rectangular getRectangular() {
		return rectangular;
	}

	public Coordinate getCenter() {
		return center;
	}

	public void setCenter(Coordinate center) {
		this.center = center;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Rectangular getRectangular(Coordinate center, int radius) {
		return GeoUtils.getRectangular(center, radius);
	}
}
