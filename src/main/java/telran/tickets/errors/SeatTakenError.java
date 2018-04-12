package telran.tickets.errors;

public class SeatTakenError extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	public SeatTakenError(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
}
