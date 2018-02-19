package telran.tickets.api.dto;

import java.util.HashSet;

public class DeleteTicketRequest {
	private String email;
	private HashSet<String> ticketsId;
	public DeleteTicketRequest(String email, HashSet<String> ticketsId) {
		this.email = email;
		this.ticketsId = ticketsId;
	}
	public DeleteTicketRequest() {
	}
	public String getEmail() {
		return email;
	}
	public HashSet<String> getTicketsId() {
		return ticketsId;
	}
	
	

}
