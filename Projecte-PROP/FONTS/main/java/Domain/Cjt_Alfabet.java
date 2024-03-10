package Domain;

import java.util.HashMap;
import java.util.ArrayList;

import exceptions.AlfabetAlreadyExistsException;
import exceptions.AlfabetNotFoundException;
import exceptions.CharacterNotInAlphabetException;
import exceptions.InsufficientCharactersException;
import exceptions.TooManyCharactersException;
import exceptions.LlistaAlreadyExistsException;
import exceptions.LlistaNotFoundException;

/**
 * Classe que representa el conjunt d'alfabets d'un usuari.
 * @author Dídac
 */

public class Cjt_Alfabet {

	/**
	 * Llista d'alfabets que composen el conjunt d'alfabets.
	 * 
	 */
	private HashMap<String, Alfabet> llista_alfabets;
	/**
	 * Mida del conjunt d'alfabets.
	 */
	private int size;

	// Creadores
	/**
	 * Creadora que inicialitza el Cjt_Alfabet.
	 */
	public Cjt_Alfabet(HashMap<String,Alfabet> llista_alfabets) {
		this.llista_alfabets = llista_alfabets;
		this.size = llista_alfabets.size();
	}

	// Getters

	/**
	 * Obté la mida del conjunt d'alfabets.
	 * 
	 * @return Mida del conjunt.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Obté l'Alfabet amb el nom donat del conjunt d'alfabets.
	 * 
	 * @param nom Nom de l'Alfabet que es vol obtenir.
	 * @return L'Alfabet amb el nom donat.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al
	 *                                  conjunt.
	 */
	public Alfabet getAlfabet(String nom) throws AlfabetNotFoundException {
		if (llista_alfabets.containsKey(nom)) {
			return llista_alfabets.get(nom);
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom + " no existeix");
	}
	/**
	 * Obté la llista de noms de teclats que tenen l'alfabet amb el nom donat com alfabet.
	 * 
	 * @param nom Nom de l'Alfabet.
	 * @return Llista de noms de teclats de l'alfabet
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al
	 *                                  conjunt.
	 */
	public ArrayList<String> getTeclats(String nom) throws AlfabetNotFoundException {
		if (llista_alfabets.containsKey(nom)) {
			return llista_alfabets.get(nom).getTeclats();
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom + " no existeix");	}

	// Mètodes
	
	/**
	 * Elimina el nom donat dels teclats de l'alfabet amb el nom donat.
	 * @param nom_alfabet Nom de l'alfabet.
	 * @param nom_teclat Nom del teclat.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al
	 *                                  conjunt.
	 */
	public void eliminaTeclat(String nom_alfabet, String nom_teclat) throws AlfabetNotFoundException {
		if (llista_alfabets.containsKey(nom_alfabet)) {
			llista_alfabets.get(nom_alfabet).eliminaTeclat(nom_teclat);
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom_alfabet + " no existeix");
	}

	/**
	 * Afegeix el nom donat dels teclats de l'alfabet amb el nom donat.
	 * @param nom_alfabet Nom de l'alfabet.
	 * @param nom_teclat Nom del teclat.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al
	 *                                  conjunt.
	 */
	public void afegirTeclat(String nom_alfabet, String nom_teclat) throws AlfabetNotFoundException{
		if (llista_alfabets.containsKey(nom_alfabet)) {
			llista_alfabets.get(nom_alfabet).afegirTeclat(nom_teclat);
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom_alfabet + " no existeix");
	}

	/**
	 * Crea un Alfabet a partir d'un nom i una llista_caracters.
	 * 
	 * @param nom Nom de l'Alfabet que es vol crear.
	 * @param llista_caracters Llista amb els caràcters que es volen assignar al nou Alfabet.
	 * @throws AlfabetAlreadyExistsException   Si un Alfabet amb el mateix nom ja existeix al conjunt.
	 * @throws InsufficientCharactersException Si la llista_caracters té menys de dos caràcters.
	 * @throws TooManyCharactersException Si la llista_caracters té més de 36
	 *                                         caràcters.
	 */
	public void crearAlfabet(String nom, ArrayList<Character> llista_caracters) throws InsufficientCharactersException, AlfabetAlreadyExistsException, TooManyCharactersException {
		if (!llista_alfabets.containsKey(nom)) {
			Alfabet a = new Alfabet(nom, llista_caracters);
			llista_alfabets.put(a.getNom(), a);
			size = llista_alfabets.size();

		} else
			throw new AlfabetAlreadyExistsException("L'alfabet amb nom " + nom + " ja existeix");
	}

