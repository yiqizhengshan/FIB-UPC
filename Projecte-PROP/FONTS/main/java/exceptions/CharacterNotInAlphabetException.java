package exceptions;
/**
 * Excepció que indica que una llista o un text té un caràcter no present a l'alfabet.
 * @author Yiqi
 */
public class CharacterNotInAlphabetException extends Exception{
    /**
     * Constructora que permet crear una instància de la excepció amb un missatge específic.
     *
     * @param message Missatge que descriu la causa de la excepció.
     */
    public CharacterNotInAlphabetException(String message) {
        super(message);
    }
}
