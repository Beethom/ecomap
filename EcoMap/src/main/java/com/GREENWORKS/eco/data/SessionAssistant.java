package com.GREENWORKS.eco.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.tinylog.Logger;

import com.GREENWORKS.eco.constants.DatabaseConstants;

/***
 * This class is intended to assist the Servlets by performing all the backend database functions. 
 */
public class SessionAssistant { // TODO: After you get this class working make its methods generic. 
	
    private static SessionFactory sessionFactory = null;
    private Number locationsSize; // I want the SessionAssistant to know the size of the database tables. 
    private Number adminSize; 
    
    /***
     * This method returns the configured builder for the SessionFactory. 
     * @return returns the configured SessionFactory. 
     */
    private static SessionFactory buildSessionFactory() {
    	try {
    		Properties properties = new Properties();
    		properties.setProperty(DatabaseConstants.HIBERNATE_URL, DatabaseConstants.DATABASE_URL);
    		properties.setProperty(DatabaseConstants.HIBERNATE_USERNAME, DatabaseConstants.USERNAME);
    		properties.setProperty(DatabaseConstants.HIBERNATE_PASSWORD, DatabaseConstants.PASSWORD);
    		properties.setProperty(DatabaseConstants.HIBERNATE_DIALECT, DatabaseConstants.DIALECT);
    		properties.setProperty(DatabaseConstants.HIBERNATE_POOL, DatabaseConstants.MAX_POOL);
    		properties.setProperty(DatabaseConstants.HIBERNATE_THREAD, DatabaseConstants.THREAD);
    		properties.setProperty(DatabaseConstants.HIBERNATE_SHOW, DatabaseConstants.TRUE);
    		properties.setProperty(DatabaseConstants.HBM, DatabaseConstants.UPDATE);
    		Configuration config = new Configuration();
    		config.setProperties(properties); 
        	config.addAnnotatedClass(Admin.class);
        	config.addAnnotatedClass(EventPin.class);
        	config.addAnnotatedClass(LocationPin.class);
        	config.addAnnotatedClass(GenericPin.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            return config.buildSessionFactory(builder.build());
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    /***
     * In order to instantiate the SessionFactory thi method must be called. The SessionFactory will refer to the private method
     * buildSessionFactory() to recieve all of its configurations. 
     * @return returns an opened Session. 
     */
    public static Session openSession() {
    	sessionFactory = buildSessionFactory();
    	return getSessionFactory().openSession();
    }
    
    /***
     * This is used to shutdown the SessionFactory. It will close the connection pools and also
     * the cache. 
     */
    public void shutdown() {
        getSessionFactory().close(); // Close caches and connection pools. 
    }
	
    /***
     * This method return the SessionFactory instance variable. It is important to
     * have an instantiated sessionFactory prior to calling this method. 
     * @return The instance variable that is a SessionFactory. 
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /***
     * This method is used to insert a new dataset into the table. 
     * @param pin The Pin that will be inserted into the table. 
     */
    public void insert(Pin pin) {
    	Session session = openSession();
    	session.beginTransaction();
    	session.save(pin);
    	session.getTransaction().commit();
    	Logger.info("Pin inserted: " + pin);
    	session.close();
    }
    
    /***
     * This method is used to insert a new dataset into the table. 
     * @param admin The Admin that will be inserted into the table. 
     */
	public void insert(Admin admin) {
		Session session = openSession();
	 	session.beginTransaction();
	 	session.save(admin);
	 	session.getTransaction().commit();
	 	Logger.info("Admin inserted: " + admin);
	    session.close();
	}
	
	/***
	 * This is the update method. This method will update existing entries in the database. 
	 * @param admin The updated Admin. 
	 */
	public void update(Admin admin) {
    	Session session = openSession();
   	 	session.beginTransaction();
   	 	session.update(admin);
   	 	session.getTransaction().commit();
   	 	Logger.info("Admin update: " + admin);
   	 	session.close();
    }
    
	/***
	 * This is the update method. This method will update existing entries in the database. 
	 * @param pin The updated Pin. 
	 */
    public void update(Pin pin) {
    	Session session = openSession();
    	session.beginTransaction();
    	session.update(pin);
    	session.getTransaction().commit();
    	Logger.info("Pin update: " + pin);
    	session.close();
    }
    
    /***
     * This method will delete the Pin entry that contains an identical image to its own. 
     * @param pin Requires the Pin object that will be deleted in the database. 
     */
    public void delete(Pin pin) {
    	Session session = openSession();
    	session.beginTransaction();
    	session.delete(pin);
    	session.getTransaction().commit();
    	Logger.info("Pin deleted: " + pin);
    	session.close();
    }
   
    /***
     * This method will delete the Admin entry that contains an identical image to its own. 
     * @param admin Requires the Admin object that will be deleted in the database. 
     */
    public void delete(Admin admin) {
    	Session session = openSession();
    	session.beginTransaction();
    	session.delete(admin);
    	session.getTransaction().commit();
    	Logger.info("Admin deleted: " + admin);
    	session.close();
    }
   
    /***
     * This method is used to get a GenericPin by id. 
     * @param pin Requires a Pin object that has a populated id instance variable. 
     * @return
     */
    public Pin get(Pin pin) {
    	Session session = openSession();
    	session.beginTransaction();
    	GenericPin genericPin = session.find(GenericPin.class, pin.getId());
    	Logger.info("Returned pin: " + genericPin);
    	session.close();
    	return genericPin;
    }
   
    /***
     * This method can be viewed as an accessor method for finding objects of type Admin that
     * exist in the database or the cache. Correlation is established by id. 
     * @param admin Requires an Admin as a paramter. 
     * @return Returns the Admin. 
     */
    public Admin get(Admin admin) {
    	Session session = openSession();
    	session.beginTransaction();
    	Admin adminDb = session.find(Admin.class, admin.getId());
    	Logger.info("Returned pin: " + adminDb);
    	session.close();
    	return adminDb;
    }
   
    /***
   	 * This method uses two Strings to return an Admin object. If the parameters can not be correlated to a database
   	 * entry then the Admin object that is returned will be null. This method is used during Admin credential verification. 
   	 * @param username The username of the Admin. 
   	 * @param password The password of the Admin. 
   	 * @return Returns the results from the database as an Admin object. 
   	 */
    public Admin getByLoginCredentials(String username, String password) {
    	Session session = openSession();
    	Admin admin = (Admin) session.createQuery("FROM Admin WHERE password = :password AND username = :username").
			   					setParameter("password", password).setParameter("username", username).uniqueResult();
    	session.close();
    	return admin;
    }
   
    /***
     * This method loads a Pin object and returns it. The load is specified by the id of the Pin
     * object that is provided as a parameter. Load should only be used when it is certain that the data 
     * provided to this method can be correlated to a database entry or in the cache. 
     * @param pin A Pin object. 
     * @return The Pin object that has had its instance variables populated from the database. 
     */
    public Pin load(Pin pin) {
    	Session session = openSession();
    	session.beginTransaction();
    	GenericPin genericPin = session.load(GenericPin.class, pin.getId());
    	Logger.info("Loaded pin: " + genericPin);
    	session.close();
    	return genericPin;
    }
   
    /***
     * This method loads an Admin object and returns it. The load is specified by the id of the Admin
     * object that is provided as a parameter. Load should only be used when it is certain that the data 
     * provided to this method can be correlated to a database entry or in the cache. 
     * @param admin An Admin object. 
     * @return The Admin object that has had its instance variables populated from the database. 
     */
    public Admin load(Admin admin) {
    	Session session = openSession();
    	session.beginTransaction();
    	Admin adminDb = session.load(Admin.class, admin.getId());
    	Logger.info("Loaded pin: " + adminDb);
    	session.close();
    	return adminDb;
    }
   
    /***
     * This method returns all pins that are stored in the database. It stores all the Pins in an ArrayList. This
     * method is used in the front-end of the application. 
     * @return Returns an ArrayList of all the Pins. 
     */
    public ArrayList<Pin> getAllPins() {
    	Session session = openSession();
    	List<GenericPin> genericPinList = session.createQuery("SELECT p FROM GenericPin p", GenericPin.class).getResultList();
    	ArrayList<Pin> pinList = new ArrayList<Pin>();
    	for(Pin pin : genericPinList) {
    		pinList.add(pin);
    	}
    	return pinList;
    }
   
    /***
     * This method returns the total number of entries in the locations table. 
     * @return The total number as a long. 
     */
    public long getLocationsTableSize() {
    	Session session = openSession();
    	return (long)session.createQuery("SELECT COUNT(p) FROM GenericPin p").getSingleResult();
    }
    
}
