package com.topic.mercator.exception;

import com.topic.mercator.domain.Coordinate;


@SuppressWarnings("all")
public class OutOfChinaException extends RuntimeException {

	private Coordinate coordinate;

	public OutOfChinaException(Coordinate coordinate) {
		super(coordinate + " Out of China");
		this.coordinate = coordinate;
	}
}
