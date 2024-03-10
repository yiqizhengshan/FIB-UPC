package Persistence;

import Domain.Usuari;

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * GestorUsuari és una classe encarregada de gestionar la creació,
 * emmagatzematge i càrrega d'usuaris en el sistema. Proporciona mètodes per
 * desar i carregar informació d'usuaris en carpetes específiques i arxius
 * associats.
 * 
 * @author Jordi
 */
public class GestorUsuari {
    public GestorUsuari() {
        try {
            String directoriBase = System.getProperty("user.dir") + "/data/Usuaris/";
            File directoriData = new File(directoriBase);
            directoriData.mkdirs();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Crea una carpeta amb el nom de l'usuari al directori Usuaris de la carpeta
     * data, on guarda la informació de l'usuari
     * i crea els subdirectoris d'Alfabet i Teclat, junt amb un fitxer: password.txt
     * que conte la contrasenya de l'usuari.
     *
     * @param usuari l'usuari a guardar.
     */
    public void guardarUsuari(Usuari usuari) {
        try {
            String directoriBase = System.getProperty("user.dir") + "/data/Usuaris";
            File directorioBase = new File(directoriBase);

            directorioBase.mkdirs();

            String nomUsuari = usuari.getUsername();
            String rutaUsuari = directoriBase + "/" + nomUsuari;
            File directoriUsuari = new File(rutaUsuari);

            // Crea el subdirectori de l'usuari
            if (directoriUsuari.mkdir()) {
                // Crear subdirectorios adicionales
                String rutaAlfabets = rutaUsuari + "/Alfabets";
                String rutaTeclats = rutaUsuari + "/Teclats";

                File directoriAlfabets = new File(rutaAlfabets);
                File directoriTeclats = new File(rutaTeclats);
                directoriAlfabets.mkdir();
                directoriTeclats.mkdir();

            }

            String rutaPassword = rutaUsuari + "/password.txt";
            File archivoPassword = new File(rutaPassword);
            archivoPassword.createNewFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(archivoPassword))) {
                writer.println(usuari.getPassword());
            } catch (IOException e) {
                System.out
                        .println("Error a l'escriure el fitxer password.txt per " + nomUsuari + ": " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Carrega els usuaris guardats a la carpeta Usuaris
     *
     * @return HashMap del conjunt usuari
     */
    public HashMap<String, Usuari> carregarUsuaris() {
        try {
            HashMap<String, Usuari> cjt_usuaris = new HashMap<String, Usuari>();
            File directori = new File(System.getProperty("user.dir") + "/data/Usuaris/");

            File[] password = null;
            // per cada usuari es carrega el seu password i el seu nom
            for (File usuari : directori.listFiles()) {
                if (usuari.isDirectory()) {
                    password = usuari.listFiles();
                    String username = usuari.getName();
                    String password_usuari = null;
                    for (File fitxer : password) {
                        if (fitxer.getName().equals("password.txt")) {
                            Scanner scanner = new Scanner(fitxer);
                            password_usuari = scanner.nextLine();
                            scanner.close();
                        }
                    }
                    Usuari objecte_usuari = new Usuari(username, password_usuari);
                    cjt_usuaris.put(username, objecte_usuari);
                }
            }
            return cjt_usuaris;

        } catch (FileNotFoundException e) {
            System.out.println("Error al carregar usuaris: Fitxer no trobat");
            return null;
        } catch (Exception e) {
            System.out.println("Error al carregar usuaris: " + e.getMessage());
            return null;
        }
    }
}