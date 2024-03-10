package exceptions;

/**
 * Excepció que indica que no s'ha trobat un usuari amb el nom d'usuari especificat.
 * 
 * @author Jordi
 */
public class UsuariNotFoundException extends ObjectNotFoundException {

    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
    public UsuariNotFoundException(String message) {
        super(message);
    }
}
