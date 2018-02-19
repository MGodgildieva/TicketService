package telran.tickets.dao;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import telran.tickets.api.dto.FavouriteRequest;
import telran.tickets.api.dto.RegisterClient;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.TicketRequest;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.users.Client;
import telran.tickets.interfaces.IClient;
@Repository
public class ClientRepository implements IClient {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public String register(RegisterClient client) {
		Client newClient = new Client(client);
		try {
			em.persist(newClient);
		} catch (Exception e) {
			return "Error";
		}
		return newClient.getClientId();
	}

	@Override
	@Transactional
	public boolean buyTicket(TicketRequest ticket) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public boolean addToFavourite(FavouriteRequest favRequest) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<ShortEventInfo> getFavourite(String clientId) {
		Client client =  em.find(Client.class, clientId);
		Set<Event> events = client.getFavourite();
		//TODO
		return null;
	}

	@Override
	public Client getProfile(String clientId) {
		return em.find(Client.class, clientId);
	}

	@Override
	@Transactional
	public Client changeProfile(Client clientWithNewInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forgottenPassword(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
