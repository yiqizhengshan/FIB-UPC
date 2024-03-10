package Persistence;

import Domain.Llista_Paraules;
import exceptions.CharacterNotInAlphabetException;

import java.io.*;
import java.util.*;

/**
 * Aquesta classe representa el gestor de les llistes de paraules.
 * S'encarrega de guardar, carregar i eliminar les llistes de paraules.
 * @author Yiqi
 */
public class GestorLlista_Paraules {
    public GestorLlista_Paraules() {
    }

    /**
     * Guarda la llista de paraules que se li passa per parametre a l'alfabet amb el nom donat i de l'usuari donat.
     * @param lp Llista de paraules que es vol guardar
     * @param nom_alfabet nom de l'alfabet del qual pertany la llista de paraules que es vol guardar
     * @param nom_llista nom de la llista de paraules que es vol guardar
     * @param usuari nom de l'usuari propietari de l'alfabet del qual pertany la llista de paraules que es vol guardar
     */
    public void guardarLlistaParaula(Llista_Paraules lp, String nom_alfabet, String nom_llista, String usuari) {
        String directori = System.getProperty("user.dir");
        directori = directori + "/data/Usuaris/" + usuari + "/Alfabets/" + nom_alfabet + "/Llistes_Frequencies/"; // /data/Usuaris/nomUsuari/Alfabets/nom_alfabet/Llistes_Frequencies/
        afegirLlistaParaula(lp, directori);
    }

    /**
     * Elimina la llista de paraules que té el nom donat de l'alfabet amb el nom donat i de l'usuari donat.
     * @param nom_alfabet nom de l'alfabet del qual pertany la llista de paraules que es vol esborrar
     * @param nom_llista nom de la llista de paraules que es vol esborrar
     * @param usuari nom de l'usuari propietari de l'alfabet del qual pertany la llista de paraules que es vol esborrar
     */
    public void eliminarLlistaParaulaNom(String nom_alfabet, String nom_llista, String usuari) {
        String directori = System.getProperty("user.dir");
        directori = directori + "/data/Usuaris/" + usuari + "/Alfabets/" + nom_alfabet + "/Llistes_Frequencies/"; // /data/Usuaris/nomUsuari/Alfabets/nom_alfabet/Llistes_Frequencies/
        eliminarLlistaParaules(nom_llista, directori);
    }

