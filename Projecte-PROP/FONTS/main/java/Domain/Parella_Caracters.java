package Domain;
import java.util.*;

import exceptions.MatriuParellaCaractersNotValidException;

/**
 * Classe que representa les parelles de caracters que es formen a partir de les paraules d'una llista de paraules.
 * @author Yiqi
 */
public class Parella_Caracters {
    // ATRIBUTS

    /**
     * Llista amb els caracters i la posicio que els representa.
     */
    private HashMap<Character, Integer> ordre_caracters;

    /**
     * Matriu de parelles de caracters amb les seves frequencies tenint en compte la posicio de la llista ordre_caracters.
     */
    private ArrayList<ArrayList<Integer>> matriu_parella_caracters;

    // CONSTRUCTORES

    /**
     * Constructora per defecte que inicialitza la matriu de parelles de caracters amb l'input donat.
     * @param alfabet Alfabet a partir del qual es construeix tot.
     * @param llista_paraula_freq Llista de paraules amb les seves frequencies.
     */
    public Parella_Caracters(ArrayList<Character> alfabet, HashMap<String, Integer> llista_paraula_freq) {
        this.ordre_caracters = alfabetToHashMap(alfabet);
        this.matriu_parella_caracters = creaMatriuParelles(llista_paraula_freq);
    }

    // COPIADORES

    /**
     * Constructora que inicialitza la classe Parella_Caracters a partir d'una altra Parella_Caracters.
     * @param lpc Parella_Caracters que es vol copiar.
     */
    public Parella_Caracters(Parella_Caracters lpc) {
        this.ordre_caracters = new HashMap<Character, Integer>(lpc.ordre_caracters);
        this.matriu_parella_caracters = new ArrayList<ArrayList<Integer>>(lpc.matriu_parella_caracters);
    }

    // GETTERS


    /**
     * Obté la matriu de parelles de caracters.
     * @return Matriu de parelles de caracters.
     */
    public ArrayList<ArrayList<Integer>> getMatriuParellaCaracters() {
        return this.matriu_parella_caracters;
    }

    /**
     * Obté la llista de caracters amb la seva posicio.
     * @return Llista de caracters amb la seva posicio.
     */
    public HashMap<Character, Integer> getOrdreCaracters() {
        return this.ordre_caracters;
    }

    /**
     * Obté la llista de caracters amb la seva posicio en forma d'array.
     * @return Llista de caracters amb la seva posicio en forma d'array.
     */
    public ArrayList<Character> getArrayOrdreCaracters() {
        ArrayList<Character> array_ordre_caracters = new ArrayList<Character>(this.ordre_caracters.size());

        for (Map.Entry<Character, Integer> entrada : this.ordre_caracters.entrySet()) {
            array_ordre_caracters.add(entrada.getValue(), entrada.getKey());
        }

        return array_ordre_caracters;
    }

    // SETTERS

    /**
     * Modifica la matriu de parelles de caracters.
     * @param new_matriu_parella_caracters Matriu de parelles de caracters que es vol assignar.
     * @throws MatriuParellaCaractersNotValidException Si la matriu no te el mateix tamany que l'alfabet.
     */
    public void setMatriuParellaCaracters(ArrayList<ArrayList<Integer>> new_matriu_parella_caracters) throws MatriuParellaCaractersNotValidException {
        if (new_matriu_parella_caracters.size() != this.ordre_caracters.size()) throw new MatriuParellaCaractersNotValidException("La matriu no te el mateix tamany que l'alfabet");
        else this.matriu_parella_caracters = new_matriu_parella_caracters;
    }

    /**
     * Modifica la llista de caracters amb la seva posicio.
     * @param new_ordre_caracters Llista de caracters amb la seva posicio que es vol assignar.
     */
    public void setOrdreCaracters(HashMap<Character, Integer> new_ordre_caracters) {
        this.ordre_caracters = new_ordre_caracters;
    }

    // METODES

    /**
     * Actualitza la llista de caracters amb la seva posicio i la matriu de parelles de caracters.
     * @param alfabet Alfabet a partir del qual es construeix tot.
     * @param llista_paraula_freq Llista de paraules amb les seves frequencies.
     */
    public void actualitzar(ArrayList<Character> alfabet, HashMap<String, Integer> llista_paraula_freq) {
        this.ordre_caracters = alfabetToHashMap(alfabet);
        this.matriu_parella_caracters = creaMatriuParelles(llista_paraula_freq);
    }

    // AUXILIARY FUNCTIONS

    /**
     * Donat un alfabet, crea un HashMap amb els caracters i la seva posicio.
     * @param alfabet Alfabet a partir del qual es construeix el HashMap.
     * @return HashMap amb els caracters i la seva posicio.
     */
    private HashMap<Character, Integer> alfabetToHashMap(ArrayList<Character> alfabet) {
        HashMap<Character, Integer> ordre_caracters = new HashMap<Character, Integer>();

        for (int i = 0; i < alfabet.size(); ++i) {
            ordre_caracters.put(alfabet.get(i), i);
        }

        return ordre_caracters;
    }

    /**
     * Donat un HashMap, crea la matriu de parella de caracters.
     * @param llista_paraula_freq Llista de paraules amb les seves frequencies.
     * @return Matriu de parelles de caracters amb les seves frequencies tenint en compte la posicio de la llista ordre_caracters.
     */
    private ArrayList<ArrayList<Integer>> creaMatriuParelles(HashMap<String, Integer> llista_paraula_freq) {

        ArrayList<ArrayList<Integer>> matriu_parella_caracters = new ArrayList<ArrayList<Integer>>();
        int n = this.ordre_caracters.size();

        // Inicialitzar la matriu de tamany n x n amb 0s
        for (int i = 0; i < n; ++i) {
            ArrayList<Integer> parella_caracters = new ArrayList<Integer>();
            for (int j = 0; j < n; ++j) {
                parella_caracters.add(0);
            }
            matriu_parella_caracters.add(parella_caracters);
        }

        // Omplir la matriu
        for (Map.Entry<String, Integer> entrada : llista_paraula_freq.entrySet()) {
            // Primer agafar la paraula i la freq
            String paraula = entrada.getKey();
            int freq = entrada.getValue();

            // Segon agafar els caracters de la paraula per parelles i sumar la freq a la posicio de la matriu segons odre_caracters
            if (paraula.length() != 1) {
                for (int i = 0; i < paraula.length() - 1; ++i) {
                    int pos1 = this.ordre_caracters.get(paraula.charAt(i));
                    int pos2 = this.ordre_caracters.get(paraula.charAt(i + 1));

                    // fa falta? la matriu seria simetrica. PREGUNTAR OSCAR
                    if (pos1 != pos2) {
                        matriu_parella_caracters.get(pos1).set(pos2, matriu_parella_caracters.get(pos1).get(pos2) + freq);
                        matriu_parella_caracters.get(pos2).set(pos1, matriu_parella_caracters.get(pos2).get(pos1) + freq);
                    }
                }
            }
        }

        return matriu_parella_caracters;
    }
}
