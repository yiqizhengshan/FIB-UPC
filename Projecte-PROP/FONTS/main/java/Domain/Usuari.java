package Domain;

/**
 * Classe que representa un usuari amb informació bàsica com el nom d'usuari i
 * contrasenya.
 * 
 * @author Jordi
 */
public class Usuari {
    //Atributs
    /**
     * Nom d'usuari de l'usuari.
     */
    String username;
    /**
     * Contrasenya de l'usuari.
     */
    String password;

    /**
     * Constructora per defecte que inicialitza les propietats de l'usuari amb
     * valors buits.
     */
    public Usuari() {
        this.username = "";
        this.password = "";
    }

    /**
     * Constructora que permet crear un usuari amb un nom d'usuari i una 
     * contrasenya específics.
     *
     * @param username Nom d'usuari a assignar.
     * @param password Contrasenya a assignar.
     */
    public Usuari(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Obté el nom d'usuari de l'usuari.
     *
     * @return Nom d'usuari de l'usuari.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obté la contrasenya de l'usuari.
     *
     * @return Contrasenya de l'usuari.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Estableix el nom d'usuari de l'usuari.
     *
     * @param username Nou nom d'usuari a assignar.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Estableix la contrasenya de l'usuari.
     *
     * @param password Nova contrasenya a assignar.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
