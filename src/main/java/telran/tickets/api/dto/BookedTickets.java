package telran.tickets.api.dto;

public class BookedTickets {
	Iterable<ClientBookedTicket> tickets;

	public BookedTickets(Iterable<ClientBookedTicket> tickets) {
		this.tickets = tickets;
	}

	public BookedTickets() {
	}

	public Iterable<ClientBookedTicket> getTickets() {
		return tickets;
	}

	public void setTickets(Iterable<ClientBookedTicket> tickets) {
		this.tickets = tickets;
	}
	
	

}
