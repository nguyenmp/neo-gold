package com.nguyenmp.neogold.beans;

import java.io.Serializable;

public class CourseID implements Serializable, Comparable<CourseID>{
	private static final long serialVersionUID = 7032013L;
	private String department;
	private String number;
	private String suffix;
	private String name;
	
	// Getters and setters for department
	
	/**
	 * @return the abbreviated department name (eg. "ANTH" or "CMPSC")
	 */
	public String getDepartment() {
		return department;
	}
	
	/**
	 * we will trim the department and set it to upper case
	 * @param department the department to set
	 * @see #getDepartment()
	 */
	public void setDepartment(String department) {
		this.department = department.trim().toUpperCase();
	}
	
	// Getters and setters for courseNumber
	
	/**
	 * @return the number which is just an integer 
	 * (eg. Anth 2L's course number is 2)
	 */
	public String getNumber() {
		return number;
	}
	
	/**
	 * @param number the number to set, we trim this string
	 * @see #getNumber()
	 */
	public void setNumber(String number) {
		this.number = number.trim();
	}
	
	// Getters and setters for course suffix
	
	/**
	 * @return the course's id suffix (eg. Chem 1AL's suffix is AL)
	 */
	public String getSuffix() {
		return suffix;
	}
	
	/**
	 * @param courseSuffix the courseSuffix to set, 
	 * we capitalize and trim this suffix
	 * @see #getCourseSuffix()
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix.trim().toUpperCase();
	}
	
	// the getters and setters for the name
	
	/**
	 * @return the name of the course which is probably abbreviated
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set, we trim this input
	 * @see #getName()
	 */
	public void setName(String name) {
		this.name = name.trim();
	}
	
	// Important Object methods
	
	/**
	 * If the object is an instance of CourseID, we will compare 
	 * the department, name, suffix, and number for equality.
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof CourseID) {
			CourseID courseID = (CourseID) object;
			
			return this.department.equals(courseID.department) && 
					this.number.equals(courseID.number) &&
					this.suffix.equals(courseID.suffix) && 
					this.name.equals(courseID.name);
		}
		
		else {
			return super.equals(object);
		}
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;

		if (department != null) hashCode += department.hashCode();
		if (number != null) hashCode += number.hashCode();
		if (suffix != null) hashCode += suffix.hashCode();
		if (name != null) hashCode += name.hashCode();
		
		return hashCode;
	}
	
	@Override
	public String toString() {
		// Replace null strings with empty strings
		String department = this.department == null ? "" : this.department;
		String number = this.number == null ? "" : this.number;
		String suffix = this.suffix == null ? "" : this.suffix;
		String name = this.name == null ? "" : this.name;
		
		// Return formatted string
		return String.format("%s %s%s - %s", department, number, suffix, name);
	}
	
	// Implement Comparable<CourseID> method
	@Override
	public int compareTo(CourseID other) {
		// Sequentially compare department, then number, then suffix
		int compareResult = department.compareTo(other.department);
		if (compareResult == 0) compareResult = number.compareTo(other.number);
		if (compareResult == 0) compareResult = suffix.compareTo(other.suffix);
		
		return compareResult;
	}
}
