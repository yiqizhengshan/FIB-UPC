package Domain;

import java.util.*;

import exceptions.CharacterNotInAlphabetException;

/**
 * Classe que representa una llista de paraules amb les seves frequencies.
 * @author Yiqi
 */
public class Llista_Paraules {
    // ATRIBUTS

    /**
     * Nom de la llista de paraules.
     */
    private String nom;

    /**
     * Mida de la llista de paraules.
     */
    private int size;

    /**
     * Llista de paraules amb les seves frequencies.
     */
    private HashMap<String, Integer> llista_paraula_freq;

    // CONSTUCTORES

    /**
     * Constructora per defecte que inicialitza la llista de paraules buida amb el nom donat.
     * @param nom
     */
    public Llista_Paraules(String nom) {
        this.nom = nom;
        this.size = 0;
        this.llista_paraula_freq = new HashMap<String, Integer>();
    }

    // COPIADORES

    /**
     * Constructora que inicialitza la llista de paraules a partir d'una altra llista de paraules.
     * @param lp Llista de paraules que es vol copiar.
     */
    public Llista_Paraules(Llista_Paraules lp) {
        this.nom = lp.nom;
        this.size = lp.size;
        this.llista_paraula_freq = new HashMap<String, Integer>(lp.llista_paraula_freq);
    }

    // GETTERS

    /**
     * Obte les paraules de la llista de paraules sense frequencies.
     * @return Array de paraules de la llista de paraules.
     */
    public String[] getParaules() {
        return this.llista_paraula_freq.keySet().toArray(new String[0]);
    }

    /**
     * Obte el nom de la llista de paraules.
     * @return Nom de la llista de paraules.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Obte la mida de la llista de paraules.
     * @return Mida de la llista de paraules.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Obte la llista de paraules amb les seves frequencies.
     * @return Llista de paraules amb les seves frequencies.
     */
    public HashMap<String, Integer> getLlistaParaulaFreq() {
        return this.llista_paraula_freq;
    }

    /**
     * Obte la llista de paraules amb les seves frequencies en format String.
     * @return Llista de paraules amb les seves frequencies en format String.
     */
    public String[][] getLlistaParaulaFreqString() {
        String[][] llista = new String[this.size][2];
        int i = 0;
        for (Map.Entry<String, Integer> entry : this.llista_paraula_freq.entrySet()) {
            llista[i][0] = entry.getKey();
            llista[i][1] = entry.getValue().toString();
            ++i;
        }
        return llista;
    }

    // SETTERS

    /**
     * Modifica el nom de la llista de paraules.
     * @param nom Nom de la llista de paraules que es vol assignar.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifica la llista de paraules amb les seves frequencies.
     * @param llista_paraula_freq Llista de paraules amb les seves frequencies que es vol assignar.
     */
    public void setLlistaParaulaFreq(HashMap<String, Integer> llista_paraula_freq) {
        this.llista_paraula_freq = llista_paraula_freq;
        this.size = this.llista_paraula_freq.size();
    }

    // METODES

    /**
     * La llista de paraules-frequencies es forma a partir d'un text.
     * @param text Text del qual es volen extreure les paraules.
     * @param a Alfabet del text.
     */
    public void afegirLlistaText(String text, ArrayList<Character> a) {
        this.llista_paraula_freq = stringToHashMap(text, a);
        this.size = this.llista_paraula_freq.size();
    }

    /**
     * La llista de paraules-frequencies es forma a partir d'una llista de paraules amb les seves frequencies.
     * @param llista Llista de paraules amb les seves frequencies.
     * @param a Alfabet de la llista de paraules.
     * @throws CharacterNotInAlphabetException Si algun caracter de la llista no pertany a l'alfabet.
     */
    public void afegirLlistaDonada(String llista, ArrayList<Character> a) throws CharacterNotInAlphabetException{
        this.llista_paraula_freq = llistaToHashMap(llista, a);
        this.size = this.llista_paraula_freq.size();
    }

    /**
     * Esborra de la llista de paraules-frequencies les paraules que contenen un caracter donat.
     * @param c Caracter que es vol eliminar de la llista de paraules-frequencies.
     */
    public void actualitzarFreq(Character c) {
        Iterator<String> iterator = this.llista_paraula_freq.keySet().iterator();

        while (iterator.hasNext()) {
            String paraula = iterator.next();
            if (paraula.contains(c.toString())) {
                iterator.remove(); // Utiliza el iterador para eliminar el elemento actual
            }
        }

        this.size = this.llista_paraula_freq.size();
    }

    // AUXILIARY FUNCTIONS

