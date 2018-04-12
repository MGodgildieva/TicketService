package telran.tickets.api.dto;

public class TicketId {
	Boolean success;
	Long ticketId;
	String message;
	public TicketId(Long ticketId) {
		this.success = true;
		this.ticketId = ticketId;
	}
	public TicketId(String message) {
		this.success = false;
		this.message = message;
	}
	public TicketId() {
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
