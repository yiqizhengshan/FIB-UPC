package exceptions;
/**
 * Excepció genèrica que indica que ja existeix un objecte amb el mateix nom d'objecte.
 * @author Dídac
 */
public class ObjectAlreadyExistsException extends Exception {
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
	public ObjectAlreadyExistsException(String message) {
		super(message);
	}

}