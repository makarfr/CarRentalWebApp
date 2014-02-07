package runner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import model.Client;

public class Runner {
	
	// public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CarRentalWebApp");
  //public static EntityManager em = emf.createEntityManager();
	 
	    public static void main(String[] args) {

        	
	    	    String DATASOURCE_CONTEXT = "java:jboss/postgresDB";
	    	    
	    	    Connection result = null;
	    	    try {
	    	      Context initialContext = new InitialContext();
	    	      if ( initialContext == null){
	    	        System.out.println("JNDI problem. Cannot get InitialContext.");
	    	      }
	    	      DataSource datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
	    	      if (datasource != null) {
	    	        result = datasource.getConnection();
	    	      }
	    	      else {
	    	    	  System.out.println("Failed to lookup datasource.");
	    	      }
	    	    }
	    	    catch ( NamingException ex ) {
	    	    	System.out.println("Cannot get connection: NAme " + ex);
	    	    }
	    	    catch(SQLException ex){
	    	    	System.out.println("Cannot get connection: SQL" + ex);
	    	    }
	    	    System.out.println("Connection not null: " + result != null);
	    	
// Client client = new Client();
	
	 //      Query query = em.createNamedQuery("Client.getByNameSurname", Client.class);
	//	em.persist(client);
	    //    query.setParameter("name","a");
	    //    query.setParameter("surname","c");
	    //    List<Client> result =  query.getResultList();
	   //
	    
	     //   System.out.println(result.get(0).getClientPhoneNumber());
	    }
}
