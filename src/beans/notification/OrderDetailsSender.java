package beans.notification;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import model.Client;
import model.Contract;

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

		String subject = String.format(rb.getString("email.subject"), "Car Rental");
		String body = String.format(rb.getString("email.body"), getAttributes(contract, client));

		boolean sent = new EmailSender().sendEmailMessage(
				//	contract.getRegisterUser().getRegisterLogin(),
				"makarfr@gmail.com",
				subject, 
				body);

		System.out.println("email is successfully sent: " + sent);
	}

	public static void main(String args[]) {
		OrderDetailsSender s = new OrderDetailsSender();
		s.send(null);

	}

	private Object[] getAttributes(Contract contract, Client client) {

		Object[] attributes = new Object[] {
				client.getClientName(),
				"Car Rental", //replace
				contract.getContractId(),
				contract.getStatus(),
				dateFormat.format(contract.getContractDateFrom()),
				dateFormat.format(contract.getContractDateTo()),
				contract.getCar().getCarInfo(),
				contract.getTotalPrice(),
				"Ukraine, Kiev, st.Street",//replace
				"+38 012 345-67-89",//replace
				"Car Rental",//replace
				"Ukraine, Kiev, st.Street",//replace

		};

		return attributes;
	}

}
