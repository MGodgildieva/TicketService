package telran.tickets.dao;

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

import telran.tickets.api.dto.ClientProfile;
import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ReservationRequest;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketRequest;
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
		if (eventSeat.isTaken()) {
			return false;
		}
		Set<EventSeat> clientTickets = client.getBoughtTickets();
		clientTickets.add(eventSeat);
		client.setBoughtTickets(clientTickets);
		em.merge(client);
		eventSeat.setTaken(true);
		eventSeat.setBuyer(client);
		em.merge(eventSeat);
		Event event = em.find(Event.class, Integer.parseInt(request.getEventId()));
		Integer count = event.getBoughtTickets();
		event.setBoughtTickets(++count);
		em.merge(event);
		return true;
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
	@Scheduled (cron = "*/60 * * * * *")
	public void checkSeat() {
		Query query = em.createQuery("SELECT e FROM EventSeat e WHERE e.bookingTime IS NOT NULL AND TIMESTAMPDIFF(MINUTE, e.bookingTime, CURTIME())>=10");
		List<EventSeat> bookedSeats = query.getResultList();
		for (EventSeat eventSeat : bookedSeats) {
			eventSeat.setTaken(false);
			eventSeat.setBookingTime(null);
			em.merge(eventSeat);
		}
	}
}
	
