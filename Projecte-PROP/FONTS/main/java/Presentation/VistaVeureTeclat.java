package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Flow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.util.List;
import exceptions.*;

/**
 * Aquesta classe representa la vista per veure un teclat.
 * Pots modificar dos caracters del teclat o tornar enrere a la vista de gestió
 * de teclats.
 * 
 * @author Oscar
 */
public class VistaVeureTeclat extends JPanel {

    private CtrlPresentacio ctrlPresentacio;

    // Atributs
    private String nom_teclat;
    private String nom_alfabet;
    private boolean alfabet_eliminat;
    private boolean alfabet_modificat;
    private Integer cost_teclat;

    private ArrayList<ArrayList<Character>> layout;

    // Panells
    private JPanel panel_superior = new JPanel();
    private JPanel panel_bottons = new JPanel();
    private JPanel panel_noms = new JPanel();
    private JPanel panel_nom_teclat = new JPanel();
    private JPanel panel_nom_alfabet = new JPanel();
    private JPanel panel_enrere = new JPanel();
    private JPanel panel_mig = new JPanel();
    private JPanel panel_teclat = new JPanel();
    private JPanel panel_cost = new JPanel();

    // Labels
    private JLabel label_nom_teclat = new JLabel();
    private JLabel label_nom_alfabet = new JLabel();
    private JLabel label_cost = new JLabel();

    // Botons
    private JButton btnModificarTeclat = new JButton("Modificar Teclat");
    private JButton btnReexecució_SA = new JButton("Reexecutar amb SA");
    private JButton btnEnrere = new JButton("enrere");

