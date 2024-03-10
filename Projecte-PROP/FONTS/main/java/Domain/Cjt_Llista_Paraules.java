package Domain;
import java.util.*;

import exceptions.CharacterNotInAlphabetException;
import exceptions.LlistaAlreadyExistsException;
import exceptions.LlistaNotFoundException;

/**
 * Classe que representa el conjunt de llistes de paraules que s'utilitzen per crear un teclat.
 * @author Yiqi
 */

public class Cjt_Llista_Paraules {
    // ATRIBUTS

    /**
     * Mida del conjunt de llistes de paraules.
     */
    private int size;

    /**
     * Llista de llistes de paraules que composen el conjunt de llistes de paraules.
     */
    private HashMap<String, Llista_Paraules> cjt_llistes;
    
    // CONSTRUCTORES

    /**
     * Constructora per defecte que inicialitza el conjunt de llistes de paraules buit.
     */
    public Cjt_Llista_Paraules() {
        this.size = 0;
        this.cjt_llistes = new HashMap<String, Llista_Paraules>();
    }

    // COPIADORES

    /**
     * Constructora que inicialitza el conjunt de llistes de paraules a partir d'un altre conjunt de llistes de paraules.
     * @param cjt Conjunt de llistes de paraules que es vol copiar.
     */
    public Cjt_Llista_Paraules(Cjt_Llista_Paraules cjt) {
        this.cjt_llistes = new HashMap<String, Llista_Paraules>(cjt.cjt_llistes);
        this.size = cjt.size;
    }
    
    // GETTERS

    /**
     * Obté la mida del conjunt de llistes de paraules.
     * @return Mida del conjunt de llistes de paraules.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Obté una sola llista de paraules amb la seva frequencia, a partir de totes les llistes de paraules del conjunt.
     * @return La llista de paraules amb el nom donat.
     */
    public HashMap<String, Integer> getCjt() {
        return cjtLlistesToHashMap();
    }

    /**
     * Obté un HashMap amb totes les llistes de paraules del conjunt.
     * @return HashMap amb totes les llistes de paraules del conjunt.
     */
    public HashMap<String, Llista_Paraules> getCjtLlistes() {
        return this.cjt_llistes;
    }

    /**
     * Obté una llista de paraules amb el nom donat.
     * @param nom Nom de la llista de paraules que es vol obtenir.
     * @return La llista de paraules amb el nom donat.
     * @throws LlistaNotFoundException Si la llista amb el nom donat no existeix.
     */
    public Llista_Paraules getLlista(String nom) throws LlistaNotFoundException{
        if (!this.cjt_llistes.containsKey(nom)) throw new LlistaNotFoundException("La llista amb nom " + nom + " no existeix");
        else return this.cjt_llistes.get(nom);
    }

    /**
     * Obté una llista de paraules amb el nom donat en format String[][].
     * @param nom Nom de la llista de paraules que es vol obtenir.
     * @return La llista de paraules amb el nom donat.
     * @throws LlistaNotFoundException Si la llista amb el nom donat no existeix.
     */
    public String[][] getLlistaParaulaFreqString(String nom) throws LlistaNotFoundException {
        if (!this.cjt_llistes.containsKey(nom)) throw new LlistaNotFoundException("La llista amb nom " + nom + " no existeix");
        else return this.cjt_llistes.get(nom).getLlistaParaulaFreqString();
    }

    // SETTERS

    /**
     * Modifica el conjunt de llistes de paraules.
     * @param cjt_llistes Conjunt de llistes de paraules que es vol assignar.
     */
    public void setCjt(HashMap<String, Llista_Paraules> cjt_llistes) {
        this.cjt_llistes = cjt_llistes;
        this.size = cjt_llistes.size();
    }

    // METODES

    /**
     * Afegeix una llista de paraules al conjunt a partir d'un text.
     * @param text Text del qual es volen extreure les paraules.
     * @param nom Nom de la llista de paraules que es vol crear.
     * @param a Alfabet que s'utilitzarà per a crear la llista de paraules.
     * @throws LlistaAlreadyExistsException Si una llista de paraules amb el mateix nom ja existeix al conjunt.
     */
    public void afegirLlistaText(String text, String nom, ArrayList<Character> a) throws LlistaAlreadyExistsException{
        if (this.cjt_llistes.containsKey(nom)) throw new LlistaAlreadyExistsException("La llista amb nom " + nom + " ja existeix");
        else {
            Llista_Paraules lp = new Llista_Paraules(nom);
            lp.afegirLlistaText(text, a);

            this.cjt_llistes.put(nom, lp);
            this.size++;
        }
    }

    /**
     * Afegeix una llista de paraules al conjunt a partir d'un text. Metode que nomes es per als test amb mockito.
     * @param text Text del qual es volen extreure les paraules.
     * @param nom Nom de la llista de paraules que es vol crear.
     * @param a Alfabet que s'utilitzarà per a crear la llista de paraules.
     * @param lp Instancia Llista_Paraules ja inicialitzada
     * @throws LlistaAlreadyExistsException Si una llista de paraules amb el mateix nom ja existeix al conjunt.
     */
    public void afegirLlistaText(String text, String nom, ArrayList<Character> a, Llista_Paraules lp) throws LlistaAlreadyExistsException{
        if (this.cjt_llistes.containsKey(nom)) throw new LlistaAlreadyExistsException("La llista amb nom " + nom + " ja existeix");
        else {
            lp.afegirLlistaText(text, a);

            this.cjt_llistes.put(nom, lp);
            this.size++;
        }
    }

