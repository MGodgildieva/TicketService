package telran.tickets.dao;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EmailSender {

	public EmailSender() {
	}


	public void sendEmail(String to, byte[] ticket) throws IOException {
		String from = "ticketservice2018@gmail.com";
		String host = "smtp.gmail.com";
	    String username = "ticketservice2018";
	    String password = "tickets2018";
	    Properties props = new Properties();
	    props.setProperty("mail.smtp.ssl.enable", "true");
	    props.setProperty("mail.smtp.host", host);
	    Session session = Session.getInstance(props);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("TicketService");
			message.setText("This message has been sent automatically.");
			MimeBodyPart messageBodyPart = new MimeBodyPart();
	        Multipart multipart = new MimeMultipart();
	        DataSource source = new ByteArrayDataSource(ticket, "application/pdf");
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName("ticket.pdf");
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);
			Transport.send(message, username, password);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
