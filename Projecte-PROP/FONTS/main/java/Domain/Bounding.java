package Domain;

import java.util.*;

/**
 * Classe que s'encarrega de calcular la cota Gilmore-Lawler.
 * 
 * @author Oscar
 */
public class Bounding {

    /**
     * Matriu de frequencies on la posicio (i, j) representa la
     * frequencia del caracter i amb el caracter j.
     */
    private ArrayList<ArrayList<Integer>> frequencies;

    /**
     * Estat del layout on la posicio (i, j) representa l'index del
     * caracter, que es troba en aquesta posicio.
     */
    private ArrayList<ArrayList<Integer>> estat;

    /**
     * Objecte de la classe Hungarian que ens permet calcular la part de l'algorisme
     * Hungarian.
     */
    private Hungarian hungarian;

    /**
     * Creadora de la classe Bounding.
     */
    public Bounding() {
    };

    /**
     * Funcio que calcula el bounding de l'estat actual.
     * 
     * @param frequencies Frequencies dels caracters.
     * @param estat       Estat actual del layout.
     * @return Bounding de l'estat actual.
     */
    public int calcularBounding(ArrayList<ArrayList<Integer>> frequencies, ArrayList<ArrayList<Integer>> estat) {
        this.frequencies = frequencies;
        this.estat = estat;
        this.hungarian = new Hungarian();
        return cotaGilmoreLawler();
    }

    /**
     * Funcio que retorna la cota inferior del estat amb Gilmore-Lawler.
     * 
     * @return Cota inferior del estat.
     */
    private int cotaGilmoreLawler() {
        // preprocessament del bounding per obtenir els index i posicio
        // dels caracters colocats i no colocats del estat
        HashSet<Integer> index_no_colocats_set = new HashSet<>();
        for (int i = 0; i < frequencies.size(); i++)
            index_no_colocats_set.add(i);
        // colocats sera una arrayList the map.entrys on la primera entrada es la
        // posicio (i, j) en l'estat, i la segona es el index del caracter
        ArrayList<Map.Entry<Map.Entry<Integer, Integer>, Integer>> colocats = new ArrayList<>();
        ArrayList<Map.Entry<Integer, Integer>> posicio_no_colocats = new ArrayList<>();
        for (int i = 0; i < estat.size(); i++) {
            for (int j = 0; j < estat.get(i).size(); j++) {
                int index_caracter = estat.get(i).get(j);
                if (index_caracter != -1) {
                    colocats.add(new AbstractMap.SimpleEntry<>(new AbstractMap.SimpleEntry<>(i, j), index_caracter));
                    index_no_colocats_set.remove(index_caracter);
                } else {
                    posicio_no_colocats.add(new AbstractMap.SimpleEntry<>(i, j));
                }
            }
        }
        ArrayList<Integer> index_no_colocats = new ArrayList<Integer>(index_no_colocats_set);
        // calcul del primer terme de la cota
        int primer_terme = calcularPrimerTerme(colocats);
        // calcul de les matrius necessaries per la suma del segon i tercer terme de la
        // cota
        ArrayList<ArrayList<Integer>> C1 = calcularC1(index_no_colocats, posicio_no_colocats, colocats);
        ArrayList<ArrayList<Integer>> C2 = calcularC2(index_no_colocats, posicio_no_colocats);
        ArrayList<ArrayList<Integer>> C12 = calcularC12(index_no_colocats, posicio_no_colocats, C1, C2);
        // calcul de la suma del segon i tercer terme de la cota
        int suma_segon_tercer_terme = hungarian.calcularHungarianMatriu(C12);
        return primer_terme + suma_segon_tercer_terme;
    }

    /**
     * Funcio que calcula el primer terme de la cota
     * 
     * @param colocats llista amb map.entrys on la primera entrada es la posicio (i,
     *                 j)
     *                 en l'estat i la segona es el index del caracter
     */
    private int calcularPrimerTerme(ArrayList<Map.Entry<Map.Entry<Integer, Integer>, Integer>> colocats) {
        int primer_terme = 0;
        for (int i = 0; i < colocats.size(); i++) {
            for (int j = i + 1; j < colocats.size(); j++) {
                primer_terme += distancia(colocats.get(i), colocats.get(j));
            }
        }
        return primer_terme;
    }

    /**
     * Funcio que calcula la matriu C1
     * 
     * @param index_no_colocats   llista amb els indexs dels caracters no colocats
     * @param posicio_no_colocats llista amb les posicions (i, j) dels indexs no
     *                            colocats
     * @param colocats            llista amb map.entrys on la primera entrada es la
     *                            posicio (i, j)
     *                            en l'estat i la segona es el index del caracter
     */
    private ArrayList<ArrayList<Integer>> calcularC1(ArrayList<Integer> index_no_colocats,
            ArrayList<Map.Entry<Integer, Integer>> posicio_no_colocats,
            ArrayList<Map.Entry<Map.Entry<Integer, Integer>, Integer>> colocats) {
        ArrayList<ArrayList<Integer>> C1 = new ArrayList<>();
        for (int i = 0; i < index_no_colocats.size(); i++) {
            ArrayList<Integer> fila = new ArrayList<>();
            for (int k = 0; k < posicio_no_colocats.size(); k++) {
                int cost = 0;
                for (int j = 0; j < colocats.size(); j++) {
                    Map.Entry<Map.Entry<Integer, Integer>, Integer> x = new AbstractMap.SimpleEntry<>(
                            posicio_no_colocats.get(k),
                            index_no_colocats.get(i));
                    Map.Entry<Map.Entry<Integer, Integer>, Integer> y = colocats.get(j);
                    cost += distancia(x, y);
                }
                fila.add(cost);
            }
            C1.add(fila);
        }
        return C1;
    }

