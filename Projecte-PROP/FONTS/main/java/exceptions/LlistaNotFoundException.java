package exceptions;
/**
 * Excepció que indica que no s'ha trobat una llista amb el nom de llista especificat.
 * @author Yiqi
 */
public class LlistaNotFoundException extends ObjectNotFoundException{
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
	public LlistaNotFoundException(String message) {
		super(message);
	}
}
