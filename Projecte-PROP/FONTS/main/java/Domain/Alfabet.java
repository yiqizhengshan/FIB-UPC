package Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import exceptions.CharacterNotInAlphabetException;
import exceptions.InsufficientCharactersException;
import exceptions.TooManyCharactersException;
import exceptions.LlistaAlreadyExistsException;
import exceptions.LlistaNotFoundException;

/**
 * Classe que representa un alfabet, amb el seu nom i la llista de caràcters que
 * el compon. També inclou
 * les llistes de paraules amb les seves freqüències associades a ell i les
 * freqüències de parella de caràcters creada a partir
 * d'aquestes llistes per a la creació d'un teclat.
 * 
 * @author Dídac
 */

public class Alfabet {

	// Atributs
	/**
	 * Nom de l'Alfabet
	 */
	private String nom;
	/**
	 * Llista de caràcters que composen l'Alfabet
	 */
	private HashSet<Character> llista_caracters;
	/**
	 * Conjunt de llistes paraula freqüència associades a aquest Alfabet
	 */
	private Cjt_Llista_Paraules cjt_llista_paraules;
	/**
	 * Conjunt de freqüències de parelles de caràcters creada a partir del
	 * cjt_llista_paraules
	 */
	private Parella_Caracters parella_caracters;
	/**
	 * Nom dels teclats que utilitzen aquest Alfabet
	 */ 
	private HashSet<String> teclats;
	/**
	 * Mida de llista_caracters, per a saber la quantitat de caràcters que componen
	 * l'Alfabet
	 */
	private int size;

	/**
	 * Constructora que permet crear un Alfabet amb tots els atributs inicialitzats.
	 * 
	 * @param nom              Nom de l'Alfabet a assignar.
	 * @param llista_caracters Caràcters a assignar.
	 * @throws InsufficientCharactersException Si la llista_caracters té menys de dos
	 *                                         caràcter.
	 * @throws TooManyCharactersException Si la llista_caracters té més de 36
	 *                                         caràcters.
	 */
	public Alfabet(String nom, ArrayList<Character> llista_caracters) throws InsufficientCharactersException , TooManyCharactersException {
		HashSet<Character> caractersSet = new HashSet<Character>(llista_caracters);
		if (caractersSet.size() < 2)
			throw new InsufficientCharactersException("Un alfabet necessita almenys dos caràcters");
		if (llista_caracters.size() > 36)
			throw new TooManyCharactersException("Un alfabet només pot tenir fins a 36 caràcters, i has introduit: " + llista_caracters.size() );
		this.nom = nom;
		this.llista_caracters = new HashSet<Character>(caractersSet);
		this.cjt_llista_paraules = new Cjt_Llista_Paraules();
		this.parella_caracters = new Parella_Caracters(llista_caracters, new HashMap<String, Integer>());
		this.teclats = new HashSet<String>();
		this.size = llista_caracters.size();
	}

	/**
	 * Constructora que permet crear un Alfabet amb tots els atributs inicialitzats
	 * i donar-li instancies de classes
	 * ja inicialitzades
	 * 
	 * @param nom                 Nom de l'Alfabet a assignar.
	 * @param llista_caracters    Caràcters a assignar.
	 * @param cjt_llista_paraules Instancia Cjt_Llista_Paraules ja inicialitzada.
	 * @param parella_caracters   Instancia Parella_Caracters ja inicialitzada.
	 * @throws InsufficientCharactersException Si la llista_caracters té menys de dos
	 *                                         caràcter.
	 * @throws TooManyCharactersException Si la llista_caracters té més de 36
	 *                                         caràcters.
	 */
	public Alfabet(String nom, ArrayList<Character> llista_caracters, Cjt_Llista_Paraules cjt_llista_paraules, Parella_Caracters parella_caracters,ArrayList<String> teclats) 
			throws InsufficientCharactersException, TooManyCharactersException {
		HashSet<Character> caractersSet = new HashSet<Character>(llista_caracters);
		if (caractersSet.size() < 2) throw new InsufficientCharactersException("Un alfabet necessita almenys dos caràcters");
		if (llista_caracters.size() > 36)
			throw new TooManyCharactersException("Un alfabet només pot tenir fins a 36 caràcters, i has introduit: " + llista_caracters.size() );
		this.nom = nom;
		this.llista_caracters = new HashSet<Character>(caractersSet);
		this.cjt_llista_paraules = cjt_llista_paraules;
		this.parella_caracters = parella_caracters;
		this.teclats = new HashSet<String>(teclats);
		this.size = llista_caracters.size();
	}

	/**
	 * Creadora que inicialitza un Alfabet a partir d'un altre Alfabet.
	 * 
	 * @param a Alfabet a copiar.
	 */
	public Alfabet(Alfabet a) {
		this.nom = a.nom;
		this.llista_caracters = new HashSet<Character>(a.llista_caracters);
		this.cjt_llista_paraules = new Cjt_Llista_Paraules(a.cjt_llista_paraules);
		this.parella_caracters = new Parella_Caracters(a.parella_caracters);
		this.teclats = a.teclats;
		this.size = a.size;
	}

	// getters