    /**
     * Funcio que calcula la matriu C2
     * 
     * @param index_no_colocats   llista amb els indexs dels caracters no colocats
     * @param posicio_no_colocats llista amb les posicions (i, j) dels indexs no
     *                            colocats
     */
    private ArrayList<ArrayList<Integer>> calcularC2(ArrayList<Integer> index_no_colocats,
            ArrayList<Map.Entry<Integer, Integer>> posicio_no_colocats) {
        ArrayList<ArrayList<Integer>> T = calcularT(index_no_colocats);
        ArrayList<ArrayList<Integer>> D = calcularD(posicio_no_colocats);

        ArrayList<ArrayList<Integer>> C2 = new ArrayList<>();
        for (int i = 0; i < index_no_colocats.size(); i++) {
            ArrayList<Integer> fila = new ArrayList<>();
            for (int k = 0; k < posicio_no_colocats.size(); k++) {
                int producte_escalar = 0;
                for (int j = 0; j < T.get(i).size(); j++) {
                    producte_escalar += T.get(i).get(j) * D.get(k).get(j);
                }
                fila.add(producte_escalar);
            }
            C2.add(fila);
        }
        return C2;
    }

    /**
     * Funcio que calcula la matriu T
     * 
     * @param index_no_colocats llista amb els indexs dels caracters no colocats
     */
    private ArrayList<ArrayList<Integer>> calcularT(ArrayList<Integer> index_no_colocats) {
        ArrayList<ArrayList<Integer>> T = new ArrayList<>();
        for (int i = 0; i < index_no_colocats.size(); i++) {
            ArrayList<Integer> fila = new ArrayList<>();
            for (int j = 0; j < index_no_colocats.size(); j++) {
                if (i != j) {
                    int index_i = index_no_colocats.get(i);
                    int index_j = index_no_colocats.get(j);
                    fila.add(frequencies.get(index_i).get(index_j));
                }
            }
            fila.sort(Comparator.naturalOrder());
            T.add(fila);
        }
        return T;
    }

    /**
     * Funcio que calcula la matriu D
     * 
     * @param posicio_no_colocats llista amb les posicions (i, j) dels indexs no
     *                            colocats
     */
    private ArrayList<ArrayList<Integer>> calcularD(ArrayList<Map.Entry<Integer, Integer>> posicio_no_colocats) {
        ArrayList<ArrayList<Integer>> D = new ArrayList<>();
        for (int k = 0; k < posicio_no_colocats.size(); k++) {
            ArrayList<Integer> fila = new ArrayList<>();
            Map.Entry<Integer, Integer> posicio_1 = posicio_no_colocats.get(k);
            for (int j = 0; j < posicio_no_colocats.size(); j++) {
                if (k != j) {
                    Map.Entry<Integer, Integer> posicio_2 = posicio_no_colocats.get(j);
                    int distancia_i = Math.abs(posicio_1.getKey() - posicio_2.getKey());
                    int distancia_j = Math.abs(posicio_1.getValue() - posicio_2.getValue());
                    fila.add(distancia_i + distancia_j);
                }
            }
            fila.sort(Comparator.reverseOrder());
            D.add(fila);
        }
        return D;
    }

    /**
     * Funcio que calcula la matriu C12
     * 
     * @param index_no_colocats   llista amb els indexs dels caracters no colocats
     * @param posicio_no_colocats llista amb les posicions (i, j) dels indexs no
     *                            colocats
     * @param C1                  matriu C1
     * @param C2                  matriu C2
     */
    private ArrayList<ArrayList<Integer>> calcularC12(ArrayList<Integer> index_no_colocats,
            ArrayList<Map.Entry<Integer, Integer>> posicio_no_colocats, ArrayList<ArrayList<Integer>> C1,
            ArrayList<ArrayList<Integer>> C2) {
        ArrayList<ArrayList<Integer>> C12 = new ArrayList<>();
        for (int i = 0; i < index_no_colocats.size(); i++) {
            ArrayList<Integer> fila = new ArrayList<>();
            for (int k = 0; k < posicio_no_colocats.size(); k++) {
                fila.add(C1.get(i).get(k) + C2.get(i).get(k));
            }
            C12.add(fila);
        }
        return C12;
    }

    /**
     * Funcio que retorna la distancia entre dos caracters.
     * 
     * @param x 1r Caracter.
     * @param y 2n Caracter.
     * @return Distancia entre dos caracters.
     */
    private int distancia(Map.Entry<Map.Entry<Integer, Integer>, Integer> x,
            Map.Entry<Map.Entry<Integer, Integer>, Integer> y) {
        // x representa un Map.Entry on la key (primer terme) representa la posicio
        // (i, j) que tindra al layout, mentre que el value (segon terme) conte el
        // index del caracter, y representa el mateix
        Map.Entry<Integer, Integer> coords_1 = x.getKey();
        int index_caracter_1 = x.getValue();
        Map.Entry<Integer, Integer> coords_2 = y.getKey();
        int index_caracter_2 = y.getValue();

        int distancia_i = Math.abs(coords_1.getKey() - coords_2.getKey());
        int distancia_j = Math.abs(coords_1.getValue() - coords_2.getValue());
        int cost = frequencies.get(index_caracter_1).get(index_caracter_2);

        return (distancia_i + distancia_j) * cost;
    }

}
