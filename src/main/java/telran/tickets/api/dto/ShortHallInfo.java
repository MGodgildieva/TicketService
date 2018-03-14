package telran.tickets.api.dto;

import telran.tickets.entities.objects.Hall;

public class ShortHallInfo {
	private String hallId;
	private String hallName;
	private String city;
	private String street;
	private String house;
	private String description;
	public ShortHallInfo() {
	}
	public ShortHallInfo(Hall hall) {
		this.hallId = hall.getHallId().toString();
		this.hallName =  hall.getHallName();
		this.city =  hall.getCity();
		this.street = hall.getStreet();
		this.house =  hall.getHouse();
		this.description =  hall.getDescription();
	}
	public String getHallId() {
		return hallId;
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
	
	

}
