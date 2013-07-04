package com.nguyenmp.neogold.beans;

import java.io.Serializable;

public class Quarter implements Serializable, Comparable<Quarter> {
	private static final long serialVersionUID = 7032013L;
	
	public static final int QUARTER_WINTER = 1, QUARTER_SPRING = 2, QUARTER_SUMMER = 3, QUARTER_FALL = 4;
	private int quarter = 0;
	private int year = 0;
	
	public Quarter() {
		this(0, 0);
	}
	
	public Quarter(int year, int quarter) {
		this.year = year;
		this.quarter = quarter;
	}
	
	public Quarter(int code) {
		quarter = year % 10; // Get the last digit
		year = year / 10; // Get the first n-1 digits
	}
	
	// Getter methods
	
	public int getValue() {
		return Integer.valueOf(Integer.toString(year) + quarter);
	}
	
	public String getName() {
		String quarterName;
		switch(quarter) {
		case QUARTER_WINTER:
			quarterName = "Winter";
			break;
		case QUARTER_SPRING:
			quarterName = "Spring";
			break;
		case QUARTER_SUMMER:
			quarterName = "Summer";
			break;
		case QUARTER_FALL:
			quarterName = "Fall";
			break;
		default:
			quarterName = "Unknown";
			break;
		}
		
		return quarterName + " " + year;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getQuarter() {
		return quarter;
	}
	
	// Setter methods
	
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	// Important Object methods to override
	@Override
	public int hashCode() {
		return quarter + year;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Quarter) {
			Quarter quarter = (Quarter) object;
			return this.quarter == quarter.quarter && this.year == quarter.year;
		} else {
			return super.equals(object);
		}
	}

	@Override
	public int compareTo(Quarter o) {
		return this.getValue() - o.getValue();
	}
}
