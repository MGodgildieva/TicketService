package telran.tickets.api.dto;

import telran.tickets.entities.objects.Seat;

public class HallSeat {
	private String place;
	private String row;
	private String realRow;
	private String realPlace;
	private String type;
	
	public HallSeat() {
	}
	public HallSeat(Seat seat) {
		this.place =  seat.getPlace();
		this.row = seat.getRow();
		this.realPlace = seat.getRealPlace();
		this.realRow = seat.getRealRow();
		this.type = seat.getType();
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
	
	

}
