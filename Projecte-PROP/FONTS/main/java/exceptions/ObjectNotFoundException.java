package exceptions;
/**
 * Excepció genèrica que indica que no s'ha trobat un objecte amb el nom d'objecte especificat.
 * @author Dídac
 */
public class ObjectNotFoundException extends Exception {
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
	public ObjectNotFoundException(String message) {
		super(message);
	}

}