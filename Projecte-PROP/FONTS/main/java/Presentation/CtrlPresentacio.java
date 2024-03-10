package Presentation;

import Domain.CtrlDomini;

import exceptions.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;

/**
 * Controlador de la presentació que canvia entre les vistes i
 * comunica la capa de presentació amb les altres capes.
 * @author Dídac
 */
public class CtrlPresentacio {

	private JFrame mainFrame;

	private JPanel mainPanel;

	private CardLayout layout;

	private CtrlDomini ctrldomini;

	private VistaInici vistainici;

	private VistaRegistrarse vistaregistrarse;

	private VistaPrincipal vistaprincipal;

	private VistaGestioAlfabet vistaGestioAlfabet;

	private VistaCrearAlfabet vistaCrearAlfabet;

	private VistaVeureAlfabet vistaVeureAlfabet;

	private VistaModificarAlfabet vistaModificarAlfabet;

	private VistaGestioTeclat vistaGestioTeclat;

	private VistaCrearTeclat vistaCrearTeclat;

	private VistaVeureTeclat vistaVeureTeclat;

	private VistaGestioLlistes vistaGestioLlistes;

	private VistaCrearLlista vistaCrearLlista;

	private VistaVeureLlista vistaVeureLlista;

	private VistaModificarTeclat vistaModificarTeclat;

	private static CtrlPresentacio ctrlpresentacio;

	private CtrlPresentacio() {
		ctrldomini = CtrlDomini.getInstance();
	}

	public static CtrlPresentacio getInstance() {
		if (ctrlpresentacio == null) {
			ctrlpresentacio = new CtrlPresentacio();
		}
		return ctrlpresentacio;
	}

	public void iniciarCtrlPresentacio() {
		mainFrame = new JFrame("Frame principal");
		mainFrame.setMinimumSize(new Dimension(900, 600));
		mainFrame.setPreferredSize(mainFrame.getMinimumSize());
		mainFrame.setResizable(true);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);

		layout = new CardLayout();
		mainPanel = new JPanel();
		mainPanel.setLayout(layout);

		mainFrame.add(mainPanel);

		vistainici = new VistaInici();
		vistaregistrarse = new VistaRegistrarse();
		vistaprincipal = new VistaPrincipal();
		vistaGestioAlfabet = new VistaGestioAlfabet();
		vistaGestioTeclat = new VistaGestioTeclat();
		vistaCrearTeclat = new VistaCrearTeclat();
		vistaVeureTeclat = new VistaVeureTeclat();
		vistaGestioLlistes = new VistaGestioLlistes();
		vistaCrearAlfabet = new VistaCrearAlfabet();
		vistaVeureAlfabet = new VistaVeureAlfabet();
		vistaModificarAlfabet = new VistaModificarAlfabet();
		vistaCrearLlista = new VistaCrearLlista();
		vistaVeureLlista = new VistaVeureLlista();
		vistaModificarTeclat = new VistaModificarTeclat();

		mainPanel.add(vistainici, "VistaInici");
		mainPanel.add(vistaregistrarse, "VistaRegistrarse");
		mainPanel.add(vistaprincipal, "VistaPrincipal");
		mainPanel.add(vistaGestioAlfabet, "VistaGestioAlfabet");
		mainPanel.add(vistaCrearAlfabet, "VistaCrearAlfabet");
		mainPanel.add(vistaVeureAlfabet, "VistaVeureAlfabet");
		mainPanel.add(vistaModificarAlfabet, "VistaModificarAlfabet");
		mainPanel.add(vistaGestioTeclat, "VistaGestioTeclat");
		mainPanel.add(vistaCrearTeclat, "VistaCrearTeclat");
		mainPanel.add(vistaVeureTeclat, "VistaVeureTeclat");
		mainPanel.add(vistaGestioLlistes, "VistaGestioLlistes");
		mainPanel.add(vistaCrearLlista, "VistaCrearLlista");
		mainPanel.add(vistaVeureLlista, "VistaVeureLlista");
		mainPanel.add(vistaModificarTeclat, "VistaModificarTeclat");

