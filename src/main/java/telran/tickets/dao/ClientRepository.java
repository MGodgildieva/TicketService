package telran.tickets.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import telran.tickets.api.dto.ClientBookedTicket;
import telran.tickets.api.dto.ClientProfile;
import telran.tickets.api.dto.ClientTicket;
import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ReservationRequest;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.ShortRegisterClient;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketId;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.EventSeat;
import telran.tickets.entities.objects.Ticket;
import telran.tickets.entities.users.Client;
import telran.tickets.entities.users.Confirmation;
import telran.tickets.entities.users.Organiser;
import telran.tickets.errors.DatabaseError;
import telran.tickets.errors.EmailError;
import telran.tickets.errors.JsonError;
import telran.tickets.errors.RegistrationError;
import telran.tickets.errors.SeatTakenError;
import telran.tickets.interfaces.IClient;

@Repository
public class ClientRepository implements IClient {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public SuccessResponse register(RegisterClient client) {
		SuccessResponse response = new SuccessResponse();
		Client possibleClient = em.find(Client.class, client.getEmail());
		Organiser possibleOrganiser = em.find(Organiser.class, client.getEmail());
		if (possibleClient == null && possibleOrganiser == null) {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString;
			try {
				jsonInString = mapper.writeValueAsString(client);
			} catch (JsonProcessingException e1) {
				throw new JsonError("JSON parsing problem");
			}
			// String code = RandomStringUtils.randomAlphanumeric(10);
			String code = RandomStringUtils.randomNumeric(4);
			Confirmation conf = new Confirmation(code, jsonInString, new Date());
			String text = "Your confirmation code: " + code;
			EmailSender sender = new EmailSender(client.getEmail());
			sender.sendEmailWithText(text);
			try {
				em.persist(conf);
			} catch (Exception e) {
				throw new DatabaseError("Database error");
			}
		} else {
			throw new RegistrationError("There is already a user with this email");
		}
		response.setSuccess(true);
		response.setResponse("A message with the confirmation code has been sent to your email");
		return response;
	}

