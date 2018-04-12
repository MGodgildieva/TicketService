package telran.tickets.api.dto;

import telran.tickets.entities.objects.EventSeat;

public class HallEventSeat {
	private Integer id;
	private String place;
	private String row;
	private Integer price;
	private Integer colour;
	private String realRow;
	private String realPlace;
	private String type;
	private boolean isTaken;
	
	public HallEventSeat() {
	}
	public HallEventSeat(EventSeat seat) {
		this.id = seat.getId();
		this.colour = seat.getColour();
		this.place =  seat.getSeat().getPlace();
		this.row = seat.getSeat().getRow();
		this.price = Integer.parseInt(seat.getPrice());
		this.realPlace = seat.getSeat().getRealPlace();
		this.realRow = seat.getSeat().getRealRow();
		this.type = seat.getSeat().getType();
		this.isTaken = seat.getIsTaken();
	}

	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getRealRow() {
		return realRow;
	}
	public void setRealRow(String realRow) {
		this.realRow = realRow;
	}
	public String getRealPlace() {
		return realPlace;
	}
	public void setRealPlace(String realPlace) {
		this.realPlace = realPlace;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean getIsTaken() {
		return isTaken;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getColour() {
		return colour;
	}
	public void setColour(Integer colour) {
		this.colour = colour;
	}
	
	
	
}
