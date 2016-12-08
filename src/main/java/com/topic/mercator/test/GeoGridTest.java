package com.topic.mercator.test;

import java.util.Set;

import com.topic.mercator.RectangularIndexBuilder;
import com.topic.mercator.domain.Circle;
import com.topic.mercator.domain.Coordinate;
import com.topic.mercator.domain.GeoGraph;
import com.topic.mercator.domain.Polygon;
import com.topic.mercator.enums.CoordinateType;
import com.topic.mercator.util.MercatorProjectionUtil;

/**
 * 墨卡托方格测试
 * Copyright (c) 2015 www.imdada.cn All rights reserved.
 * 版权归属 New Dada 所有,未经许可不得任意复制与传播.
 * @author cdzhangwei
 * @since 2016年12月8日
 */
public class GeoGridTest {

	/**
	 * 获取经纬度对应的方格编号
	 * @param lng
	 * @param lat
	 * @return
	 */
	public Long getGridIndex(double lng, double lat){
		
		//订单参数
		Coordinate coordinate = new Coordinate(lng, lat, CoordinateType.QQ);
		
		return MercatorProjectionUtil.calcGridId(coordinate);
	}
	
	/**
	 * 获取一个圆形区域 覆盖的方格数
	 * @param lng 圆中点 经度
	 * @param lat 圆中点 维度
	 * @param radius 半径
	 * @return
	 */
	public Set<Long> getCircleGrids(double lng, double lat, int radius){
		
		//定义圆心
		Coordinate center = new Coordinate(lng, lat, CoordinateType.QQ);
		GeoGraph geoGraph = new Circle(center, radius);
		
		//编译方格
		RectangularIndexBuilder builder = new RectangularIndexBuilder();
		Set<Long> grids = builder.buildGrids(geoGraph);
		
		return grids;
	}
	
	/**
	 * 获取一个多边形区域覆盖的方格数
	 * @param paths 多边形每个点对应的经纬度数组
	 * @return
	 */
	public Set<Long> getPolygonGrids(Coordinate [] paths){
		GeoGraph geoGraph = new Polygon(paths);
		
		//编译方格
		RectangularIndexBuilder builder = new RectangularIndexBuilder();
		Set<Long> grids = builder.buildGrids(geoGraph);
		
		return grids;
	}
}
