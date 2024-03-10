package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
/**
 * Vista on es pot modificar un alfabet seleccionat a la VistaGestioAlfabet.
 * @author Dídac
 */
public class VistaModificarAlfabet extends JPanel{

    private CtrlPresentacio ctrlPresentacio;

    private String nom_alfabet;
    ArrayList<Character> caracters;

    private JPanel panell_principal = new JPanel();
    private JPanel panell_enrere = new JPanel();

    private JLabel label_afegir = new JLabel("Afegir caràcter");
    private JLabel label_eliminar = new JLabel("Eliminar Caràcter");

    private JTextField textField_afegir = new JTextField(1);
    private JComboBox<Character> comboBox_eliminar = new JComboBox<Character>();

    private JButton buto_afegir = new JButton("Afegir");
    private JButton buto_eliminar = new JButton("Eliminar");
    private JButton buto_enrere = new JButton("enrere");

    /**
     * Constructora de la vista
     */
    public VistaModificarAlfabet() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }    

    /**
     * Actualitza la vista refent el combobox
     * @param nom_alfabet Nom de l'alfabet que es vol modificar
     * @param caracters Caracters de l'alfabet que es poden modificar
     */
    public void update(String nom_alfabet,ArrayList<Character> caracters) {
        this.nom_alfabet = nom_alfabet;
        this.caracters = caracters;
        comboBox_eliminar.removeAllItems();
        comboBox_eliminar.addItem(' ');
        for(Character caracter: caracters) comboBox_eliminar.addItem(caracter);
    }

    /**
     * Inicialitza els components de la vista
     */
    private void inicialitzar_components() {
    	setLayout(new BorderLayout());
        panell_principal.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);  // Márgenes entre componentes

        grid.gridx = 0;
        grid.gridy = 0;
        grid.anchor = GridBagConstraints.WEST;
        panell_principal.add(label_afegir, grid);

        grid.gridx = 1;
        grid.gridy = 0;
        panell_principal.add(textField_afegir, grid);

        grid.gridx = 2;
        grid.gridy = 0;
        panell_principal.add(buto_afegir, grid);

        grid.gridx = 0;
        grid.gridy = 1;
		panell_principal.add(label_eliminar, grid);

        grid.gridx = 1;
        grid.gridy = 1;
        panell_principal.add(comboBox_eliminar, grid);

        grid.gridx = 2;
        grid.gridy = 1;
        panell_principal.add(buto_eliminar, grid);

        panell_enrere.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panell_enrere.add(buto_enrere);

        add(panell_principal,BorderLayout.CENTER);
        add(panell_enrere,BorderLayout.SOUTH);  

    }

    /**
     * Inicialitza els listeners de la vista
     */
    private void inicialitzar_listeners() {

        buto_enrere.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
            textField_afegir.setText("");
            ctrlPresentacio.canviarVistaGestioAlfabet();
            }
        });
        buto_afegir.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                String caracter = textField_afegir.getText();
                if (caracter.length() != 1) JOptionPane.showMessageDialog(null,"Només pots introduir un caràcter");
                else {
                    try {
                        if(ctrlPresentacio.afegirCaracter(nom_alfabet,caracter.charAt(0))) {
                        ctrlPresentacio.canviarVistaGestioAlfabet();
                    }
                    else JOptionPane.showMessageDialog(null,"El caràcter introduït ja és a l'alfabet");
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }
                }
            }
        });
        buto_eliminar.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                Character caracter = (Character) comboBox_eliminar.getSelectedItem();
                if (!caracter.equals(' ')) {
                    try {
                        ctrlPresentacio.eliminarCaracter(nom_alfabet,caracter);
                    }catch (Exception e) {
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }
                    ctrlPresentacio.canviarVistaGestioAlfabet();
                } else JOptionPane.showMessageDialog(null,"No hi ha cap caràcter seleccionat per eliminar");
            }
        });
    }

}

