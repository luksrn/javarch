package com.github.javarch.support.utils;

import java.util.Date;

public class DateRange {

	private Date _start;
	private Date _end;

	public DateRange(Date start, Date end) {
		_start = start;
		_end = end;
	}

	public Date getStart() {
		return _start;
	}

	public Date getEnd() {
		return _end;
	}
 
}
