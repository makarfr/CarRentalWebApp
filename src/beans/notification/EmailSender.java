package beans.notification;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public enum EmailSender {
	INSTANCE;

	private final String SENDER_EMAIL = "iryna.v.kovalenko@gmail.com";

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

		Session s = Session.getDefaultInstance(props);
		/*Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USER_NAME, PASSSWORD);
			}
		});*/

		try {

			Message message = new MimeMessage(s);
			message.setFrom(new InternetAddress(SENDER_EMAIL));
			message.setContent(msgText,"text/html");

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddressTo));
			message.setSubject(msgSubject);
			
			Transport.send(message);

			return true;

		} catch (MessagingException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

}
