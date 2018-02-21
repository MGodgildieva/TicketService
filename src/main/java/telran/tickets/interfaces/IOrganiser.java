package telran.tickets.interfaces;

import telran.tickets.api.dto.AddEvent;
import telran.tickets.api.dto.EditEvent;
import telran.tickets.api.dto.EventOrgRequest;
import telran.tickets.api.dto.HallRequest;
import telran.tickets.api.dto.OrgHallRequest;
import telran.tickets.api.dto.OrgTypeRequest;
import telran.tickets.api.dto.RegisterOrganiser;
import telran.tickets.api.dto.ShortEventInfo;
import telran.tickets.api.dto.VisibleRequest;
import telran.tickets.entities.objects.Hall;

public interface IOrganiser {
	String register (RegisterOrganiser organiser);
	Iterable<ShortEventInfo> getEventsByDate(String email);
	Iterable<ShortEventInfo> getEventsByHall(OrgHallRequest orgHallRequest);
	Iterable<ShortEventInfo> getEventsByType(OrgTypeRequest orgTypeRequest);
	EditEvent editEvent(EventOrgRequest eventOrgRequest);
	boolean addEvent(AddEvent event);
	boolean hideEvent(VisibleRequest visibleRequest);
	boolean deleteEvent(String eventId);
	RegisterOrganiser getProfile(String email);
	Iterable<Hall> getHalls(String email);
	boolean addHall(HallRequest hallRequest);
}
