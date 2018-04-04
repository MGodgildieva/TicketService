package telran.tickets.errors;

public class JsonError extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	public JsonError(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	

}
