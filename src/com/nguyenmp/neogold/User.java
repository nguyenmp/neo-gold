package com.nguyenmp.neogold;

public class User {
	// Bean fields
	private String ucsbNetID = null;
	private String password = null;
	
	// Getters
	/**
	 * @return the raw UCSBNetID stored in this bean
	 */
	public String getUcsbNetID() {
		return this.ucsbNetID;
	}
	
	/**
	 * @return the raw password stored in this bean
	 */
	public String getPassword() {
		return this.password;
	}
	
	// Setters
	
	/**
	 * Sets the bean's net id
	 * @param id the value to set the ucsbNetID to
	 */
	public void setUcsbNetId(String id) {
		this.ucsbNetID = id;
	}
	
	/**
	 * Sets the bean's password field
	 * @param password the new value for the password field
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	// Important Object methods
	
	@Override
	public int hashCode() {
		int idHash = ucsbNetID == null ? 0 : ucsbNetID.hashCode();
		int passHash = password == null ? 0 : password.hashCode();
		
		return idHash + passHash;
	}
	
	@Override
	public boolean equals(Object object) {
		// If the object is of type User, compare the fields
		if (object instanceof User) {
			User user = (User) object;
			return user.ucsbNetID == this.ucsbNetID && 
					user.password == this.password;
		}
		
		// Otherwise, compare using the supertype... probably Object
		else {
			return super.equals(object);
		}
	}
}
