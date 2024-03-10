package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.util.*;

/**
 * Aquesta classe representa la vista per gestionar les llistes d'un alfabet.
 * Pots crear una llista, eliminar una llista, veure una llista o tornar enrere a la vista de gestió d'alfabets.
 * Per poder dur a terme les funcionalitats d'eliminar o veure, has de tenir creada una llista prèviament
 * i seleccionar-la de la llista de llistes que es mostra.
 * @author Yiqi
 */
@SuppressWarnings("serial")
public class VistaGestioLlistes extends JPanel{

    private CtrlPresentacio ctrlPresentacio;

    // Atributs
    private String nom_alfabet;

    // Panells
    private JPanel panel_botons = new JPanel();
    private JPanel panel_enrere = new JPanel();
    private JPanel panel_mig = new JPanel();

    // Botons
    private JButton btnCrearLlista = new JButton("Crear Llista");
    private JButton btnEliminarLlista = new JButton("Eliminar Llista");
    private JButton btnVeureLlista = new JButton("Veure Llista");
    private JButton btnEnrere = new JButton("enrere");

    // JList
    private JList<String> llistes;

    /**
     * Constructora de la vista
     */
    public VistaGestioLlistes() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }

    /**
     * Inicialitza els components de la vista
     */
    private void inicialitzar_components() {
        setLayout(new BorderLayout());

        // Panel de dalt
        panel_botons.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel_botons.add(btnCrearLlista);
        panel_botons.add(btnEliminarLlista);
        panel_botons.add(btnVeureLlista);

        // Panel del mig
        panel_mig.setLayout(new GridLayout(1, 3));

        String aux[] = {};
        llistes = new JList<String>(aux);
        llistes.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        llistes.setLayoutOrientation(JList.VERTICAL);
        llistes.setVisibleRowCount(-1);

        JScrollPane listScroller = new JScrollPane(llistes);

        panel_mig.add(new JPanel());
        panel_mig.add(listScroller, BorderLayout.CENTER);
        panel_mig.add(new JPanel());


        // Panel de enrere
        panel_enrere.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel_enrere.add(btnEnrere);

        add(panel_botons, BorderLayout.NORTH);  // Celda superior BOTONES
        add(panel_mig, BorderLayout.CENTER);        // Celda centro centro JList
        add(panel_enrere, BorderLayout.SOUTH);       // Celda inferior derecha BOTON enrere
    }

    /**
     * Inicialitza els listeners de la vista
     */
    private void inicialitzar_listeners() {

        btnCrearLlista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic en el botón de Crear Llista
                ctrlPresentacio.canviarVistaCrearLlista(nom_alfabet);
            }
        });

        btnEliminarLlista.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                String llista_escollida = llistes.getSelectedValue();
                if(llista_escollida != null) {
                    try {
                        // Acción al hacer clic en el botón de Eliminar Llista
                        ctrlPresentacio.eliminarLlista(nom_alfabet, llista_escollida);
                        update();
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null, "Error a l'eliminar la llista: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No has seleccionat cap llista", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVeureLlista.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                String llista_escollida = llistes.getSelectedValue();
                if(llista_escollida != null) {
                    try {
                        // Acción al hacer clic en el botón de Veure Llista
                        ctrlPresentacio.canviarVistaVeureLlista(nom_alfabet, llista_escollida);
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al fer display de la Llista: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No has seleccionat cap llista", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEnrere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic en el botón de Enrere
                ctrlPresentacio.canviarVistaGestioAlfabet();
            }
        });
    }

    /**
     * Actualitza el nom de l'alfabet
     * @param nom_alfabet
     */
    public void setNomAlfabet(String nom_alfabet) {
        this.nom_alfabet = nom_alfabet;
    }

    /**
     * Actualitza la llista de llistes que es mostra
     */
    public void update() {
        llistes.setListData(ctrlPresentacio.consultarLlistes(nom_alfabet));
    }
}
