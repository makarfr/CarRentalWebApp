package beans.notification;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import model.Client;
import model.Contract;

import org.stringtemplate.v4.ST;

import common.SessionHelper;

public class OrderDetailsSender {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

	public OrderDetailsSender() {		
	}
	
	public void send(Contract contract) {
		System.out.println("preparing to send email...");
		ResourceBundle rb = ResourceBundle.getBundle("beans/notification/email_template", 
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
		
		Client client = (Client) SessionHelper.getAttribute("client");
		Map<String, Object> attributes = getAttributes(contract, client);
		
		String subject = getFormattedText(rb.getString("email.subject"), attributes);
		String body = getFormattedText(rb.getString("email.body"), attributes);
		System.out.println("body = " + body);
		boolean sent = EmailSender.INSTANCE.sendEmailMessage(
											//	contract.getRegisterUser().getRegisterLogin(), 
				"makarfr@gmail.com",
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
		attributes.put("pick_up_location", "Ukraine, Kiev, st.Street");
		attributes.put("company_cell_phone", "+38 012 345-67-89");
		return attributes;
	}
	
	
	private String getFormattedText(String text, Map<String, Object> attributes) {	
		ST st = new ST(text);
		for (Entry<String, Object> entry : attributes.entrySet()) {
			st.add(entry.getKey(), entry.getValue());
		}
		System.out.println(st.toString());
		return st.render();
	}
	
}
