package com.nguyenmp.neogold.beans;

import java.io.Serializable;


/**
 * A representative Java Bean that encapsulates the abstract Lecture.  A lecture is an 
 * instance of a Course that is being taught by a professor.  You can think of a Course 
 * as being the blueprint of a Lecture, dictating what will be taught but the Lecture 
 * is how the course material is taught and how the instructions are implemented.
 *
 * Each Lecture has a unique enrollment code (enrlCd) which can be used to identify 
 * one from another.
 */
public class Lecture implements Serializable, Comparable<Lecture> {
	private static final long serialVersionUID = 70320132021L;

	/** An integer identifier that is unique for each 
	 * Lecture and Section on a quarterly basis. */
	private String enrlCd;
	
	/** Raw HTML representation of the days this course meets on. */
	private String days;

	/** Raw HTML representation of the times this course meets on. */
	private String times;
	
	/** Raw HTML representation of the instructors that teach this Lecture.  
	 * Multiple instructors will be separated by a HTML <br> element. */
	private String instructors;

	/** Raw HTML representation of the locations this course meets at. 
	 * Multiple locations are separated by an HTML <br> element. */
	private String locations;
	
	/** The maximum capacity for this lecture.*/
	private String max;
	
	/** The status of space in this lecture.  Can be an integer representing 
	 * the number of remaining spots or:
	 * <ul>
	 *     <li>Full - indicating there are no more spaces</li>
	 *     <li>Closed - indicating enrollment is closed for this lecture</li>
	 * </ul>*/
	private String space;

	/**
	 * An array list of Sections that belong under this lecture
	 */
	private Section[] sections;
	
	private Quarter quarter;
	
	// Getters
	/**
	 * An integer identifier that is unique for each 
	 * Lecture and Section on a quarterly basis.
	 */
	public String getEnrollmentCode() {
		return this.enrlCd;
	}

	/** Raw HTML representation of the days this course meets on. */
	public String getDays() {
		return this.days;
	}

	/** Raw HTML representation of the times this course meets on. */
	public String getTimes() {
		return this.times;
	}
	
	/** Raw HTML representation of the instructors that teach this Lecture.  
	 * Multiple instructors will be separated by a HTML <br> element. */
	public String getInstructors() {
		return this.instructors;
	}
	
	public String getLocations() {
		return this.locations;
	}
	
	public String getMaximum() {
		return this.max;
	}
	
	public String getSpace() {
		return this.space;
	}
	
	public Section[] getSections() {
		return this.sections;
	}
	
	public Quarter getQuarter() {
		return this.quarter;
	}
	
	// Setters
	public void setEnrollmentCode(String enrollmentCode) {
		this.enrlCd = enrollmentCode;
	}
	
	public void setDays(String days) {
		this.days = days;
	}
	
	public void setTimes(String times) {
		this.times = times;
	}
	
	public void setInstructors(String instructors) {
		this.instructors = instructors;
	}
	
	public void setLocations(String locations) {
		this.locations = locations;
	}
	
	public void setMaximum(String maximum) {
		this.max = maximum;
	}
	
	public void setSpace(String space) {
		this.space = space;
	}
	
	public void setSections(Section[] sections) {
		this.sections = sections;
	}
	
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	
	// Important Object methods to override
	
	@Override
	public String toString() {
		return String.format("%s	%s	%s	%s	%s	%s	%s", enrlCd, days, times, instructors, locations, max, space);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Lecture) {
			Lecture lecture = (Lecture) object;
			return lecture.enrlCd.equals(this.enrlCd) && lecture.quarter.equals(this.quarter);
		} else {
			return super.equals(object);
		}
	}
	
	@Override
	public int hashCode() {
		return this.enrlCd.hashCode() + this.quarter.hashCode();
	}
	
	// Implements Comparable<Lecture>
	
	@Override
	public int compareTo(Lecture o) {
		int compareResult = this.quarter.compareTo(o.quarter);
		if (compareResult == 0) this.enrlCd.compareTo(o.enrlCd);
		return compareResult;
	}
}