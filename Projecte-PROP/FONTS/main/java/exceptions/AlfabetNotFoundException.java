package exceptions;
/**
 * Excepció que indica que no s'ha trobat un alfabet amb el nom d'alfabet especificat.
 * @author Dídac
 */
public class AlfabetNotFoundException extends ObjectNotFoundException{
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
	public AlfabetNotFoundException(String message) {
		super(message);
	}

}