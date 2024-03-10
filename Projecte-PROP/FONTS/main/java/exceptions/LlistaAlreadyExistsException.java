package exceptions;

/**
 * Excepció que indica que ja existeix una llista amb el mateix nom de llista.
 * @author Yiqi
 */
public class LlistaAlreadyExistsException extends ObjectAlreadyExistsException{
	/**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
    public LlistaAlreadyExistsException(String message) {
		super(message);
	}
}
