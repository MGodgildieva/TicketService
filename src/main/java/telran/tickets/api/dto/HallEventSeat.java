package telran.tickets.api.dto;

import telran.tickets.entities.objects.EventSeat;

public class HallEventSeat {
	private String place;
	private String row;
	private String price;
	private String realRow;
	private String realPlace;
	private String type;
	private boolean isAvailable;
	
	public HallEventSeat() {
	}
	public HallEventSeat(EventSeat seat) {
		this.place =  seat.getSeat().getPlace();
		this.row = seat.getSeat().getRow();
		this.price = seat.getPrice();
		this.realPlace = seat.getSeat().getRealPlace();
		this.realRow = seat.getSeat().getRealRow();
		this.type = seat.getSeat().getType();
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
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
	public boolean isAvailable() {
		return isAvailable;
	}
	
}
