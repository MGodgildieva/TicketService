package telran.tickets.api.dto;

public class BoughtTickets {
	Iterable<ClientTicket> tickets;

	public BoughtTickets(Iterable<ClientTicket> tickets) {
		this.tickets = tickets;
	}

	public BoughtTickets() {
	}

	public Iterable<ClientTicket> getTickets() {
		return tickets;
	}

	public void setTickets(Iterable<ClientTicket> tickets) {
		this.tickets = tickets;
	}
	

}
