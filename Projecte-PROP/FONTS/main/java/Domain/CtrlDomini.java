package Domain;

import java.util.*;
import java.io.File;

import exceptions.*;
import Persistence.*;

/**
 * Controlador del domini que comunica la capa del domini amb les altres capes.
 * @author Dídac
 */
public class CtrlDomini {
	/**
	 * Conjunt d'alfabets de l'usuari actual.
	 */
	private Cjt_Alfabet cjt_alfabet;
	/**
	 * Conjunt de teclats de l'usuari actual.
	 */
	private Cjt_Teclats cjt_teclat;
	/**
	 * Conjunt d'usuaris.
	 */
	private Cjt_Usuari cjt_usuari;
	/**
	 * Gestor d'alfabets de la capa de persistència.
	 */
	private GestorAlfabet gestor_alfabet = new GestorAlfabet();
	/**
	 * Gestor d'usuaris de la capa de persistència.
	 */
	private GestorUsuari gestor_usuari = new GestorUsuari();
	/**
	 * Gestor de llistes de paraules de la capa de persistència.
	 */
	private GestorLlista_Paraules gestor_llista_paraules = new GestorLlista_Paraules();
	/**
	 * Gestor de teclats de la capa de persistència.
	 */
	private GestorTeclat gestor_teclat = new GestorTeclat();
	/**
	 * Nom de l'usuari actual amb la sessió iniciada.
	 */
	private String usuari_actual;
	/**
	 * Instància única de la classe CtrlDomini per a seguir el patró singleton.
	 */
	private static CtrlDomini ctrldomini;

	/**
	 * Constructora que inicia el cjt_usuaris a partir de la capa de persistència.
	 */
	private CtrlDomini() {
		cjt_usuari = new Cjt_Usuari();
		cjt_usuari.setLlistaUsuaris(gestor_usuari.carregarUsuaris());
	}

	/**
	 * Mètode estàtic per a obtenir l'única instància del CtrlDomini per a seguir el
	 * patró singleton.
	 * 
	 * @return Instància única de CtrlDomini
	 */
	public static CtrlDomini getInstance() {
		if (ctrldomini == null) {
			ctrldomini = new CtrlDomini();
		}
		return ctrldomini;
	}

	// Funcions usuari

	/**
	 * Mètode que registra un nou usuari amb el nom d'usuari i la contrasenya
	 * donats.
	 *
	 * @param username Nom d'usuari del nou usuari.
	 * @param password Contrasenya del nou usuari.
	 * @throws UsuariAlreadyExistsException Si ja existeix un usuari amb el mateix
	 *                                      username.
	 */
	public void registrarUsuari(String username, String password) throws UsuariAlreadyExistsException {
		Usuari usr = new Usuari(username, password);
		cjt_usuari.afegirUsuari(usr);
		gestor_usuari.guardarUsuari(usr);
	}

	/**
	 * Inicia la sessió per un usuari amb el nom d'usuari i la contrasenya
	 * proporcionats.
	 *
	 * @param username Nom d'usuari de l'usuari que vol iniciar sessió.
	 * @param password Contrasenya de l'usuari que vol iniciar sessió.
	 * @return Cert si la contrasenya és correcte, fals si és incorrecte.
	 * @throws UsuariNotFoundException Si l'usuari amb el username donat no existeix
	 *                                 al conjunt d'usuaris.
	 */
	public Boolean iniciarSessio(String username, String password) throws UsuariNotFoundException {
		if (cjt_usuari.password_valida(username, password)) {
			usuari_actual = username;
			HashMap<String, Alfabet> llista_alfabets = gestor_alfabet.carregarAlfabets(usuari_actual);
			cjt_alfabet = new Cjt_Alfabet(llista_alfabets);
			HashMap<String, Teclat> llista_teclats = gestor_teclat.carregarTeclats(usuari_actual);
			cjt_teclat = new Cjt_Teclats(llista_teclats);
			return true;
		}
		return false;
	}

	/**
	 * Tanca la sessió de l'usuari actual per a que pugui iniciar sessió un altre
	 * usuari.
	 */
	public void tancarSessio() {
		cjt_alfabet = null;
		cjt_teclat = null;
	}

