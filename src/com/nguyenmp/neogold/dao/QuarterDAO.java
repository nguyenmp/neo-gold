package com.nguyenmp.neogold.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.nguyenmp.neogold.beans.Department;
import com.nguyenmp.neogold.beans.Quarter;

/**
 * The Data Access Object for the Department bean providing an abstraction 
 * layer between the database and the business logic.
 * @author Mark Nguyen
 * @see Department
 */
public class QuarterDAO {
	private static final String ENTITY_KIND = "Quarter";
	private static final String PROPERTY_YEAR = "year", PROPERTY_QUARTER = "quarter";
	
	public boolean put(int year, int quarter) {
		// If the quarter already exists in our DB, don't do anything
		Quarter quarterObj = new Quarter();
		quarterObj.setYear(year);
		quarterObj.setQuarter(quarter);
		List<Quarter> quarters = getAll();
		if (quarters.contains(quarterObj)) return false;
		
		// Otherwise, add the quarter to our DB
		Entity entity = new Entity(ENTITY_KIND);
		entity.setProperty(PROPERTY_YEAR, year);
		entity.setProperty(PROPERTY_QUARTER, quarter);
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		datastoreService.put(entity);
		return true;
	}
	
	public boolean put(int code) {
		int year = code / 10;
		int quarter = code % 10;
		
		return put(year, quarter);
	}
	
	public boolean put(String code) {
		int integerCode = Integer.parseInt(code);
		return put(integerCode);
	}
	
	public List<Quarter> getAll() {
		// Create query for all Departments
		Query query = new Query(ENTITY_KIND);
		query.addProjection(new PropertyProjection(PROPERTY_YEAR, Integer.class));
		query.addProjection(new PropertyProjection(PROPERTY_QUARTER, Integer.class));
		
		// Execute query 
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		List<Entity> resultSet = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
		
		// Parse the resulting list of DB entries
		List<Quarter> quarters = new ArrayList<Quarter>();
		for (Entity entity : resultSet) {
			
			// For each entry, parse the DB entry into a Quarter bean
			Quarter quarter = new Quarter();
			quarter.setYear((Integer) entity.getProperty(PROPERTY_YEAR));
			quarter.setQuarter((Integer) entity.getProperty(PROPERTY_QUARTER));
			
			// Add the bean to the list of Departments
			quarters.add(quarter);
		}
		
		// Return the list of Departments generated from DB entries
		return quarters;
	}
}
