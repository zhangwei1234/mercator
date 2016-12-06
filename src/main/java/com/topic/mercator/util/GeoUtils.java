package com.topic.mercator.util;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.topic.mercator.domain.Circle;
import com.topic.mercator.domain.Coordinate;
import com.topic.mercator.domain.GeoGraph;
import com.topic.mercator.domain.Polygon;
import com.topic.mercator.domain.Rectangular;

/**
 * 工具类 Copyright (c) 2015 www.imdada.cn All rights reserved. 版权归属 New Dada
 * 所有,未经许可不得任意复制与传播.
 * 
 * @author cdzhangwei
 * @since 2016年12月6日
 */
public final class GeoUtils {

	static final Integer R = 6370856;

	public static double getDistance(Coordinate c1, Coordinate c2) {
		if (c1 == null || c2 == null)
			throw new IllegalArgumentException("计算坐标距离时，传入坐标为空");
		
		return getDistance(c1.getLatitude(), c1.getLongtitude(),c2.getLatitude(), c2.getLongtitude());
	}

	public static double getDistance(double lat1, double lng1, double lat2,double lng2) {
		float x = (float) ((lng2 - lng1) * PI * R* cos((lat1 + lat2) / 2.0D * PI / 180.0D) / 180.0D);
		float y = (float) ((lat2 - lat1) * PI * R / 180.0D);
		float distance = (float) hypot(x, y);
		return distance;
	}

	public static boolean isPointInPolygon(Coordinate coordinate,Polygon polygon) {
		if (polygon == null)
			throw new IllegalArgumentException("判断点是否在多边形内，未传入多边形，对应点："+ coordinate);

		Rectangular rectangular = polygon.getRectangular();
		if (!isPointInRect(coordinate, rectangular)) {
			return false;
		}
		Coordinate[] pts = polygon.getPaths();

		int N = pts.length;
		boolean boundOrVertex = true;
		int intersectCount = 0;
		double precision = 2.E-010D;

		Coordinate p = coordinate;

		Coordinate p1 = pts[0];
		for (int i = 1; i <= N; i++) {
			if (p.equals(p1)) {
				return boundOrVertex;
			}

			Coordinate p2 = pts[(i % N)];
			if ((p.getLatitude() < min(p1.getLatitude(), p2.getLatitude()))|| (p.getLatitude() > max(p1.getLatitude(),p2.getLatitude()))) {
				p1 = p2;
			} else {
				if ((p.getLatitude() > min(p1.getLatitude(), p2.getLatitude()))&& (p.getLatitude() < max(p1.getLatitude(),p2.getLatitude()))) {
					if (p.getLongtitude() <= max(p1.getLongtitude(),p2.getLongtitude())) {
						if ((p1.getLatitude() == p2.getLatitude())&& (p.getLongtitude() >= min(p1.getLongtitude(), p2.getLongtitude()))) {
							return boundOrVertex;
						}

						if (p1.getLongtitude() == p2.getLongtitude()) {
							if (p1.getLongtitude() == p.getLongtitude()) {
								return boundOrVertex;
							}
							intersectCount++;
						} else {
							double xinters = (p.getLatitude() - p1.getLatitude())* (p2.getLongtitude() - p1.getLongtitude())/ (p2.getLatitude() - p1.getLatitude())+ p1.getLongtitude();
							if (abs(p.getLongtitude() - xinters) < precision) {
								return boundOrVertex;
							}

							if (p.getLongtitude() < xinters) {
								intersectCount++;
							}
						}
					}
				} else if ((p.getLatitude() == p2.getLatitude())&& (p.getLongtitude() <= p2.getLongtitude())) {
					Coordinate p3 = pts[((i + 1) % N)];
					if ((p.getLatitude() >= min(p1.getLatitude(),p3.getLatitude()))&& (p.getLatitude() <= max(p1.getLatitude(),p3.getLatitude())))
						intersectCount++;
					else {
						intersectCount += 2;
					}
				}

				p1 = p2;
			}
		}
		if (intersectCount % 2 == 0) {
			return false;
		}
		return true;
	}

	public static boolean isPointInRect(Coordinate coordinate, float[] bounds) {
		if (bounds.length != 4)
			return false;
		
		return (coordinate.getLongtitude() >= bounds[0])
				&& (coordinate.getLongtitude() <= bounds[1])
				&& (coordinate.getLatitude() >= bounds[2])
				&& (coordinate.getLatitude() <= bounds[3]);
	}

	public static boolean isPointInRect(Coordinate coordinate,Rectangular rectangular) {
		if ((coordinate == null) || (rectangular == null))
			return false;
		
		return (coordinate.getLongtitude() >= rectangular.getMinLng())
				&& (coordinate.getLongtitude() <= rectangular.getMaxLng())
				&& (coordinate.getLatitude() >= rectangular.getMinLat())
				&& (coordinate.getLatitude() <= rectangular.getMaxLat());
	}

	public static Rectangular getRectangular(Coordinate center, int radius) {
		final double[] around = getAround(center.getLatitude(),center.getLongtitude(), radius);
		return new Rectangular(around[3], around[2], around[1], around[0]);
	}

	public static Rectangular getRectangular(Polygon polygon) {
		return polygon.getRectangular();
	}

	public static double[] getAround(double lat, double lng, int radius) {
		Double latitude = Double.valueOf(lat);
		Double longitude = Double.valueOf(lng);

		Double degree = Double.valueOf(111293.63611111112D);
		double radiusMile = radius;

		Double dpmLat = Double.valueOf(1.0D / degree.doubleValue());
		Double radiusLat = Double.valueOf(dpmLat.doubleValue() * radiusMile);
		Double minLat = Double.valueOf(latitude.doubleValue()- radiusLat.doubleValue());
		Double maxLat = Double.valueOf(latitude.doubleValue()+ radiusLat.doubleValue());

		Double mpdLng = Double.valueOf(degree.doubleValue()* cos(latitude.doubleValue() * 0.0174532925199433D));
		Double dpmLng = Double.valueOf(1.0D / mpdLng.doubleValue());
		Double radiusLng = Double.valueOf(dpmLng.doubleValue() * radiusMile);
		Double minLng = Double.valueOf(longitude.doubleValue()- radiusLng.doubleValue());
		Double maxLng = Double.valueOf(longitude.doubleValue()+ radiusLng.doubleValue());

		return new double[] { minLat.doubleValue(), maxLat.doubleValue(),minLng.doubleValue(), maxLng.doubleValue() };
	}

	public static boolean isPointInCircle(Coordinate coordinate,Coordinate center, Integer radius) {
		final double distance = getDistance(coordinate, center);
		return distance < radius;
	}

	public static boolean isPointInGeoGraph(Coordinate coordinate,GeoGraph geoGraph) {
		if (geoGraph instanceof Circle)
			return isPointInCircle(coordinate, ((Circle) geoGraph).getCenter(),
					((Circle) geoGraph).getRadius());

		if (geoGraph instanceof Polygon)
			return isPointInPolygon(coordinate, (Polygon) geoGraph);

		throw new RuntimeException("unknown graph type");
	}
}
