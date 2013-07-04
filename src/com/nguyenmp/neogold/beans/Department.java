package com.nguyenmp.neogold.beans;

import com.nguyenmp.neogold.dao.DepartmentDAO;

/**
 * A Java Bean that encapsulates the Departmental data we use to show to clients.
 * This includes the name of the department ("Anthropology"), the ID of the 
 * department ("ANTH"), and the alternative query terms ("Study Of Humans").
 * @author Mark Nguyen
 * @see DepartmentDAO
 */
public class Department implements Comparable<Department> {
	// Bean Fields
	private String name = null;
	private String id = null;
	private String alternatives = null;
	
	// Getters
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getAlternatives() {
		return alternatives;
	}
	
	// Setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setAlternatives(String alternatives) {
		this.alternatives = alternatives;
	}
	
	// Object methods
	@Override
	public boolean equals(Object object) {
		if (object instanceof Department) {
			Department department = (Department) object;
			return this.name == department.name && this.id == department.id && this.alternatives == department.alternatives;
		}
		else {
			return super.equals(object);
		}
	}
	
	@Override
	public int hashCode() {
		int nameHash = name == null ? 0 : name.hashCode();
		int idHash = id == null ? 0 : id.hashCode();
		int altHash = alternatives == null ? 0 : alternatives.hashCode();
		
		return nameHash + idHash + altHash;
	}
	
	// Implements Comparable<Department>
	/**
	 * Compares using the lexographic ordering of the Department's ID 
	 * while ignoring case sensitvity.
	 * @see java.lang.String.compareToIgnoreCase(String str)
	 */
	@Override
	public int compareTo(Department o) {
		// Compare the ID's using the String.compareToIgnoreCase(String) method
		return this.id.compareToIgnoreCase(o.id);
	}
}
