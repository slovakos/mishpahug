package telran.ashkelon2018.mishpahug.exceptions;

public class RevoteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int code ;
	String message;
	public RevoteException() {
		this.code = 409;
		this.message="User has already voted for the event or can't vote for the event!";
		
	}
}
