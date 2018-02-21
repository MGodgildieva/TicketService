package telran.tickets.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import telran.tickets.api.dto.ClientProfile;
import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.TicketRequest;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.Ticket;
import telran.tickets.entities.users.Client;
import telran.tickets.entities.users.Organiser;
import telran.tickets.interfaces.IClient;

@Repository
public class ClientRepository implements IClient {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public String register(RegisterClient client) {
		Client newClient = new Client(client);
		Client possibleClient = em.find(Client.class, client.getEmail());
		Organiser possibleOrganiser = em.find(Organiser.class, client.getEmail());
		if (possibleClient == null && possibleOrganiser == null) {
			try {
				em.persist(newClient);
			} catch (Exception e) {
				return null; //"There is already a user with this email"
			}
		} else {
			return null; //"There is already a user with this email"
		}
		return newClient.getEmail();
	}

	@Override
	@Transactional
	public boolean buyTicket(TicketRequest request) {
		Ticket ticket = new Ticket(request);
		Client client = em.find(Client.class, request.getEmail());
		Set<Ticket> clientTickets = client.getBoughtTickets();
		clientTickets.add(ticket);
		client.setBoughtTickets(clientTickets);
		em.merge(client);
		Event event = em.find(Event.class, request.getEventId());
		Set<Ticket> eventTickets = event.getBoughtTickets();
		eventTickets.add(ticket);
		event.setBoughtTickets(eventTickets);
		Map<Integer, boolean[][]> eventCategories = event.getCategories();
		boolean[][] seats = event.getCategories().get(Integer.parseInt(ticket.getPrice()));
		seats[Integer.parseInt(ticket.getRow())][Integer.parseInt(ticket.getPlace())] = false;
		eventCategories.put(Integer.parseInt(ticket.getPrice()), seats);
		event.setCategories(eventCategories);
		em.merge(event);
		return true;
	}

	@Override
	@Transactional
	public boolean addToFavourite(FavouriteRequest favRequest) {
		Client client = em.find(Client.class, favRequest.getEmail());
		Set<Event> faves = client.getFavourite();
		if (favRequest.isFavourite()) {
			Event event = em.find(Event.class, favRequest.getEventId());
			faves.add(event);
			client.setFavourite(faves);
		} else {
			Iterator<Event> iter = faves.iterator();
			while (iter.hasNext()) {
				if (iter.next().getEventId().equals(favRequest.getEventId())) {
					faves.remove(iter.next());
				}
			}
		}
		return true;
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
	public ClientProfile getProfile(String clientId) {
		Client client = em.find(Client.class, clientId);
		ClientProfile profile = new ClientProfile(client);
		return profile;
	}

	@Override
	@Transactional
	public ClientProfile changeProfile(ClientProfile clientWithNewInfo) {
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
		if (client.getPhone() != clientWithNewInfo.getPhone()) {
			client.setPhone(clientWithNewInfo.getPhone());
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
		if (client.getType() != clientWithNewInfo.getType()) {
			client.setType(clientWithNewInfo.getType());
		}
		em.merge(client);
		return new ClientProfile(client);
	}

	@Override
	public String forgottenPassword(String email) {
		Client client = em.find(Client.class, email);
		return client.getPassword();
	}

}
