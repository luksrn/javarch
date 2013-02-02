package com.github.javarch.support.utils;

import java.util.Date;

/**
 * 
 * @author luksrn
 *
 */
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
 
	/**
	 * Verifica se os intervalos de data se sobrepõem.
	 * 
	 *  @see http://c2.com/cgi/wiki?TestIfDateRangesOverlap
	 * 
	 * @param fromDateRange
	 * @return
	 */
	public boolean overLap(DateRange fromDateRange){
		Date start2 = fromDateRange.getStart();
		Date end2 = fromDateRange.getEnd();
		
		return (_start.getTime() <= end2.getTime() && start2.getTime() <= _end.getTime())
				|| ( _start.getTime() == start2.getTime() && ( _start.getTime() == _end.getTime() || start2.getTime() == end2.getTime()));
	}
}
