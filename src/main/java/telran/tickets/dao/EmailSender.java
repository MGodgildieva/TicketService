package telran.tickets.dao;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import telran.tickets.errors.EmailError;

public class EmailSender {
	String to;

	public EmailSender() {
	}

	public EmailSender(String to) {
		this.to = to;
	}

	public MimeMessage prepareMessage() throws EmailError{
		String from = "ticketservice2018@gmail.com";
		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.setProperty("mail.smtp.ssl.enable", "true");
		props.setProperty("mail.smtp.host", host);
		Session session = Session.getInstance(props);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("TicketService");
		} catch (AddressException e) {
			throw new EmailError("Invalid email");
		} catch (MessagingException e) {
			throw new EmailError("Invalid email");
		}
		return message;
	}

	public void sendEmailWithTicket(byte[] ticket) throws EmailError {
		String username = "ticketservice2018";
		String password = "tickets2018";
		MimeMessage message = prepareMessage();
		try {
			message.setText("This message has been sent automatically.");
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			DataSource source = new ByteArrayDataSource(ticket, "application/pdf");
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("ticket.pdf");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message, username, password);
		} catch (MessagingException mex) {
			throw new EmailError("Invalid email");
		}
	}
	public void sendEmailWithText(String text) throws EmailError {
		String username = "ticketservice2018";
		String password = "tickets2018";
		MimeMessage message = prepareMessage();
		try {
			message.setText(text);
			Transport.send(message, username, password);
		} catch (MessagingException mex) {
			throw new EmailError("Invalid email");
		}
	}

}
