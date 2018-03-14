package telran.tickets.api.dto;

public class EventScheme {
	private String width;
	private String height;
	private HallEventSeat[] hallSeats;
	public EventScheme() {
	}
	public EventScheme(String width, String height, HallEventSeat[] seats) {
		this.width = width;
		this.height = height;
		this.hallSeats = seats;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public HallEventSeat[] getSeats() {
		return hallSeats;
	}
	public void setSeats(HallEventSeat[] seats) {
		this.hallSeats = seats;
	}
	
	

}
