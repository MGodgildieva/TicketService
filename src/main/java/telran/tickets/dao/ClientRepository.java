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

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import telran.tickets.api.dto.ClientProfile;
import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ReservationRequest;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.ShortRegisterClient;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketRequest;
import telran.tickets.api.dto.TicketsRequest;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.EventSeat;
import telran.tickets.entities.users.Client;
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
		Client newClient = new Client(client);
		Client possibleClient = em.find(Client.class, client.getPhone());
		Organiser possibleOrganiser = em.find(Organiser.class, client.getPhone());
		if (possibleClient == null && possibleOrganiser == null) {
			try {
				em.persist(newClient);
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
		response.setResponse(newClient.getPhone());
		return response;
	}

	@Override
	@Transactional
	public boolean buyTicket(TicketRequest request) {
		Client client = em.find(Client.class, request.getPhone());
		EventSeat eventSeat = em.find(EventSeat.class, Integer.parseInt(request.getEventSeatId()));
		if (eventSeat.isTaken() && eventSeat.getBuyer() != client) {
			return false;
		}
		Set<EventSeat> clientTickets = client.getBoughtTickets();
		clientTickets.add(eventSeat);
		client.setBoughtTickets(clientTickets);
		try {
			em.merge(client);
		} catch (Exception e1) {
			return false;
		}
		eventSeat.setTaken(true);
		eventSeat.setBuyer(client);
		eventSeat.setBookingTime(null);
		try {
			em.merge(eventSeat);
		} catch (Exception e1) {
			return false;
		}
		Event event = em.find(Event.class, Integer.parseInt(request.getEventId()));
		Integer count = event.getBoughtTickets();
		event.setBoughtTickets(++count);
		PdfCreator creator = new PdfCreator(eventSeat);
		try {
			em.merge(event);
			EmailSender sender = new EmailSender();
			if (client.getEmail() != null) {
				sender.sendEmail(client.getEmail(), creator.createTicketWithoutSaving());
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean addToFavourite(FavouriteRequest favRequest) {
		Client client = em.find(Client.class, favRequest.getPhone());
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
	public Set<ShortEventInfo> getFavourite(String clientId) {
		Client client = em.find(Client.class, clientId);
		Set<Event> events = client.getFavourite();
		Set<ShortEventInfo> response = new HashSet<>();
		for (Event event : events) {
			ShortEventInfo eventInfo = new ShortEventInfo(event);
			response.add(eventInfo);
		}
		return response;
	}

	@Override
	public ClientProfile getProfile(String phone) {
		return new ClientProfile(em.find(Client.class, phone));
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
		em.merge(client);
		return new ClientProfile(client);
	}

	@Override
	@Transactional
	public boolean bookTicket(ReservationRequest request) {
		Date currentDate = new Date();
		EventSeat seat = em.find(EventSeat.class, Integer.parseInt(request.getSeatId()));
		seat.setTaken(request.getIsBooked());
		seat.setBookingTime(currentDate);
		seat.setBuyer(em.find(Client.class, request.getPhone()));
		try {
			em.merge(seat);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
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
				em.merge(eventSeat);
			}
		}
	}

	@Override
	@Transactional
	public SuccessResponse register(ShortRegisterClient client) {
		SuccessResponse response = new SuccessResponse();
		Client newClient = new Client(client);
		Client possibleClient = em.find(Client.class, client.getPhone());
		Organiser possibleOrganiser = em.find(Organiser.class, client.getPhone());
		if (possibleClient == null && possibleOrganiser == null) {
			try {
				em.persist(newClient);
			} catch (Exception e) {
				e.printStackTrace();
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
		response.setResponse(newClient.getPhone());
		return response;
	}

	@Override
	@Transactional
	public boolean buyTickets(TicketsRequest request) throws IOException {
		Client client = em.find(Client.class, request.getPhone());
		List<String> eventSeatIds = new ArrayList<>(Arrays.asList(request.getEventSeatIds()));
		Set<EventSeat> clientTickets = client.getBoughtTickets();
		Event event = em.find(Event.class, Integer.parseInt(request.getEventId()));
		Integer count = event.getBoughtTickets();
		PdfCreator creator = new PdfCreator();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
		for (String id : eventSeatIds) {
			EventSeat eventSeat = em.find(EventSeat.class, Integer.parseInt(id));
			if (eventSeat.isTaken() && eventSeat.getBuyer() != client) {
				return false;
			}
			clientTickets.add(eventSeat);
			eventSeat.setTaken(true);
			eventSeat.setBuyer(client);
			eventSeat.setBookingTime(null);
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
			EmailSender sender = new EmailSender();
			if (client.getEmail() != null) {
				sender.sendEmail(client.getEmail(), pdfToBytes);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
