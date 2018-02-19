package telran.tickets.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import telran.tickets.api.dto.EventClientRequest;
import telran.tickets.api.dto.FullEventInfo;
import telran.tickets.api.dto.HallEventInfo;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.TypeRequest;
import telran.tickets.interfaces.IGeneral;
@Repository
public class GeneralRepository implements IGeneral{
	@PersistenceContext
	EntityManager em;

	@Override
	public Iterable<String> getCities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<String> getHallsByCity(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ShortEventInfo> getEventsByDate(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ShortEventInfo> getEventsByPlace(String place) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ShortEventInfo> getEventsByType(TypeRequest typeRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FullEventInfo getEvent(EventClientRequest eventClientRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HallEventInfo getFullHall(String eventId) {
		// TODO Auto-generated method stub
		return null;
	}

}
