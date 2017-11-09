package exceptions;

public class PhotoNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhotoNotFoundException (String message) {
		super(message);
	}
}
