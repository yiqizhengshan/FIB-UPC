package Domain;

import java.util.HashMap;

import exceptions.UsuariAlreadyExistsException;
import exceptions.UsuariNotFoundException;

/**
 * Classe que representa un conjunt d'usuaris amb funcionalitats com afegir,
 * obtenir i validar usuaris.
 * 
 * @author Jordi
 */
public class Cjt_Usuari {
    //Atributs
    /**
	 * Llista d'usuaris que composen el conjunt d'usuaris.
    */
    private HashMap<String, Usuari> llista_usuaris;
    /**
     * Mida del conjunt d'usuaris.
     */
    private int size;

    /**
     * Constructora per defecte que inicialitza el conjunt d'usuaris.
     */
    public Cjt_Usuari() {
        llista_usuaris = new HashMap<String, Usuari>();
        size = 0;
    }

    /**
     * Obté la mida actual del conjunt d'usuaris.
     *
     * @return La mida del conjunt d'usuaris.
     */
    public int getSize() {
        return size;
    }

    /**
     * S'assigna la llista_usuaris del conjint
     *
     * @param llista_usuaris llista a assignar.
     */
    public void setLlistaUsuaris(HashMap<String, Usuari> llista_usuaris) {
        this.llista_usuaris = llista_usuaris;
    }

    /**
     * Obté un usuari del conjunt pel seu nom d'usuari.
     *
     * @param username Nom d'usuari a buscar.
     * @return L'usuari corresponent al nom d'usuari donat.
     * @throws UsuariNotFoundException Si l'usuari no és troba al conjunt.
     */
    public Usuari getUsuari(String username) throws UsuariNotFoundException {
        if (llista_usuaris.containsKey(username)) {
            return llista_usuaris.get(username);
        } else {
            throw new UsuariNotFoundException("L'usuari amb username " + username + " no existeix");
        }
    }

    /**
     * Crea un nou usuari i l'afegeix al conjunt d'usuaris.
     *
     * @param username Nom d'usuari del nou usuari.
     * @param password Contrasenya del nou usuari.
     * @throws UsuariAlreadyExistsException Si ja existeix un usuari amb el mateix nom d'usuari.
     */
    public void creaUsuari(String username, String password) throws UsuariAlreadyExistsException {
        if (!llista_usuaris.containsKey(username)) {
            Usuari u = new Usuari(username, password);
            llista_usuaris.put(u.getUsername(), u);
            size = llista_usuaris.size();
        } else {
            throw new UsuariAlreadyExistsException("L'usuari amb username " + username + " ja existeix");
        }
    }

    /**
     * Afegeix un usuari existent al conjunt d'usuaris.
     *
     * @param u Usuari a afegir al conjunt.
     * @throws UsuariAlreadyExistsException Si ja existeix un usuari amb el mateix nom d'usuari.
     */
    public void afegirUsuari(Usuari u) throws UsuariAlreadyExistsException {
        if (!llista_usuaris.containsKey(u.getUsername())) {
            llista_usuaris.put(u.getUsername(), u);
            size = llista_usuaris.size();
        } else {
            throw new UsuariAlreadyExistsException("L'usuari amb username " + u.getUsername() + " ja existeix");
        }
    }

    /**
     * Verifica si una contrasenya coincideix amb la del nom d'usuari donat.
     *
     * @param username Nom d'usuari a verificar.
     * @param password Contrasenya a verificar.
     * @return Cert si la contrasenya coincideix, fals altrament.
     * @throws UsuariNotFoundException Si l'usuari no és troba al conjunt.
     */
    public boolean password_valida(String username, String password) throws UsuariNotFoundException {
        if (llista_usuaris.containsKey(username)) {
            return llista_usuaris.get(username).getPassword().equals(password);
        } else {
            throw new UsuariNotFoundException("L'usuari amb username " + username + " no existeix");
        }
    }
}