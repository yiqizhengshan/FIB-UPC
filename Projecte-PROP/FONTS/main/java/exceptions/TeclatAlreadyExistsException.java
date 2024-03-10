package exceptions;
/**
 * Excepció que indica que ja existeix un teclat amb el mateix nom de teclat.
 */
public class TeclatAlreadyExistsException extends ObjectAlreadyExistsException {
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
	public TeclatAlreadyExistsException(String message) {
		super(message);
	}

}