package exceptions;
/**
 * Excepció que indica que ja existeix un alfabet amb el mateix nom d'alfabet.
 * @author Dídac
 */
public class AlfabetAlreadyExistsException extends ObjectAlreadyExistsException {
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
	public AlfabetAlreadyExistsException(String message) {
		super(message);
	}

}