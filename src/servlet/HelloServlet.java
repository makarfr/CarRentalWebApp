package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.CarModel;
import model.Client;
import model.RegisterUser;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    String DATASOURCE_CONTEXT = "java:jboss/postgresDB";
	    String message;
	    Connection result = null;
	    try {
	      Context initialContext = new InitialContext();
	      if ( initialContext == null){
	       message = "JNDI problem. Cannot get InitialContext.";
	      }
	      DataSource datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
	      if (datasource != null) {
	        result = datasource.getConnection();
	      }
	      else {
	    	  message = "Failed to lookup datasource.";
	      }
	    }
	    catch ( NamingException ex ) {
	    	 message = "Cannot get connection: NAme " + ex;
	    }
	    catch(SQLException ex){
	    	 message = "Cannot get connection: SQL" + ex;
	    }
	    message = "Connection not null: " + (result != null);
	    
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CarRentalWebApp");
	  EntityManager em = emf.createEntityManager();
	//  Client client = new Client();
	//  em.persist(client);
	/*  Query query = em.createNamedQuery("Client.getByNameSurname", Client.class);
	  query.setParameter("name","karl");
	    query.setParameter("surname","urban");
	      List<Client> result1 =  query.getResultList();*/
	  
	  Query query = em.createNamedQuery("RegisterUser.findByLogin", RegisterUser.class);
		query.setParameter("login", "usr");
	//	query.setParameter("desc", "fiesta");
			  //em.createNamedQuery("Client.findAll", Client.class);
      List<RegisterUser> result1 =  query.getResultList();
	  
	  message = message + " \n" + result1.size() + " " + result1.get(0).getRegisterPassword();
	      
	    response.setContentType( "text/html" );  
        PrintWriter out = response.getWriter();  
        out.println( "<html><head>" );  
        out.println( "<title>A Sample Servlet!</title>" );  
        out.println( "</head>" );  
        out.println( "<body>" );  
        out.println( "<h1>"+ message +"</h1>" );  
        out.println( "</body></html>" );  
        out.close();  
	}

}
