package com.nguyenmp.neogold.beans;


/**
 * A representative Java Bean that encapsulates the abstract Lecture.  A lecture is an 
 * instance of a Course that is being taught by a professor.  You can think of a Course 
 * as being the blueprint of a Lecture, dictating what will be taught but the Lecture 
 * is how the course material is taught and how the instructions are implemented.
 *
 * Each Lecture has a unique enrollment code (enrlCd) which can be used to identify 
 * one from another.
 */
public class Lecture {
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
	
	@Override
	public String toString() {
		return String.format("%s	%s	%s	%s	%s	%s	%s", enrlCd, days, times, instructors, locations, max, space);
	}
}