	// Alfabet

	/**
	 * Crea un nou alfabet amb el nom i la llista de caràcters donats.
	 * 
	 * @param nom              Nom de l'alfabet.
	 * @param llista_caracters Llista de caràcters que componen l'alfabet.
	 * @throws AlfabetAlreadyExistsException   Si ja existeix un alfabet amb el nom
	 *                                         donat.
	 * @throws InsufficientCharactersException Si la llista de caràcters no té
	 *                                         suficients caràcters.
	 * @throws TooManyCharactersException      Si la llista de caràcters té més de
	 *                                         36
	 *                                         caràcters.
	 */
	public void crearAlfabet(String nom, ArrayList<Character> llista_caracters)
			throws AlfabetAlreadyExistsException, InsufficientCharactersException, TooManyCharactersException {
		Alfabet alfabet = new Alfabet(nom, llista_caracters);
		cjt_alfabet.afegirAlfabet(alfabet);
		gestor_alfabet.guardarAlfabet(alfabet, usuari_actual);
	}

	/**
	 * Retorna una llista amb els noms de tots els alfabet del cjt_alfabet.
	 * 
	 * @return Llista dels noms dels alfabets del cjt_alfabet
	 */
	public ArrayList<String> consultarAlfabets() {
		return cjt_alfabet.consultarAlfabets();
	}

	/**
	 * Retorna un alfabet amb el nom donat.
	 * 
	 * @param nom Nom de l'alfabet.
	 * @return El alfabet amb el nom donat.
	 * @throws AlfabetNotFoundException Si no existeix un alfabet amb el nom donat.
	 */
	public Alfabet getAlfabet(String nom) throws AlfabetNotFoundException {
		return cjt_alfabet.getAlfabet(nom);
	}

	/**
	 * Retorna els caràcters que componen l'alfabet amb el nom donat.
	 * 
	 * @param nom Nom de l'alfabet.
	 * @return Llista amb els caràcters de l'alfabet amb el nom donat.
	 * @throws AlfabetNotFoundException Si no existeix un alfabet amb el nom donat.
	 */
	public ArrayList<Character> veureAlfabet(String nom) throws AlfabetNotFoundException {
		return cjt_alfabet.getCaracters(nom);
	}

	/**
	 * Elimina l'alfabet amb el nom donat.
	 * 
	 * @param nom Nom de l'alfabet.
	 * @throws AlfabetNotFoundException Si no existeix un alfabet amb el nom donat.
	 * @throws TeclatNotFoundException  Si un teclat no existeix.
	 */
	public void eliminarAlfabet(String nom) throws AlfabetNotFoundException, TeclatNotFoundException {
		ArrayList<String> teclats = cjt_alfabet.getTeclats(nom);
		cjt_teclat.actualitzarTeclatsAmbAlfabetEliminat(teclats);
		actualitzarAtributsTeclatsPersistencia(teclats);
		cjt_alfabet.eliminarAlfabet(nom);
		gestor_alfabet.eliminarAlfabet(nom, usuari_actual);
	}

	/**
	 * Afegeix el caracter donat a l'alfabet amb el nom donat.
	 * 
	 * @param nom Nom de l'alfabet.
	 * @param c   Caràcter a afegir.
	 * @return Fals si el caràcter ja és a l'alfabet.
	 * @throws AlfabetNotFoundException   Si no existeix un alfabet amb el nom
	 *                                    donat.
	 * @throws TooManyCharactersException Si l'alfabet ja té 36 caràcters
	 * @throws TeclatNotFoundException    Si un teclat no existeix.
	 */
	public Boolean afegirCaracter(String nom, Character c)
			throws AlfabetNotFoundException, TooManyCharactersException, TeclatNotFoundException {
		Boolean afegit = cjt_alfabet.afegirCaracter(nom, c);
		ArrayList<String> teclats = cjt_alfabet.getTeclats(nom);
		cjt_teclat.actualitzarTeclatsAmbAlfabetModificat(teclats);
		actualitzarAtributsTeclatsPersistencia(teclats);
		Alfabet alfabet = cjt_alfabet.getAlfabet(nom);
		gestor_alfabet.guardarAlfabet(alfabet, usuari_actual);
		return afegit;
	}

