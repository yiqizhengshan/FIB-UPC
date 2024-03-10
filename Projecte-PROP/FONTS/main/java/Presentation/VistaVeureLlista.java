package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 * Aquesta classe representa la vista per veure una llista.
 * Pots veure una llista de paraules amb la seva respectiva freqüència en una taula.
 * També pots tornar enrere a la vista de gestió de llistes.
 * @author Yiqi
 */
public class VistaVeureLlista extends JPanel {

    private CtrlPresentacio ctrlPresentacio;

    // Atributs
    private String nom_alfabet;
    private String nom_llista;

    // Atributs de la taula
    private String[][] text;
    private String[] titol;

    // Panells
    private JPanel panel_enrere = new JPanel();
    private JPanel panel_mig = new JPanel();

    // Botons
    private JButton btnEnrere = new JButton("enrere");

    // Taula
    private JTable taula;

    /**
     * Constructora de la vista
     */
    public VistaVeureLlista() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }
    
    /**
     * Inicialitza els components de la vista
     */
    private void inicialitzar_components() {
        setLayout(new BorderLayout());

        // Panel del mig
        panel_mig.setLayout(new GridLayout(1, 3));

        text = new String[][] {};
        titol = new String[] { "Paraula", "Frequencia" };
        taula = new JTable(text, titol);
        taula.setEnabled(false);

        JScrollPane listScroller = new JScrollPane(taula);

        panel_mig.add(new JPanel());
        panel_mig.add(listScroller, BorderLayout.CENTER);
        panel_mig.add(new JPanel());

        // Panel de enrere
        panel_enrere.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_enrere.add(btnEnrere);

        // Afegir els panels al frame
        add(panel_mig, BorderLayout.CENTER); // Celda centro centro JTable
        add(panel_enrere, BorderLayout.SOUTH); // Celda inferior derecha BOTON enrere
    }

    /**
     * Inicialitza els listeners de la vista
     */
    private void inicialitzar_listeners() {

        btnEnrere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic en el botón de enrere
                ctrlPresentacio.canviarVistaGestioLlistes(nom_alfabet);
            }
        });
    }

    /**
     * Actualitza el nom de l'alfabet
     * @param nom_alfabet Nom de l'alfabet
     */
    public void setNomAlfabet(String nom_alfabet) {
        this.nom_alfabet = nom_alfabet;
    }

    /**
     * Actualitza el nom de la llista
     * @param nom_llista Nom de la llista
     */
    public void setNomLlista(String nom_llista) {
        this.nom_llista = nom_llista;
    }

    /**
     * Actualitza la taula de la vista
     */
    public void update() {
        try {
            text = ctrlPresentacio.getLlistaParaulaFreqString(nom_alfabet, nom_llista);
            taula.setModel(new DefaultTableModel(text, titol));
            taula.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
