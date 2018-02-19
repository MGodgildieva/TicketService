package telran.tickets.entities.objects;

import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
@Entity
public class Event {
	@Id
	String eventId;
	String org;
	String artist;
	String title;
	String date;
	String time;
	String type;
	String description;
	String imageUrl;
	String hall;
	Boolean isHidden;
	Boolean isDeleted;
	@ElementCollection
	@CollectionTable(name = "seats_categories")
	@MapKeyColumn(name = "price")
	@Column(name = "seats")
	Map<Integer, boolean[][]> categories; //bought seats - deleted
	@OneToMany
	Set<Ticket> boughtTickets; 
}
