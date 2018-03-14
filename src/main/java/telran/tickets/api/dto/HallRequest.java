package telran.tickets.api.dto;

public class HallRequest {
	private String email;
	private String hallName;
	private String city;
	private String street;
	private String house;
	private String description;
	private String width;
	private String height;
	private HallSeat[] hallSeats;
	public HallRequest() {
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public HallSeat[] getSeats() {
		return hallSeats;
	}
	public void setSeats(HallSeat[] seats) {
		this.hallSeats = seats;
	}
	public String getHallName() {
		return hallName;
	}
	public String getCity() {
		return city;
	}
	public String getStreet() {
		return street;
	}
	public String getHouse() {
		return house;
	}
	public String getDescription() {
		return description;
	}
	public String getWidth() {
		return width;
	}
	public String getHeight() {
		return height;
	}
	public HallSeat[] getHallSeats() {
		return hallSeats;
	}
	
	
	
	
}
