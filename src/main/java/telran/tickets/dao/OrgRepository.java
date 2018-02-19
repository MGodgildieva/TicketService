package telran.tickets.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import telran.tickets.api.dto.EditEvent;
import telran.tickets.api.dto.EventOrgRequest;
import telran.tickets.api.dto.HallRequest;
import telran.tickets.api.dto.OrgHallRequest;
import telran.tickets.api.dto.OrgTypeRequest;
import telran.tickets.api.dto.RegisterOrganiser;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.VisibleRequest;
import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.Hall;
import telran.tickets.interfaces.IOrganiser;
@Repository
public class OrgRepository implements IOrganiser {
	@PersistenceContext
	EntityManager em;
	@Override
	@Transactional
	public String register(RegisterOrganiser organiser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ShortEventInfo> getEventsByDate(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ShortEventInfo> getEventsByHall(OrgHallRequest orgHallRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ShortEventInfo> getEventsByType(OrgTypeRequest orgTypeRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public EditEvent editEvent(EventOrgRequest eventOrgRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public boolean addEvent(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public boolean hideEvent(VisibleRequest visibleRequest) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public boolean deleteEvent(String eventId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RegisterOrganiser getProfile(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Hall> getHalls(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public boolean addHall(HallRequest hallRequest) {
		// TODO Auto-generated method stub
		return false;
	}

}
