package telran.tickets.entities.objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import telran.tickets.api.dto.HallSeat;
@Entity
public class Seat {
	@Id
	@GeneratedValue
	private Integer id;
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	private Hall hall;
	private String row;
	private String place;
	private String realRow;
	private String realPlace;
	private String type;
	
	public Seat() {
	}
	
	public Seat(HallSeat seat, Hall hall) {
		this.row = seat.getRow();
		this.place = seat.getPlace();
		this.realRow = seat.getRealRow();
		this.realPlace = seat.getRealPlace();
		this.type = seat.getType();
		this.hall =  hall;

	}
	
	
	public Seat(Hall hall, String row, String place, String realRow, String realPlace, String type) {
		this.hall = hall;
		this.row = row;
		this.place = place;
		this.realRow = realRow;
		this.realPlace = realPlace;
		this.type = type;
	}



	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
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

	public Integer getId() {
		return id;
	}
	
	
	
	
}
