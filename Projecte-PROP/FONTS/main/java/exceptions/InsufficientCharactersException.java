package exceptions;
/**
 * Excepció que indica que la llista de caràcters d'un alfabet no té suficients caràcters.
 * @author Dídac
 */
public class InsufficientCharactersException extends Exception {
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
	public InsufficientCharactersException(String message) {
		super(message);
	}
}