	/**
	 * Afegeix un Alfabet al conjunt d'alfabets.
	 * 
	 * @param a Alfabet a afegir.
	 * @throws AlfabetAlreadyExistsException Si un Alfabet amb el mateix nom ja existeix al conjunt.
	 */
	public void afegirAlfabet(Alfabet a) throws AlfabetAlreadyExistsException {
		if (!llista_alfabets.containsKey(a.getNom())) {
			llista_alfabets.put(a.getNom(), a);
			size = llista_alfabets.size();
		} else
			throw new AlfabetAlreadyExistsException("L'alfabet amb nom " + a.getNom() + " ja existeix");
	}

	/**
	 * Obté una llista dels noms de tots els alfabets del conjunt.
	 * 
	 * @return Llista dels noms de tots els alfabets del conjunt.
	 */
	public ArrayList<String> consultarAlfabets() {
		return new ArrayList<String>(llista_alfabets.keySet());
	}

	/**
	 * Obté una llista amb tots els caràcters de l'Alfabet amb el nom donat.
	 * 
	 * @param nom Nom de l'Alfabet del qual es vol obtenir els seus caràcters.
	 * @return Llista amb tots els caràcters de l'Alfabet amb el nom donat.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 */
	public ArrayList<Character> getCaracters(String nom) throws AlfabetNotFoundException {
		if (llista_alfabets.containsKey(nom)) {
			return llista_alfabets.get(nom).getCaracters();
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom + " no existeix");
	}

	/**
	 * Elimina un Alfabet del conjunt d'alfabets.
	 * 
	 * @param nom Nom de l'Alfabet que es vol eliminar.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 */
	public void eliminarAlfabet(String nom) throws AlfabetNotFoundException {
		if (llista_alfabets.containsKey(nom)) {
			llista_alfabets.remove(nom);
			size = llista_alfabets.size();
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom + " no existeix");
	}

	/**
	 * Afegeix un caràcter a l'Alfabet amb el nom donat.
	 * 
	 * @param nom Nom de l'Alfabet del qual es vol afegir el caràcter.
	 * @param c   Caràcter que es vol afegir.
	 * @return Fals si el caràcter ja és a l'alfabet.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 * @throws TooManyCharactersException Si l'Alfabet amb el nom donat té 36 caràcters.
	 */
	public Boolean afegirCaracter(String nom, Character c) throws AlfabetNotFoundException, TooManyCharactersException {
		if (llista_alfabets.containsKey(nom)) {
			return llista_alfabets.get(nom).addCaracter(c);
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom + " no existeix");

	}

	/**
	 * Elimina un caràcter de l'Alfabet amb el nom donat.
	 * 
	 * @param nom Nom de l'Alfabet del qual es vol eliminar el caràcter.
	 * @param c Caràcter que es vol eliminar.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 * @throws InsufficientCharactersException Si l'Alfabet només té dos caràcter.
	 */
	public void eliminarCaracter(String nom, Character c)
			throws AlfabetNotFoundException, InsufficientCharactersException {
		if (llista_alfabets.containsKey(nom)) {
			llista_alfabets.get(nom).removeCaracter(c);
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom + " no existeix");

	}

	/**
	 * Afegeix una llista de paraules a l'Alfabet amb el nom donat a partir d'un text.
	 * 
	 * @param nom_alfabet Nom de l'Alfabet al qual es vol afegir la llista de paraules.
	 * @param text Text del qual es volen extreure les paraules.
	 * @param nom_llista  Nom de la llista de paraules que es vol crear.
	 * @throws AlfabetNotFoundException     Si l'Alfabet amb el nom donat no existeix al conjunt.
	 * @throws LlistaAlreadyExistsException Si una llista de paraules amb el mateix nom ja existeix al conjunt.
	 */
	public void afegirLlistaParaulesAmbText(String nom_alfabet, String text, String nom_llista)
			throws AlfabetNotFoundException, LlistaAlreadyExistsException {
		if (llista_alfabets.containsKey(nom_alfabet)) {
			llista_alfabets.get(nom_alfabet).afegirLlistaParaulesAmbText(text, nom_llista);
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom_alfabet + " no existeix");
	}