	/**
	 * Eliminar el caracter donat a l'alfabet amb el nom donat.
	 * 
	 * @param nom Nom de l'alfabet.
	 * @param c   Caràcter a eliminar
	 * @throws AlfabetNotFoundException        Si no existeix un alfabet amb el nom
	 *                                         donat.
	 * @throws InsufficientCharactersException Si la llista de caràcters no té
	 *                                         suficients caràcters.
	 * @throws TeclatNotFoundException         Si un teclat no existeix.
	 */
	public void eliminarCaracter(String nom, Character c)
			throws AlfabetNotFoundException, InsufficientCharactersException, TeclatNotFoundException {
		cjt_alfabet.eliminarCaracter(nom, c);
		ArrayList<String> teclats = cjt_alfabet.getTeclats(nom);
		cjt_teclat.actualitzarTeclatsAmbAlfabetModificat(teclats);
		actualitzarAtributsTeclatsPersistencia(teclats);
		Alfabet alfabet = cjt_alfabet.getAlfabet(nom);
		gestor_alfabet.guardarAlfabet(alfabet, usuari_actual);
	}

	/**
	 * Importa un alfabet des d'un fitxer.
	 * 
	 * @param file Fitxer que conté l'alfabet a importar.
	 * @throws AlfabetAlreadyExistsException   Si ja existeix un alfabet amb el
	 *                                         mateix nom.
	 * @throws InsufficientCharactersException Si la llista de caràcters no té
	 *                                         suficients caràcters.
	 * @throws TooManyCharactersException      Si l'alfabet té més de 36 caràcters.
	 */
	public void importarAlfabet(File file) throws AlfabetAlreadyExistsException, InsufficientCharactersException,
			TooManyCharactersException, NoSuchElementException, NumberFormatException {
		Alfabet alfabet = gestor_alfabet.carregarFitxer(file);
		cjt_alfabet.afegirAlfabet(alfabet);
		gestor_alfabet.guardarAlfabet(alfabet, usuari_actual);
	}

	// Llistes

	/**
	 * Afegix una llista de paraules amb el text donat a l'alfabet amb el nom donat
	 * i li assigna el nom donat.
	 * 
	 * @param nom_alfabet Nom de l'alfabet.
	 * @param text        Text que conté les paraules que es posaran a la llista.
	 * @param nom_llista  Nom de la llista a crear.
	 * @throws AlfabetNotFoundException     Si l'alfabet amb el nom donat no
	 *                                      existeix.
	 * @throws LlistaAlreadyExistsException Si ja existeix una llista amb el nom
	 *                                      donat.
	 * @throws LlistaNotFoundException      Si la llista no existeix.
	 * @throws TeclatNotFoundException      Si un teclat no existeix.
	 */
	public void afegirLlistaParaulesAmbText(String nom_alfabet, String text, String nom_llista)
			throws AlfabetNotFoundException, LlistaAlreadyExistsException, LlistaNotFoundException,
			TeclatNotFoundException {
		cjt_alfabet.afegirLlistaParaulesAmbText(nom_alfabet, text, nom_llista);
		Llista_Paraules lp = cjt_alfabet.consultarLlista(nom_alfabet, nom_llista);
		ArrayList<String> teclats = cjt_alfabet.getTeclats(nom_alfabet);
		cjt_teclat.actualitzarTeclatsAmbAlfabetModificat(teclats);
		actualitzarAtributsTeclatsPersistencia(teclats);
		gestor_llista_paraules.guardarLlistaParaula(lp, nom_alfabet, nom_llista, usuari_actual);
	}

