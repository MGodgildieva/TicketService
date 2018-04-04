package telran.tickets.errors;

public class DatabaseError extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	public DatabaseError(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
	

}
