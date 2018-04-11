package telran.tickets.interfaces;

import java.util.Date;

import telran.tickets.api.dto.AddOrganiser;
import telran.tickets.api.dto.BanRequest;

public interface IAdmin {
	boolean addOrganiser(AddOrganiser organiser);
	Iterable<AddOrganiser> getOrganisers();
	boolean deleteOrganiser(String email);
	boolean banOrganiser(BanRequest banRequest);
	boolean addLicense(String email);
	//boolean cleanDatabase();
	boolean deleteTicket(Long ticketId);
	public boolean falseHall(Integer width, Integer height);
	public boolean falseEvent(String artist, String title, String city, Date date, String time, String type,
			String description, String imageUrl, Integer hallId,
			 Integer allTickets, String priceRange) throws Exception;
}