	/**
	 * Afegix una llista de paraules donada a l'alfabet amb el nom donat i li
	 * assigna el nom donat.
	 * 
	 * @param nom_alfabet Nom de l'alfabet.
	 * @param llista      llista de paraules a afegir.
	 * @param nom_llista  Nom de la llista a crear.
	 * @throws AlfabetNotFoundException        Si l'alfabet amb el nom donat no
	 *                                         existeix.
	 * @throws LlistaAlreadyExistsException    Si ja existeix una llista amb el nom
	 *                                         donat.
	 * @throws CharacterNotInAlphabetException Si algun caràcter de la llista no
	 *                                         pertany a l'alfabet.
	 * @throws LlistaNotFoundException         Si la llista no existeix.
	 * @throws TeclatNotFoundException         Si un teclat no existeix.
	 */
	public void afegirLlistaParaulesDonada(String nom_alfabet, String llista, String nom_llista)
			throws AlfabetNotFoundException, LlistaAlreadyExistsException, CharacterNotInAlphabetException,
			LlistaNotFoundException, TeclatNotFoundException {
		cjt_alfabet.afegirLlistaParaulesDonada(nom_alfabet, llista, nom_llista);
		Llista_Paraules lp = cjt_alfabet.consultarLlista(nom_alfabet, nom_llista);
		ArrayList<String> teclats = cjt_alfabet.getTeclats(nom_alfabet);
		cjt_teclat.actualitzarTeclatsAmbAlfabetModificat(teclats);
		actualitzarAtributsTeclatsPersistencia(teclats);
		gestor_llista_paraules.guardarLlistaParaula(lp, nom_alfabet, nom_llista, usuari_actual);
	}

	/**
	 * Consulta els noms de les llistes de paraules associades a l'alfabet amb el
	 * nom donat.
	 * 
	 * @param nom_alfabet Nom de l'alfabet.
	 * @return Llista dels noms de les llistes de paraules associades a l'alfabet.
	 * @throws AlfabetNotFoundException Si l'alfabet amb el nom donat no existeix.
	 */
	public ArrayList<String> consultarLlistes(String nom_alfabet) throws AlfabetNotFoundException {
		return cjt_alfabet.consultarLlistes(nom_alfabet);
	}

	/**
	 * Retorna la llista de paraules freqüencia amb el nom donat de l'alfabet amb el
	 * nom donat.
	 * 
	 * @param nom_alfabet Nom de l'alfabet.
	 * @param nom_llista  Nom de la llista.
	 * @return Llista de paraules freqüencies.
	 * @throws AlfabetNotFoundException Si l'alfabet amb el nom donat no existeix.
	 * @throws LlistaNotFoundException  Si la llista amb el nom donat no existeix.
	 */
	public String[][] veureLlista(String nom_alfabet, String nom_llista)
			throws AlfabetNotFoundException, LlistaNotFoundException {
		return cjt_alfabet.getLlistaParaulaFreqString(nom_alfabet, nom_llista);
	}

	/**
	 * Elimina la llista de paraules amb el nom donat de l'alfabet amb el nom donat.
	 * 
	 * @param nom_alfabet Nom de l'alfabet.
	 * @param nom_llista  Nom de la llista.
	 * @throws AlfabetNotFoundException Si l'alfabet amb el nom donat no existeix.
	 * @throws LlistaNotFoundException  Si la llista amb el nom donat no existeix.
	 * @throws TeclatNotFoundException  Si un teclat no existeix.
	 */
	public void eliminarLlista(String nom_alfabet, String nom_llista)
			throws AlfabetNotFoundException, LlistaNotFoundException, TeclatNotFoundException {
		cjt_alfabet.eliminarLlista(nom_alfabet, nom_llista);
		ArrayList<String> teclats = cjt_alfabet.getTeclats(nom_alfabet);
		cjt_teclat.actualitzarTeclatsAmbAlfabetModificat(teclats);
		gestor_llista_paraules.eliminarLlistaParaulaNom(nom_alfabet, nom_llista, usuari_actual);
		actualitzarAtributsTeclatsPersistencia(teclats);

	}

