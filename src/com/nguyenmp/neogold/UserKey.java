package com.nguyenmp.neogold;

import org.datanucleus.util.Base64;



/**
 * Bean class encapsulating the salt used to encrypt the entity as well as 
 * the unique ID of the entity in the DB so that users can look up previous 
 * elements.
 * @author Mark Nguyen
 */
public class UserKey {
	private byte[] salt = null;
	private long id = 0;
	
	// Constructor that must initialize all member fields
	UserKey(long id, byte[] salt) {
		this.id = id;
		this.salt = salt;
	}
	
	// Getters
	public byte[] getSalt() {
		return salt;
	}
	
	public long getId() {
		return id;
	}
	
	// Setters
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	// Important Object methods
	@Override
	public int hashCode() {
		return (int) (salt.hashCode() + id);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof UserKey) {
			UserKey key = (UserKey) object;
			return this.id == key.id && this.salt.equals(key.salt);
		}
		else {
			return super.equals(object);
		}
	}
	
	/**
	 * Encodes the salt as a base64 string and then joins it with the ID using an 
	 * ampersand (&) using the format like:<br />
	 * <br />
	 * <code>salt&id</code>
	 */
	@Override
	public String toString() {
		// Encode the salt to a Base64 String
		char[] encodedSalt = Base64.encode(getSalt());
		String saltString = new String(encodedSalt);
		
		// Convert the ID into a string
		String idString = Long.toString(getId());
		
		// Return the salt joined by the id with an ampersand
		return saltString + "&" + idString;
	}
	
	/**
	 * Reverses the process of the {@link UserKey#toString()} method.
	 * @param string the input string to parse
	 * @return a UserKey containing the same information as the String
	 * if it was properly formatted as the {@link UserKey#toString()} 
	 * method specified.
	 */
	public static UserKey fromString(String string) {
		String[] parts = string.split("&");
		if (parts.length >= 2) {
			String saltString = parts[0];
			String idString = parts[1];
			Long id = Long.parseLong(idString);
			byte[] salt = saltString.getBytes();
			UserKey userKey = new UserKey(id, salt);
			return userKey;
		} else return null;
	}
}