package telran.tickets.errors;

public class RegistrationError extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public RegistrationError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	

}
