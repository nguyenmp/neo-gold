package com.nguyenmp.neogold;

public class Department {
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
}
