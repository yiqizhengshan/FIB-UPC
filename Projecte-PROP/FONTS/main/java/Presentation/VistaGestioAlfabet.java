package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

import exceptions.*;

/**
 * Vista on es pot eliminar un alfabet, i on es pot anar a les vistes per a crear un alfabet, per a visualitzar-lo o per modificar-lo.
 * @author Dídac
 */ 
@SuppressWarnings("serial")
public class VistaGestioAlfabet extends JPanel{

    private CtrlPresentacio ctrlPresentacio;

    private JPanel panell_butons = new JPanel();
    private JPanel panell_llista = new JPanel();
    private JPanel panell_enrere = new JPanel();

    private JButton buto_crearAlfabet = new JButton("Crear Alfabet");
    private JButton buto_modificarAlfabet = new JButton("Modificar Alfabet");
    private JButton buto_eliminarAlfabet = new JButton("Eliminar Alfabet");
    private JButton buto_veureLlistes = new JButton("Veure Llistes");
    private JButton buto_veureAlfabet =  new JButton("Veure Alfabet");
    private JButton buto_enrere = new JButton("enrere");

    private JList<String> alfabets;

    /**
     * Constructora de la vista
     */
    public VistaGestioAlfabet() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }

    /**
     * Actualitza la llista d'alfabets que es mostra a la vista
     */
    public void updateList(){
        alfabets.setListData(ctrlPresentacio.consultarAlfabets());
    }

    /**
     * Inicialitza els components de la vista
     */
    private void inicialitzar_components() {
        setLayout(new BorderLayout());

        panell_butons.setLayout(new FlowLayout());
        panell_butons.add(buto_crearAlfabet);
        panell_butons.add(buto_veureAlfabet);
        panell_butons.add(buto_modificarAlfabet);
        panell_butons.add(buto_eliminarAlfabet);
        panell_butons.add(buto_veureLlistes);



        String[] alf = {};//ctrlPresentacio.getNomsAlfabets();
        alfabets = new JList<String>(alf);
        alfabets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alfabets.setLayoutOrientation(JList.VERTICAL);
        alfabets.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(alfabets);
        listScroller.setPreferredSize(new Dimension(150, 80));

        panell_llista.setLayout(new GridLayout(1,3));
        panell_llista.add(new JPanel());
        panell_llista.add(listScroller);
        panell_llista.add(new JPanel());


        panell_enrere.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panell_enrere.add(buto_enrere);

        add(panell_llista,BorderLayout.CENTER);
        add(panell_butons,BorderLayout.NORTH);
        add(panell_enrere,BorderLayout.SOUTH);  
    }

    /**
     * Inicialitza els listeners de la vista
     */
    private void inicialitzar_listeners() {

        buto_crearAlfabet.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
              ctrlPresentacio.canviarVistaCrearAlfabet();        
            }
        });

        buto_veureAlfabet.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                String nom = alfabets.getSelectedValue();
                if(nom != null) {
                    try {
                        ctrlPresentacio.canviarVistaVeureAlfabet(nom);
                    } catch(Exception e) {
                        System.out.println("Error al veure l'alfabet: " + e.getMessage());
                    }
                } else JOptionPane.showMessageDialog(null,"No hi ha cap alfabet seleccionat");

            }
        });

        buto_modificarAlfabet.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                String nom = alfabets.getSelectedValue();
                if(nom != null) {
                    try {
                        ctrlPresentacio.canviarVistaModificarAlfabet(nom);
                    } catch(Exception e) {
                        System.out.println("Error al canviar vista per modificar l'alfabet: " + e.getMessage());
                    } 
                } else JOptionPane.showMessageDialog(null,"No hi ha cap alfabet seleccionat");
            }
        });

        buto_eliminarAlfabet.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                String nom = alfabets.getSelectedValue();
                if(nom != null) {
                    try {
                        ctrlPresentacio.eliminarAlfabet(nom);
                        updateList();
                    } catch(Exception e) {
                        System.out.println("Error al eliminar l'alfabet: " + e.getMessage());
                    }
                } else JOptionPane.showMessageDialog(null,"No hi ha cap alfabet seleccionat");
            }
        });
        
        buto_veureLlistes.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                String nom = alfabets.getSelectedValue();
                if (nom != null) {
                    ctrlPresentacio.canviarVistaGestioLlistes(nom);
                } else JOptionPane.showMessageDialog(null,"No hi ha cap alfabet seleccionat");
            } 
        });

        buto_enrere.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
            ctrlPresentacio.canviarVistaPrincipal();
            }
        });


    }

}