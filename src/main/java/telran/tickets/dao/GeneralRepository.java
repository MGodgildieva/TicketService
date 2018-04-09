package telran.tickets.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import telran.tickets.api.dto.EventClientRequest;
import telran.tickets.api.dto.FullEventInfo;
import telran.tickets.api.dto.HallEventInfo;
import telran.tickets.api.dto.LoginRequest;
import telran.tickets.api.dto.LoginResponse;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketsRequest;
import telran.tickets.api.dto.TypeRequest;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.EventSeat;
import telran.tickets.entities.objects.Hall;
import telran.tickets.entities.users.Client;
import telran.tickets.entities.users.Organiser;
import telran.tickets.errors.DatabaseError;
import telran.tickets.errors.RegistrationError;
import telran.tickets.interfaces.IGeneral;

@Repository
public class GeneralRepository implements IGeneral {
	@PersistenceContext
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<String> getCities() {
		Query query = em.createQuery("SELECT city FROM Hall");
		return new HashSet<>(query.getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<String> getHallsByCity(String city) {
		Query query; 
		if (city != "") {
			query= em.createQuery("SELECT h.hallId FROM Hall h WHERE h.city=?1");
			query.setParameter(1, city);
		}else {
			query = em.createQuery("SELECT h FROM Hall h");
		}
		return new HashSet<>(query.getResultList());
	}
	
	private Iterable<ShortEventInfo> transformList(List<Event> events){
		List<ShortEventInfo> eventInfos = new ArrayList<>();
		for (Event event : events) {
			if(!event.getIsHidden() && !event.getIsDeleted()) {
				eventInfos.add(new ShortEventInfo(event));
			}
		}
		return eventInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsByDate(String city) {
		Query query;
		if (city != "") {
			query = em.createQuery("SELECT e FROM Event e WHERE e.city=?1 AND e.date>=?2 ORDER BY e.date ASC");
			query.setParameter(1, city);
		}else {
			query = em.createQuery("SELECT e FROM Event e WHERE e.date>=?2 ORDER BY e.date ASC");
		}
		query.setParameter(2, new Date());
		return transformList(query.getResultList());	
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsByPlace(String hallId) {
		Query query = em.createQuery("SELECT e FROM Event e WHERE hall_hall_id=?1 AND e.date>=?2 ORDER BY e.date ASC");
		query.setParameter(1, Integer.parseInt(hallId));
		query.setParameter(2, new Date());
		return transformList(query.getResultList());	
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsByType(TypeRequest typeRequest) {
		Query query;
		if (typeRequest.getCity() != "") {
			query = em.createQuery("SELECT e FROM Event e WHERE e.type=?1 AND e.city=?2 AND e.date>=?3 ORDER BY e.date ASC");
			query.setParameter(1, typeRequest.getType());
			query.setParameter(2, typeRequest.getCity());
			query.setParameter(3, new Date());
		}else {
			query = em.createQuery("SELECT e FROM Event e WHERE e.type=?1 AND e.date>=?3 ORDER BY e.date ASC");
			query.setParameter(1, typeRequest.getType());
			query.setParameter(3, new Date());
		}
		return transformList(query.getResultList());	
	}

	@Override
	public FullEventInfo getEvent(EventClientRequest eventClientRequest) {
		Event event = em.find(Event.class, Integer.parseInt(eventClientRequest.getEventId()));
		FullEventInfo response = new FullEventInfo(event);
		if (eventClientRequest.getEmail()!="") {
			Client client = em.find(Client.class, eventClientRequest.getEmail());
			response.setFavourite(client.getFavourite().contains(event));
		}else {
			response.setFavourite(false);
		}
		response.setEmail(eventClientRequest.getEmail());
		Integer availableTickets = event.getAllTickets() - event.getBoughtTickets();
		response.setTicketCount(availableTickets.toString());
		response.setPriceRange(event.getPriceRange());
		return response;
	}

	@Override
	public HallEventInfo getFullHall(String eventId) {
		Event event = em.find(Event.class, Integer.parseInt(eventId));
		Hall hall = event.getHall();
		return new HallEventInfo(event, hall);
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		Client possibleClient = em.find(Client.class, request.getEmail());
		Organiser possibleOrganiser = em.find(Organiser.class, request.getEmail());
		if (possibleClient == null && possibleOrganiser == null) {
			throw new RegistrationError("Wrong email");
		}
		if (possibleClient != null) {
			if (possibleClient.getPassword().equals(request.getPassword())) {
				return new LoginResponse(request.getEmail(), possibleClient.getType());
			}
			else {
				throw new RegistrationError("Wrong password");
			}
		}
		if (possibleOrganiser != null) {
			if (possibleOrganiser.getPassword().equals(request.getPassword()) 
					&& possibleOrganiser.getIsBanned() == false) {
				return new LoginResponse(request.getEmail(), possibleOrganiser.getType());
			}
			else {
				throw new RegistrationError("Wrong password");
			}
		}
		throw new DatabaseError("Database error");
	}
	@Override
	@Transactional
	public SuccessResponse forgottenPassword(String email) {
		Client client = em.find(Client.class, email);
		Organiser org =  em.find(Organiser.class, email);
		EmailSender sender = new EmailSender(email);
		String text = "Your new password: ";
		if (client == null && org == null) {
			throw new RegistrationError("There is no such user");
		}
		if (client!=null) {
			String newPassword = RandomStringUtils.randomAlphanumeric(10);
			client.setPassword(newPassword);
			sender.sendEmailWithText(text + newPassword);
			try {
				em.merge(client);
				return new SuccessResponse(true, "A message with new password has been sent to your email");
			} catch (Exception e) {
				throw new DatabaseError("Database error");
			}
		}
		if (org!= null) {
			String newPassword = RandomStringUtils.randomAlphanumeric(10);
			org.setPassword(newPassword);
			sender.sendEmailWithText(text + newPassword);
			try {
				em.merge(org);
				return new SuccessResponse(true, "A message with new password has been sent to your email");
			} catch (Exception e) {
				throw new DatabaseError("Database error");
			}
		}
		throw new DatabaseError("Database error");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<String> getTypes() {
		Query query = em.createQuery("SELECT type FROM Event");
		return new HashSet<>(query.getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsOnDate(long date) throws ParseException {
		Query query = em.createQuery("SELECT e FROM Event e WHERE e.date=?1 ORDER BY e.date ASC");
		query.setParameter(1, new Date(date));
		return transformList(query.getResultList());	
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsInDateInterval(long firstDate, long lastDate) throws ParseException {
		Query query = em.createQuery("SELECT e FROM Event e WHERE e.date>=?1 AND e.date<=?2 ORDER BY e.date ASC");
		query.setParameter(1, new Date(firstDate));
		query.setParameter(2, new Date(lastDate));
		return transformList(query.getResultList());	
	}

	@Override
	@Transactional
	public boolean buyTicketsWithoutRegistration(TicketsRequest request) throws IOException {
		List<String> eventSeatIds = new ArrayList<>(Arrays.asList(request.getEventSeatIds()));
		Event event = em.find(Event.class, Integer.parseInt(request.getEventId()));
		Integer count = event.getBoughtTickets();
		PdfCreator creator = new PdfCreator();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
		for (String id : eventSeatIds) {
			EventSeat eventSeat = em.find(EventSeat.class, Integer.parseInt(id));
			if (eventSeat.isTaken()) {
				return false;
			}
			eventSeat.setTaken(true);
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
		byte [] pdfToBytes = baos.toByteArray();
		baos.close();
		event.setBoughtTickets(count);
		try {
			em.merge(event);
			EmailSender sender = new EmailSender(request.getEmail());
			sender.sendEmailWithTicket(pdfToBytes);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Iterable<ShortEventInfo> searchEvents(String text) {
		Query query = em.createQuery("SELECT e FROM Event e WHERE LOWER(e.title) LIKE LOWER(?1) OR LOWER(e.artist) LIKE LOWER(?1) ORDER BY e.date ASC");
		query.setParameter(1, "%" + text + "%");
		return transformList(query.getResultList());
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Iterable<ShortEventInfo> filterEvents(String type, Long date1, Long date2) {
		Query query = null;
		if (type != "" & date1 != null & date2 != null) {
			query = em.createQuery("SELECT e FROM Event e WHERE e.date>=?1 AND e.date<=?2 AND e.type=?3 ORDER BY e.date ASC");
			query.setParameter(1, new Date(date1));
			query.setParameter(2, new Date(date2));
			query.setParameter(3, type);
		}
		if (type != "" & date1 != null & date2 == null) {
			query = em.createQuery("SELECT e FROM Event e WHERE e.date=?1 AND e.type=?3 ORDER BY e.date ASC");
			query.setParameter(1, new Date(date1));
			query.setParameter(3, type);
		}
		if (type == "" & date1 != null & date2 != null) {
			query = em.createQuery("SELECT e FROM Event e WHERE e.date>=?1 AND e.date<=?2 ORDER BY e.date ASC");
			query.setParameter(1, new Date(date1));
			query.setParameter(2, new Date(date2));
		}
		if (type == "" & date1 != null & date2 == null) {
			query = em.createQuery("SELECT e FROM Event e WHERE e.date=?1 ORDER BY e.date ASC");
			query.setParameter(1, new Date(date1));
		}
		if (type != "" & date1 == null & date2 == null) {
			query = em.createQuery("SELECT e FROM Event e WHERE e.date>=?4 AND e.type=?3 ORDER BY e.date ASC");
			query.setParameter(3, type);
			query.setParameter(4, new Date());
		}
		return transformList(query.getResultList());
	}

	
}
