/**
 * Thrown when an illegal move occurs.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.15032012
 *
 */
public class IllegalMoveException extends Exception {
	
	public IllegalMoveException() {
		super();
	}
	
	public IllegalMoveException(String message) {
		super(message);
	}
	
	public IllegalMoveException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public IllegalMoveException(Throwable cause) {
		super(cause);
	}

}
