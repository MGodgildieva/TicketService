package telran.tickets.interfaces;

import java.io.IOException;
import java.text.ParseException;

import telran.tickets.api.dto.EventClientRequest;
import telran.tickets.api.dto.FullEventInfo;
import telran.tickets.api.dto.HallEventInfo;
import telran.tickets.api.dto.LoginRequest;
import telran.tickets.api.dto.LoginResponse;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.TicketsRequest;
import telran.tickets.api.dto.TypeRequest;

public interface IGeneral {
	LoginResponse login(LoginRequest request);
	Iterable<String> getCities();
	Iterable<String> getTypes();
	Iterable<String> getHallsByCity(String city);
	Iterable<ShortEventInfo> getEventsByDate(String city);
	Iterable<ShortEventInfo> getEventsByPlace(String place);
	Iterable<ShortEventInfo> getEventsByType(TypeRequest typeRequest);
	Iterable<ShortEventInfo> getEventsOnDate(long date) throws ParseException;
	Iterable<ShortEventInfo> getEventsInDateInterval(long firstDate, long lastDate) throws ParseException;
	FullEventInfo getEvent(EventClientRequest eventClientRequest);
	HallEventInfo getFullHall(String eventId);
	SuccessResponse forgottenPassword(String email);
	boolean buyTicketsWithoutRegistration(TicketsRequest request) throws IOException;
	Iterable<ShortEventInfo> searchEvents(String text);
	Iterable<ShortEventInfo> filterEvents(String type, Long date1, Long date2);
	

}
