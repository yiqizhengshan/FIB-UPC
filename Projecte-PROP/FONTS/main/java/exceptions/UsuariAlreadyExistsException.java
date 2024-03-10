package exceptions;

/**
 * Excepció que indica que ja existeix un usuari amb el mateix nom d'usuari.
 * 
 * @author Jordi
 */
public class UsuariAlreadyExistsException extends ObjectAlreadyExistsException {

    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
    public UsuariAlreadyExistsException(String message) {
        super(message);
    }
}
