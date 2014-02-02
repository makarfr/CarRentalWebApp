package beans.notification;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

	private final String SENDER_EMAIL = "carrentalweb@gmail.com";
	private final String SENDER_PASSWORD = "testParole";

	/**
	 * Returns true if email is successfully sent, false otherwise
	 * @param emailAddressTo
	 * @param msgSubject
	 * @param msgText
	 * @return
	 */
	public boolean sendEmailMessage(String emailAddressTo, String msgSubject, String msgText) {

		//Create email sending properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(SENDER_EMAIL));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddressTo));

			message.setSubject(msgSubject);
			message.setContent(msgText,"text/html; charset=\"UTF-8\"");
			message.setHeader("Content-Transfer-Encoding", "base64");
			Transport.send(message);

			return true;

		} catch (MessagingException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return false;
		}
	}

}
