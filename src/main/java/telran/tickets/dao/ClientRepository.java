package telran.tickets.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

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
import telran.tickets.api.dto.TicketsRequest;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.EventSeat;
import telran.tickets.entities.users.Client;
import telran.tickets.entities.users.Confirmation;
import telran.tickets.entities.users.Organiser;
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
			try {
				ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(client);
				String code = RandomStringUtils.randomAlphanumeric(10);
				Confirmation conf =  new Confirmation(code, jsonInString);
				String text = "Your confirmation code: " + code;
				EmailSender sender =  new EmailSender(client.getEmail());
				sender.sendEmailWithText(text);
				em.persist(conf);
			} catch (Exception e) {
				response.setSuccess(false);
				response.setResponse("Database error");
				return response;
			}
		} else {
			response.setSuccess(false);
			response.setResponse("There is already a user with this email");
			return response;
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
	public boolean bookTicket(ReservationRequest request) {
		Date currentDate = new Date();
		Client client = em.find(Client.class, request.getEmail());
		Set<EventSeat> bookedTickets = client.getBookedTickets();
		for (String seatId : request.getEventSeatIds()) {
			EventSeat seat = em.find(EventSeat.class, Integer.parseInt(seatId));
			seat.setTaken(request.getIsBooked());
			if (request.getIsBooked()) {
				seat.setBookingTime(currentDate);
				seat.setBooker(client);
				bookedTickets.add(seat);
			}else {
				seat.setBookingTime(null);
				seat.setBooker(null);
				bookedTickets.remove(seat);
			}
			try {
				em.merge(seat);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		client.setBookedTickets(bookedTickets);
		try {
			em.merge(client);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Scheduled(cron = "*/60 * * * * *")
	public void checkSeat() {
		Query query1 = em.createQuery("SELECT e FROM EventSeat e");
		if (!query1.getResultList().isEmpty()) {
			Query query = em.createQuery(
					"SELECT e FROM EventSeat e WHERE e.bookingTime IS NOT NULL AND EXTRACT(EPOCH FROM e.bookingTime) - EXTRACT(EPOCH FROM current_timestamp) >= 600");
			List<EventSeat> bookedSeats = query.getResultList();
			for (EventSeat eventSeat : bookedSeats) {
				eventSeat.setTaken(false);
				eventSeat.setBookingTime(null);
				eventSeat.setBooker(null);
				em.merge(eventSeat);
			}
		}
	}

	@Override
	@Transactional
	public SuccessResponse register(ShortRegisterClient client) {
		SuccessResponse response = new SuccessResponse();
		Client possibleClient = em.find(Client.class, client.getEmail());
		Organiser possibleOrganiser = em.find(Organiser.class, client.getEmail());
		if (possibleClient == null && possibleOrganiser == null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(new RegisterClient(client));
				String code = RandomStringUtils.randomAlphanumeric(10);
				Confirmation conf =  new Confirmation(code, jsonInString);
				String text = "Your confirmation code: " + code;
				EmailSender sender =  new EmailSender(client.getEmail());
				sender.sendEmailWithText(text);
				em.persist(conf);
			} catch (Exception e) {
				response.setSuccess(false);
				response.setResponse("Database error");
				return response;
			}
		} else {
			response.setSuccess(false);
			response.setResponse("There is already a user with this email");
			return response;
		}
		response.setSuccess(true);
		response.setResponse("A message with the confirmation code has been sent to your email");
		return response;
	}

	@Override
	@Transactional
	public boolean buyTickets(TicketsRequest request) throws IOException {
		Client client = em.find(Client.class, request.getEmail());
		System.out.println(Arrays.toString(request.getEventSeatIds()));
		List<String> eventSeatIds = new ArrayList<>(Arrays.asList(request.getEventSeatIds()));
		Set<EventSeat> clientTickets = client.getBoughtTickets();
		Event event = em.find(Event.class, Integer.parseInt(request.getEventId()));
		Integer count = event.getBoughtTickets();
		PdfCreator creator = new PdfCreator();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
		for (String id : eventSeatIds) {
			EventSeat eventSeat = em.find(EventSeat.class, Integer.parseInt(id));
			if (eventSeat.isTaken() && eventSeat.getBooker() != client) {
				return false;
			}
			clientTickets.add(eventSeat);
			eventSeat.setTaken(true);
			eventSeat.setBuyer(client);
			eventSeat.setBookingTime(null);
			eventSeat.setBuyingTime(new Date());
			try {
				em.merge(eventSeat);
				creator.createTicketInRectangle(eventSeat, pdfDoc);
			} catch (Exception e1) {
				return false;
			}
			count++;
		}
		pdfDoc.close();
		byte[] pdfToBytes = baos.toByteArray();
		baos.close();
		client.setBoughtTickets(clientTickets);
		try {
			em.merge(client);
		} catch (Exception e1) {
			return false;
		}
		event.setBoughtTickets(count);
		try {
			em.merge(event);
			EmailSender sender = new EmailSender(client.getEmail());
			if (client.getEmail() != null) {
				sender.sendEmailWithTicket(pdfToBytes);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Iterable<ClientTicket> getBoughtTickets(String email) {
		Client client =  em.find(Client.class, email);
		Set<ClientTicket> boughtTickets = new HashSet<>();
		for (EventSeat eventSeat : client.getBoughtTickets()) {
			boughtTickets.add(new ClientTicket(eventSeat, email));
		}
		return boughtTickets;
	}

	@Override
	public Iterable<ClientBookedTicket> getBookedTickets(String email) {
		Client client =  em.find(Client.class, email);
		Set<ClientBookedTicket> bookedTickets = new HashSet<>();
		for (EventSeat eventSeat : client.getBookedTickets()) {
			bookedTickets.add(new ClientBookedTicket(eventSeat, email));
		}
		return bookedTickets;
	}

	@Override
	@Transactional
	public SuccessResponse checkConfirmation(String code) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Confirmation conf =  em.find(Confirmation.class, code);
		RegisterClient client = mapper.readValue(conf.getInfo(), RegisterClient.class);
		try {
			em.remove(conf);
		} catch (Exception e) {
			e.printStackTrace();
			return new SuccessResponse(false, "Wrong confirmation code");
		}
		Client newClient =  new Client(client); 
		try {
			em.persist(newClient);
			return new SuccessResponse(true, newClient.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
			return new SuccessResponse(false, "Database error");
		}	
	}
}
