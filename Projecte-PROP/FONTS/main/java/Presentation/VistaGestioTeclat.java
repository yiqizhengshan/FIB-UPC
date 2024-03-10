package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * La classe VistaGestioTeclat és una interfície gràfica que permet gestionar
 * els teclats en una aplicació.
 * Consisteix en un JPanel que proporciona botons per crear, veure i eliminar
 * teclats, així com una llista de
 * teclats disponibles. La classe interactua amb el controlador de presentació
 * per realitzar les operacions
 * corresponents. Conté un botó enrere que redirigeix a la vista principal.
 * 
 * @author Jordi
 */
@SuppressWarnings("serial")
public class VistaGestioTeclat extends JPanel {
    // atributs
    private CtrlPresentacio ctrlPresentacio;

    // panells
    private JPanel botons_panel = new JPanel();
    private JPanel llista_panel = new JPanel();
    private JPanel enrere_panel = new JPanel();

    // botons
    private JButton boto_crearTeclat = new JButton("Crear Teclat");
    private JButton boto_veureTeclat = new JButton("Veure Teclat");
    private JButton boto_eliminarTeclat = new JButton("Eliminar Teclat");
    private JButton boto_enrere = new JButton("enrere");

    // llista de teclats
    private JList<String> teclats;

    /**
     * Constructora que inicialitza la VistaGestioTeclat
     */
    public VistaGestioTeclat() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }

    /**
     * Aquest mètode inicialitza els components de la VistaGestioTeclat
     */
    public void updateList() {
        teclats.setListData(ctrlPresentacio.consultarTeclats());
    }

    /**
     * Aquest mètode inicialitza els components de la VistaGestioTeclat
     */
    private void inicialitzar_components() {
        // configuracio del borderlayout
        setLayout(new BorderLayout());
        botons_panel.setLayout(new FlowLayout());
        botons_panel.add(boto_crearTeclat);
        botons_panel.add(boto_veureTeclat);
        botons_panel.add(boto_eliminarTeclat);

        // configuracio de la llista de teclats
        String[] teclat = {};
        teclats = new JList<String>(teclat);
        teclats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teclats.setLayoutOrientation(JList.VERTICAL);
        teclats.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(teclats);
        listScroller.setPreferredSize(new Dimension(150, 80));

        // configuracio del format de la llista
        llista_panel.setLayout(new GridLayout(1, 3));
        llista_panel.add(new JPanel());
        llista_panel.add(listScroller);
        llista_panel.add(new JPanel());

        // configuracio del panell del boto enrere
        enrere_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        enrere_panel.add(boto_enrere);

        // afegim els panells al borderlayout
        add(llista_panel, BorderLayout.CENTER);
        add(botons_panel, BorderLayout.NORTH);
        add(enrere_panel, BorderLayout.SOUTH);
    }

    /**
     * Aquest mètode inicialitza els listeners de la VistaGestioTeclat
     */
    private void inicialitzar_listeners() {

        // listener per al boto de crear teclat
        boto_crearTeclat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ctrlPresentacio.canviarVistaCrearTeclat();
            }
        });

        // listener per al boto de veure teclat
        boto_veureTeclat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // Vista o pop-up en que es pot veure el teclat
                String teclat_escollit = teclats.getSelectedValue();
                if (teclat_escollit != null) {
                    try {
                        String nomAlfabetTeclat = ctrlPresentacio.consultarNomAlfabetTeclat(teclat_escollit);
                        int cost_teclat = ctrlPresentacio.consultarCostTeclat(teclat_escollit);
                        boolean alfabet_eliminat = ctrlPresentacio.getAlfabetEliminat(teclat_escollit);
                        boolean alfabet_modificat = ctrlPresentacio.getAlfabetModificat(teclat_escollit);
                        ctrlPresentacio.canviarVistaVeureTeclat(teclat_escollit, nomAlfabetTeclat, cost_teclat, alfabet_eliminat, alfabet_modificat);

                    } catch (Exception e) {
                        System.out.println("Error al veure el teclat: " + e.getMessage());
                    }
                }
                else JOptionPane.showMessageDialog(null, "No has seleccionat cap teclat", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // listener per al boto de eliminar teclat
        boto_eliminarTeclat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String nom = teclats.getSelectedValue();
                if (nom != null) {
                    try {
                        ctrlPresentacio.eliminarTeclat(nom);
                        updateList();
                    } catch (Exception e) {
                        System.out.println("Error al eliminar el teclat: " + e.getMessage());
                    }
                }
                else JOptionPane.showMessageDialog(null, "No has seleccionat cap teclat", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // listener per al boto de enrere
        boto_enrere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ctrlPresentacio.canviarVistaPrincipal();
            }
        });

    }
}