		canviarVistaInici();
		mainFrame.setVisible(true);
	}

	public void tancarFinestra() {
		mainFrame.dispose();
	}

	//
	public void enable() {
		mainFrame.setEnabled(true);
	}

	public void disable() {
		mainFrame.setEnabled(false);
	}

	// Metodes canvi de vista
	public void canviarVistaInici() {
		layout.show(mainPanel, "VistaInici");
		mainFrame.setTitle("Iniciar sessió");
	}

	public void canviarVistaPrincipal() {
		layout.show(mainPanel, "VistaPrincipal");
		mainFrame.setTitle("Menú principal");
	}

	public void canviarVistaRegistrarse() {
		layout.show(mainPanel, "VistaRegistrarse");
		mainFrame.setTitle("Registrar-se");
	}

	public void canviarVistaGestioAlfabet() {
		vistaGestioAlfabet.updateList();
		layout.show(mainPanel, "VistaGestioAlfabet");
		mainFrame.setTitle("Gestió Alfabets");

	}

	public void canviarVistaCrearAlfabet() {
		layout.show(mainPanel, "VistaCrearAlfabet");
		mainFrame.setTitle("Crear Alfabets");

	}

	public void canviarVistaGestioTeclat() {
		vistaGestioTeclat.updateList();
		layout.show(mainPanel, "VistaGestioTeclat");
		mainFrame.setTitle("Gestió Teclats");
	}

	public void canviarVistaCrearTeclat() {
		vistaCrearTeclat.update();
		layout.show(mainPanel, "VistaCrearTeclat");
		mainFrame.setTitle("Crear Teclat");
	}

	public void canviarVistaVeureTeclat(String nom_teclat, String nom_alfabet, int cost_teclat,
			boolean alfabet_eliminat,
			boolean alfabet_modificat) {
		vistaVeureTeclat.setNomTeclat(nom_teclat);
		vistaVeureTeclat.setAlfabetEliminat(alfabet_eliminat);
		vistaVeureTeclat.setAlfabetModificat(alfabet_modificat);
		vistaVeureTeclat.setNomAlfabet(nom_alfabet);
		vistaVeureTeclat.setCostTeclat(cost_teclat);
		vistaVeureTeclat.update();
		layout.show(mainPanel, "VistaVeureTeclat");
		mainFrame.setTitle("Veure Teclat");
	}

	public void canviarVistaGestioLlistes(String nom_alfabet) {
		vistaGestioLlistes.setNomAlfabet(nom_alfabet);
		vistaGestioLlistes.update();
		layout.show(mainPanel, "VistaGestioLlistes");
		mainFrame.setTitle("Gestió Llistes");
	}

	public void canviarVistaCrearLlista(String nom_alfabet) {
		vistaCrearLlista.setNomAlfabet(nom_alfabet);
		layout.show(mainPanel, "VistaCrearLlista");
		mainFrame.setTitle("Crear Llista");
	}

	public void canviarVistaVeureLlista(String nom_alfabet, String nom_llista) {
		vistaVeureLlista.setNomAlfabet(nom_alfabet);
		vistaVeureLlista.setNomLlista(nom_llista);
		vistaVeureLlista.update();
		layout.show(mainPanel, "VistaVeureLlista");
		mainFrame.setTitle("Veure Llista");
	}

	public void canviarVistaModificarTeclat(String nom_teclat) {
		vistaModificarTeclat.setNomTeclat(nom_teclat);
		layout.show(mainPanel, "VistaModificarTeclat");
		mainFrame.setTitle("Modificar Teclat");
	}

	public void canviarVistaVeureAlfabet(String nom_alfabet) throws AlfabetNotFoundException {
		vistaVeureAlfabet.update(nom_alfabet, veureAlfabet(nom_alfabet));
		layout.show(mainPanel, "VistaVeureAlfabet");
		mainFrame.setTitle("Veure Alfabet");
	}

	public void canviarVistaModificarAlfabet(String nom_alfabet) throws AlfabetNotFoundException {
		vistaModificarAlfabet.update(nom_alfabet, veureAlfabet(nom_alfabet));
		layout.show(mainPanel, "VistaModificarAlfabet");
		mainFrame.setTitle("Modificar Alfabet");
	}

	// Metodes CtrlDomini

	public void registrarse(String username, String password) throws UsuariAlreadyExistsException {
		ctrldomini.registrarUsuari(username, password);
	}

	public Boolean iniciarSessio(String username, String password) throws UsuariNotFoundException {
		return ctrldomini.iniciarSessio(username, password);
	}

	public void tancarSessio() {
		ctrldomini.tancarSessio();
	}

	// Alfabet
	public void crearAlfabet(String nom, ArrayList<Character> llista_caracters)
			throws AlfabetAlreadyExistsException, InsufficientCharactersException, TooManyCharactersException {
		ctrldomini.crearAlfabet(nom, llista_caracters);
	}

	public String[] consultarAlfabets() {
		ArrayList<String> alfabets = ctrldomini.consultarAlfabets();
		return alfabets.toArray(new String[alfabets.size()]);
	}

	public ArrayList<Character> veureAlfabet(String nom) throws AlfabetNotFoundException {
		return ctrldomini.veureAlfabet(nom);
	}

	public void eliminarAlfabet(String nom) throws AlfabetNotFoundException, TeclatNotFoundException {
		ctrldomini.eliminarAlfabet(nom);
	}

	public Boolean afegirCaracter(String nom, Character c)
			throws AlfabetNotFoundException, TooManyCharactersException, TeclatNotFoundException {
		return ctrldomini.afegirCaracter(nom, c);
	}

	public void eliminarCaracter(String nom, Character c)
			throws AlfabetNotFoundException, InsufficientCharactersException, TeclatNotFoundException {
		ctrldomini.eliminarCaracter(nom, c);
	}

	public void importarAlfabet(File file)
			throws AlfabetAlreadyExistsException, InsufficientCharactersException, TooManyCharactersException,
			NoSuchElementException, NumberFormatException {
		ctrldomini.importarAlfabet(file);
	}

	// Llistes

	public void afegirLlistaParaulesAmbText(String nom_alfabet, String text, String nom_llista)
			throws AlfabetNotFoundException, LlistaAlreadyExistsException, LlistaNotFoundException,
			TeclatNotFoundException {
		ctrldomini.afegirLlistaParaulesAmbText(nom_alfabet, text, nom_llista);
	}

	public void afegirLlistaParaulesDonada(String nom_alfabet, String llista, String nom_llista)
			throws AlfabetNotFoundException, LlistaAlreadyExistsException, CharacterNotInAlphabetException,
			LlistaNotFoundException, TeclatNotFoundException {
		ctrldomini.afegirLlistaParaulesDonada(nom_alfabet, llista, nom_llista);
	}

	public String[] consultarLlistes(String nom_alfabet) {
		try {
			ArrayList<String> llistes = ctrldomini.consultarLlistes(nom_alfabet);
			return llistes.toArray(new String[llistes.size()]);
		} catch (Exception e) {
			return null;
		}
	}

	public String[][] getLlistaParaulaFreqString(String nom_alfabet, String nom_llista)
			throws AlfabetNotFoundException, LlistaNotFoundException {
		return ctrldomini.veureLlista(nom_alfabet, nom_llista);
	}

	public void eliminarLlista(String nom_alfabet, String nom_llista)
			throws AlfabetNotFoundException, LlistaNotFoundException, TeclatNotFoundException {
		ctrldomini.eliminarLlista(nom_alfabet, nom_llista);
	}

	public void importarLlista(String nom_alfabet, File file)
			throws AlfabetNotFoundException, LlistaAlreadyExistsException, CharacterNotInAlphabetException,
			TeclatNotFoundException, NoSuchElementException, NumberFormatException {
		ctrldomini.importarLlista(file, nom_alfabet);
	}

	// Teclat

	public void crearTeclatQAP(String nom_teclat, String nom_alfabet)
			throws TeclatAlreadyExistsException, AlfabetNotFoundException {
		ctrldomini.crearTeclatQAP(nom_teclat, nom_alfabet);
	}

	public void crearTeclatSA(String nom_teclat, String nom_alfabet)
			throws TeclatAlreadyExistsException, AlfabetNotFoundException {
		ctrldomini.crearTeclatSA(nom_teclat, nom_alfabet);
	}

	public String[] consultarTeclats() {
		ArrayList<String> teclats = ctrldomini.consultarTeclats();
		return teclats.toArray(new String[teclats.size()]);
	}

	public String consultarNomAlfabetTeclat(String nom_teclat)
			throws TeclatNotFoundException, AlfabetModificatOEliminatException {
		return ctrldomini.consultarNomAlfabetTeclat(nom_teclat);
	}

	public void eliminarTeclat(String nom)
			throws TeclatNotFoundException, AlfabetNotFoundException, AlfabetModificatOEliminatException {
		ctrldomini.eliminarTeclat(nom);
	}

	public ArrayList<ArrayList<Character>> veureTeclat(String nom) throws TeclatNotFoundException {
		return ctrldomini.veureTeclat(nom);
	}

	public boolean getAlfabetModificat(String nom) throws TeclatNotFoundException {
		return ctrldomini.getAlfabetModificat(nom);
	}

	public boolean getAlfabetEliminat(String nom) throws TeclatNotFoundException {
		return ctrldomini.getAlfabetEliminat(nom);
	}

	public void intercanviarCaractersTeclat(String nom_teclat, Character c1, Character c2)
			throws TeclatNotFoundException, CharacterNotInAlphabetException, AlfabetModificatOEliminatException {
		ctrldomini.intercanviarCaractersTeclat(nom_teclat, c1, c2);
	}

	public int consultarCostTeclat(String nom_teclat) throws TeclatNotFoundException {
		return ctrldomini.consultarCostTeclat(nom_teclat);
	}

	public void importarTeclat(File file)
			throws InsufficientCharactersException, TooManyCharactersException, TeclatAlreadyExistsException,
			NoSuchElementException, AlfabetNotFoundException {
		ctrldomini.importarTeclat(file);
	}

	public double costIntercanviarCaractersTeclat(String nom_teclat, Character c1, Character c2)
			throws TeclatNotFoundException, CharacterNotInAlphabetException, AlfabetModificatOEliminatException {
		return ctrldomini.costIntercanviarCaractersTeclat(nom_teclat, c1, c2);
	}

	public double costRexecutarSATeclat(String nom_teclat)
			throws TeclatNotFoundException, AlfabetNotFoundException {
		return ctrldomini.costRexecutarSATeclat(nom_teclat);
	}

	public void assignarCopiaSATeclat(String nom_teclat)
			throws TeclatNotFoundException, AlfabetNotFoundException, AlfabetModificatOEliminatException {
		ctrldomini.assignarCopiaSATeclat(nom_teclat);
	}
}