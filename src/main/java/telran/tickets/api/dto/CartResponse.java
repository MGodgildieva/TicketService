package telran.tickets.api.dto;

import java.util.HashSet;

import telran.tickets.entities.objects.Ticket;

public class CartResponse {
	private HashSet<Ticket> tickets;

	public CartResponse(HashSet<Ticket> tickets) {
		this.tickets = tickets;
	}

	public CartResponse() {
	}

	public HashSet<Ticket> getTickets() {
		return tickets;
	}
	
	
}
