package telran.tickets.api.dto;

import java.util.Map;
import java.util.Set;

public class HallEventInfo {
	private String hallId;
	private String hallName;
	private String eventTitle;
	private String artist;
	private String date;
	private String ticketPriceRange;
	private Map<String, String[][]> categories;
	private Set<String> boughtTickets;

	public HallEventInfo(String hallId, String hallName, String eventTitle, String artist, String date,
			String ticketPriceRange, Map<String, String[][]> categories, Set<String> boughtTickets) {
		this.hallId = hallId;
		this.hallName = hallName;
		this.eventTitle = eventTitle;
		this.artist = artist;
		this.date = date;
		this.ticketPriceRange = ticketPriceRange;
		this.categories = categories;
		this.boughtTickets = boughtTickets;
	}

	public HallEventInfo() {
	}

	public String getHallId() {
		return hallId;
	}

	public String getHallName() {
		return hallName;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public String getArtist() {
		return artist;
	}

	public String getDate() {
		return date;
	}

	public String getTicketPriceRange() {
		return ticketPriceRange;
	}

	public Map<String, String[][]> getCategories() {
		return categories;
	}

	public Set<String> getBoughtTickets() {
		return boughtTickets;
	}

}