	/**
	 * Importa la llista de paraules del fitxer i li assigna a l'alfabet amb el nom
	 * donat.
	 * 
	 * @param file        Fitxer amb la llista a importar
	 * @param nom_alfabet Nom de la llista.
	 * @throws AlfabetNotFoundException        Si l'alfabet amb el nom donat no
	 *                                         existeix.
	 * @throws LlistaNotFoundException         Si la llista amb el nom donat no
	 *                                         existeix.
	 * @throws CharacterNotInAlphabetException Si un caràcter de la llista no
	 *                                         pertany a l'alfabet amb el nom donat.
	 * @throws TeclatNotFoundException         Si un teclat no existeix.
	 * @throws NoSuchElementException          Si el format del fitxer és
	 *                                         incorrecte.
	 * @throws NumberFormatException           Si el format del fitxer és
	 *                                         incorrecte.
	 */
	public void importarLlista(File file, String nom_alfabet)
			throws AlfabetNotFoundException, LlistaAlreadyExistsException, CharacterNotInAlphabetException,
			TeclatNotFoundException, NoSuchElementException, NumberFormatException {
		Llista_Paraules llista = gestor_llista_paraules.carregarFile(file, cjt_alfabet.getCaracters(nom_alfabet));
		cjt_alfabet.afegirLlista(llista, nom_alfabet);
		ArrayList<String> teclats = cjt_alfabet.getTeclats(nom_alfabet);
		cjt_teclat.actualitzarTeclatsAmbAlfabetModificat(teclats);
		actualitzarAtributsTeclatsPersistencia(teclats);
		gestor_llista_paraules.guardarLlistaParaula(llista, nom_alfabet, llista.getNom(), usuari_actual);

	}

	// Teclat
	/**
	 * Crea un teclat amb el nom donat a partir de l'alfabet amb el nom donat i
	 * l'algorisme QAP
	 * 
	 * @param nom_teclat  Nom del teclat.
	 * @param nom_alfabet Nom de l'alfabet.
	 * @throws TeclatAlreadyExistsException Si el ja existeix un teclat amb el nom
	 *                                      donat.
	 * @throws AlfabetNotFoundException     Si l'alfabet amb el nom donat no
	 *                                      existeix.
	 */
	public void crearTeclatQAP(String nom_teclat, String nom_alfabet)
			throws TeclatAlreadyExistsException, AlfabetNotFoundException {
		Estrategia_Teclat qap = new QAP();
		Alfabet alfabet = cjt_alfabet.getAlfabet(nom_alfabet);
		double nd = Math.sqrt(alfabet.getSize());
		nd = Math.ceil(nd);
		int n = (int) nd;
		ArrayList<ArrayList<Character>> layout = new ArrayList<ArrayList<Character>>();
		for (int i = 0; i < n; ++i) {
			ArrayList<Character> tmp = new ArrayList<Character>();
			for (int j = 0; j < n; ++j) {
				tmp.add(' ');
			}
			layout.add(tmp);
		}
		Teclat teclat = new Teclat(nom_teclat, alfabet, layout, qap);
		cjt_teclat.addTeclat(teclat);
		cjt_alfabet.afegirTeclat(nom_alfabet, nom_teclat);
		gestor_teclat.guardarTeclat(teclat, usuari_actual);
	}

	/**
	 * Crea un teclat amb el nom donat a partir de l'alfabet amb el nom donat i
	 * l'algorisme SA.
	 * 
	 * @param nom_teclat  Nom del teclat.
	 * @param nom_alfabet Nom de l'alfabet.
	 * @throws TeclatAlreadyExistsException Si ja existeix un teclat amb el nom
	 *                                      donat.
	 * @throws AlfabetNotFoundException     Si l'alfabet amb el nom donat no
	 *                                      existeix.
	 */
	public void crearTeclatSA(String nom_teclat, String nom_alfabet)
			throws TeclatAlreadyExistsException, AlfabetNotFoundException {
		Estrategia_Teclat sa = new SA();
		Alfabet alfabet = cjt_alfabet.getAlfabet(nom_alfabet);
		double nd = Math.sqrt(alfabet.getSize());
		nd = Math.ceil(nd);
		int n = (int) nd;
		ArrayList<ArrayList<Character>> layout = new ArrayList<ArrayList<Character>>();
		for (int i = 0; i < n; ++i) {
			ArrayList<Character> tmp = new ArrayList<Character>();
			for (int j = 0; j < n; ++j) {
				tmp.add(' ');
			}
			layout.add(tmp);
		}
		Teclat teclat = new Teclat(nom_teclat, alfabet, layout, sa);
		cjt_teclat.addTeclat(teclat);
		cjt_alfabet.afegirTeclat(nom_alfabet, nom_teclat);
		gestor_teclat.guardarTeclat(teclat, usuari_actual);
	}

