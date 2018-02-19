package telran.tickets.entities.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Hall {
	@Id
	String hallId;
	String hallName;
	String city;
	String street;
	String house;
	String description;
	boolean [][] seats; // есть место, нет места
}
