package Persistence;

import Domain.Alfabet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.NoSuchElementException;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

import exceptions.InsufficientCharactersException;
import exceptions.TooManyCharactersException;

/**
 * Gestor que guarda i carrega els alfabets de la capa de persistencia
 * i també de fitxers donats per l'usuari.
 * 
 * @author Dídac
 */
public class GestorAlfabet {
	private GestorLlista_Paraules gestorllista = new GestorLlista_Paraules();

	/**
	 * Guarda l'Alfabet donat a la carpeta Alfabet de la carpeta de l'usuari amb el
	 * username donat
	 * 
	 * @param alfabet  Alfabet a guardar.
	 * @param username Nom de l'usuari.
	 */
	public void guardarAlfabet(Alfabet alfabet, String username) {
		String directori = System.getProperty("user.dir");
		directori = directori + "/data/Usuaris/" + username + "/Alfabets";
		String nomalf = alfabet.getNom();
		try {
			// Conseguim informacio de l'alfabet per a crear/sobrescriure directori i el
			// .txt
			String nom = alfabet.getNom();
			String directoriAlfabet = directori + '/' + nom;
			ArrayList<Character> caracters = alfabet.getCaracters();

			// Creem el file i directoris pare si no existeix, es pot fer el if(!exists())
			File file = new File(directoriAlfabet, nom + ".txt");
			if (!file.getParentFile().exists())
				file.getParentFile().mkdir();
			if (!file.exists())
				file.createNewFile();
			String directori_llistes = directoriAlfabet + "/" + "Llistes_Frequencies";
			File file_directori_llistes = new File(directori_llistes);
			if (!file_directori_llistes.exists())
				file_directori_llistes.mkdir();
			gestorllista.guardarLlistesParaules(alfabet.getCjtLlistes(), directori_llistes);

			// Escrivim o sobreescivim el file.
			try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) { // false per a sobreesciure
				writer.println(nom);
				writer.println(caracters.size());
				for (int i = 0; i < caracters.size(); ++i) {
					writer.print(caracters.get(i));
					writer.print(' ');
				}

			} catch (IOException e) {
				System.out.println("Error al sobrescriure: " + nom + ".txt" + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("Error creant el file de l'alfabet " + nomalf + ": " + e.getMessage());
		}

	}

	/**
	 * Carrega els Alfabets guardats a la carpeta Alfabets de l'usuari amb el
	 * username donat
	 * 
	 * @param username Nom de l'usuari
	 */
	public HashMap<String, Alfabet> carregarAlfabets(String username) {
		try {
			HashMap<String, Alfabet> cjt_alfabet = new HashMap<String, Alfabet>();
			// Directori base d'alfabets
			File directori = new File(System.getProperty("user.dir") + "/data/Usuaris/" + username + "/Alfabets");
			File[] alfabets = null;
			if (directori.exists()) {
				alfabets = directori.listFiles();
			}
			for (File alfabet : alfabets) {
				File alf = new File(alfabet + "/" + alfabet.getName() + ".txt");
				String llistes = alfabet.getAbsolutePath() + "/" + "Llistes_Frequencies";
				Alfabet objecte_alfabet = carregarFitxer(alf);
				objecte_alfabet.setCjtLlistes(gestorllista.carregarLlistaParaules(llistes));
				String nom = objecte_alfabet.getNom();
				cjt_alfabet.put(nom, objecte_alfabet);
			}
			return cjt_alfabet;
		} catch (Exception e) {
			System.out.println("Error al carregar alfabets");
			return null;
		}
	}

	/**
	 * Elimina el fitxer que conté l'alfabet amb el nom donat i de l'usuari amb el
	 * username donat.
	 * 
	 * @param nom_alfabet Nom de l'alfabet a eliminar.
	 * @param username    Nom de l'usuari al que pertany l'alfabet.
	 */
	public void eliminarAlfabet(String nom_alfabet, String username) {
		try {
			// Directori base d'alfabets
			String directori = System.getProperty("user.dir");
			directori = directori + "/data/Usuaris/" + username + "/Alfabets";
			String directoriAlfabet = directori + '/' + nom_alfabet;
			File file = new File(directoriAlfabet);
			eliminarDirectori(file);
		} catch (Exception e) {
			System.out.println("Error creant el file: " + e.getMessage());
		}
	}

	/**
	 * Elimina el fitxer donat, i si es un directori elimina primer tots els fitxers
	 * continguts.
	 * 
	 * @param file Fitxer a eliminar
	 */
	private void eliminarDirectori(File file) {
		try {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File f : files) {
					eliminarDirectori(f);
				}
			}
			file.delete();

		} catch (Exception e) {
			System.out.println("Error eliminant el file: " + e.getMessage());

		}
	}

	/**
	 * Carrega l'alfabet contingut al fitxer donat.
	 * 
	 * @param file Fitxer amb l'alfabet.
	 * @return Alfabet que hi havia al fitxer.
	 * @throws InsufficientCharactersException Si l'alfabet no té suficients
	 *                                         caràctes.
	 * @throws TooManyCharactersException      Si l'alfabet té més de 36 caràcters.
	 * @throws NoSuchElementException          Si el format del fitxer és
	 *                                         incorrecte.
	 * @throws NumberFormatException           Si el format del fitxer és
	 *                                         incorrecte.
	 */
	public Alfabet carregarFitxer(File file) throws InsufficientCharactersException, TooManyCharactersException,
			NoSuchElementException, NumberFormatException {
		try {
			Scanner scanner = new Scanner(file);
			ArrayList<String> caracter = new ArrayList<String>();
			ArrayList<Character> caracters = new ArrayList<Character>();
			String nom = scanner.nextLine();
			int num_caracters = Integer.parseInt(scanner.nextLine());
			for (int i = 0; i < num_caracters; ++i) {
				caracter.add(scanner.next());
				caracters.add(caracter.get(i).trim().charAt(0));
			}
			Alfabet objecte_alfabet = new Alfabet(nom, caracters);
			scanner.close();
			return objecte_alfabet;
		} catch (IOException e) {
			System.out.println("Error al llegir el fitxer " + file.getName() + ": " + e.getMessage());
			return null;
		}
	}
}