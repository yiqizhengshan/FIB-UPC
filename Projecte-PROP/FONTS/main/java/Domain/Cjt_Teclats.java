package Domain;

import java.util.HashMap;

import exceptions.TeclatAlreadyExistsException;
import exceptions.TeclatNotFoundException;
import exceptions.AlfabetModificatOEliminatException;
import exceptions.CharacterNotInAlphabetException;

import java.util.Map;
import java.util.ArrayList;

/**
 * Classe que representa el conjunt de teclats d'un usuari.
 * 
 * @author Oscar
 */
public class Cjt_Teclats {
	/**
	 * Llista de teclats que composen el conjunt de teclats.
	 */
	private HashMap<String, Teclat> llista_teclats;

	/**
	 * Mida del conjunt de teclats.
	 */
	private int size;

	/**
	 * Creadora que inicialitza el Cjt_Teclats.
	 */
	public Cjt_Teclats() {
		llista_teclats = new HashMap<String, Teclat>();
		size = 0;
	}

	/**
	 * Creadora que inicialitza el Cjt_Teclats a partir d'una altra llista de
	 * teclats.
	 * 
	 * @param llista_teclats Llista de teclats que es vol copiar.
	 */
	public Cjt_Teclats(HashMap<String, Teclat> llista_teclats) {
		this.llista_teclats = new HashMap<String, Teclat>(llista_teclats);
		size = llista_teclats.size();
	}

	/**
	 * Obté la mida del conjunt de teclats.
	 * 
	 * @return Mida del conjunt de teclats.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Obté un teclat del conjunt de teclats a partir del nom del teclat.
	 * 
	 * @param nom Nom del teclat que es vol obtenir.
	 * @throws TeclatNotFoundException            Si el teclat amb el nom donat no
	 *                                            existeix.
	 * @return El teclat amb el nom donat.
	 */
	public Teclat getTeclat(String nom) throws TeclatNotFoundException {
		if (llista_teclats.containsKey(nom)) {
			return llista_teclats.get(nom);
		} else {
			throw new TeclatNotFoundException("El teclat amb nom " + nom + " no existeix");
		}
	}

	/**
	 * Obté el nom d'un teclat del conjunt de teclats a partir del nom del teclat.
	 * 
	 * @param nom Nom del teclat que es vol obtenir.
	 * @throws TeclatNotFoundException            Si el teclat amb el nom donat no
	 *                                            existeix..
	 * @return El teclat amb el nom donat.
	 */
	public String getNomAlfabetTeclat(String nom) throws TeclatNotFoundException {
		return getTeclat(nom).getNomAlfabet();
	}

	/**
	 * Obté el layout de un teclat del conjunt de teclats a partir del nom del
	 * teclat.
	 * 
	 * @param nom Nom del teclat que te el layout que es vol obtenir.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no existeix.
	 * @return El layout del teclat amb el nom donat.
	 */
	public ArrayList<ArrayList<Character>> getLayoutTeclat(String nom) throws TeclatNotFoundException {
		return getTeclat(nom).getLayout();
	}

	/**
	 * Obté el estat del boolea alfabet modificat per saber si el teclat amb nom ha
	 * estat modificat.
	 * 
	 * @param nom Nom del teclat que te el layout que es vol obtenir.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no existeix.
	 * @return El boolea que ens indicara si el alfabet ha estat modificat.
	 */
	public boolean getAlfabetModificat(String nom) throws TeclatNotFoundException {
		return getTeclat(nom).getAlfabetModificat();
	}

	/**
	 * Obté el estat del boolea alfabet modificat per saber si el teclat amb nom ha
	 * estat eliminat.
	 * 
	 * @param nom Nom del teclat que te el layout que es vol obtenir.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no existeix.
	 * @return El boolea que ens indicara si el alfabet ha estat eliminat.
	 */
	public boolean getAlfabetEliminat(String nom) throws TeclatNotFoundException {
		return getTeclat(nom).getAlfabetEliminat();
	}

	/**
	 * Obté els noms de tots els teclats del conjunt de teclats.
	 * 
	 * @return Llista amb els noms de tots els teclats del conjunt de teclats.
	 */
	public ArrayList<String> consultarTeclats() {
		ArrayList<String> nom_teclats = new ArrayList<String>();
		for (Map.Entry<String, Teclat> entry : llista_teclats.entrySet()) {
			nom_teclats.add(entry.getKey());
		}
		return nom_teclats;
	}

	/**
	 * Afegeic un teclat al conjunt de teclats.
	 * 
	 * @param t Teclat que es vol afegir.
	 * @throws TeclatAlreadyExistsException Si el teclat que es vol afegir ja
	 *                                      existeix.
	 */
	public void addTeclat(Teclat t) throws TeclatAlreadyExistsException {
		if (llista_teclats.containsKey(t.getNom())) {
			throw new TeclatAlreadyExistsException("El teclat amb nom " + t.getNom() + " ja existeix");
		} else {
			llista_teclats.put(t.getNom(), t);
			size = llista_teclats.size();
		}
	}

