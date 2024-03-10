package exceptions;
/**
 * Excepció que indica que la matriu de parella caràcters no té un tamany vàlid.
 */
public class MatriuParellaCaractersNotValidException extends Exception {
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
    public MatriuParellaCaractersNotValidException(String message) {
		super(message);
	}
}
