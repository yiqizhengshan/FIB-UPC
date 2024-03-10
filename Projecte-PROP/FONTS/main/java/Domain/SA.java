package Domain;

import java.util.*;

/**
 * Classe que implementa el segon algorisme escollit (Recuita Simulada o SA)
 * 
 * @author Oscar
 */
public class SA implements Estrategia_Teclat {
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
     * Creadora que inicialitza el problema SA.
     */
    public SA() {
    };

    /**
     * Funcio que retorna el layout despres d'aplicar l'algorisme SA.
     * 
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
        simulatedAnnealing(1000.0, 0.03);
        return layout;
    }

    /**
     * Funcio que retorna el layout despres d'aplicar l'algorisme
     * 
     * @param temperatura_inicial
     * @param factor_enfriament
     */
    private void simulatedAnnealing(double temperatura_inicial, double factor_enfriament) {
        Random random = new Random();
        // calculem les dimensions del layout dimensions = files * columnes
        int dimensions = layout.size() * layout.get(0).size();
        // generem un array amb les posicions dels caracters en ordre aleatori
        // en el cas que no hi hagi prou caracters per omplir el layout posara -1
        ArrayList<Integer> random_caracters_index = obtenirRandomArray(dimensions);
        // creem l'estat inicial on les posicions del layout estaran assginades
        // segons l'ordre dels element de l'array random_caracters_index
        ArrayList<ArrayList<Integer>> estat = crearEstat(random_caracters_index);
        int cost = calcularCost(estat);

        double temperatura = temperatura_inicial;
        // iterem mentre fins que tinguem una temperatura molt baixa
        while (temperatura > 1e-3) {
            // generem una nova solucio al intercanviar dos caracters aleatoris
            ArrayList<ArrayList<Integer>> nou_estat = copiarEstat(estat);
            intercanviarDuesPosicionsAleatories(random, dimensions, estat, nou_estat);
            // calculem el cost de la nova solucio
            int nou_cost = calcularCost(nou_estat);
            // decidim si acceptar el nou estat en el cas que ens millori la solucio
            // o per la probabilitat del simulated annealing
            if (nou_cost < cost || random.nextDouble() < Math.exp((cost - nou_cost) / temperatura)) {
                estat = nou_estat;
                cost = nou_cost;
            }
            // disminuim la temperatura
            temperatura *= 1 - factor_enfriament;
        }

        construirLayout(estat);
    }

    /**
     * Funcio que obte una array amb les posicions dels caracters en ordre aleatori
     * 
     * @param dimensions les dimensions del layout calculat com files * columnes
     **/
    private ArrayList<Integer> obtenirRandomArray(int dimensions) {
        ArrayList<Integer> random_caracters_index = new ArrayList<Integer>();
        for (int i = 0; i < caracters.size(); i++) {
            random_caracters_index.add(i);
        }
        while (random_caracters_index.size() < dimensions) {
            random_caracters_index.add(-1);
        }
        Collections.shuffle(random_caracters_index);
        return random_caracters_index;
    }

    /**
     * Funcio que ens crea un estat, on cada posicio (i, j) representa l'index del
     * caracter ocupant aquesta posicio, a partir dels elements de l'array amb
     * les posicions aleatories dels caracters
     * 
     * @param random_caracters_index les dimensions del layout calculat com files *
     *                               columnes
     **/
    private ArrayList<ArrayList<Integer>> crearEstat(ArrayList<Integer> random_caracters_index) {
        ArrayList<ArrayList<Integer>> estat = new ArrayList<>();
        for (int i = 0; i < layout.size(); i++) {
            ArrayList<Integer> fila = new ArrayList<>();
            for (int j = 0; j < layout.get(i).size(); j++) {
                int index = i * layout.get(i).size() + j;
                fila.add(random_caracters_index.get(index));
            }
            estat.add(fila);
        }
        return estat;
    }

    /**
     * Funcio que ens calcula el cost del estat
     * 
     * @param estat estat actual del layout on la posicio (i, j) representa l'index
     *              del caracter ocupant aquesta posicio
     **/
    private int calcularCost(ArrayList<ArrayList<Integer>> estat) {
        int cost = 0;
        for (int i_1 = 0; i_1 < estat.size(); i_1++) {
            for (int j_1 = 0; j_1 < estat.get(i_1).size(); j_1++) {
                for (int i_2 = 0; i_2 < estat.size(); i_2++) {
                    for (int j_2 = 0; j_2 < estat.get(i_2).size(); j_2++) {
                        int index_caracter_1 = estat.get(i_1).get(j_1);
                        int index_caracter_2 = estat.get(i_2).get(j_2);
                        if (index_caracter_1 != -1 && index_caracter_2 != -1) {
                            int distancia_i = Math.abs(i_2 - i_1);
                            int distancia_j = Math.abs(j_2 - j_1);
                            int pes = frequencies.get(index_caracter_1).get(index_caracter_2);
                            cost += (distancia_i + distancia_j) * pes;
                        }
                    }
                }
            }
        }
        // dividim entre 2 perque cada parella de caracters es compten 2 cops
        return cost / 2;
    }

    /**
     * Funcio que retorna una copia del estat
     * 
     * @param estat estat actual del layout on la posicio (i, j) representa l'index
     *              del caracter ocupant aquesta posicio
     **/
    private ArrayList<ArrayList<Integer>> copiarEstat(ArrayList<ArrayList<Integer>> estat) {
        ArrayList<ArrayList<Integer>> nou_estat = new ArrayList<>();
        for (int i = 0; i < estat.size(); i++) {
            nou_estat.add(new ArrayList<>(estat.get(i)));
        }
        return nou_estat;
    }

    /**
     * Funcio que intercanvia dues posicions aleatories del layout
     * 
     * @param random     objecte de nombres aleatoris
     * @param dimensions les dimensions del layout calculat com files * columnes
     * @param estat      estat actual del layout on la posicio (i, j) representa
     *                   l'index
     *                   del caracter ocupant aquesta posicio
     * @param nou_estat  nou estat on es guardara el resultat de l'intercanvi de
     *                   posicions aleatories
     **/
    private void intercanviarDuesPosicionsAleatories(Random random, int dimensions,
            ArrayList<ArrayList<Integer>> estat, ArrayList<ArrayList<Integer>> nou_estat) {
        int index_1 = random.nextInt(dimensions);
        int index_2 = random.nextInt(dimensions);
        while (index_1 == index_2) {
            index_2 = random.nextInt(dimensions);
        }
        // i representa la fila i j la columna
        int i_1 = index_1 / estat.get(0).size();
        int j_1 = index_1 % estat.get(0).size();
        int i_2 = index_2 / estat.get(0).size();
        int j_2 = index_2 % estat.get(0).size();
        nou_estat.get(i_1).set(j_1, estat.get(i_2).get(j_2));
        nou_estat.get(i_2).set(j_2, estat.get(i_1).get(j_1));
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
}
