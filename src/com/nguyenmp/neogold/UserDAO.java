package com.nguyenmp.neogold;

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
import com.google.appengine.api.datastore.Key;

public class UserDAO {
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	private static final char[] PASSWORD = null;
	
	/**
	 * Puts the username and password into the DB and returns the seed used to encrypt the password
	 * @param username the username to store into the DB
	 * @param password the password to store into the DB
	 * @return the key used to encrypt the password for secure storage
	 */
	public String put(String username, String password) {
		byte[] salt = generateSalt(64);
		
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Entity entity = new Entity("User");
		entity.setProperty("username", username);
		entity.setProperty("password", password);
		entity.setProperty("created", System.currentTimeMillis());
		Key key = datastoreService.put(entity);
		return new String(salt);
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
	
	/**
	 * Initializes the supplied array of bytes to random values
	 * @param array the array to randomize
	 */
	private static void generateSalt(byte[] array) {
		RANDOM.nextBytes(array);
	}
	
	
}
