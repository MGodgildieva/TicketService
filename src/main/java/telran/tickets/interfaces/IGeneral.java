package telran.tickets.interfaces;

import telran.tickets.api.dto.EventClientRequest;
import telran.tickets.api.dto.FullEventInfo;
import telran.tickets.api.dto.HallEventInfo;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.TypeRequest;

public interface IGeneral {
	Iterable<String> getCities();
	String getInfo();
	Iterable<String> getHallsByCity(String city);
	Iterable<ShortEventInfo> getEventsByDate(String city);
	Iterable<ShortEventInfo> getEventsByPlace(String place);
	Iterable<ShortEventInfo> getEventsByType(TypeRequest typeRequest);
	FullEventInfo getEvent(EventClientRequest eventClientRequest);
	HallEventInfo getFullHall(String eventId);

}
