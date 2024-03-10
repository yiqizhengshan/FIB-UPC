package Persistence;

import Domain.Teclat;
import exceptions.InsufficientCharactersException;
import exceptions.TooManyCharactersException;
import Domain.Alfabet;
import Domain.CtrlDomini;

import java.io.*;
import java.util.*;

/**
 * GestorTeclat és una classe encarregada de gestionar la creació,
 * emmagatzematge i càrrega de teclats en el sistema. Proporciona mètodes per
 * desar i carregar informació d'usuaris en carpetes específiques i arxius
 * associats.
 * 
 * @author Oscar
 */
public class GestorTeclat {
    public GestorTeclat() {
    }

    /**
     * Crea una fitxer amb el nom del teclat al directori Teclats de la carpeta
     * /data/Usuaris/usuari, on guarda la informació del teclat.
     *
     * @param teclat teclat a guardar
     * @param usuari nom de l'usuari
     */
    public void guardarTeclat(Teclat teclat, String usuari) {
        String directori = System.getProperty("user.dir");
        directori = directori + "/data/Usuaris/" + usuari + "/Teclats/";
        afegirTeclat(teclat, directori);
    }

    /**
     * Elimina elf fitxer amb el nom del teclat al directori Teclats de la carpeta
     * /data/Usuaris/usuari, on es guarda la informació del teclat.
     *
     * @param nom_teclat teclat a guardar
     * @param usuari nom de l'usuari
     */
    public void eliminarTeclatNom(String nom_teclat, String usuari) {
        String directori = System.getProperty("user.dir");
        directori = directori + "/data/Usuaris/" + usuari + "/Teclats/";
        eliminarTeclat(nom_teclat, directori);
    }

    /**
     * Carrega els teclats guardats a la carpeta /data/Usuaris/usuari/Teclats
     *
     * @return HashMap del conjunt de teclats
     */
    public HashMap<String, Teclat> carregarTeclats(String usuari) {
        CtrlDomini ctrldomini = CtrlDomini.getInstance();
        HashMap<String, Teclat> cjt_teclat = new HashMap<String, Teclat>();
        try {
            String directori = System.getProperty("user.dir");
            directori = directori + "/data/Usuaris/" + usuari + "/Teclats/";
            File folder = new File(directori);

            File[] teclats = folder.listFiles();
            for (File file : teclats) {
                if (file.isFile()) {
                    try {
                        // Llegeix la informació del teclat del fitxer
                        Scanner scanner = new Scanner(file);
                        String nom = scanner.nextLine();
                        String alf_modificat = scanner.nextLine();
                        String alf_eliminat = scanner.nextLine();
                        int size = Integer.parseInt(scanner.nextLine());
                        String nomAlfabet = scanner.nextLine();
                        ArrayList<ArrayList<Character>> layout = new ArrayList<ArrayList<Character>>();
                        for (int i = 0; i < size; i++) {
                            String[] fila = scanner.nextLine().split(",");
                            ArrayList<Character> fila_layout = new ArrayList<Character>();
                            for (int j = 0; j < fila.length; j++) {
                                fila_layout.add(fila[j].charAt(0));
                            }
                            layout.add(fila_layout);
                        }
                        // Crea el teclat i l'afegeix al conjunt de teclats
                        if (nomAlfabet.equals("importar_teclat")) {
                            Teclat teclat = new Teclat(nom, layout);
                            cjt_teclat.put(nom, teclat);
                            teclat.setAlfabetEliminat(true);
                            teclat.setAlfabetModificat(false);
                        } else if (alf_eliminat.equals("true")) {
                            // Si l'alfabet ha estat eliminat, es crea un alfabet dummy
                            // amb el mateix nom que l'alfabet previ
                            Alfabet alfabet = new Alfabet(nomAlfabet,
                                    new ArrayList<Character>(Arrays.asList('a', 'b')));
                            Teclat teclat = new Teclat(nom, alfabet, layout,false,true);
                            cjt_teclat.put(nom, teclat);
                        } else {
                            Alfabet alfabet = ctrldomini.getAlfabet(nomAlfabet);
                            Teclat teclat = new Teclat(nom, alfabet, layout,alf_modificat.equals("true"),false);
                            alfabet.afegirTeclat(nom);
                            cjt_teclat.put(nom, teclat);
                        }

                        scanner.close();
                    } catch (IOException e) {
                        System.out
                                .println("Error al carregar el fitxer: " + file.getName() + ".txt: " + e.getMessage());
                    }
                }
            }
            return cjt_teclat;
        } catch (Exception e) {
            System.out.println("Error al carregar teclats: " + e.getMessage());
            return null;
        }
    }

    /**
     * Crea una fitxer amb el nom del teclat al directori FILE_PATH on es guardara
     * la informació del teclat.
     *
     * @param teclat    teclat a guardar
     * @param FILE_PATH nom de l'usuari
     */
    private void afegirTeclat(Teclat teclat, String FILE_PATH) {
        try {
            String nom_teclat = teclat.getNom();
            String rutaTeclatTxt = FILE_PATH + nom_teclat + ".txt";
            File file = new File(rutaTeclatTxt);
            file.createNewFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                ArrayList<ArrayList<Character>> layout = teclat.getLayout();
                writer.println(nom_teclat);
                writer.println(teclat.getAlfabetModificat());
                writer.println(teclat.getAlfabetEliminat());
                writer.println(layout.size());
                writer.println(teclat.getNomAlfabet());
                for (int i = 0; i < layout.size(); ++i) {
                    for (int j = 0; j < layout.get(i).size(); ++j) {
                        if (j > 0) {
                            writer.print(",");
                        }
                        writer.print(layout.get(i).get(j));
                    }
                    writer.println();
                }
            } catch (IOException e) {
                System.out.println("Error a l'afegir el fitxer: " + nom_teclat + ".txt: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina elf fitxer amb el nom del teclat al directori FILE_PATH on es guarda
     * la informació del teclat.
     *
     * @param nom_teclat    teclat a guardar
     * @param FILE_PATH nom de l'usuari
     */
    private void eliminarTeclat(String nom_teclat, String FILE_PATH) {
        try {
            String rutaTeclatTxt = FILE_PATH + nom_teclat + ".txt";
            File file = new File(rutaTeclatTxt);

            if (!file.delete()) {
                System.out.println("Error al eliminar el fitxer " + nom_teclat + ".txt");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Carrega l'alfabet contingut al fitxer donat.
     * 
     * @param file Fitxer amb l'alfabet.
     * @return El Teclat que hi havia al fitxer.
     */
    public Teclat carregarFitxer(File file) throws InsufficientCharactersException, TooManyCharactersException,
            NoSuchElementException, NumberFormatException {
        try {
            Scanner scanner = new Scanner(file);
            String nom = scanner.nextLine();
            int size = Integer.parseInt(scanner.nextLine());
            ArrayList<ArrayList<Character>> layout = new ArrayList<ArrayList<Character>>();
            for (int i = 0; i < size; i++) {
                String[] fila = scanner.nextLine().split(",");
                ArrayList<Character> fila_layout = new ArrayList<Character>();
                for (int j = 0; j < fila.length; j++) {
                    fila_layout.add(fila[j].charAt(0));
                }
                layout.add(fila_layout);
            }
            scanner.close();
            return new Teclat(nom, layout);
        } catch (IOException e) {
            System.out.println("Error al llegir el fitxer " + file.getName() + ": " + e.getMessage());
            return null;
        }
    }
}