	/**
	 * Obté l'atribut size de l'Alfabet.
	 * 
	 * @return Size de Alfabet.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Obté el nom de l'Alfabet.
	 * 
	 * @return Nom de Alfabet.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Obté els caràcters de l'Alfabet, en forma d'ArrayList per a altres classes.
	 * 
	 * @return Caràcters de l'Alfabet.
	 */
	public ArrayList<Character> getCaracters() {
		return HashSetToArrayList(llista_caracters);
	}
	/**
	 * Obté el nom dels teclats que utilitzen l'Alfabet, en forma d'ArrayList per a altres classes.
	 * 
	 * @return Noms dels teclats de l'Alfabet.
	 */
	public ArrayList<String> getTeclats() {
		return new ArrayList<String>(teclats);
	}

	// Mètodes
	/**
	 * Assigna la llista de nom de teclats a l'atribut teclats.
	 * 
	 * @param teclats Llista de noms de teclats a assignar.
	 */
	public void setTeclats(ArrayList<String> teclats) {
		this.teclats = new HashSet<String>(teclats);
	}

	/**
	 * Elimina el nom donat dels teclats.
	 * @param nom_teclat Nom del teclat a eliminar.
	 * 
	 */
	public void eliminaTeclat(String nom_teclat) {
		teclats.remove(nom_teclat);
	}
	/**
	 * Afegeix el nom donat dels teclats.
	 * @param nom_teclat Nom del teclat a afegir.
	 */
	public void afegirTeclat(String nom_teclat) {
		teclats.add(nom_teclat);
	}

	/**
	 * Afegeix un caràcter de llista_caracters si encara no el conté i s'actualitza
	 * parella_caracters.
	 * 
	 * @param c Caràcter a afegir a llista_caracters.
	 * @return Fals si el caràcter ja és a l'Alfabet.
	 * @throws TooManyCharactersException Si la llista_caracters té 36
	 *                                         caràcters.
	 */
	public Boolean addCaracter(char c) throws TooManyCharactersException {
		if (llista_caracters.size() >= 36)
			throw new TooManyCharactersException("Un alfabet només pot tenir fins a 36 caràcters");
		if (!llista_caracters.contains(c)) {
			llista_caracters.add(c);
			size = llista_caracters.size();
			actualitza_Parella_Caracters(HashSetToArrayList(llista_caracters));
			return true;
		}
		else return false;
	}

	/**
	 * Elimina un caràcter llista_caracters si el conté i s'actualitza
	 * parella_caracters i cjt_llista_paraules.
	 * 
	 * @param c Caràcter a eliminar de llista_caracters.
	 * @throws InsufficientCharactersException Si la llista_caracters té només dos
	 *                                         caràcter.
	 */
	public void removeCaracter(char c) throws InsufficientCharactersException {
		if (llista_caracters.contains(c)) {
			if (llista_caracters.size() <= 2)throw new InsufficientCharactersException("Un alfabet necessita almenys dos caràcters");
			llista_caracters.remove(c);
			size = llista_caracters.size();
			cjt_llista_paraules.actualitzarFreq(c);
			actualitza_Parella_Caracters(HashSetToArrayList(llista_caracters));
		}
	}

	// Cjt_llistes
	/**
	 * Afegeix una Llista_Paraules a partir d'un text a cjt_llista_paraules i
	 * s'actualitza parella_caracters.
	 * 
	 * @param text Text del qual es volen extreure les paraules.
	 * @param nom  Nom de la llista de paraules que es vol crear.
	 * @throws LlistaAlreadyExistsException Si ja existeix una Llista_Paraules amb
	 *                                      el nom donat.
	 */
	public void afegirLlistaParaulesAmbText(String text, String nom) throws LlistaAlreadyExistsException {
		cjt_llista_paraules.afegirLlistaText(text, nom, HashSetToArrayList(this.llista_caracters));
		actualitza_Parella_Caracters(HashSetToArrayList(llista_caracters));
	}

	/**
	 * Afegeix una Llista_Paraules a partir d'una llista de paraules amb les seves freqüències cjt_llista_paraules i s'actualitza
	 * parella_caracters.
	 * 
	 * @param llista Llista de paraules amb les seves freqüències.
	 * @param nom    Nom de la llista de paraules que es vol crear.
	 * @throws LlistaAlreadyExistsException    Si ja existeix una Llista_Paraules
	 *                                         amb el nom donat.
	 * @throws CharacterNotInAlphabetException Si algun caràcter de la llista no
	 *                                         pertany a l'Alfabet.
	 */
	public void afegirLlistaParaulesDonada(String llista, String nom) throws LlistaAlreadyExistsException, CharacterNotInAlphabetException {
		cjt_llista_paraules.afegirLlistaDonada(llista, nom, HashSetToArrayList(this.llista_caracters));
		actualitza_Parella_Caracters(HashSetToArrayList(llista_caracters));
	}
	/**
	 * Afegeix una Llista_Paraules a cjt_llista_paraules i s'actualitza
	 * parella_caracters.
	 * 
	 * @param llista Llista de paraules.
	 * @throws LlistaAlreadyExistsException    Si ja existeix una Llista_Paraules
	 *                                         amb el nom donat.
	 */
	public void afegirLlista(Llista_Paraules llista) throws LlistaAlreadyExistsException{
		cjt_llista_paraules.afegirLlista(llista);
		actualitza_Parella_Caracters(HashSetToArrayList(llista_caracters));
	}

