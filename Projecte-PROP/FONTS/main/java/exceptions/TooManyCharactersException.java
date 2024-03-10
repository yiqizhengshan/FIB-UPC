package exceptions;
/**
 * Excepció que indica que la llista de caràcters d'un alfabet té masses caràcters.
 * @author Dídac
 */
public class TooManyCharactersException extends Exception {
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
	public TooManyCharactersException(String message) {
		super(message);
	}
}