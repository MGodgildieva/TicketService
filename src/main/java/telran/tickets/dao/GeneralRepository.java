package telran.tickets.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.property.AreaBreakType;

import telran.tickets.api.dto.BuyingRequestNoReg;
import telran.tickets.api.dto.BuyingTicketsRequestNoReg;
import telran.tickets.api.dto.EventClientRequest;
import telran.tickets.api.dto.FullEventInfo;
import telran.tickets.api.dto.HallEventInfo;
import telran.tickets.api.dto.LoginRequest;
import telran.tickets.api.dto.LoginResponse;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TypeRequest;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.EventSeat;
import telran.tickets.entities.objects.Hall;
import telran.tickets.entities.users.Client;
import telran.tickets.entities.users.Organiser;
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
		Query query = em.createQuery("SELECT h.hallId FROM Hall h WHERE h.city=?1");
		query.setParameter(1, city);
		return new HashSet<>(query.getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsByDate(String city) {
		Query query = em.createQuery("SELECT e FROM Event e WHERE e.city=?1 ORDER BY e.date ASC");
		query.setParameter(1, city);
		Set<Event> events = new HashSet<>(query.getResultList());
		Set<ShortEventInfo> eventInfos = new HashSet<>();
		for (Event event : events) {
			if(!event.getIsHidden() && !event.getIsDeleted()) {
				eventInfos.add(new ShortEventInfo(event));
			}
		}
		return eventInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsByPlace(String hallId) {
		Query query = em.createQuery("SELECT e FROM Event e WHERE hall_hall_id=?1 ORDER BY e.date ASC");
		query.setParameter(1, hallId);
		Set<Event> events = new HashSet<>(query.getResultList());
		Set<ShortEventInfo> eventInfos = new HashSet<>();
		for (Event event : events) {
			if(!event.getIsHidden() && !event.getIsDeleted()) {
				eventInfos.add(new ShortEventInfo(event));
			}
		}
		return eventInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsByType(TypeRequest typeRequest) {
		Query query = em.createQuery("SELECT e FROM Event e WHERE e.type=?1 AND e.city=?2 ORDER BY e.date ASC");
		query.setParameter(1, typeRequest.getType());
		query.setParameter(2, typeRequest.getCity());
		Set<Event> events = new HashSet<>(query.getResultList());
		Set<ShortEventInfo> eventInfos = new HashSet<>();
		for (Event event : events) {
			if(!event.getIsHidden() && !event.getIsDeleted()) {
				eventInfos.add(new ShortEventInfo(event));
			}
		}
		return eventInfos;
	}

	@Override
	public FullEventInfo getEvent(EventClientRequest eventClientRequest) {
		Client client = em.find(Client.class, eventClientRequest.getPhone());
		Event event = em.find(Event.class, Integer.parseInt(eventClientRequest.getEventId()));
		FullEventInfo response = new FullEventInfo(event);
		response.setPhone(eventClientRequest.getPhone());
		response.setFavourite(client.getFavourite().contains(event));
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
		Client possibleClient = em.find(Client.class, request.getId());
		Organiser possibleOrganiser = em.find(Organiser.class, request.getId());
		if (possibleClient == null && possibleOrganiser == null) {
			return new LoginResponse("Wrong email");
		}
		if (possibleClient != null) {
			if (possibleClient.getPassword().equals(request.getPassword())) {
				return new LoginResponse(request.getId(), possibleClient.getType());
			}
			else {
				return new LoginResponse("Wrong password");
			}
		}
		if (possibleOrganiser != null) {
			if (possibleOrganiser.getPassword().equals(request.getPassword()) 
					&& possibleOrganiser.getIsBanned() == false) {
				return new LoginResponse(request.getId(), possibleOrganiser.getType());
			}
			else {
				return new LoginResponse("Wrong password");
			}
		}
		return new LoginResponse("Database error");
	}
	@Override
	public SuccessResponse forgottenPassword(String id) {
		Client client = em.find(Client.class, id);
		Organiser org =  em.find(Organiser.class, id);
		if (client == null && org == null) {
			return new SuccessResponse(false, "There is no such user");
		}
		if (client!=null) {
			return new SuccessResponse(true,client.getPassword());
		}
		if (org!= null) {
			return new SuccessResponse(true,org.getPassword());
		}
		return new SuccessResponse(false, "Database error");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<String> getTypes() {
		Query query = em.createQuery("SELECT type FROM Event");
		return new HashSet<>(query.getResultList());
	}

	@Override
	@Transactional
	public boolean buyTicketWithoutRegistration(BuyingRequestNoReg request) throws IOException {
		EventSeat eventSeat = em.find(EventSeat.class, Integer.parseInt(request.getEventSeatId()));
		if (eventSeat.isTaken()) {
			return false;
		}
		eventSeat.setTaken(true);
		try {
			em.merge(eventSeat);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Event event = eventSeat.getEvent();
		Integer count = event.getBoughtTickets();
		event.setBoughtTickets(++count);
		PdfCreator creator =  new PdfCreator(eventSeat);
		try {
			em.merge(event);
			EmailSender sender = new EmailSender();
			sender.sendEmail(request.getEmail(), creator.createTicketWithoutSaving());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsOnDate(String date) throws ParseException {
		Query query = em.createQuery("SELECT e FROM Event e WHERE e.date=?1 ORDER BY e.date ASC");
		query.setParameter(1, new SimpleDateFormat("dd/MM/yyyy").parse(date));
		Set<Event> events = new HashSet<>(query.getResultList());
		Set<ShortEventInfo> eventInfos = new HashSet<>();
		for (Event event : events) {
			if(!event.getIsHidden() && !event.getIsDeleted()) {
				eventInfos.add(new ShortEventInfo(event));
			}
		}
		return eventInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsInDateInterval(String firstDate, String lastDate) throws ParseException {
		Query query = em.createQuery("SELECT e FROM Event e WHERE e.date>=?1 AND e.date<=?2 ORDER BY e.date ASC");
		query.setParameter(1, new SimpleDateFormat("dd/MM/yyyy").parse(firstDate));
		query.setParameter(2, new SimpleDateFormat("dd/MM/yyyy").parse(lastDate));
		Set<Event> events = new HashSet<>(query.getResultList());
		Set<ShortEventInfo> eventInfos = new HashSet<>();
		for (Event event : events) {
			if(!event.getIsHidden() && !event.getIsDeleted()) {
				eventInfos.add(new ShortEventInfo(event));
			}
		}
		return eventInfos;
	}

	@Override
	@Transactional
	public boolean buyTicketsWithoutRegistration(BuyingTicketsRequestNoReg request) throws IOException {
		List<String> eventSeatIds = new ArrayList<>(Arrays.asList(request.getEventSeatIds()));
		Event event = em.find(Event.class, Integer.parseInt(request.getEventId()));
		Integer count = event.getBoughtTickets();
		PdfCreator creator = new PdfCreator();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
		Document document = new Document(pdfDoc);
		document.setMargins(50, 50, 50, 50);
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
		document.setFont(font);
		document.setFontSize(18);
		for (String id : eventSeatIds) {
			EventSeat eventSeat = em.find(EventSeat.class, Integer.parseInt(id));
			if (eventSeat.isTaken()) {
				return false;
			}
			eventSeat.setTaken(true);
			eventSeat.setBookingTime(null);
			try {
				em.merge(eventSeat);
				creator.createPage(eventSeat, document);
				if (pdfDoc.getNumberOfPages() != eventSeatIds.size()) {
					document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				}
			} catch (Exception e1) {
				return false;
			}
			count++;
		}
		document.close();
		byte [] pdfToBytes = baos.toByteArray();
		baos.close();
		event.setBoughtTickets(count);
		try {
			em.merge(event);
			EmailSender sender = new EmailSender();
			sender.sendEmail(request.getEmail(), pdfToBytes);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
}
