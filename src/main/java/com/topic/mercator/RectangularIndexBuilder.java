package com.topic.mercator;

import java.util.Set;
import java.util.TreeSet;

import com.topic.mercator.domain.Coordinate;
import com.topic.mercator.domain.GeoGraph;
import com.topic.mercator.domain.Rectangular;
import static com.topic.mercator.util.MercatorProjectionUtil.*;
import static com.topic.mercator.util.GeoUtils.*;

/**
 * 根据中心点坐标, 圆形半径,或多边形点坐标计算，方格编号 Copyright (c) 2015 www.imdada.cn All rights
 * reserved. 版权归属 New Dada 所有,未经许可不得任意复制与传播.
 * 
 * @author cdzhangwei
 * @since 2016年11月30日
 */
public class RectangularIndexBuilder {

	/**
	 * 将圆形区域或多边形区域编译成固定方格编号
	 * 
	 * @param geoGraph
	 * @return
	 * @throws RuntimeException
	 */
	public Set<Long> buildGrids(GeoGraph geoGraph)
			throws RuntimeException {
		/* 得到最小覆盖矩形 */
		Rectangular rectangular = geoGraph.getRectangular();
		TreeSet<Long> gridSet = new TreeSet<Long>();

		/* 用矩形的三个角确定三个角的方格 */
		final Long wsId = calcGridId(new Coordinate(rectangular.getMinLng(),rectangular.getMinLat()));
		final Long esId = calcGridId(new Coordinate(rectangular.getMaxLng(),rectangular.getMinLat()));
		final Long wnId = calcGridId(new Coordinate(rectangular.getMinLng(),rectangular.getMaxLat()));

		if (wsId.equals(esId) && wsId.equals(wnId)) {// 如果只定位到了一个方格直接返回
			gridSet.add(wnId);
		}
		
		/* 循环所有方格判断哪些被覆盖了 */
		long gridNumInRow = esId - wsId + 1;
		for (long i = wsId; i <= wnId; i += GRID_NUM_IN_ONE_ROW) {
			for (int j = 0; j < gridNumInRow; j++) {
				long gridId = i + j;
				if (isGridBeCovered(gridId, geoGraph)) {
					// 散列正排索引里加入门店覆盖的格子（Long型自动排序）
					gridSet.add(gridId);
				}
			}
		}
		return gridSet;
	}

	private boolean isGridBeCovered(long gridId, GeoGraph geoGraph) {
		// calcGeoLat()
		long row = gridId / GRID_NUM_IN_ONE_ROW;// 编号从0开始
		long column = gridId % GRID_NUM_IN_ONE_ROW;// 编号从0开始

		long mctLeftUpX = INIT_WEST_MCT + column * MERCATOR_GRID_LENGTH;
		long mctLeftUpY = (row + 1) * MERCATOR_GRID_LENGTH;
		if (isPointInGeoGraph(new Coordinate(calcGeoLng(mctLeftUpX),calcGeoLat(mctLeftUpY)), geoGraph)){
			return true;
		}

		long mctRightUpX = INIT_WEST_MCT + (column + 1) * MERCATOR_GRID_LENGTH;
		long mctRightUpY = (row + 1) * MERCATOR_GRID_LENGTH;
		if (isPointInGeoGraph(new Coordinate(calcGeoLng(mctRightUpX),calcGeoLat(mctRightUpY)), geoGraph)){
			return true;
		}

		long mctRightDownX = INIT_WEST_MCT + (column + 1)* MERCATOR_GRID_LENGTH;
		long mctRightDownY = row * MERCATOR_GRID_LENGTH;
		
		if (isPointInGeoGraph(new Coordinate(calcGeoLng(mctRightDownX),calcGeoLat(mctRightDownY)), geoGraph)){
			return true;
		}

		long mctLeftDownX = INIT_WEST_MCT + column * MERCATOR_GRID_LENGTH;
		long mctLeftDownY = row * MERCATOR_GRID_LENGTH;
		
		if (isPointInGeoGraph(new Coordinate(calcGeoLng(mctLeftDownX),calcGeoLat(mctLeftDownY)), geoGraph)){
			return true;
		}
		
		return false;
	}
}
