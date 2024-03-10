package Domain;

import java.util.*;

/**
 * Classe que implementa la part de l'Hungarian algorithm el qual s'encarrega de
 * fer el tractament de
 * grafs bipartits.
 * 
 * @author Oscar
 */
public class Hungarian {
    /**
     * Matriu de costos
     */
    private ArrayList<ArrayList<Integer>> matriu;

    /**
     * Creadora que inicialitza el problema Hungarian.
     */
    public Hungarian() {
    };

    /**
     * Funcio que retorna el resultat de l'algorisme hungarian.
     * 
     * @param matriu Matriu de costos.
     * @return Resultat de l'algorisme hungarian.
     */
    public int calcularHungarianMatriu(ArrayList<ArrayList<Integer>> matriu) {
        if (matriu.size() == 0)
            return 0;
        this.matriu = obtenirMatriuQuadrada(matriu);
        return hungarian();
    }

    /**
     * Funcio que retorna una copia de la matriu, amb tantes files de 0 afegides
     * perque sigui cuadrada.
     * 
     * @param matriu Matriu de costos.
     * @return Retorna una una copia de la matriu, amb tantes files de 0 afegides
     *         perque sigui cuadrada.
     */
    private ArrayList<ArrayList<Integer>> obtenirMatriuQuadrada(ArrayList<ArrayList<Integer>> matriu) {
        ArrayList<ArrayList<Integer>> matriu_quadrada = new ArrayList<>();
        for (int i = 0; i < matriu.size(); i++) {
            matriu_quadrada.add(new ArrayList<>(matriu.get(i)));
        }
        // mentre la matriu no sigui quadrada, afegim files de 0
        // mai afegirem columnes, perque sempre tindrem mes columnes que files
        // ja que les columnes representen els caracters i les files les posicions
        while (matriu_quadrada.get(0).size() > matriu_quadrada.size()) {
            ArrayList<Integer> fila = new ArrayList<>();
            for (int j = 0; j < matriu_quadrada.get(0).size(); j++) {
                fila.add(0);
            }
            matriu_quadrada.add(fila);
        }
        return matriu_quadrada;
    }

