package exceptions;
/**
 * Excepció que indica que no s'ha trobat un teclat amb el nom de teclat especificat.
 */
public class TeclatNotFoundException extends ObjectNotFoundException {
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
	public TeclatNotFoundException(String message) {
		super(message);
	}

}