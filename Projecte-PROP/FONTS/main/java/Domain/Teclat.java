package Domain;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.CharacterNotInAlphabetException;
import exceptions.AlfabetModificatOEliminatException;

/**
 * Classe que representa un teclat.
 * 
 * @author Oscar
 */
public class Teclat {
    /**
     * Layout del teclat.
     */
    private ArrayList<ArrayList<Character>> layout;
    /**
     * Alfabet del teclat.
     */
    private Alfabet alfabet;
    /**
     * Nom del teclat.
     */
    private String nom;
    /**
     * Mida de l'alfabet.
     */
    private int size;
    /**
     * Cost del teclat.
     */
    private int cost;
    /**
     * Alfabet modificat.
     */
    private boolean alf_modificat = false;
    /**
     * Alfabet eliminat.
     */
    private boolean alf_eliminat = false;
    /**
     * Estrategia del teclat.
     */
    private Estrategia_Teclat estrategia;
    /**
     * Copia del teclat, despres de recalcular amb SA.
     */
    private Teclat copia_teclat;

    /**
     * Creadora que inicialitza el teclat.
     * 
     * @param nom     Nom del teclat.
     * @param alfabet Alfabet del teclat.
     * @param layout  Layout del teclat.
     */
    public Teclat(String nom, Alfabet alfabet, ArrayList<ArrayList<Character>> layout, Estrategia_Teclat estrategia) {
        this.estrategia = estrategia;
        this.layout = estrategia.crearLayout(alfabet.getMatriuParellaCaracters(), layout, alfabet.getCaracters());
        this.alfabet = alfabet;
        this.nom = nom;
        this.size = alfabet.getSize();
        this.cost = calcularCost();
    }

    public Teclat(String nom, Alfabet alfabet, ArrayList<ArrayList<Character>> layout,Boolean alf_modificat, Boolean alf_eliminat) {
        this.layout = layout;
        this.alfabet = alfabet;
        this.nom = nom;
        this.alf_modificat = alf_modificat;
        this.alf_eliminat = alf_eliminat;
        this.size = alfabet.getSize();
        this.cost = calcularCost();
    }