	/**
	 * Retorna una llista amb el nom de tots els teclats del cjt_teclats.
	 * 
	 * @return Llista amb el nom de tots els teclats del cjt_teclats.
	 */
	public ArrayList<String> consultarTeclats() {
		return cjt_teclat.consultarTeclats();
	}

	/**
	 * Retorna el nom de l'alfabet associat al teclat amb el nom donat.
	 * 
	 * @param nom Nom del teclat
	 * @return Nom de l'alfabet associat al teclat.
	 * @throws TeclatNotFoundException            Si el teclat amb el nom donat no
	 *                                            existeix.
	 * @throws AlfabetModificatOEliminatException Si un dels caràcters ha estat
	 *                                            modificat o eliminat de l'alfabet.
	 */
	public String consultarNomAlfabetTeclat(String nom)
			throws TeclatNotFoundException, AlfabetModificatOEliminatException {
		return cjt_teclat.getNomAlfabetTeclat(nom);
	}

	/**
	 * Elimina el teclat amb el nom donat.
	 * 
	 * @param nom Nom del teclat.
	 * @throws TeclatNotFoundException            Si el teclat amb el nom donat no
	 *                                            existeix.
	 * @throws AlfabetNotFoundException           Si l'alfabet no existeix.
	 * @throws AlfabetModificatOEliminatException Si un dels caràcters ha estat
	 *                                            modificat o eliminat de l'alfabet.
	 */
	public void eliminarTeclat(String nom)
			throws TeclatNotFoundException, AlfabetNotFoundException, AlfabetModificatOEliminatException {
		String nom_alfabet = cjt_teclat.getNomAlfabetTeclat(nom);
		Boolean eliminat = cjt_teclat.getTeclat(nom).getAlfabetEliminat();
		cjt_teclat.removeTeclat(nom);
		if (!eliminat)
			cjt_alfabet.eliminaTeclat(nom_alfabet, nom);
		gestor_teclat.eliminarTeclatNom(nom, usuari_actual);
	}

	/**
	 * Retorna el layout del teclat amb el nom donat.
	 * 
	 * @param nom Nom del teclat.
	 * @return Layout del teclat.
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no existeix.
	 */
	public ArrayList<ArrayList<Character>> veureTeclat(String nom) throws TeclatNotFoundException {
		return cjt_teclat.getLayoutTeclat(nom);
	}

	/**
	 * Retorna el boolea alfabet modificat per saber si el teclat amb nom ha estat
	 * modificat.
	 * 
	 * @param nom
	 * @return
	 * @throws TeclatNotFoundException
	 */
	public boolean getAlfabetModificat(String nom) throws TeclatNotFoundException {
		return cjt_teclat.getAlfabetModificat(nom);
	}

	/**
	 * Retorna el boolea alfabet eliminat per saber si el teclat amb nom ha estat
	 * eliminat.
	 * 
	 * @param nom
	 * @return
	 * @throws TeclatNotFoundException
	 */
	public boolean getAlfabetEliminat(String nom) throws TeclatNotFoundException {
		return cjt_teclat.getAlfabetEliminat(nom);
	}

	/**
	 * Intercanvia al layout del teclat amb el nom donat els caràcters donats.
	 * 
	 * @param nom Nom del teclat.
	 * @param c1  Caràcter a intercanviar amb c2.
	 * @param c2  Caràcter a intercanviar amb c1.
	 * @throws TeclatNotFoundException            Si el teclat amb el nom donat no
	 *                                            existeix.
	 * @throws CharacterNotInAlphabetException    Si el un dels caràcters no pertany
	 *                                            a
	 *                                            l'alfabet del teclat.
	 * @throws AlfabetModificatOEliminatException Si un dels caràcters ha estat
	 *                                            modificat o eliminat de l'alfabet.
	 */
	public void intercanviarCaractersTeclat(String nom, Character c1, Character c2)
			throws TeclatNotFoundException, CharacterNotInAlphabetException, AlfabetModificatOEliminatException {
		gestor_teclat.eliminarTeclatNom(nom, usuari_actual);

		cjt_teclat.intercanviarCaractersTeclat(nom, c1, c2);
		Teclat teclat = cjt_teclat.getTeclat(nom);

		gestor_teclat.guardarTeclat(teclat, usuari_actual);
	}

