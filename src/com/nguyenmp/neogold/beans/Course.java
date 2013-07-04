package com.nguyenmp.neogold.beans;

import java.io.Serializable;

/**
 * A representative Java Bean that encapsulates the abstract Course.  A course
 * is similar to a subject matter.  It contains only one topic that is being
 * taught by the department.  A single Course can contain multiple Lectures similar to how a
 * single lecture can contain multiple discussion sections.
 */
public class Course implements Serializable, Comparable<Course> {
	private static final long serialVersionUID = 70320131954L;

	/**
	 * An enumeration of the currently available grading options.  This one
	 * means you can choose between Letter Grade and Pass/No Pass.
	 */
	public static final String GRADING_OPTIONAL = "Optional";

	/**
	 * An enumeration of the currently available grading options.  This one
	 * means you can only take this class for a letter grade and it'll
	 * have to count towards your GPA.
	 */
	public static final String GRADING_LETTER_GRADE = "Letter Grade";

	/**
	 * An enumeration of the currently available grading options.  This one
	 * means you can take this class to satisfy requirements but will not count
	 * towards your GPA as calculated by UCSB.  It may count towards other transcripts
	 * though according to each individual college.
	 */
	public static final String GRADING_PASS_NO_PASS = "Pass/No Pass";

	private CourseID courseID;
	private String units;
	private String grading;
	private String session;
	private Lecture[] lectures;
	
	private String quarter;
	
	// Getters
	
	/**
	 * <p>
	 * The name of the Course but not
	 * in a fully expanded form. This includes the abbreviated department
	 * name, the course number, the course number suffix, a hyphen, and the abbreviated
	 * course title.  The fully expanded course title can be found by getting
	 * the detailed course listing.</p>
	 * <p>
	 * Example: <code>WRIT    109ST - WRIT SCIENCE/TECH</code>
	 * </p>
	 */
	public CourseID getCourseID() {
		return courseID;
	}
	
	/**
	 * The number of units a course can be taken for.  
	 * (Usually in the form of <code>4.0</code> or <code>1.0-6.0</code>)
	 */
	public String getUnits() {
		return units;
	}


	/**
	 * The grading options available for this course.  ("Letter Grade", 
	 * "Optional", "Pass/No Pass")
	 *
	 * @see #GRADING_OPTIONAL
	 * @see #GRADING_LETTER_GRADE
	 * @see #GRADING_PASS_NO_PASS
	 */
	public String getGrading() {
		return this.grading;
	}

	/**
	 * A string representing which Summer Session this course is taught in.
	 * Taken directly from GOLD.
	 */
	public String getSession() {
		return this.session;
	}

	/**
	 * An array of available lectures teaching this specific course
	 */
	public Lecture[] getLectures() {
		return this.lectures;
	}
	
	// Setters
	
	// Getters
	
	public String getQuarter() {
		return quarter;
	}
	
	/**
	 * @see #getCourseID()
	 * @param courseID
	 */
	public void setCourseID(CourseID courseID) {
		this.courseID = courseID;
	}
	
	/**
	 * @see #getUnits()
	 * @param units
	 */
	public void setUnits(String units) {
		this.units = units;
	}
	
	/**
	 * @see #getGrading()
	 * @param grading
	 */
	public void setGrading(String grading) {
		this.grading = grading;
	}

	/**
	 * @see #getSession()
	 * @param session
	 */
	public void setSession(String session) {
		this.session = session;
	}
	
	/**
	 * @see #getLectures()
	 * @param lectures
	 */
	public void setLectures(Lecture[] lectures) {
		this.lectures = lectures;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	
	/**
	 * <p>Prints the object in the form of <code>name  units grading</code>
	 * where the whitespace between name and units is a tab and the
	 * whitespace between units and grading option is a space.</p>
	 * <p>Essentially: the same as calling
	 * <code>String.format("%s	%s	%s", name, units, grading)</code></p>
	 */
	@Override
	public String toString() {
		return String.format("%s	%s	%s", courseID.toString(), units, grading);
	}

	@Override
	public int compareTo(Course course) {
		int compareResult = quarter.compareTo(course.quarter);
		if (compareResult == 0) courseID.compareTo(course.courseID);
		return compareResult;
	}
}