package com.nguyenmp.neogold.dao;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.datanucleus.util.Base64;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.nguyenmp.neogold.beans.User;
import com.nguyenmp.neogold.beans.UserKey;

/**
 * The Data Access Object for the User bean providing an abstraction 
 * layer between the database and the business logic.
 * @author Mark Nguyen
 * @see User
 */
public class UserDAO {
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	// According to the algorithm, the salt must be 8 bytes long
	private static final int SALT_SIZE = 8;
	
	
	private static final char[] PASSWORD = null;
	private static final String ENTITY_KIND = "User";
	private static final String PROPERTY_ENCRYPTED_USERNAME = "username", 
			PROPERTY_ENCRYPTED_PASSWORD = "encryptedPassword",
			PROPERTY_CREATED_UTC = "createdUTC",
			PROPERTY_ACCESSED_UTC = "accessedUTC";
	
	/**
	 * Puts the username and password into the DB in an encrypted using PBE with MD5 and DES form using a randomly generated salt.  
	 * The salt is returned in a Java Bean and is required to decrypt the username and password stored 
	 * in the database. The UserKey also encapsulates the ID of the entity in the database allowing one 
	 * to look up the specific user's credentials and decrypting it.  This is all handled behind the scenes.
	 * @param username the username to encrypt and store into the DB
	 * @param password the password to encrypt and store into the DB
	 * @return a Java Bean encapsulating the salt and unique ID
	 * @see UserKey
	 * @see #get(UserKey)
	 */
	public static UserKey put(String username, String password) {
		// Encrypt the username and password with a randomly generated salt
		byte[] salt = generateSalt(SALT_SIZE);
		String encryptedUsername = transform(username, true, salt);
		String encryptedPassword = transform(password, true, salt);
		
		// Create the Database Entity with the encrypted data
		Entity entity = new Entity(ENTITY_KIND);
		entity.setProperty(PROPERTY_ENCRYPTED_USERNAME, encryptedUsername);
		entity.setProperty(PROPERTY_ENCRYPTED_PASSWORD, encryptedPassword);
		long currentTime = System.currentTimeMillis();
		entity.setProperty(PROPERTY_CREATED_UTC, currentTime);
		entity.setProperty(PROPERTY_ACCESSED_UTC, currentTime);
		
		// Store the entity into the database and get the key
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Key key = datastoreService.put(entity);
		long id = key.getId();
		
		// Return the generated UserKey bean used for lookups
		return new UserKey(id, salt);
	}
	
	/**
	 * Updates when this element was last accessed to the current time.
	 * @param userKey The key corresponding to the user to update
	 * @throws EntityNotFoundException if the specified user could not be found
	 */
	public static void updateAccess(UserKey userKey) throws EntityNotFoundException {
		// Get entity from DB using the key provided
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(ENTITY_KIND, userKey.getId());
		Entity entity = datastoreService.get(key);
		
		// Update the data
		entity.setProperty(PROPERTY_ACCESSED_UTC, System.currentTimeMillis());
		
		// Put the updated entity into the database
		datastoreService.put(entity);
	}
	
	/**
	 * Retrieves the credentials and details of a specific user from the {@link UserKey}.  Behind 
	 * the scenes, this method decrypts the username and password using the given salt in the reverse 
	 * manner as it was encrypted in the {@link #put(String, String)} method.
	 * @param userKey the Java Bean encapsulating the {@link Entity}'s unique ID and encryption salt.
	 * @return a {@link User} bean that represents the properties of the user specified by the {@link UserKey}
	 * @throws EntityNotFoundException the User specified no longer exists in our DB.
	 * This indicates that the credentials have expired or have never existed in the 
	 * first place.
	 */
	public static User get(UserKey userKey) throws EntityNotFoundException {
		// Extract values from UserKey bean
		byte[] salt = userKey.getSalt();
		long id = userKey.getId();
		
		// Condition check
		if (salt.length != SALT_SIZE) return null;
		
		// Build query
		Query query = new Query(ENTITY_KIND);
		query.addProjection(new PropertyProjection(PROPERTY_ENCRYPTED_USERNAME, String.class));
		query.addProjection(new PropertyProjection(PROPERTY_ENCRYPTED_PASSWORD, String.class));
		query.addProjection(new PropertyProjection(PROPERTY_CREATED_UTC, Long.class));
		Key key = KeyFactory.createKey(ENTITY_KIND, id);
		
		// Execute query and retrieve result
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Entity entity = datastoreService.get(key);
		
		// Build and initialize User bean with entity data
		User user = new User();
		
		// Set the username
		String encryptedUsername = (String) entity.getProperty(PROPERTY_ENCRYPTED_USERNAME);
		String rawUsername = transform(encryptedUsername, false, salt);
		user.setUcsbNetId(rawUsername);
		
		// Set the password
		String encryptedPassword = (String) entity.getProperty(PROPERTY_ENCRYPTED_PASSWORD);
		String rawPassword = transform(encryptedPassword, false, salt);
		user.setPassword(rawPassword);
		
		// Set the creation time
		Long createdUTC = (Long) entity.getProperty(PROPERTY_CREATED_UTC);
		user.setCreatedUTC(createdUTC);
		Long accessedUTC = (Long) entity.getProperty(PROPERTY_ACCESSED_UTC);
		user.setAccessedUTC(accessedUTC);
		
		return user;
	}
	
	/**
	 * Attempts to remove the Entity in the database that corresponds 
	 * to the UserKey provided. We do not use the salt size in this 
	 * case so you can just create a UserKey bean with an arbitrary 
	 * salt and as long as the ID is wellformed and corresponds to the 
	 * database Entity, then the deletion will be fine.  This method 
	 * will not fail on an entity that does not exist and we cannot 
	 * be sure if one exists during this deletion.
	 * @param userKey the UserKey object containing the ID for the 
	 * entity to delete.
	 */
	public static void remove(UserKey userKey) {
		// Extract values from UserKey bean
		long id = userKey.getId();
		
		// Create identifier for the entity we want to remove
		Key entityKey = KeyFactory.createKey(ENTITY_KIND, id);
		
		// Delete the entity from DB using the key
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		datastoreService.delete(entityKey);
	}
	
	/**
	 * Transforms the input string either by encrypting or decrypting 
	 * it using the PBE algorithm with MD5 and DES.  The resulting byte 
	 * array will be stored as a base
	 * @param value the String to transform
	 * @param encrypt true to encrypt, false to decrypt
	 * @param salt the byte array used to salt the encryption
	 * @return the encrypted value as a String
	 */
	private static String transform(String value, boolean encrypt, byte[] salt) {
		try {
	        int cipherMode = encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
	        
			final byte[] bytes = value!=null ? value.getBytes() : new byte[0];
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
	        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
	        pbeCipher.init(cipherMode, key, new PBEParameterSpec(salt, 20));
	        return new String(Base64.encode(pbeCipher.doFinal(bytes)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Creates a new array of bytes and initializes its values to random values
	 * @param size the size of the array to create and initialize
	 * @return the created and initialized array
	 */
	private static byte[] generateSalt(int size) {
		byte[] result = new byte[size];
		RANDOM.nextBytes(result);
		return result;
	}
}
