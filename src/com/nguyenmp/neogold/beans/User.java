package com.nguyenmp.neogold.beans;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 6282013L;
	
	// Bean fields
	private String ucsbNetID = null;
	private String password = null;
	private long createdUTC = 0L;
	private long accessedUTC = 0L;
	
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
	
	public long getCreatedUTC() {
		return createdUTC;
	}
	
	public long getAccessedUTC() {
		return accessedUTC;
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
	
	public void setCreatedUTC(long createdUTC) {
		this.createdUTC = createdUTC;
	}
	
	public void setAccessedUTC(long accessedUTC) {
		this.accessedUTC = accessedUTC;
	}
	
	// Important Object methods
	
	@Override
	public int hashCode() {
		int idHash = ucsbNetID == null ? 0 : ucsbNetID.hashCode();
		int passHash = password == null ? 0 : password.hashCode();
		
		return idHash + passHash;
	}
}