    /**
     * Afegeix una llista de paraules al conjunt a partir d'una llista de paraules amb les frequencies.
     * @param llista Llista de paraules amb les frequencies.
     * @param nom Nom de la llista de paraules que es vol crear.
     * @param a Alfabet que s'utilitzarà per a crear la llista de paraules.
     * @throws LlistaAlreadyExistsException Si una llista de paraules amb el mateix nom ja existeix al conjunt.
     * @throws CharacterNotInAlphabetException Si algun caràcter de la llista no pertany a l'Alfabet.
     */
    public void afegirLlistaDonada(String llista, String nom, ArrayList<Character> a) throws LlistaAlreadyExistsException, CharacterNotInAlphabetException {
        if (this.cjt_llistes.containsKey(nom)) throw new LlistaAlreadyExistsException("La llista amb nom " + nom + " ja existeix");
        else {
            Llista_Paraules lp = new Llista_Paraules(nom);
            lp.afegirLlistaDonada(llista, a);

            this.cjt_llistes.put(nom, lp);
            this.size++;
        }
    }

    // Aquesta funcio es per al mock que utilitza mockito

    /**
     * Afegeix una llista de paraules al conjunt a partir d'una llista de paraules amb les frequencies. Metode que nomes es per als test amb mockito.
     * @param llista Llista de paraules amb les frequencies.
     * @param nom Nom de la llista de paraules que es vol crear.
     * @param a Alfabet que s'utilitzarà per a crear la llista de paraules.
     * @param lp Instancia Llista_Paraules ja inicialitzada
     * @throws LlistaAlreadyExistsException Si una llista de paraules amb el mateix nom ja existeix al conjunt.
     * @throws CharacterNotInAlphabetException Si algun caràcter de la llista no pertany a l'Alfabet.
     */
    public void afegirLlistaDonada(String llista, String nom, ArrayList<Character> a, Llista_Paraules lp) throws LlistaAlreadyExistsException, CharacterNotInAlphabetException {
        if (this.cjt_llistes.containsKey(nom)) throw new LlistaAlreadyExistsException("La llista amb nom " + nom + " ja existeix");
        else {
            lp.afegirLlistaDonada(llista, a);

            this.cjt_llistes.put(nom, lp);
            this.size++;
        }
    }

    /**
     * Afegeix una llista de paraules al conjunt.
     * @param llista Llista de paraules que es vol afegir.
     * @throws LlistaAlreadyExistsException Si una llista de paraules amb el mateix nom ja existeix al conjunt.
     */
    public void afegirLlista(Llista_Paraules llista) throws LlistaAlreadyExistsException {
        if (this.cjt_llistes.containsKey(llista.getNom())) throw new LlistaAlreadyExistsException("La llista amb nom " + llista.getNom() + " ja existeix");
        else {
            this.cjt_llistes.put(llista.getNom(), llista);
            this.size++;
        }
    }

    /**
     * Obté una llista dels noms de totes les llistes de paraules del conjunt.
     * @return Llista dels noms de totes les llistes de paraules del conjunt.
     */
    public ArrayList<String> consultarLlistes() {
        ArrayList<String> nomsLlistes = new ArrayList<String>();
        for (Map.Entry<String, Llista_Paraules> entrada : this.cjt_llistes.entrySet()) {
            nomsLlistes.add(entrada.getKey());
        }
        return nomsLlistes;
    }

    /**
     * Elimina la llista amb el nom donat.
     * @param nom Nom de la llista de paraules que es vol eliminar.
     * @throws LlistaNotFoundException Si la llista amb el nom donat no existeix.
     */
    public void eliminarLlista(String nom) throws LlistaNotFoundException {
        if (!this.cjt_llistes.containsKey(nom)) throw new LlistaNotFoundException("La llista amb nom " + nom + " no existeix");
        else {
            this.cjt_llistes.remove(nom);
            this.size = this.cjt_llistes.size();
        }
    }

    /**
     * Elimina de totes les llistes de paraules, les paraules que tinguin el caracter donat.
     * @param c Caracter que es vol eliminar de totes les llistes de paraules.
     */
    public void actualitzarFreq(Character c) {
        for (Map.Entry<String, Llista_Paraules> entrada : this.cjt_llistes.entrySet()) {
            entrada.getValue().actualitzarFreq(c);
        }
    }

    // AUXILIARY FUNCTIONS

    /**
     * Transforma el conjunt de llistes de paraules a un HashMap<String, Integer> on la clau és la paraula i el valor és la frequencia.
     * @return HashMap<String, Integer> on la clau és la paraula i el valor és la frequencia.
     */
    private HashMap<String, Integer> cjtLlistesToHashMap() {
        HashMap<String, Integer> cjt = new HashMap<String, Integer>();

        for (Map.Entry<String, Llista_Paraules> p : this.cjt_llistes.entrySet()) {
            Llista_Paraules lp = cjt_llistes.get(p.getKey());
            HashMap<String, Integer> llista = lp.getLlistaParaulaFreq();

            for (Map.Entry<String, Integer> paraula_freq : llista.entrySet()) {
                String paraula = paraula_freq.getKey();
                Integer freq = paraula_freq.getValue();

                if (cjt.containsKey(paraula)) {
                    Integer freq2 = cjt.get(paraula);
                    cjt.put(paraula, freq + freq2);
                }
                else cjt.put(paraula, freq);
            }
        }
        return cjt;
    }
}
