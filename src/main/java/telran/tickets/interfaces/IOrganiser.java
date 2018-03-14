package telran.tickets.interfaces;

import java.text.ParseException;

import telran.tickets.api.dto.AddEvent;
import telran.tickets.api.dto.EditEvent;
import telran.tickets.api.dto.EventOrgRequest;
import telran.tickets.api.dto.HallRequest;
import telran.tickets.api.dto.HallScheme;
import telran.tickets.api.dto.OrgHallRequest;
import telran.tickets.api.dto.OrgTypeRequest;
import telran.tickets.api.dto.RegisterOrganiser;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.ShortHallInfo;
import telran.tickets.api.dto.SuccessResponse;
import telran.tickets.api.dto.VisibleRequest;

public interface IOrganiser {
	SuccessResponse register (RegisterOrganiser organiser);
	Iterable<ShortEventInfo> getEventsByDate(String email);
	Iterable<ShortEventInfo> getEventsByHall(OrgHallRequest orgHallRequest);
	Iterable<ShortEventInfo> getEventsByType(OrgTypeRequest orgTypeRequest);
	EditEvent editEvent(EventOrgRequest eventOrgRequest);
	boolean addEvent(AddEvent event) throws ParseException, Exception;
	boolean hideEvent(VisibleRequest visibleRequest);
	boolean deleteEvent(String eventId);
	RegisterOrganiser getProfile(String email);
	Iterable<ShortHallInfo> getHalls(String email);
	String addHall(HallRequest hallRequest);
	HallScheme getHallScheme(String hallId);
}
