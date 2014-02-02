package beans.notification;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import dao.interfaces.ClientDAOInterface;

import model.Car;
import model.Client;
import model.Contract;

@ManagedBean
@RequestScoped
public class OrderDetailsSender {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
	@EJB
	private ClientDAOInterface<Client> clientDao;

	public OrderDetailsSender() {		
	}
	
	public void send(Contract contract) {
		System.out.println("preparing to send email...");
		
		ResourceBundle rb = ResourceBundle.getBundle("email_template", 
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
		
		Client client = clientDao.getByUser(contract.getRegisterUser().getRegisterId());
		Map<String, Object> attributes = getAttributes(contract, client);
		
		String subject = getFormattedText(rb.getString("email.subject"), attributes);
		String body = getFormattedText(rb.getString("email.body"), attributes);
		
		boolean sent = EmailSender.INSTANCE.sendEmailMessage(
												contract.getRegisterUser().getRegisterLogin(), 
												subject, 
												body);
		
		System.out.println("email is successfully sent: " + sent);
	}
	
	private Map<String, Object> getAttributes(Contract contract, Client client) {
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("company_name", "Car Rental");
		attributes.put("client_name", client.getClientName() + " " + client.getClientSurname());
		attributes.put("order_id", contract.getContractId());
		attributes.put("order_status", contract.getStatus());
		attributes.put("date_from", dateFormat.format(contract.getContractDateFrom()));
		attributes.put("date_to", dateFormat.format(contract.getContractDateTo()));
		attributes.put("car_details", contract.getCar().getCarInfo());
		attributes.put("total_sum", contract.getTotalPrice());
		attributes.put("pick_up_location", "Kiev, Kudryashova 18-b st.");
		attributes.put("company_cell_phone", "007-1234567");
		return attributes;
	}
	
	
	private String getFormattedText(String text, Map<String, Object> attributes) {		
		StringTemplate st = new StringTemplate(text);
		st.setAttributes(attributes);
		return st.toString();
	}
	
}
