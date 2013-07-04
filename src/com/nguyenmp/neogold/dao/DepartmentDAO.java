package com.nguyenmp.neogold.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.nguyenmp.neogold.beans.Department;

/**
 * The Data Access Object for the Department bean providing an abstraction 
 * layer between the database and the business logic.
 * @author Mark Nguyen
 * @see Department
 */
public class DepartmentDAO {
	private static final String ENTITY_KIND = "Department";
	private static final String PROPERTY_NAME = "name", 
			PROPERTY_ID = "id",
			PROPERTY_ALTERNATIVES = "alternatives";
	
	/**
	 * Creates a new entry in the Department table with the given attributes
	 * @param name the full titled name of the department (e.g., Chicano/a Ethnic Studies)
	 * @param id the unique identifier for the department (e.g., CH ST)
	 * @param alternatives alternative spellings or search queries separated by spaces -- case insensitive (e.g., CHST Latino Latina Chicana Mexican Study)
	 * @return the unique identifier for the entry in the database
	 * @see {@link #PROPERTY_NAME}, {@link #PROPERTY_ID}, {@link #PROPERTY_ALTERNATIVES}
	 */
	public long put(String name, String id, String alternatives) {
		// Create the Database Entity with the data
		Entity entity = new Entity(ENTITY_KIND);
		entity.setProperty(PROPERTY_NAME, name);
		entity.setProperty(PROPERTY_ID, id);
		entity.setProperty(PROPERTY_ALTERNATIVES, alternatives);
		
		// Store the entity into the database and get the key
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Key key = datastoreService.put(entity);
		long keyID = key.getId();
		
		// Return the unique identifier for the Entity
		return keyID;
	}
	
	/**
	 * Retrieves all Departments listed in the Database
	 * @return a list of all Departments in our DB
	 * @see Department
	 */
	public List<Department> getAll() {
		// Create query for all Departments
		Query query = new Query(ENTITY_KIND);
		query.addProjection(new PropertyProjection(PROPERTY_NAME, String.class));
		query.addProjection(new PropertyProjection(PROPERTY_ID, String.class));
		query.addProjection(new PropertyProjection(PROPERTY_ALTERNATIVES, String.class));
		
		// Execute query 
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		List<Entity> resultSet = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
		
		// Parse the resulting list of DB entries
		List<Department> departments = new ArrayList<Department>();
		for (Entity entity : resultSet) {
			
			// For each entry, parse the DB entry into a Department bean
			Department department = new Department();
			department.setName((String) entity.getProperty(PROPERTY_NAME));
			department.setId((String) entity.getProperty(PROPERTY_ID));
			department.setAlternatives((String) entity.getProperty(PROPERTY_ALTERNATIVES));
			
			// Add the bean to the list of Departments
			departments.add(department);
		}
		
		// Return the list of Departments generated from DB entries
		return departments;
	}
}