    /**
     * El HashMap de tipus String, Llista_Paraules que se li passa per parametre, es
     * guarda en un arxiu de tipus txt.
     * @param lp        HashMap de tipus String, Llista_Paraules que es vol guardar
     * @param FILE_PATH PATH de la carpeta on es volen guardar totes les llistes de
     *                  paraules
     */
    public void guardarLlistesParaules(HashMap<String, Llista_Paraules> lp, String FILE_PATH) {
        try {
            for (Llista_Paraules llista : lp.values()) {
                afegirLlistaParaula(llista, FILE_PATH);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * La instancia de Llista_Paraules que se li passa per parametre, es guarda en
     * un arxiu de tipus txt.
     * @param lp        Instancia de Llista_Paraules que es vol guardar
     * @param FILE_PATH PATH de la carpeta on es vol guardar la llista de paraules
     */
    private void afegirLlistaParaula(Llista_Paraules lp, String FILE_PATH) {
        try {
            String nomLlista = lp.getNom();

            // Guardar llista de paraules
            String rutaLlistaParaulesTxt = FILE_PATH + "/" + nomLlista + ".txt";
            File file = new File(rutaLlistaParaulesTxt);
            file.createNewFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(nomLlista);
                writer.println(lp.getSize());
                for (Map.Entry<String, Integer> entry : lp.getLlistaParaulaFreq().entrySet()) {
                    writer.println(entry.getKey() + " " + entry.getValue());
                }
            } catch (IOException e) {
                System.out.println("Error a l'afegir el fitxer " + nomLlista + ".txt: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Carrega totes les llistes de paraules que hi ha a la carpeta amb PATH
     * FILE_PATH.
     * 
     * @param FILE_PATH PATH de la carpeta on es volen guardar totes les llistes de
     *                  paraules
     * @return HashMap de tipus String, Llista_Paraules amb totes les llistes de
     *         paraules que hi ha a la carpeta amb PATH FILE_PATH
     */
    public HashMap<String, Llista_Paraules> carregarLlistaParaules(String FILE_PATH) {
        HashMap<String, Llista_Paraules> cjtLlistes = new HashMap<String, Llista_Paraules>();

        try {
            File folder = new File(FILE_PATH);
            File[] lp = folder.listFiles();

            for (File file : lp) {
                Llista_Paraules llista = carreglarLlistaIndividual(file);
                if (llista != null) cjtLlistes.put(llista.getNom(), llista);
                else System.out.println("Error al carregar la llista de paraules " + file.getName());
            }
            return cjtLlistes;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Carrega la llista de paraules que hi ha a l'arxiu amb PATH FILE_PATH.
     * @param file Arxiu que es vol carregar
     * @return Instancia de Llista_Paraules amb la llista de paraules que hi ha a l'arxiu amb PATH FILE_PATH
     */
    private Llista_Paraules carreglarLlistaIndividual(File file) {
        Llista_Paraules llista = null;
        if (file.isFile()) {
            try {
                Scanner scanner = new Scanner(file);

                String nom = scanner.nextLine();
                int size = Integer.parseInt(scanner.nextLine());
                HashMap<String, Integer> llista_paraula_freq = new HashMap<String, Integer>();

                for (int i = 0; i < size; i++) {
                    String[] paraula_freq = scanner.nextLine().split(" ");
                    llista_paraula_freq.put(paraula_freq[0], Integer.parseInt(paraula_freq[1]));
                }

                llista = new Llista_Paraules(nom);
                llista.setLlistaParaulaFreq(llista_paraula_freq);
                scanner.close();
            } catch (IOException e) {
                System.out.println("Error a l'afegir el fitxer " + file.getName() + ".txt: " + e.getMessage());
            }
        }
        return llista;
    }

    /**
     * Carrega la llista de paraules que hi ha a l'arxiu amb PATH FILE_PATH.
     * @param file Arxiu que es vol carregar
     * @param alfabet Alfabet amb el qual es vol comprovar que les paraules de la llista de paraules pertanyen a l'alfabet
     * @return Instancia de Llista_Paraules amb la llista de paraules que hi ha a l'arxiu amb PATH FILE_PATH
     * @throws CharacterNotInAlphabetException Si alguna paraula de la llista de paraules no pertany a l'alfabet
     * @throws NoSuchElementException Si alguna paraula de la llista de paraules no te el format correcte
     * @throws NumberFormatException Si vols passar a int una paraula que no es un numero
     */
    public Llista_Paraules carregarFile(File file, ArrayList<Character> alfabet) throws CharacterNotInAlphabetException, NoSuchElementException, NumberFormatException {
        Llista_Paraules llista = null;
        if (file.isFile()) {
            try {
                Scanner scanner = new Scanner(file);

                String nom = scanner.nextLine();
                int size = Integer.parseInt(scanner.nextLine());
                HashMap<String, Integer> llista_paraula_freq = new HashMap<String, Integer>();

                for (int i = 0; i < size; i++) {
                    String[] paraula_freq = scanner.nextLine().split(" ");
                    if (paraula_freq.length != 2) {
                        scanner.close();
                        throw new NoSuchElementException("La paraula " + paraula_freq[0] + "te un format incorrecte");
                    }
                    //devuelve true si pertenece al alfabeto
                    if (!pertanyAlfabet(paraula_freq[0], alfabet)) {
                        scanner.close();
                        throw new CharacterNotInAlphabetException("La paraula " + paraula_freq[0] + "te un caracter que no es troba a l'alfabet");
                    }
                    llista_paraula_freq.put(paraula_freq[0], Integer.parseInt(paraula_freq[1]));
                }

                llista = new Llista_Paraules(nom);
                llista.setLlistaParaulaFreq(llista_paraula_freq);
                scanner.close();

            } catch (IOException e) {
                System.out.println("Error a l'afegir el fitxer " + file.getName() + ".txt: " + e.getMessage());
            }
        }
        return llista;
    }

    /**
     * Elimina la llista de paraules amb el nom donat.
     * 
     * @param nom       Nom de la llista de paraules que es vol eliminar
     * @param FILE_PATH PATH de la carpeta on es vol esborrar la llista de paraules
     */
    private void eliminarLlistaParaules(String nom, String FILE_PATH) {
        try {
            String rutaLlistaParaulesTxt = FILE_PATH + "/" + nom + ".txt";
            File file = new File(rutaLlistaParaulesTxt);
            file.delete();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Comprova si la paraula pertany a l'alfabet
     * @param paraula Paraula que es vol comprovar
     * @param alfabet Alfabet a comprovar
     * @return True si la paraula pertany a l'alfabet, false altrament
     */
    private boolean pertanyAlfabet(String paraula, ArrayList<Character> alfabet) {
        for (int i = 0; i < paraula.length(); i++) {
            if (!alfabet.contains(paraula.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