    /**
     * Funcio que transforma un text en un HashMap de <paraules, freq>
     * @param paraules Text en format string.
     * @param a Alfabet del text.
     * @return HashMap de <paraules, freq>.
     */
    private HashMap<String, Integer> stringToHashMap(String paraules, ArrayList<Character> a) {
        HashSet<Character> alfabet = new HashSet<Character>(a);
        HashMap<String, Integer> caracters = new HashMap<String, Integer>();
        String aux = "";
        String accents = "àáèéíìóòúù";
        String accents_majuscules = "ÀÁÈÉÍÌÓÒÚÙ";
        String majuscules = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < paraules.length(); ++i) {
            if (alfabet.contains(paraules.charAt(i)) && i != paraules.length() - 1) {
                aux = aux + paraules.charAt(i);
            }
            // mirem si el caracter es un accent
            else if (accents.contains(paraules.charAt(i) + "") && i != paraules.length() - 1) {
                Character c = paraules.charAt(i);
                if ((c == 'à' || c == 'á') && alfabet.contains('a')) aux = aux + 'a';
                else if ((c == 'è' || c == 'é') && alfabet.contains('e')) aux = aux + 'e';
                else if ((c == 'í' || c == 'ì') && alfabet.contains('i')) aux = aux + 'i';
                else if ((c == 'ó' || c == 'ò') && alfabet.contains('o')) aux = aux + 'o';
                else if ((c == 'ú' || c == 'ù') && alfabet.contains('u')) aux = aux + 'u';
            }
            //mirem si el caracter es una majuscula
            else if (majuscules.contains(paraules.charAt(i) + "") && i != paraules.length() - 1) {
                Character c = paraules.charAt(i);
                if (alfabet.contains(Character.toLowerCase(c))) aux = aux + Character.toLowerCase(c);
            }
            //mirem si el caracter es una majuscula amb accent
            else if (accents_majuscules.contains(paraules.charAt(i) + "") && i != paraules.length() - 1) {
                Character c = paraules.charAt(i);
                if ((c == 'À' || c == 'Á') && alfabet.contains('a')) aux = aux + 'a';
                else if ((c == 'È' || c == 'É') && alfabet.contains('e')) aux = aux + 'e';
                else if ((c == 'Í' || c == 'Ì') && alfabet.contains('i')) aux = aux + 'i';
                else if ((c == 'Ó' || c == 'Ò') && alfabet.contains('o')) aux = aux + 'o';
                else if ((c == 'Ú' || c == 'Ù') && alfabet.contains('u')) aux = aux + 'u';
            }

            //estem al cas en que estem mirant l'ultim caracter
            else {
                if (i == paraules.length() - 1 && alfabet.contains(paraules.charAt(i))) {
                    aux = aux + paraules.charAt(i);
                }
                else if (i == paraules.length() - 1 && accents.contains(paraules.charAt(i) + "")) {
                    Character c = paraules.charAt(i);
                    if ((c == 'à' || c == 'á') && alfabet.contains('a')) aux = aux + 'a';
                    else if ((c == 'è' || c == 'é') && alfabet.contains('e')) aux = aux + 'e';
                    else if ((c == 'í' || c == 'ì') && alfabet.contains('i')) aux = aux + 'i';
                    else if ((c == 'ó' || c == 'ò') && alfabet.contains('o')) aux = aux + 'o';
                    else if ((c == 'ú' || c == 'ù') && alfabet.contains('u')) aux = aux + 'u';
                }
                else if (i == paraules.length() - 1 && majuscules.contains(paraules.charAt(i) + "")) {
                    Character c = paraules.charAt(i);
                    if (alfabet.contains(Character.toLowerCase(c))) aux = aux + Character.toLowerCase(c);
                }
                else if (i == paraules.length() - 1 && accents_majuscules.contains(paraules.charAt(i) + "")) {
                    Character c = paraules.charAt(i);
                    if ((c == 'À' || c == 'Á') && alfabet.contains('a')) aux = aux + 'a';
                    else if ((c == 'È' || c == 'É') && alfabet.contains('e')) aux = aux + 'e';
                    else if ((c == 'Í' || c == 'Ì') && alfabet.contains('i')) aux = aux + 'i';
                    else if ((c == 'Ó' || c == 'Ò') && alfabet.contains('o')) aux = aux + 'o';
                    else if ((c == 'Ú' || c == 'Ù') && alfabet.contains('u')) aux = aux + 'u';
                }

                
                int n = 0;
                if (caracters.containsKey(aux)) {
                    n = caracters.get(aux);
                }
                if (aux != "") {
                    caracters.put(aux, n + 1);
                }
                aux = "";
            }
        }

        return caracters;
    }

    /**
     * Funcio que transforma un string de paraules amb les seves freqs en un HashMap de <paraula, freq>
     * @param llista String de paraules amb les seves freqs.
     * @param a Alfabet de la llista de paraules.
     * @return HashMap de <paraula, freq>.
     * @throws CharacterNotInAlphabetException Si algun caracter de la llista no pertany a l'alfabet.
     */
    private HashMap<String, Integer> llistaToHashMap(String llista, ArrayList<Character> a) throws CharacterNotInAlphabetException{
        HashMap<String, Integer> llista_paraula_freq = new HashMap<String, Integer>();
        String paraula = "";
        String numero = "";
        int i = 0;
        int n = llista.length();

        while (i < n) {
            // S'ha de fer una excepcio per quan et donin a la llista, una paraula que te un caracter que no es a l'alfabet
            Character c = llista.charAt(i);
            if (c != ' ' && !a.contains(c)) throw new CharacterNotInAlphabetException("El caracter " + c + " no es troba a l'alfabet");
            
            else if (llista.charAt(i) != ' ') paraula = paraula + llista.charAt(i);

            // si es un espai, segur que te un numero despres
            else {
                ++i;
                while (i < n && llista.charAt(i) != ' ') {
                    numero = numero + llista.charAt(i);
                    ++i;
                }
                int num = Integer.parseInt(numero);
                llista_paraula_freq.put(paraula, num);
                paraula = "";
                numero = "";
            }
            ++i;
        }
        return llista_paraula_freq;
    }
}