	/**
	 * Retorna el cost del teclat amb el nom donat.
	 * 
	 * @param nom Nom del teclat.
	 * @return Cost del teclat
	 * @throws TeclatNotFoundException Si el teclat amb el nom donat no existeix.
	 */
	public int consultarCostTeclat(String nom) throws TeclatNotFoundException {
		return cjt_teclat.consultarCostTeclat(nom);
	}

	/**
	 * Importa un teclat des d'un fitxer.
	 * 
	 * @param file Fitxer que conté el teclat a importar.
	 * @throws InsufficientCharactersException Si la llista de caràcters no té
	 *                                         suficients caràcters.
	 * @throws TooManyCharactersException      Si l'alfabet té més de 36 caràcters.
	 * @throws TeclatAlreadyExistsException   Si ja existeix un teclat amb el
	 *                                         mateix nom.
	 * @throws NoSuchElementException		 Error de format.
	 * @throws AlfabetNotFoundException L'alfabet no existeix.
	 */
	public void importarTeclat(File file) throws InsufficientCharactersException, TooManyCharactersException,
			TeclatAlreadyExistsException, NoSuchElementException, AlfabetNotFoundException {
		Teclat teclat = gestor_teclat.carregarFitxer(file);
		cjt_teclat.addTeclat(teclat);
		gestor_teclat.guardarTeclat(teclat, usuari_actual);
	}

	/**
	 * Obté el guany del cost en percentatge al intercanviar els caracters c1 i c2
	 * d'un teclat amb el nom donat.
	 * 
	 * @param nom
	 * @param c1
	 * @param c2
	 * @return
	 * @throws TeclatNotFoundException
	 * @throws CharacterNotInAlphabetException
	 * @throws AlfabetModificatOEliminatException Si un dels caràcters ha estat
	 *                                            modificat o eliminat de l'alfabet.
	 */
	public double costIntercanviarCaractersTeclat(String nom, Character c1, Character c2)
			throws TeclatNotFoundException, CharacterNotInAlphabetException, AlfabetModificatOEliminatException {
		return cjt_teclat.costIntercanviarCaractersTeclat(nom, c1, c2);
	}

	/**
	 * Obté el guany del cost en percentatge al rexecutar amb l'algorisme SA el
	 * teclat amb nom donat.
	 * 
	 * @param nom
	 * @return
	 * @throws TeclatNotFoundException
	 * @throws AlfabetNotFoundException
	 */
	public double costRexecutarSATeclat(String nom) throws TeclatNotFoundException, AlfabetNotFoundException {
		return cjt_teclat.costRexecutarSATeclat(nom);
	}

	/**
	 * Assigna al teclat amb el nom donat la seva copia, corresponent a una
	 * rexecucio de l'algorisme SA.
	 * 
	 * @param nom_teclat
	 * @throws TeclatNotFoundException
	 * @throws AlfabetNotFoundException
	 */
	public void assignarCopiaSATeclat(String nom_teclat) throws TeclatNotFoundException, AlfabetNotFoundException {
		Teclat teclat = cjt_teclat.getTeclat(nom_teclat);
		gestor_teclat.eliminarTeclatNom(nom_teclat, usuari_actual);
		cjt_teclat.assignarCopiaSATeclat(nom_teclat);
		gestor_teclat.guardarTeclat(teclat, usuari_actual);
	}

	/**
	 * Actualitza tots els teclat a la capa persistencia.
	 * 
	 * @param teclats
	 * @throws TeclatNotFoundException
	 */
	private void actualitzarAtributsTeclatsPersistencia(ArrayList<String> teclats) throws TeclatNotFoundException {
		for (String teclat : teclats) {
			gestor_teclat.guardarTeclat(cjt_teclat.getTeclat(teclat), usuari_actual);
		}
	}
}