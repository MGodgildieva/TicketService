package telran.tickets.api.dto;

import java.text.SimpleDateFormat;

import telran.tickets.entities.objects.EventSeat;

public class ClientBookedTicket {
	String email;
	String eventTitle;
	String hallTitle;
	String date;
	String time;
	String row;
	String place;
	String price;
	String bookingTime;
	public ClientBookedTicket() {
	}
	public ClientBookedTicket(EventSeat eventSeat, String email) {
		this.email =  email;
		this.eventTitle = eventSeat.getEvent().getTitle();
		this.hallTitle = eventSeat.getEvent().getHall().getHallName();
		this.date = new SimpleDateFormat("dd/MM/yyyy").format(eventSeat.getEvent().getDate());
		this.time = eventSeat.getEvent().getTime();
		this.row = eventSeat.getSeat().getRealRow();
		this.place = eventSeat.getSeat().getRealPlace();
		this.price = eventSeat.getPrice();
		this.bookingTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(eventSeat.getBookingTime());
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getHallTitle() {
		return hallTitle;
	}
	public void setHallTitle(String hallTitle) {
		this.hallTitle = hallTitle;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	
}
