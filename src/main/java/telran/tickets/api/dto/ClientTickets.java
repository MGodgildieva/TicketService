package telran.tickets.api.dto;

public class ClientTickets {
	Iterable<ClientTicket> tickets;

	public ClientTickets(Iterable<ClientTicket> tickets) {
		this.tickets = tickets;
	}

	public ClientTickets() {
	}

	public Iterable<ClientTicket> getTickets() {
		return tickets;
	}

	public void setTickets(Iterable<ClientTicket> tickets) {
		this.tickets = tickets;
	}
	

}
