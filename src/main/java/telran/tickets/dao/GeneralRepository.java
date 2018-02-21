package telran.tickets.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ch.qos.logback.core.pattern.PostCompileProcessor;
import telran.tickets.api.dto.EventClientRequest;
import telran.tickets.api.dto.FullEventInfo;
import telran.tickets.api.dto.HallEventInfo;
import telran.tickets.api.dto.LoginRequest;
import telran.tickets.api.dto.LoginResponse;
import telran.tickets.api.dto.ShortEventInfo;
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
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<String> getHallsByCity(String city) {
		Query query = em.createQuery("SELECT h FROM Hall h WHERE h.city=?1");
		query.setParameter(1, city);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsByDate(String city) {
		Query query = em.createQuery("SELECT e FROM Event e WHERE e.city=?1 ORDER BY e.date ASC");
		query.setParameter(1, city);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsByPlace(String hallId) {
		Query query = em.createQuery("SELECT e FROM Event e WHERE e.hallId=?1 ORDER BY e.date ASC");
		query.setParameter(1, hallId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ShortEventInfo> getEventsByType(TypeRequest typeRequest) {
		Query query = em.createQuery("SELECT e FROM Event e WHERE e.type=?1 AND e.city?=2 ORDER BY e.date ASC");
		query.setParameter(1, typeRequest.getType());
		query.setParameter(2, typeRequest.getCity());
		return query.getResultList();
	}

	@Override
	public FullEventInfo getEvent(EventClientRequest eventClientRequest) {
		Client client = em.find(Client.class, eventClientRequest.getEmail());
		Event event = em.find(Event.class, eventClientRequest.getEventId());
		FullEventInfo response = new FullEventInfo(event);
		response.setFavourite(client.getFavourite().contains(event));
		Integer availableTickets = event.getAllTickets() - (Integer) event.getBoughtTickets().size();
		response.setTicketCount(availableTickets.toString());
		List<Integer> prices = new ArrayList<>(event.getCategories().keySet());
		prices.sort(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		response.setPriceRange(prices.get(0).toString() + " - " + prices.get(prices.size()).toString());
		return response;
	}

	@Override
	public HallEventInfo getFullHall(String eventId) {
		Event event = em.find(Event.class, eventId);
		Hall hall = em.find(Hall.class, event.getHallId());
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		Client possibleClient = em.find(Client.class, request.getEmail());
		Organiser possibleOrganiser = em.find(Organiser.class, request.getEmail());
		LoginResponse response = new LoginResponse();
		if (possibleClient == null && possibleOrganiser == null) {
			return null; //error - wrong email
		}
		if (possibleClient != null) {
			if (possibleClient.getPassword().equals(request.getPassword())) {
				response.setEmail(request.getEmail());
				response.setType(possibleClient.getType());
			}
			else {
				return null; //error - wrong password
			}
		}
		if (possibleOrganiser != null) {
			if (possibleOrganiser.getPassword().equals(request.getPassword()) 
					&& possibleOrganiser.getIsBanned() == false) {
				response.setEmail(request.getEmail());
				response.setType(possibleOrganiser.getType());
			}
			else {
				return null; //error - wrong password
			}
		}
		return response;
	}

}
