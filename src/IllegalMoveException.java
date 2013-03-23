/**
 * Thrown when an illegal move occurs.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.20032013
 *
 */
public class IllegalMoveException extends Exception {

	/**
	 * Removes warnings!
	 */
	private static final long serialVersionUID = 6221847028052337813L;

	public IllegalMoveException() {
		super();
	}
	
	public IllegalMoveException(String message) {
		super(message);
	}

}
