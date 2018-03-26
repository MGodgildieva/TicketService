package telran.tickets.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import telran.tickets.api.dto.EventClientRequest;
import telran.tickets.api.dto.FullEventInfo;
import telran.tickets.api.dto.HallEventInfo;
import telran.tickets.api.dto.LoginRequest;
import telran.tickets.api.dto.LoginResponse;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TypeRequest;
import telran.tickets.entities.objects.Event;
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

	
}