    public Teclat(String nom, ArrayList<ArrayList<Character>> layout) {
        try {
            this.layout = layout;
            ArrayList<Character> caracters = new ArrayList<Character>();
            caracters.add('a');
            caracters.add('b');
            this.alfabet = new Alfabet("importar_teclat", caracters);
            this.nom = nom;
            this.size = alfabet.getSize();
            this.alf_eliminat = true;
            this.cost = calcularCost();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Creadora que inicialitza el teclat a partir d'un altre teclat.
     * 
     * @param t Teclat que es vol copiar.
     */
    public Teclat(Teclat t) {
        this.layout = new ArrayList<ArrayList<Character>>(t.layout);
        this.alfabet = new Alfabet(t.alfabet);
        this.nom = t.nom;
        this.size = t.size;
        this.cost = t.cost;
    }

    /**
     * Obté el layout del teclat.
     * 
     * @return Layout del teclat.
     */
    public ArrayList<ArrayList<Character>> getLayout() {
        return layout;
    }

    /**
     * Obté l'alfabet del teclat.
     * 
     * @return Alfabet del teclat.
     */
    public ArrayList<Character> getCaracters() {
        return alfabet.getCaracters();
    }

    /**
     * Obté el nom del teclat.
     * 
     * @return Nom del teclat.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obté la mida del teclat.
     * 
     * @return Mida del teclat.
     */
    public int getSize() {
        return size;
    }

    /**
     * Obté el nom del alfabet del teclat.
     * 
     * @return Nom del alfabet.
     */
    public String getNomAlfabet() {
        return this.alfabet.getNom();
    }

    /**
     * Obté el boolea que indica si s'ha modificat l'alfabet.
     * 
     * @return boolea de la modificacio de l'alfabet.
     */
    public boolean getAlfabetModificat() {
        return alf_modificat;
    }

    /**
     * Obté el boolea que indica si s'ha modificat l'alfabet.
     * 
     * @return boolea de la modificacio de l'alfabet.
     */
    public boolean getAlfabetEliminat() {
        return alf_eliminat;
    }

    /**
     * Obté el cost del teclat.
     * 
     * @return Cost del teclat.
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Modifica el nom del teclat.
     * 
     * @param nom Nou nom del teclat.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifica l'alfabet del teclat.
     * 
     * @param alfabet Nou alfabet del teclat.
     */
    public void setAlfabet(Alfabet alfabet) {
        this.alfabet = alfabet;
        this.size = alfabet.getSize();
    }

    /**
     * Modifica el layout del teclat.
     * 
     * @param layout Nou layout del teclat.
     */
    public void setLayout(ArrayList<ArrayList<Character>> layout) {
        this.layout = layout;
    }

    /**
     * Modifica el boolea que indica si s'ha modificat l'alfabet amb el valor donat.
     * 
     * @param valor Valor pel que s'ha de canviar
     */
    public void setAlfabetModificat(Boolean valor) {
        alf_modificat = valor;
    }

    /**
     * Modifica el boolea que indica si s'ha modificat l'alfabet amb el valor donat.
     * 
     * @param valor Valor pel que s'ha de canviar
     */
    public void setAlfabetEliminat(Boolean valor) {
        alf_eliminat = valor;
    }

    /**
     * Funcio que intercanvia dos caracters del layout.
     * 
     * @param c1 Primer caracter a intercanviar.
     * @param c2 Segon caracter a intercanviar.
     * @throws CharacterNotInAlphabetException    Si algun caracter de la llista no
     *                                            pertany a l'alfabet.
     * @throws AlfabetModificatOEliminatException Si un dels caràcters ha estat
     *                                            modificat o eliminat de l'alfabet.
     */
    public void intercanviarCaracters(Character c1, Character c2)
            throws CharacterNotInAlphabetException, AlfabetModificatOEliminatException {
        if (alf_eliminat) {
            throw new AlfabetModificatOEliminatException(
                    "L'alfabet amb el que ha estat creat el teclat ha estat eliminat");
        }
        ArrayList<Character> caracters = alfabet.getCaracters();
        if (!caracters.contains(c1) || !caracters.contains(c2)) {
            throw new CharacterNotInAlphabetException("El caracter no es troba a l'alfabet");
        } else {
            for (int i = 0; i < layout.size(); ++i) {
                for (int j = 0; j < layout.get(i).size(); ++j) {
                    if (layout.get(i).get(j).equals(c1)) {
                        layout.get(i).set(j, c2);
                    } else if (layout.get(i).get(j).equals(c2)) {
                        layout.get(i).set(j, c1);
                    }
                }
            }
            if (!alf_modificat) cost = calcularCost();
        }
    }

    /**
     * Obté el guany del cost en percentage al intercanviar els caracterters c1 i c2
     * al layout.
     * 
     * @param c1 Primer caracter a intercanviar.
     * @param c2 Segon caracter a intercanviar.
     * @throws CharacterNotInAlphabetException    Si algun caracter de la llista no
     *                                            pertany a l'alfabet.
     * @throws AlfabetModificatOEliminatException Si un dels caràcters ha estat
     *                                            modificat o eliminat de l'alfabet.
     * @return Guany en percentage al intercanviar els caracterters c1 i c2.
     */
    public double costIntercanviarCaracters(Character c1, Character c2)
            throws CharacterNotInAlphabetException, AlfabetModificatOEliminatException {
        intercanviarCaracters(c1, c2);
        int cost_al_intercanviar = calcularCost();
        intercanviarCaracters(c1, c2);
        double guany = Double.valueOf(cost) / Double.valueOf(cost_al_intercanviar) - 1.0;
        if(Double.isNaN(guany)) guany = 0;
        return 100*guany;
    }

    /**
     * Calcula el cost del teclat.
     * 
     * @return Cost del teclat.
     */
    public int calcularCost() {
        if (!alf_eliminat && !alf_modificat) {
            ArrayList<ArrayList<Integer>> matriu_frequencies = alfabet.getMatriuParellaCaracters();
            ArrayList<Character> caracters = alfabet.getCaracters();
            HashMap<Character, Integer> index_caracters = new HashMap<Character, Integer>();
            for (int i = 0; i < caracters.size(); i++) {
                index_caracters.put(caracters.get(i), i);
            }
            index_caracters.put(' ', -1);
            return calcularCostLayout(matriu_frequencies, index_caracters);
        } else {
            return 0;
        }
    }

    /**
     * Calcula el cost del layout donada una matriu de frequencies dels caracters.
     * 
     * @param frequencies Matriu de frequencies dels caracters.
     * @param index       HashMap amb els caracters i la seva posicio.
     * @return Cost del teclat.
     */
    private int calcularCostLayout(ArrayList<ArrayList<Integer>> frequencies, HashMap<Character, Integer> index) {
        int cost = 0;
        for (int i = 0; i < layout.size(); i++) {
            for (int j = 0; j < layout.get(i).size(); j++) {
                for (int k = 0; k < layout.size(); k++) {
                    for (int l = 0; l < layout.get(k).size(); l++) {
                        if (i != k || j != l) {
                            int index_caracter_1 = index.get(layout.get(i).get(j));
                            int index_caracter_2 = index.get(layout.get(k).get(l));
                            if (index_caracter_1 != -1 && index_caracter_2 != -1) {
                                int distancia = Math.abs(i - k) + Math.abs(j - l);
                                cost += frequencies.get(index_caracter_1).get(index_caracter_2) * distancia;
                            }
                        }
                    }
                }
            }
        }
        // dividim entre 2 perque cada parella de caracters es compten 2 cops
        return cost / 2;
    }

    /**
     * Obté el guany del cost en percentatge si es fa una rexecucio del algorisme
     * SA.
     * 
     * @return Guany en cost que te el teclat al aplicar una rexecucio de
     *         l'algorisme SA.
     */
    public double costRexecutarSA() {
        ArrayList<ArrayList<Character>> layout_buit = new ArrayList<ArrayList<Character>>();
        for (int i = 0; i < layout.size(); ++i) {
            ArrayList<Character> tmp = new ArrayList<Character>();
            for (int j = 0; j < layout.get(i).size(); ++j) {
                tmp.add(' ');
            }
            layout_buit.add(tmp);
        }
        copia_teclat = new Teclat("copia", this.alfabet, layout_buit, new SA());
        double speed_up = Double.valueOf(cost) / Double.valueOf(copia_teclat.getCost()) - 1.0;
        if(Double.isNaN(speed_up)) speed_up = 0;
        return 100 * speed_up;
    }

    /**
     * Assigna al teclat actual el layout y cost de la seva copia, la qual correspon
     * a una rexecucio per SA.
     */
    public void assignarCopiaSA() {
        this.layout = copia_teclat.getLayout();
        this.cost = copia_teclat.getCost();
        this.alf_modificat = false;
    }
}
