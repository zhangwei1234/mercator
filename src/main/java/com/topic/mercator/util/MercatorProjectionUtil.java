package com.topic.mercator.util;

import static java.lang.Math.atan;
import static java.lang.Math.log;
import static java.lang.Math.sinh;
import static java.lang.Math.tan;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import com.topic.mercator.domain.Coordinate;
import com.topic.mercator.domain.GeoGraph;
import com.topic.mercator.domain.Rectangular;
import com.topic.mercator.exception.OutOfChinaException;

import static com.topic.mercator.util.GeoUtils.*;

public final class MercatorProjectionUtil {

	// 中国的起始经纬度
	private final static double INIT_NORTH_LAT = 54;
	private final static double INIT_SOUTH_LAT = 0;// 此值暂时不用，墨卡托公式中没有说明用法
	private final static double INIT_WEST_LNG = 74;
	private final static double INIT_EAST_LNG = 135;
	
	public final static Rectangular CHINA = new Rectangular(INIT_EAST_LNG,INIT_WEST_LNG, INIT_NORTH_LAT, INIT_SOUTH_LAT);
	
	// 墨卡托平面方格的边长
	public static final int MERCATOR_GRID_LENGTH = 200;
	public static final long INIT_EAST_MCT = calcMercatorX(INIT_EAST_LNG);
	public static final long INIT_WEST_MCT = calcMercatorX(INIT_WEST_LNG);
	
	// 墨卡托平面一行中的方格数
	public static final long GRID_NUM_IN_ONE_ROW = (INIT_EAST_MCT - INIT_WEST_MCT)/ MERCATOR_GRID_LENGTH;
	
	// 单个门店允许的最大配送范围,120KM*120KM
	public static final Long MAX_DELIVERY_RANGE = 600 * 200 * 600L * 200;

	/**
	 * 通过经纬坐标计算墨卡托平面方格编号
	 *
	 * @param coordinate
	 * @return
	 */
	public static Long calcGridId(Coordinate coordinate) {
		if (!isPointInRect(coordinate, CHINA)) {
			throw new OutOfChinaException(coordinate);
		}
		
		long mercatorX = calcMercatorX(coordinate.getLongtitude());
		long mercatorY = calcMercatorY(coordinate.getLatitude());
		
		/* 编号从0开始时，此墨卡托坐标对应的方格编号 */
		long gridId = mercatorY / MERCATOR_GRID_LENGTH * GRID_NUM_IN_ONE_ROW+ (mercatorX - INIT_WEST_MCT) / MERCATOR_GRID_LENGTH;
		
		return gridId;
	}

	/* 计算墨卡托平面的y坐标 */
	public static long calcMercatorY(double lat) {
		final double v = log(tan(toRadians(45 + lat / 2))) * R;
		return (long) v;
	}

	/* 计算墨卡托平面的x坐标 */
	public static long calcMercatorX(double lng) {
		final double v = lng * 111192.4;// 111192.4 = R*PI/180
		return (long) v;
	}

	public static double calcGeoLat(long mercatorY) {
		final double v = toDegrees(atan(sinh((double) mercatorY / R)));

		return v;
	}

	public static double calcGeoLng(long mercatorX) {
		final double v = mercatorX / 111192.4;
		return v;
	}

	/**
	 * 判断门店的配送范围是否超过规定的最大范围值
	 * 
	 * @param geoGraph
	 * @return
	 */
	public static boolean exceedMaxRange(GeoGraph geoGraph) {
		try {
			/* 得到最小覆盖矩形 */
			Rectangular rectangular = geoGraph.getRectangular();

			Long wng = calcGridId(new Coordinate(rectangular.getMinLng(),rectangular.getMaxLat()));
			Long eng = calcGridId(new Coordinate(rectangular.getMaxLng(),rectangular.getMaxLat()));
			Long esg = calcGridId(new Coordinate(rectangular.getMaxLng(),rectangular.getMinLat()));

			Long length = (eng - wng + 1) * MERCATOR_GRID_LENGTH;

			Long width = (((eng - esg) / GRID_NUM_IN_ONE_ROW) + 1)* MERCATOR_GRID_LENGTH;

			if (MAX_DELIVERY_RANGE < length * width) {
				return true;
			}
		} catch (OutOfChinaException e) {// 配送范围不在中国时返回true
			return true;
		}
		return false;
	}
}