    /**
     * Constructor de la classe VistaVeureTeclat.
     * Inicialitza els components i estableix els listeners necessaris.
     */
    public VistaVeureTeclat() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }

    /**
     * Mètode per crear i retornar un panell amb un teclat numèric.
     * 
     * @return Un JPanel amb un teclat numèric.
     */
    private JPanel crearTeclatNumeros() {
        JPanel panel = new JPanel(new GridLayout(3, 3));

        ArrayList<ArrayList<Character>> numerosLayout = new ArrayList<>();
        numerosLayout.add(new ArrayList<>(List.of('1', '2', '3')));
        numerosLayout.add(new ArrayList<>(List.of('4', '5', '6')));
        numerosLayout.add(new ArrayList<>(List.of('7', '8', '9')));

        // es crea un boto per cada cela del teclat
        for (ArrayList<Character> fila : numerosLayout) {
            for (Character cela : fila) {
                JButton botonLetra = new JButton(cela.toString());
                panel.add(botonLetra);
            }
        }

        return panel;
    }

    /**
     * Mètode per crear i retornar un panell amb tecles especials.
     * 
     * @return Un JPanel amb tecles especials.
     */
    private JPanel crearTecladoEspeciales() {
        JPanel panel = new JPanel(new GridLayout(3, 3));

        ArrayList<ArrayList<Character>> especialesLayout = new ArrayList<>();
        especialesLayout.add(new ArrayList<>(List.of('.', ',', '\u2190')));
        especialesLayout.add(new ArrayList<>(List.of('`', '´', '\u21B5')));
        especialesLayout.add(new ArrayList<>(List.of('!', '?', '\u007F')));

        for (ArrayList<Character> fila : especialesLayout) {
            for (Character cela : fila) {
                JButton botonLetra = new JButton(cela.toString());
                panel.add(botonLetra);
            }
        }

        return panel;
    }

    /**
     * Mètode per inicialitzar els components de la interfície gràfica.
     * Estableix la disposició dels panells i afegeix els elements necessaris.
     */
    private void inicialitzar_components() {
        setLayout(new BorderLayout());

        // Panel superior
        panel_superior.setLayout(new GridLayout(2, 1));
        // Panel pel boto de modificar teclat
        panel_bottons.setLayout(new FlowLayout());
        panel_bottons.add(btnModificarTeclat);
        panel_bottons.add(btnReexecució_SA);
        panel_superior.add(panel_bottons);
        // Panel amb els noms del teclat i alfabet
        panel_noms.setLayout(new GridLayout(3, 1));
        panel_nom_teclat.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel_nom_teclat.add(label_nom_teclat);
        panel_noms.add(panel_nom_teclat);
        panel_cost.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel_cost.add(label_cost);
        panel_noms.add(panel_cost);
        panel_nom_alfabet.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel_nom_alfabet.add(label_nom_alfabet);
        panel_noms.add(panel_nom_alfabet);
        panel_superior.add(panel_noms);

        // Panel del mig
        panel_mig.setLayout(new GridLayout(1, 3));
        panel_mig.add(crearTecladoEspeciales());
        JPanel panel_aux = new JPanel();
        panel_aux.setLayout(new BorderLayout());
        panel_aux.add(panel_teclat, BorderLayout.CENTER);
        panel_aux.add(new JButton(" "), BorderLayout.SOUTH);
        panel_mig.add(panel_aux);
        panel_mig.add(crearTeclatNumeros());

        JPanel panel_flow = new JPanel(new BorderLayout());
        panel_flow.add(panel_mig, BorderLayout.CENTER);
        panel_flow.add(new JPanel(), BorderLayout.NORTH);
        panel_flow.add(new JPanel(), BorderLayout.EAST);
        panel_flow.add(new JPanel(), BorderLayout.WEST);

        // Panel de enrere
        panel_enrere.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_enrere.add(btnEnrere);

        // Colocar els panels en les seves posicions corresponents
        add(panel_superior, BorderLayout.NORTH);
        add(panel_flow, BorderLayout.CENTER);
        add(panel_enrere, BorderLayout.SOUTH);
    }

    /**
     * Mètode per inicialitzar els listeners dels botons.
     * Defineix les accions a realitzar quan es prem els botons.
     */
    private void inicialitzar_listeners() {
        btnModificarTeclat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    if (alfabet_eliminat) {
                        JOptionPane.showMessageDialog(null, "No es pot modificar un teclat amb un alfabet eliminat",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    ctrlPresentacio.canviarVistaModificarTeclat(nom_teclat);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        btnReexecució_SA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    if (alfabet_eliminat) {
                        JOptionPane.showMessageDialog(null, "No es pot reexecutar un teclat amb un alfabet eliminat",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        double percentatge = ctrlPresentacio.costRexecutarSATeclat(nom_teclat);
                        String percentatge_string = String.format("%.2f", percentatge);
                        int result = JOptionPane.showConfirmDialog(
                                null,
                                "El teclat " + nom_teclat +
                                        ", millorara un " + percentatge_string
                                        + "% respecte el cost del teclat original. \n"
                                        + "Segur que vols continuar? ",
                                "Confirmation",
                                JOptionPane.YES_NO_OPTION);

                        // Check the user's choice
                        if (result == JOptionPane.YES_OPTION) {
                            ctrlPresentacio.assignarCopiaSATeclat(nom_teclat);
                            int nou_cost = ctrlPresentacio.consultarCostTeclat(nom_teclat);
                            alfabet_modificat = false;
                            ctrlPresentacio.canviarVistaVeureTeclat(nom_teclat, nom_alfabet, nou_cost,
                                    alfabet_eliminat, alfabet_modificat);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        btnEnrere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrlPresentacio.canviarVistaGestioTeclat();
            }
        });
    }

    /**
     * Mètode per establir el nom del teclat.
     * 
     * @param nom_teclat El nom del teclat.
     */
    public void setNomTeclat(String nom_teclat) {
        this.nom_teclat = nom_teclat;
    }

    /**
     * Mètode per establir el nom de l'alfabet associat al teclat.
     * 
     * @param nom_alfabet El nom de l'alfabet.
     */
    public void setNomAlfabet(String nom_alfabet) {
        this.nom_alfabet = nom_alfabet;
    }

    /**
     * Mètode per establir el cost del teclat.
     * 
     * @param cost_teclat El cost del teclat.
     */
    public void setCostTeclat(int cost_teclat) {
        this.cost_teclat = cost_teclat;
    }

    /**
     * Mètode per indicar si l'alfabet associat al teclat ha estat eliminat.
     * 
     * @param alfabet_eliminat True si l'alfabet ha estat eliminat, false altrament.
     */
    public void setAlfabetEliminat(boolean alfabet_eliminat) {
        this.alfabet_eliminat = alfabet_eliminat;
    }

    /**
     * Mètode per indicar si l'alfabet associat al teclat ha estat modificat.
     * 
     * @param alfabet_modificat True si l'alfabet ha estat modificat, false
     *                          altrament.
     */
    public void setAlfabetModificat(boolean alfabet_modificat) {
        this.alfabet_modificat = alfabet_modificat;
    }

    /**
     * Mètode per actualitzar la vista amb la informació actualitzada.
     */
    public void update() {
        // actualitza el nom del teclat i alfabet
        label_nom_teclat.setText("Nom del teclat: '" + nom_teclat + "'");
        if (alfabet_eliminat)
            label_nom_alfabet.setText("Creat amb l'alfabet: '" + nom_alfabet + "', el qual ha estat eliminat");
        else if (alfabet_modificat)
            label_nom_alfabet.setText("Creat amb l'alfabet: '" + nom_alfabet + "', el qual ha estat modificat");
        else
            label_nom_alfabet.setText("Creat amb l'alfabet: '" + nom_alfabet + "'");
        label_cost.setText("Cost Teclat: " + cost_teclat);
        panel_noms.repaint();
        try {
            // actualitza el visualitzador del layout del teclat
            layout = ctrlPresentacio.veureTeclat(nom_teclat);
            int n = layout.size();
            panel_teclat.removeAll();
            panel_teclat.setLayout(new GridLayout(n, n));
            // es crea un boto per cada cela del teclat
            for (ArrayList<Character> fila : layout) {
                for (Character cela : fila) {
                    JButton boto_lletra = new JButton(cela.toString());
                    panel_teclat.add(boto_lletra);
                }
            }
            panel_teclat.revalidate();
            panel_teclat.repaint();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
