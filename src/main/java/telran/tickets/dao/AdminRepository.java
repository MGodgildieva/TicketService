package telran.tickets.dao;

import java.util.HashSet;
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

import telran.tickets.api.dto.AddOrganiser;
import telran.tickets.api.dto.BanRequest;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.EventSeat;
import telran.tickets.entities.objects.License;
import telran.tickets.entities.users.Client;
import telran.tickets.entities.users.Organiser;
import telran.tickets.interfaces.IAdmin;

@Repository
public class AdminRepository implements IAdmin {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public boolean addOrganiser(AddOrganiser organiser) {
		Organiser newOrganiser = new Organiser(organiser);
		if (!em.contains(newOrganiser)) {
			try {
				em.persist(newOrganiser);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<AddOrganiser> getOrganisers() {
		Query query = em.createQuery("SELECT s FROM Organiser s");
		if (query.getResultList() != null) {
			return query.getResultList();
		} else {
			return new HashSet<>();
		}
	}

	@Override
	@Transactional
	public boolean deleteOrganiser(String email) {
		if (em.find(Organiser.class, email) != null) {
			em.remove(em.find(Organiser.class, email));
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean banOrganiser(BanRequest banRequest) {
		Organiser organiser = em.find(Organiser.class, banRequest.getEmail());
		if (organiser != null) {
			organiser.setIsBanned(banRequest.getIsBanned());
			em.merge(organiser);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean addLicense(String email) {
		String code = RandomStringUtils.randomAlphanumeric(10);
		License license = new License(code, email);
		if (!em.contains(license)) {
			try {
				em.persist(license);
				EmailSender sender =  new EmailSender(email);
				sender.sendEmailWithText("Your license: " + code);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0 12 * * *")
	@Override
	@Transactional
	@Modifying
	public boolean cleanDatabase() {
		Query query1 = em.createQuery("SELECT e FROM Event e WHERE TIMESTAMPDIFF(DAY, e.date, CURDATE())>30");
		List<Event> events =  query1.getResultList();
		if(!events.isEmpty()) {
			Set<Integer> ids = new HashSet<>();
			for (Event event : events) {
				ids.add(event.getEventId());
			}
			Query query2 = em.createQuery("DELETE FROM EventSeat e WHERE event_event_id IN ?1");
			query2.setParameter(1, ids);
			Query query = em.createQuery("DELETE FROM Event e WHERE TIMESTAMPDIFF(DAY, e.date, CURDATE())>30");
			try {
				query.executeUpdate();
				query2.executeUpdate();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}else {
			return false;
		}
		
	}
	
	@Transactional
	@Override
	public boolean deleteTicket(String ticketId) {
		EventSeat seat = em.find(EventSeat.class, Integer.parseInt(ticketId));
		Client client = seat.getBuyer();
		seat.setTaken(false);
		seat.setBuyer(null);
		seat.setBuyingTime(null);
		Set<EventSeat> boughtTickets = client.getBoughtTickets();
		boughtTickets.remove(seat);
		client.setBoughtTickets(boughtTickets);
		try {
			em.merge(seat);
			em.merge(client);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