    /**
     * Funcio que retorna el resultat de l'algorisme hungarian.
     * 
     * @return Resultat de l'algorisme hungarian.
     */
    private int hungarian() {
        // copiar la matriu original
        ArrayList<ArrayList<Integer>> original = new ArrayList<>();
        for (int i = 0; i < matriu.size(); i++) {
            original.add(new ArrayList<>(matriu.get(i)));
        }
        // fase de preproces
        // restar minim de la fila
        for (int i = 0; i < matriu.size(); i++) {
            int minim = matriu.get(i).get(0);
            for (int j = 1; j < matriu.size(); j++) {
                minim = Math.min(minim, matriu.get(i).get(j));
            }
            for (int j = 0; j < matriu.size(); j++) {
                int valor = matriu.get(i).get(j);
                matriu.get(i).set(j, valor - minim);
            }
        }
        // restar minim de la columna
        for (int j = 0; j < matriu.size(); j++) {
            int minim = matriu.get(0).get(j);
            for (int i = 1; i < matriu.size(); i++) {
                minim = Math.min(minim, matriu.get(i).get(j));
            }
            for (int i = 0; i < matriu.size(); i++) {
                int valor = matriu.get(i).get(j);
                matriu.get(i).set(j, valor - minim);
            }
        }
        // fase iterativa
        while (true) {
            // recubrir la matriu amb el minim nombre de linies possibles
            ArrayList<Boolean> files_marcades = new ArrayList<Boolean>(Arrays.asList(new Boolean[matriu.size()]));
            Collections.fill(files_marcades, Boolean.FALSE);
            ArrayList<Boolean> columnes_marcades = new ArrayList<Boolean>(Arrays.asList(new Boolean[matriu.size()]));
            Collections.fill(columnes_marcades, Boolean.FALSE);
            marcar_linies(matriu, files_marcades, columnes_marcades);
            // comprovar si utilitzem n linies, i per tant, un 0 per cada fila y columna
            int linies = 0;
            for (int i = 0; i < files_marcades.size(); i++) {
                if (files_marcades.get(i))
                    linies++;
            }
            for (int j = 0; j < columnes_marcades.size(); j++) {
                if (columnes_marcades.get(j))
                    linies++;
            }
            if (linies == matriu.size())
                break;
            // trobem el minim no cobert
            int minim_no_cobert = -1;
            for (int i = 0; i < matriu.size(); i++) {
                for (int j = 0; j < matriu.size(); j++) {
                    int valor = matriu.get(i).get(j);
                    if (!files_marcades.get(i) && !columnes_marcades.get(j)) {
                        if (minim_no_cobert == -1 || valor < minim_no_cobert) {
                            minim_no_cobert = valor;
                        }
                    }
                }
            }
            // afegim als coberts el minim dels no coberts
            for (int i = 0; i < matriu.size(); i++) {
                for (int j = 0; j < matriu.size(); j++) {
                    if (files_marcades.get(i)) {
                        int valor = matriu.get(i).get(j);
                        matriu.get(i).set(j, valor + minim_no_cobert);
                    }
                    if (columnes_marcades.get(j)) {
                        int valor = matriu.get(i).get(j);
                        matriu.get(i).set(j, valor + minim_no_cobert);
                    }
                }
            }
            // trobem el minim
            int minim = -1;
            for (int i = 0; i < matriu.size(); i++) {
                for (int j = 0; j < matriu.size(); j++) {
                    int valor = matriu.get(i).get(j);
                    if (minim == -1 || valor < minim)
                        minim = valor;
                }
            }
            // restem el minim a tots els elements de la matriu
            for (int i = 0; i < matriu.size(); i++) {
                for (int j = 0; j < matriu.size(); j++) {
                    int valor = matriu.get(i).get(j);
                    matriu.get(i).set(j, valor - minim);
                }
            }
        }
        // calcular el resultat d'una assignacio optima
        return calcular_resultat(original, matriu);
    }

    /**
     * Funcio que relitza el marcatge de les linies.
     * 
     * @param matriu            Matriu de costos.
     * @param files_marcades    Llista de booleans que indica si la fila esta
     *                          marcada.
     * @param columnes_marcades Llista de booleans que indica si la columna esta
     *                          marcada.
     */
    private void marcar_linies(ArrayList<ArrayList<Integer>> matriu,
            ArrayList<Boolean> files_marcades, ArrayList<Boolean> columnes_marcades) {
        int[] maxim_nombre_files = new int[1];
        maxim_nombre_files[0] = 0;
        ArrayList<Map.Entry<Integer, Integer>> zeros_solucio = new ArrayList<>();
        ArrayList<Map.Entry<Integer, Integer>> zeros = new ArrayList<>();
        ArrayList<Boolean> colummnes_usades = new ArrayList<Boolean>(Arrays.asList(new Boolean[matriu.size()]));
        Collections.fill(colummnes_usades, Boolean.FALSE);
        // obtenim la assignacio mes completa possible
        backtracking(matriu, maxim_nombre_files, 0, zeros_solucio, zeros, colummnes_usades);
        // marquem les files que no tenen assignacio (estan marcadas amb true)
        Collections.fill(files_marcades, Boolean.TRUE);
        for (int i = 0; i < zeros_solucio.size(); i++) {
            int fila = zeros_solucio.get(i).getKey();
            files_marcades.set(fila, false);
        }
        // quan marca es false haurem tancat el bucle
        boolean marca = true;
        while (marca) {
            marca = false;
            // marquem les columnes que tinguin zero en una fila marcada
            for (int i = 0; i < matriu.size(); i++) {
                for (int j = 0; j < matriu.size(); j++) {
                    if (!columnes_marcades.get(j) && matriu.get(i).get(j) == 0 && files_marcades.get(i)) {
                        columnes_marcades.set(j, true);
                        marca = true;
                    }
                }
            }
            // marquem les files que tinguin un zero en fila maracada
            for (int i = 0; i < zeros_solucio.size(); i++) {
                int fila = zeros_solucio.get(i).getKey();
                int columna = zeros_solucio.get(i).getValue();
                if (!files_marcades.get(fila) && columnes_marcades.get(columna)) {
                    files_marcades.set(fila, true);
                    marca = true;
                }
            }
        }
        // acabem marcant les files no marcades
        for (int i = 0; i < files_marcades.size(); i++) {
            boolean valor = files_marcades.get(i);
            if (valor)
                files_marcades.set(i, false);
            else
                files_marcades.set(i, true);
        }
    }

