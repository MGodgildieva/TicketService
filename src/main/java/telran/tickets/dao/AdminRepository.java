package telran.tickets.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import telran.tickets.api.dto.AddOrganiser;
import telran.tickets.api.dto.BanRequest;
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
		return query.getResultList();
	}

	@Override
	@Transactional
	public boolean deleteOrganiser(String email) {
		if (em.find(Organiser.class, email)!= null) {
			em.remove(em.find(Organiser.class, email));
		}
		return true;
	}

	@Override
	@Transactional
	public boolean banOrganiser(BanRequest banRequest) {
		Organiser organiser = em.find(Organiser.class, banRequest.getEmail());
		if (organiser!= null) {
			organiser.setIsBanned(banRequest.isBanned());
			em.merge(organiser);
		}
		return true;
	}

}
