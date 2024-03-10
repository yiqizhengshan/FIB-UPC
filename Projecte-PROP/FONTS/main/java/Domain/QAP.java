package Domain;

import java.util.*;

/**
 * Classe que resol el problema QAP (Quadratic Assignment Problem) implmentat
 * mitjançant l'algorisme branch and bound,
 * utilitzant la classe de Bounding per calcular la cota Gilmore-Lawler i la
 * funcio de Hungarian que implementa tot el
 * tractament dels grafs bipartits.
 * 
 * @author Oscar
 */
public class QAP implements Estrategia_Teclat {
    /**
     * Matriu de frequencies on la posicio (i, j) representa la frequencia del
     * caracter i amb el caracter j.
     */
    private ArrayList<ArrayList<Integer>> frequencies;

    /**
     * Layout on la posicio (i, j) representa el caracter que es troba en aquesta
     * posicio.
     */
    private ArrayList<ArrayList<Character>> layout;

    /**
     * Llista de caracters que es poden assignar al layout.
     */
    private ArrayList<Character> caracters;

    /**
     * Objecte de la classe Bounding que ens permet calcular la cota Gilmore-Lawler.
     */
    private Bounding bounding;

    /**
     * Creadora que inicialitza el problema QAP.
     */
    public QAP() {
    };

    /**
     * Funcio que retorna el layout despres d'aplicar l'algorisme QAP amb branch and
     * bound.
     * 
     * @param frequencies Matriu de frequencies on la posicio (i, j) representa la
     *                    frequencia del caracter i amb el caracter j.
     * @param layout      Layout on la posicio (i, j) representa el caracter que es
     *                    troba en aquesta posicio.
     * @param caracters   Llista de caracters que es poden assignar al layout.
     */

    @Override
    public ArrayList<ArrayList<Character>> crearLayout(ArrayList<ArrayList<Integer>> frequencies,
            ArrayList<ArrayList<Character>> layout, ArrayList<Character> caracters) {
        this.frequencies = frequencies;
        this.layout = layout;
        this.caracters = caracters;
        this.bounding = new Bounding();
        branchAndBound();
        return layout;
    }

    /**
     * Funcio que retorna el layout despres d'aplicar l'algorisme QAP amb branch and
     * bound
     */
    private void branchAndBound() {
        // preprocesament on inicialitzem l'estat, on estat es una matriu amb les
        // dimensions del layout on la posicio (i, j) representa l'index del caracter
        // que es troba en aquesta posicio
        ArrayList<ArrayList<Integer>> estat_buit = new ArrayList<>();
        for (int i = 0; i < layout.size(); i++) {
            ArrayList<Integer> fila = new ArrayList<>();
            for (int j = 0; j < layout.get(i).size(); j++) {
                fila.add(-1);
            }
            estat_buit.add(fila);
        }
        SortedMap<Integer, ArrayList<ArrayList<Integer>>> pq = new TreeMap<>();
        // colocem un estat amb cap caracter colocat
        pq.put(0, estat_buit);
        boolean acabar = false;
        while (!acabar) {
            // estat agafa el primer estat de la cua de prioritats el cual
            // sera el millor candidat per a ser la solucio optima
            ArrayList<ArrayList<Integer>> estat = new ArrayList<>(pq.get(pq.firstKey()));
            pq.remove(pq.firstKey());
            // disponibles conte les posicions no assignades en el layout
            HashSet<Integer> disponibles = obtenirCaractersDisponibles(estat);
            if (disponibles.isEmpty()) {
                // l'estat representa un layout que es solucio
                // per tant, hem acabat
                acabar = true;
                construirLayout(estat);
            } else {
                // si no hem acabat, afegim else estats que podem
                // obtenir a partir de l'estat actual
                afegirEstats(pq, estat, disponibles);
            }
        }
    }

    /**
     * Funcio que construeix el layout amb els indexs del estat
     * 
     * @param estat Estat del layout on la posicio (i, j) representa l'index del
     *              caracter, que es troba en aquesta posicio.
     **/
    private void construirLayout(ArrayList<ArrayList<Integer>> estat) {
        for (int i = 0; i < layout.size(); i++) {
            for (int j = 0; j < layout.get(i).size(); j++) {
                int index_caracter = estat.get(i).get(j);
                if (index_caracter != -1) {
                    layout.get(i).set(j, caracters.get(index_caracter));
                }
            }
        }
    }

    /**
     * Funcio que afegeix els estats que es poden obtenir a partir de l'estat actual
     * utilitzant els caracters disponibles
     * 
     * @param pq          Cua de prioritats on es guarden els estats
     * @param estat       Estat del layout on la posicio (i, j) representa l'index
     *                    del
     *                    caracter, que es troba en aquesta posicio.
     * @param disponibles conjunt dels indexs dels caracters no assignats en el
     *                    layout
     **/
    private void afegirEstats(SortedMap<Integer, ArrayList<ArrayList<Integer>>> pq,
            ArrayList<ArrayList<Integer>> estat, HashSet<Integer> disponibles) {
        // Iterem sobre les posicions del estat
        for (int i = 0; i < estat.size(); i++) {
            for (int j = 0; j < estat.get(i).size(); j++) {
                int index_caracter = estat.get(i).get(j);
                // en cas que no estigui assignada, afegirem un estat
                // per cada lletra disponible
                if (index_caracter == -1) {
                    Iterator<Integer> it = disponibles.iterator();
                    while (it.hasNext()) {
                        ArrayList<ArrayList<Integer>> nou_estat = new ArrayList<>();
                        for (int k = 0; k < estat.size(); k++) {
                            nou_estat.add(new ArrayList<>(estat.get(k)));
                        }

                        nou_estat.get(i).set(j, it.next());
                        // calculem la cota del nou estat
                        int cota = bounding.calcularBounding(frequencies, nou_estat);
                        pq.put(cota, nou_estat);
                    }
                }
            }
        }
    }

    /**
     * Funcio que obte els indexs dels caracters disponibles sense assignar
     * 
     * @param estat Estat del layout on la posicio (i, j) representa l'index del
     *              caracter, que es troba en aquesta posicio.
     **/
    private HashSet<Integer> obtenirCaractersDisponibles(ArrayList<ArrayList<Integer>> estat) {
        HashSet<Integer> disponibles = new HashSet<>();
        for (int i = 0; i < caracters.size(); i++) {
            disponibles.add(i);
        }
        for (int i = 0; i < estat.size(); i++) {
            for (int j = 0; j < estat.get(i).size(); j++) {
                Integer index_caracter = estat.get(i).get(j);
                if (index_caracter != -1) {
                    disponibles.remove(index_caracter);
                }
            }
        }
        return disponibles;
    }
}