	/**
	 * Retorna la mida de cjt_llista_paraules.
	 * 
	 * @return size de cjt_llista_paraules.
	 */
	public int getSizeCjtLlistesParaules() {
		return cjt_llista_paraules.getSize();
	}

	/**
	 * Retorna un ArrayList d'Strings amb tots els noms de les Llista_Paraules de
	 * cjt_llistes_paraules.
	 * 
	 * @return ArrayList d'Strings amb tots els noms de les Llista_Paraules de
	 *         cjt_llistes_paraules.
	 */
	public ArrayList<String> consultarLlistes() {
		return cjt_llista_paraules.consultarLlistes();
	}

	/**
	 * Retorna un HashMap amb totes les Llista_Paraules de cjt_llistes_paraules.
	 * 
	 * @return HashMap on cada Llista_Paraules està indexada pel seu nom.
	 */
	public HashMap<String, Llista_Paraules> getCjtLlistes() {
		return cjt_llista_paraules.getCjtLlistes();
	}

	/**
	 * Retorna una Llista_Paraules de cjt_llistes_paraules amb el nom donat.
	 * 
	 * @param nom_llista Nom de la Llista_Paraules a retornar.
	 * @return Llista_Paraules amb el nom donat.
	 * @throws LlistaNotFoundException Si no existeix una Llista_Paraules amb el nom
	 *                                 donat.
	 */
	public Llista_Paraules getLlista(String nom_llista) throws LlistaNotFoundException {
		return cjt_llista_paraules.getLlista(nom_llista);
	}

	/**
	 * Retorna una matriu de Strings amb la freqüència de cada paraula de la
	 * Llista_Paraules amb el nom donat.
	 * 
	 * @param nom_llista Nom de la Llista_Paraules a consultar.
	 * @return Matriu de Strings amb la freqüència de cada paraula de la
	 *         Llista_Paraules amb el nom donat.
	 * @throws LlistaNotFoundException Si no existeix una Llista_Paraules amb el nom
	 *                                 donat.
	 */
	public String[][] getLlistaParaulaFreqString(String nom_llista) throws LlistaNotFoundException {
		return cjt_llista_paraules.getLlistaParaulaFreqString(nom_llista);
	}

	/**
	 * El conjunt de Llista_Paraules de cjt_llistes_paraules es substitueix per
	 * llistes.
	 * 
	 * @param llistes Conjunt de Llista_Paraules a assignar a cjt_llistes_paraules.
	 */
	public void setCjtLlistes(HashMap<String, Llista_Paraules> llistes) {
		cjt_llista_paraules.setCjt(llistes);
		actualitza_Parella_Caracters(HashSetToArrayList(llista_caracters));
	}

	/**
	 * Elimina una Llista_Paraules de cjt_llistes_paraules i s'actualitza
	 * parella_caracters.
	 * 
	 * @param nom de la Llista_Paraules a eliminar de cjt_llistes_paraules.
	 * @throws LlistaNotFoundException Si no existeix una Llista_Paraules amb el nom
	 *                                 donat.
	 */
	public void eliminarLlista(String nom) throws LlistaNotFoundException {
		cjt_llista_paraules.eliminarLlista(nom);
		actualitza_Parella_Caracters(HashSetToArrayList(llista_caracters));
	}

	// Consultores de Parella_Caracters
	/**
	 * Retorna la matriu de parella_caracters.
	 * 
	 * @return matriu de parella_caracters.
	 */
	public ArrayList<ArrayList<Integer>> getMatriuParellaCaracters() {
		return parella_caracters.getMatriuParellaCaracters();
	}

	/**
	 * Retorna l'array amb les correspondencies entre posicions i caràcters de de la
	 * matriu de parella_caracters.
	 * 
	 * @return ArrayList amb ordre_caracters de parella_caracters.
	 */
	public ArrayList<Character> getArrayOrdreCaracters() {
		return parella_caracters.getArrayOrdreCaracters();
	}

	// Metodes auxiliars
	/**
	 * Transforma un HashSet a un ArrayList.
	 * 
	 * @param set HashSet a tranformar en ArrayList.
	 * @return ArrayList amb el contingut de set.
	 */
	private ArrayList<Character> HashSetToArrayList(HashSet<Character> set) {
		ArrayList<Character> array = new ArrayList<Character>(set);
		return array;
	}

	/**
	 * Actualitza la freqüència de les parelles de lletres a parella_caracters.
	 * 
	 * @param llista_caracters Llista de caràcters de l'Alfabet en format ArrayList.
	 */
	private void actualitza_Parella_Caracters(ArrayList<Character> llista_caracters) {
		HashMap<String, Integer> llista_paraules_freq = cjt_llista_paraules.getCjt();
		parella_caracters.actualitzar(llista_caracters, llista_paraules_freq);
	}
}