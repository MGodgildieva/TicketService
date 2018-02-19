package telran.tickets.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import telran.tickets.api.dto.AddOrganiser;
import telran.tickets.api.dto.BanRequest;
import telran.tickets.interfaces.IAdmin;
@Repository
public class AdminRepository implements IAdmin {
	@PersistenceContext
	EntityManager em;
	@Override
	@Transactional
	public boolean addOrganiser(AddOrganiser organiser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<AddOrganiser> getOrganisers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public boolean deleteOrganiser(String orgId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public boolean banOrganiser(BanRequest banRequest) {
		// TODO Auto-generated method stub
		return false;
	}

}