	/**
	 * Afegeix una llista de paraules a l'Alfabet amb el nom donat a partir d'una llista de paraules amb les seves freqüències.
	 * 
	 * @param nom_alfabet Nom de l'Alfabet al que es vol afegir la llista de paraules.
	 * @param llista Llista de paraules amb les seves freqüències.
	 * @param nom_llista Nom de la llista de paraules que es vol crear.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 * @throws LlistaAlreadyExistsException Si una llista de paraules amb el mateix nom ja existeix al conjunt.
	 * @throws CharacterNotInAlphabetException Si algun dels caràcters de la llista de paraules no pertany a l'alfabet.
	 */
	public void afegirLlistaParaulesDonada(String nom_alfabet, String llista, String nom_llista) throws AlfabetNotFoundException, LlistaAlreadyExistsException, CharacterNotInAlphabetException {
		if (llista_alfabets.containsKey(nom_alfabet)) {
			llista_alfabets.get(nom_alfabet).afegirLlistaParaulesDonada(llista, nom_llista);
		} else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom_alfabet + " no existeix");
	}
	/**
	 * Afegeix una llista de paraules a l'Alfabet amb el nom donat.
	 * 
	 * @param nom_alfabet Nom de l'Alfabet al que es vol afegir la llista de paraules.
	 * @param llista Llista de paraules.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 * @throws LlistaAlreadyExistsException Si una llista de paraules amb el mateix nom ja existeix al conjunt.
	 * @throws CharacterNotInAlphabetException Si algun dels caràcters de la llista de paraules no pertany a l'alfabet.
	 */
	public void afegirLlista(Llista_Paraules llista, String nom_alfabet) throws AlfabetNotFoundException, LlistaAlreadyExistsException, CharacterNotInAlphabetException{
		if (llista_alfabets.containsKey(nom_alfabet)) {
			llista_alfabets.get(nom_alfabet).afegirLlista(llista);
		}
		else throw new AlfabetNotFoundException("L'alfabet amb nom " + nom_alfabet + " no existeix");
	}

	/**
	 * Consulta el nom de totes les llistes de paraules que hi ha a l'Alfabet amb el nom donat.
	 * 
	 * @param nom_alfabet Nom de l'Alfabet del que es volen consultar les llistes de paraules.
	 * @return Llista amb el nom de totes les llistes de paraules que hi ha a l'Alfabet amb el nom donat.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 */
	public ArrayList<String> consultarLlistes(String nom_alfabet) throws AlfabetNotFoundException {
		if (llista_alfabets.containsKey(nom_alfabet))
			return llista_alfabets.get(nom_alfabet).consultarLlistes();
		else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom_alfabet + " no existeix");
	}

	/**
	 * Consulta la llista de paraules amb el nom donat de l'Alfabet amb el nom donat
	 * 
	 * @param nom_alfabet Nom de l'Alfabet del que es volen consultar les llistes de paraules.
	 * @return Llista de paraules amb el nom donat de l'Alfabet amb el nom donat.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 * @throws LlistaNotFoundException  Si la llista de paraules amb el nom donat no existeix a l'alfabet amb el nom donat.
	 */
	public Llista_Paraules consultarLlista(String nom_alfabet, String nom_llista) throws AlfabetNotFoundException, LlistaNotFoundException {
		if (llista_alfabets.containsKey(nom_alfabet))
			return llista_alfabets.get(nom_alfabet).getLlista(nom_llista);
		else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom_alfabet + " no existeix");
	}

	/**
	 * Obté la llista de paraules amb les seves freqüències amb el nom donat del Alfabet amb el nom donat.
	 * 
	 * @param nom_alfabet Nom de l'Alfabet del que es vol obtenir la llista de paraules.
	 * @param nom_llista  Nom de la llista de paraules que es vol obtenir.
	 * @return Llista de paraules amb les seves freqüències.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 * @throws LlistaNotFoundException  Si la llista de paraules amb el nom donat no existeix a l'alfabet amb el nom donat.
	 */
	public String[][] getLlistaParaulaFreqString(String nom_alfabet, String nom_llista) throws AlfabetNotFoundException, LlistaNotFoundException {
		if (llista_alfabets.containsKey(nom_alfabet))
			return llista_alfabets.get(nom_alfabet).getLlistaParaulaFreqString(nom_llista);
		else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom_alfabet + " no existeix");
	}

	/**
	 * Elimina la llista de paraules amb el nom donat de l'Alfabet amb el nom donat.
	 * 
	 * @param nom_alfabet Nom de l'Alfabet del que es vol eliminar la llista de paraules.
	 * @param nom_llista  Nom de la llista de paraules que es vol eliminar.
	 * @throws AlfabetNotFoundException Si l'Alfabet amb el nom donat no existeix al conjunt.
	 * @throws LlistaNotFoundException  Si la llista de paraules amb el nom donat no existeix a l'alfabet amb el nom donat.
	 */
	public void eliminarLlista(String nom_alfabet, String nom_llista) throws AlfabetNotFoundException, LlistaNotFoundException {
		if (llista_alfabets.containsKey(nom_alfabet))
			llista_alfabets.get(nom_alfabet).eliminarLlista(nom_llista);
		else
			throw new AlfabetNotFoundException("L'alfabet amb nom " + nom_alfabet + " no existeix");
	}
}