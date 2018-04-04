package telran.tickets.errors;

public class EmailError extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	public EmailError(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	

}