    /**
     * Funcio que realitza el backtracking per trobar la assignacio mes completa
     * 
     * @param matriu             Matriu de costos.
     * @param maxim_nombre_files Maxim nombre de files.
     * @param fila               Fila actual.
     * @param zeros_solucio      Llista de zeros de la solucio.
     * @param zeros              Llista de zeros.
     * @param columnes_usades    Llista de booleans que indica si la columna esta
     *                           marcada.
     */
    private void backtracking(ArrayList<ArrayList<Integer>> matriu,
            int[] maxim_nombre_files, int fila,
            ArrayList<Map.Entry<Integer, Integer>> zeros_solucio,
            ArrayList<Map.Entry<Integer, Integer>> zeros,
            ArrayList<Boolean> columnes_usades) {
        // si la fila es igual al nombre de columnes, hem acabat
        if (fila == columnes_usades.size()) {
            if (zeros.size() > maxim_nombre_files[0]) {
                zeros_solucio.clear();
                zeros_solucio.addAll(zeros);
                maxim_nombre_files[0] = zeros.size();
            }
        } else {
            // si no, mirem si podem afegir un zero a la fila actual
            for (int j = 0; j < columnes_usades.size(); j++) {
                if (matriu.get(fila).get(j) == 0 && !columnes_usades.get(j)) {
                    columnes_usades.set(j, true);
                    zeros.add(new AbstractMap.SimpleEntry<>(fila, j));
                    backtracking(matriu, maxim_nombre_files, fila + 1, zeros_solucio, zeros, columnes_usades);
                    zeros.remove(zeros.size() - 1);
                    columnes_usades.set(j, false);
                }
            }
            backtracking(matriu, maxim_nombre_files, fila + 1, zeros_solucio, zeros, columnes_usades);
        }
    }

    /**
     * Calcula el resultat de l'assignació òptima basada en una assignació parcial
     * mitjançant la tècnica de backtracking.
     *
     * @param original Matriu original amb els costos d'assignació.
     * @param matriu   Matriu amb les dades d'assignació parcial.
     * @return El resultat total de l'assignació òptima.
     */
    private int calcular_resultat(ArrayList<ArrayList<Integer>> original, ArrayList<ArrayList<Integer>> matriu) {
        int[] maxim_nombre_files = new int[1];
        maxim_nombre_files[0] = 0;
        ArrayList<Map.Entry<Integer, Integer>> zeros_solucio = new ArrayList<>();
        ArrayList<Map.Entry<Integer, Integer>> zeros = new ArrayList<>();
        ArrayList<Boolean> columnes_usades = new ArrayList<Boolean>(Arrays.asList(new Boolean[matriu.size()]));
        Collections.fill(columnes_usades, Boolean.FALSE);
        // obtenim l'assignacio mes completa possible i per tant el resultat
        backtracking(matriu, maxim_nombre_files, 0, zeros_solucio, zeros, columnes_usades);
        int resultat = 0;
        // sumem els valors de la matriu original que estan a la solucio
        for (int i = 0; i < zeros_solucio.size(); i++) {
            int fila = zeros_solucio.get(i).getKey();
            int columna = zeros_solucio.get(i).getValue();
            resultat += original.get(fila).get(columna);
        }
        return resultat;
    }
}
