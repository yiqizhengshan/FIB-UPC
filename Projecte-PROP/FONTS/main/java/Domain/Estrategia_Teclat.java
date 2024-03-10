package Domain;

import java.util.*;
/**
 * Interficie per a implementar el patró estrategia amb els dos algorismes.
 * @author Dídac
 */
public interface Estrategia_Teclat {
	 /**
     * Funcio que retorna el layout d'un teclat després d'aplicar un algorisme.
     * 
     * @param frequencies Matriu de freqüències on la posicio (i, j) representa la
     *                    frequencia del caracter i amb el caracter j.
     * @param layout      Layout on la posicio (i, j) representa el caracter que es
     *                    troba en aquesta posicio.
     * @param caracters   Llista de caracters que es poden assignar al layout.
     */
	public ArrayList<ArrayList<Character>> crearLayout(ArrayList<ArrayList<Integer>> frequencies,
            ArrayList<ArrayList<Character>> layout, ArrayList<Character> caracters);

}
