package telran.tickets.api.dto;

import java.util.ArrayList;
import java.util.List;

import telran.tickets.entities.objects.Hall;
import telran.tickets.entities.objects.Seat;

public class HallScheme {
	private String width;
	private String height;
	private HallSeat[] hallSeats;
	
	
	public HallScheme() {
	}
	public HallScheme(Hall hall) {
		this.width =  hall.getWidth();
		this.height =  hall.getHeight();
		List<HallSeat> seatsList =  new ArrayList<>();
		for (Seat seat : hall.getSeats()) {
			seatsList.add(new HallSeat(seat));
		}
		this.hallSeats = seatsList.toArray(new HallSeat[seatsList.size()]);
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
	public HallSeat[] getHallSeats() {
		return hallSeats;
	}
	public void setHallSeats(HallSeat[] hallSeats) {
		this.hallSeats = hallSeats;
	}
	
	

}
