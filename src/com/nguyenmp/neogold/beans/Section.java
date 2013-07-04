package com.nguyenmp.neogold.beans;

class Section {
	
	private String enrlCd;
	private String days;
	private String times;
	private String instructors;
	private String locations;
	private String max;
	private String space;
	private String quarter;
	private String lecture;

	public String getEnrollmentCode() {
		return enrlCd;
	}

	public void setEnrollmentCode(String enrlCd) {
		this.enrlCd = enrlCd;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getInstructors() {
		return instructors;
	}

	public void setInstructors(String instructors) {
		this.instructors = instructors;
	}

	public String getLocations() {
		return locations;
	}

	public void setLocations(String locations) {
		this.locations = locations;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	/**
	 * @return the quarter as it's unique year/quarter combination. 
	 * e.g., 20132 means the Winter quarter of the year 2013.
	 */
	public String getQuarter() {
		return quarter;
	}

	/**
	 * @see #getQuarter()
	 * @see Quarter
	 * @param quarter the quarter to set
	 */
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	/**
	 * @return the unique lecture enrollment code for this class. 
	 * note that lecture enrollment codes are only unique on a per 
	 * quarter basis so you will need to also get the quarter ID 
	 * in order to find the proper parent Lecture of this Section.
	 * @see Lecture
	 */
	public String getLecture() {
		return lecture;
	}

	/**
	 * @param lecture the lecture to set
	 */
	public void setLecture(String lecture) {
		this.lecture = lecture;
	}

	@Override
	public String toString() {
		return String.format("%s	%s	%s	%s	%s	%s	%s", enrlCd, days, times, instructors, locations, max, space);
	}
}