	@Override
	@Transactional
	public boolean addToFavourite(FavouriteRequest favRequest) {
		Client client = em.find(Client.class, favRequest.getEmail());
		Set<Event> faves = client.getFavourite();
		if (favRequest.getIsFavourite()) {
			Event event = em.find(Event.class, Integer.parseInt(favRequest.getEventId()));
			faves.add(event);
		} else {
			Iterator<Event> iter = faves.iterator();
			while (iter.hasNext()) {
				if (iter.next().getEventId().equals(Integer.parseInt(favRequest.getEventId()))) {
					iter.remove();
				}
			}
		}
		client.setFavourite(faves);
		try {
			em.merge(client);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public Set<ShortEventInfo> getFavourite(String email) {
		Client client = em.find(Client.class, email);
		Set<Event> events = client.getFavourite();
		Set<ShortEventInfo> response = new HashSet<>();
		for (Event event : events) {
			ShortEventInfo eventInfo = new ShortEventInfo(event);
			response.add(eventInfo);
		}
		return response;
	}

	@Override
	public ClientProfile getProfile(String email) {
		return new ClientProfile(em.find(Client.class, email));
	}

	@Override
	@Transactional
	public ClientProfile changeProfile(ClientProfile clientWithNewInfo) {// change email
		Client client = em.find(Client.class, clientWithNewInfo.getEmail());
		if (client.getAdditionalInfo() != clientWithNewInfo.getAdditionalInfo()) {
			client.setAdditionalInfo(clientWithNewInfo.getAdditionalInfo());
		}
		if (client.getAdditionalPhone() != clientWithNewInfo.getAdditionalPhone()) {
			client.setAdditionalPhone(clientWithNewInfo.getAdditionalPhone());
		}
		if (client.getCity() != clientWithNewInfo.getCity()) {
			client.setCity(clientWithNewInfo.getCity());
		}
		if (client.getCompany() != clientWithNewInfo.getCompany()) {
			client.setCompany(clientWithNewInfo.getCompany());
		}
		if (client.getCountry() != clientWithNewInfo.getCountry()) {
			client.setCountry(clientWithNewInfo.getCountry());
		}
		if (client.getHonour() != clientWithNewInfo.getHonour()) {
			client.setHonour(clientWithNewInfo.getHonour());
		}
		if (client.getHouse() != clientWithNewInfo.getHonour()) {
			client.setHonour(clientWithNewInfo.getHonour());
		}
		if (client.getName() != clientWithNewInfo.getName()) {
			client.setName(clientWithNewInfo.getName());
		}
		if (client.getPassword() != clientWithNewInfo.getPassword()) {
			client.setPassword(clientWithNewInfo.getPassword());
		}
		if (client.getPostcode() != clientWithNewInfo.getPostcode()) {
			client.setPostcode(clientWithNewInfo.getPostcode());
		}
		if (client.getStreet() != clientWithNewInfo.getStreet()) {
			client.setStreet(clientWithNewInfo.getStreet());
		}
		if (client.getSurname() != clientWithNewInfo.getSurname()) {
			client.setSurname(clientWithNewInfo.getSurname());
		}
		if (client.getPhone() != clientWithNewInfo.getPhone()) {
			client.setPhone(clientWithNewInfo.getPhone());
		}
		em.merge(client);
		return new ClientProfile(client);
	}

	@Override
	@Transactional
	public TicketId bookTicket(ReservationRequest request) {
		Date currentDate = new Date();
		Ticket ticket = new Ticket(currentDate, null, null);
		Client client = null;
		if (!request.getEmail().equals(null)) {
			client = em.find(Client.class, request.getEmail());
		}
		Integer price = 0;
		ticket.setBooker(client);
		for (String seatId : request.getEventSeatIds()) {
			EventSeat seat = em.find(EventSeat.class, Integer.parseInt(seatId));
			if (!seat.getIsTaken()) {
				seat.setTaken(request.getIsBooked());
				seat.setTicket(ticket);
				price = price + Integer.parseInt(seat.getPrice());
				try {
					em.merge(seat);
				} catch (Exception e) {
					e.printStackTrace();
					throw new DatabaseError("Database error");
				} 
			}else {
				throw new SeatTakenError("This seat has already been taken, please, choose another.");
			}
		}
		if (client != null) {
			try {
				em.merge(client);
			} catch (Exception e) {
				e.printStackTrace();
				throw new DatabaseError("Database error");
			}
		}
		ticket.setPrice(price);
		try {
			em.merge(ticket);
			return new TicketId(ticket.getTicketId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseError("Database error");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Scheduled(cron = "*/60 * * * * *")
	public void checkSeat() {
		Query query1 = em.createQuery("SELECT e FROM Ticket e");
		if (!query1.getResultList().isEmpty()) {
			Query query = em.createQuery(
					"SELECT e FROM Ticket e WHERE e.paymentStarted IS FALSE AND EXTRACT(EPOCH FROM current_timestamp) - EXTRACT(EPOCH FROM e.bookingTime) >= 600");
			List<Ticket> bookedSeats = new ArrayList<>(query.getResultList());
			List<Long> ids = new ArrayList<>();
			for (Ticket ticket : bookedSeats) {
				ids.add(ticket.getTicketId());
			}
			Query query3 = em.createQuery("UPDATE EventSeat e SET e.isTaken = false, ticket_ticket_id = null WHERE ticket_ticket_id IN ?1");
			query3.setParameter(1, ids);
			query3.executeUpdate();
			Query query2 = em.createQuery(
					"DELETE FROM Ticket e WHERE e.paymentStarted IS FALSE AND EXTRACT(EPOCH FROM current_timestamp) - EXTRACT(EPOCH FROM e.bookingTime) >= 600");
			query2.executeUpdate();
		}
	}

	@Override
	@Transactional
	public SuccessResponse register(ShortRegisterClient client) {
		SuccessResponse response = new SuccessResponse();
		Client possibleClient = em.find(Client.class, client.getEmail());
		Organiser possibleOrganiser = em.find(Organiser.class, client.getEmail());
		if (possibleClient == null && possibleOrganiser == null) {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString;
			try {
				jsonInString = mapper.writeValueAsString(new RegisterClient(client));
			} catch (JsonProcessingException e1) {
				throw new JsonError("JSON parsing problem");
			}
			// String code = RandomStringUtils.randomAlphanumeric(10);
			String code = RandomStringUtils.randomNumeric(4);
			Confirmation conf = new Confirmation(code, jsonInString, new Date());
			String text = "Your confirmation code: " + code;
			EmailSender sender = new EmailSender(client.getEmail());
			sender.sendEmailWithText(text);
			try {
				em.persist(conf);
			} catch (Exception e) {
				throw new DatabaseError("Database Error");
			}
		} else {
			throw new RegistrationError("There is already a user with this email");
		}
		response.setSuccess(true);
		response.setResponse("A message with the confirmation code has been sent to your email");
		return response;
	}

	/*
	 * @Override
	 * 
	 * @Transactional public boolean buyTickets(TicketsRequest request) throws
	 * IOException { Client client = em.find(Client.class, request.getEmail());
	 * Set<Ticket> clientTickets = client.getBoughtTickets(); Event event =
	 * em.find(Event.class, Integer.parseInt(request.getEventId())); Integer count =
	 * event.getBoughtTickets(); PdfCreator creator = new PdfCreator();
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); PdfDocument pdfDoc
	 * = new PdfDocument(new PdfWriter(baos));
	 * System.out.println(Arrays.toString(request.getEventSeatIds())); if
	 * (!request.getTicketId().equals(null)) { Ticket ticket = em.find(Ticket.class,
	 * request.getTicketId()); ticket.setPaymentStarted(true);
	 * ticket.setBuyer(client); ticket.setBuyingTime(new Date()); count = count +
	 * ticket.getEventSeats().size(); try { em.merge(ticket); for (EventSeat e :
	 * ticket.getEventSeats()) { creator.createTicketInRectangle(e, pdfDoc); } }
	 * catch (Exception e) { return false; } }else { Ticket ticket = new Ticket(new
	 * Date(), true, client, null); List<String> eventSeatIds = new
	 * ArrayList<>(Arrays.asList(request.getEventSeatIds())); for (String id :
	 * eventSeatIds) { EventSeat eventSeat = em.find(EventSeat.class,
	 * Integer.parseInt(id)); if (eventSeat.getIsTaken() &&
	 * eventSeat.getTicket().getBooker() != client) { return false; }
	 * eventSeat.setTicket(ticket); eventSeat.setTaken(true); try {
	 * em.persist(ticket); em.merge(eventSeat);
	 * creator.createTicketInRectangle(eventSeat, pdfDoc); } catch (Exception e1) {
	 * return false; } count++; } } pdfDoc.close(); byte[] pdfToBytes =
	 * baos.toByteArray(); baos.close(); client.setBoughtTickets(clientTickets); try
	 * { em.merge(client); } catch (Exception e1) { return false; }
	 * event.setBoughtTickets(count); try { em.merge(event); EmailSender sender =
	 * new EmailSender(client.getEmail()); if (client.getEmail() != null) {
	 * sender.sendEmailWithTicket(pdfToBytes); } return true; } catch (Exception e)
	 * { return false; } }
	 */

	@Override
	public Iterable<ClientTicket> getBoughtTickets(String email) {
		Client client = em.find(Client.class, email);
		Set<ClientTicket> boughtTickets = new HashSet<>();
		for (Ticket ticket : client.getBoughtTickets()) {
			for (EventSeat e : ticket.getEventSeats()) {
				boughtTickets.add(new ClientTicket(e, email));
			}
		}
		return boughtTickets;
	}

	@Override
	public Iterable<ClientBookedTicket> getBookedTickets(String email) {
		Client client = em.find(Client.class, email);
		Set<ClientBookedTicket> bookedTickets = new HashSet<>();
		for (Ticket ticket : client.getBookedTickets()) {
			for (EventSeat e : ticket.getEventSeats()) {
				bookedTickets.add(new ClientBookedTicket(e, email));
			}
		}
		return bookedTickets;
	}

	@Override
	@Transactional
	public SuccessResponse checkConfirmation(String code) {
		ObjectMapper mapper = new ObjectMapper();
		Confirmation conf = em.find(Confirmation.class, code);
		RegisterClient client;
		try {
			client = mapper.readValue(conf.getInfo(), RegisterClient.class);
		} catch (JsonParseException e1) {
			throw new JsonError("JSON parsing problem");
		} catch (JsonMappingException e1) {
			throw new JsonError("JSON parsing problem");
		} catch (IOException e1) {
			throw new JsonError("JSON parsing problem");
		}
		try {
			em.remove(conf);
		} catch (Exception e) {
			throw new RegistrationError("Wrong confirmation code");
		}
		Client newClient = new Client(client);
		try {
			em.persist(newClient);
			return new SuccessResponse(true, newClient.getEmail());
		} catch (Exception e) {
			throw new DatabaseError("Database Error");
		}
	}

	@Scheduled(cron = "*/60 * * * * *")
	@Transactional
	@Modifying
	public void cleanConfirmation() {
		Query query1 = em.createQuery("SELECT e FROM Confirmation e");
		if (!query1.getResultList().isEmpty()) {
			Query query = em.createQuery(
					"DELETE FROM Confirmation e WHERE EXTRACT(EPOCH FROM e.time) - EXTRACT(EPOCH FROM current_timestamp) >= 3600");
			query.executeUpdate();
		}
	}

	@Override
	@Transactional
	public boolean startPayment(boolean start, Long orderId) {
		Ticket ticket = em.find(Ticket.class, orderId);
		if (ticket.equals(null)) {
			return false;
		} else {
			ticket.setPaymentStarted(start);
			try {
				em.merge(ticket);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	@Override
	@Transactional
	public boolean checkOrder(Long orderId) {
		Ticket ticket = em.find(Ticket.class, orderId);
		if (ticket.equals(null)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	@Transactional
	public boolean finishPayment(Long orderId) throws IOException {
		Date currentDate = new Date();
		Ticket ticket = em.find(Ticket.class, orderId);
		ticket.setBuyingTime(currentDate);
		ticket.setBuyer(ticket.getBooker());
		ticket.setBooker(null);
		Event event = ticket.getEventSeats().get(0).getEvent();
		Integer count = event.getBoughtTickets();
		count = count + ticket.getEventSeats().size();
		event.setBoughtTickets(count);
		try {
			em.merge(event);
		} catch (Exception e1) {
			return false;
		}
		if (ticket.getBuyer() != null) {
			PdfCreator creator = new PdfCreator();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
			for (EventSeat eventSeat : ticket.getEventSeats()) {
				creator.createTicketInRectangle(eventSeat, pdfDoc);
			}
			pdfDoc.close();
			byte[] pdfToBytes = baos.toByteArray();
			baos.close();
			try {
				em.merge(ticket);
				EmailSender sender = new EmailSender(ticket.getBuyer().getEmail());
				sender.sendEmailWithTicket(pdfToBytes);
				return true;
			} catch (EmailError e) {
				return false;
			}
		} else {
			try {
				em.merge(ticket);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	@Override
	@Transactional
	public boolean sendEmail(String email, Long orderId) throws IOException {
		Ticket ticket = em.find(Ticket.class, orderId);
		ticket.setEmail(email);
		PdfCreator creator = new PdfCreator();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
		for (EventSeat eventSeat : ticket.getEventSeats()) {
			creator.createTicketInRectangle(eventSeat, pdfDoc);
		}
		pdfDoc.close();
		byte[] pdfToBytes = baos.toByteArray();
		baos.close();
		try {
			em.merge(ticket);
			EmailSender sender = new EmailSender(email);
			sender.sendEmailWithTicket(pdfToBytes);
			return true;
		} catch (EmailError e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteTicket(Long orderId) {
		Ticket ticket = em.find(Ticket.class, orderId);
		if (!ticket.getPaymentStarted()) {
			try {
				em.remove(ticket);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
