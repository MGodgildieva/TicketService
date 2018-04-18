package telran.tickets.api.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import telran.tickets.entities.objects.Event;
import telran.tickets.entities.objects.EventSeat;
import telran.tickets.entities.objects.Hall;

public class HallEventInfo {
	private String hallId;
	private String hallName;
	private String eventTitle;
	private String artist;
	private String date;
	private String ticketPriceRange;
	private EventScheme hallScheme;

	public HallEventInfo(String hallId, String hallName, String eventTitle, String artist, String date,
			String ticketPriceRange, EventScheme hallScheme) {
		this.hallId = hallId;
		this.hallName = hallName;
		this.eventTitle = eventTitle;
		this.artist = artist;
		this.date = date;
		this.ticketPriceRange = ticketPriceRange;
		this.hallScheme = hallScheme;
	}
	
	public HallEventInfo(Event event, Hall hall) {
		this.hallId =  hall.getHallId().toString();
		this.hallName = hall.getHallName();
		this.eventTitle = event.getTitle();
		this.artist = event.getArtist();
		this.date = new SimpleDateFormat("dd MMMM yyyy").format(event.getDate());
		this.ticketPriceRange = event.getPriceRange();
		List<HallEventSeat> seats = new ArrayList<>();
		for (EventSeat eventSeat : event.getSeats()) {
			seats.add(new HallEventSeat(eventSeat));
		}
		Collections.sort(seats, new Comparator<HallEventSeat>() {

			@Override
			public int compare(HallEventSeat o1, HallEventSeat o2) {
				int sComp = Integer.parseInt(o1.getRow()) - Integer.parseInt(o2.getRow());
	            if (sComp != 0) {
	               return sComp;
	            } 
				return Integer.parseInt(o1.getPlace()) - Integer.parseInt(o2.getPlace());
			}
		});
		this.hallScheme = new EventScheme(hall.getWidth(), hall.getHeight(), seats.toArray(new HallEventSeat[seats.size()]));
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

	public EventScheme getHallScheme() {
		return hallScheme;
	}

	

}