	/**
	 * Elimina un teclat del conjunt de teclats.
	 * 
	 * @param nom Nom del teclat que es vol eliminar.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no existeix.
	 */
	public void removeTeclat(String nom) throws TeclatNotFoundException {
		if (llista_teclats.containsKey(nom)) {
			llista_teclats.remove(nom);
			size = llista_teclats.size();
		} else {
			throw new TeclatNotFoundException("El teclat amb nom " + nom + " no existeix");
		}
	}

	/**
	 * Intercanvia dos caracters del teclat.
	 * 
	 * @param nom_teclat Nom del teclat.
	 * @param c1         Primer caracter que es vol intercanviar.
	 * @param c2         Segon caracter que es vol intercanviar.
	 * @throws TeclatNotFoundException            Si el teclat amb el nom donat no
	 *                                            existeix.
	 * @throws CharacterNotInAlphabetException    Si algun caracter de la llista no
	 *                                            pertany a l'alfabet.
	 * @throws AlfabetModificatOEliminatException Si un dels caràcters ha estat
	 *                                            modificat o eliminat de l'alfabet.
	 */
	public void intercanviarCaractersTeclat(String nom_teclat, Character c1, Character c2)
			throws TeclatNotFoundException, CharacterNotInAlphabetException, AlfabetModificatOEliminatException {
		Teclat t = getTeclat(nom_teclat);
		t.intercanviarCaracters(c1, c2);
	}

	/**
	 * Obté el cost el layout d'un teclat del conjunt de teclats a partir del nom
	 * del teclat.
	 * 
	 * @param nom_teclat Nom del teclat que te el layout que es vol obtenir.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no
	 *                                 existeix.
	 * @return El layout del teclat amb el nom donat.
	 */
	public int consultarCostTeclat(String nom_teclat) throws TeclatNotFoundException {
		Teclat t = getTeclat(nom_teclat);
		return t.getCost();
	}

	/**
	 * Actualitzar el estat del boolea alfabet modificat en els teclat afectats.
	 * 
	 * @param nom_teclats Nom del teclat que te el layout que es vol obtenir.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no
	 *                                 existeix.
	 */
	public void actualitzarTeclatsAmbAlfabetModificat(ArrayList<String> nom_teclats) throws TeclatNotFoundException {
		for (String nom : nom_teclats) {
			getTeclat(nom).setAlfabetModificat(true);
		}
	}

	/**
	 * Actualitzar el estat del boolea alfabet eliminat en els teclat afectats.
	 * 
	 * @param nom_teclats Nom del teclat que te el layout que es vol obtenir.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no
	 *                                 existeix.
	 */
	public void actualitzarTeclatsAmbAlfabetEliminat(ArrayList<String> nom_teclats) throws TeclatNotFoundException {
		for (String nom : nom_teclats) {
			getTeclat(nom).setAlfabetEliminat(true);
		}
	}

	/**
	 * Obté el guany del cost en percentatge al intercanviar els caracters c1 i c2
	 * d'un teclat del conjunt de teclats a partir del nom del teclat.
	 * 
	 * @param nom_teclat Nom del teclat.
	 * @param c1         Primer caracter que es vol intercanviar.
	 * @param c2         Segon caracter que es vol intercanviar.
	 * @throws TeclatNotFoundException            Si el teclat amb el nom donat no
	 *                                            existeix.
	 * @throws AlfabetModificatOEliminatException Si un dels caràcters ha estat
	 *                                            modificat o eliminat de l'alfabet.
	 * @return Guany en percentage al intercanviar els caracterters c1 i c2.
	 */
	public double costIntercanviarCaractersTeclat(String nom_teclat, Character c1, Character c2)
			throws TeclatNotFoundException, CharacterNotInAlphabetException, AlfabetModificatOEliminatException {
		Teclat t = getTeclat(nom_teclat);
		return t.costIntercanviarCaracters(c1, c2);
	}

	/**
	 * Obté el guany del cost en percentatge si es fa una rexecucio del algorisme
	 * SA d'un teclat del conjunt de teclats a partir del nom del teclat.
	 * 
	 * @param nom_teclat Nom del teclat.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no
	 *                                 existeix.
	 * @return Guany en cost que te el teclat amb nom_teclat al aplicar una
	 *         rexecucio de l'algorisme SA.
	 */
	public double costRexecutarSATeclat(String nom_teclat) throws TeclatNotFoundException {
		Teclat t = getTeclat(nom_teclat);
		return t.costRexecutarSA();
	}

	/**
	 * Assigna el layout y cost de la seva copia, despres de executar l'algorisme SA
	 * d'un teclat del conjunt de teclats a partir del nom del teclat.
	 * 
	 * @param nom_teclat Nom del teclat.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no
	 *                                 existeix.
	 */
	public void assignarCopiaSATeclat(String nom_teclat) throws TeclatNotFoundException {
		Teclat t = getTeclat(nom_teclat);
		t.assignarCopiaSA();
	}
}