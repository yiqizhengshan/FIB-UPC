package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
/**
 * Vista on es pot visualitzar un alfabet seleccionat a la VistaGestioAlfabet.
 * @author Dídac
 */
public class VistaVeureAlfabet extends JPanel{

    private CtrlPresentacio ctrlPresentacio;

    private JPanel panell_principal = new JPanel();
    private JPanel panell_enrere = new JPanel();

    private JLabel labelNomAlfabet = new JLabel();
    private JLabel labelCaracters = new JLabel();

    private JButton buto_enrere = new JButton("enrere");

    /**
     * Constructora de la vista
     */
    public VistaVeureAlfabet() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }    

    /**
     * Actualitza la vista
     * @param nom_alfabet Nom de l'alfabet que es vol visualitzar
     * @param caracters Caracters de l'alfabet que es vol visualitzar
     */
    public void update(String nom_alfabet, ArrayList<Character> caracters) {
        labelNomAlfabet.setText("Nom Alfabet: "+ nom_alfabet);
        labelCaracters.setText("Caràcters: " + caractersToString(caracters));
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
        grid.anchor = GridBagConstraints.CENTER;
        panell_principal.add(labelNomAlfabet, grid);

        ++grid.gridy;
        panell_principal.add(labelCaracters, grid);


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
            ctrlPresentacio.canviarVistaGestioAlfabet();
            }
        });
    }

    /**
     * Converteix un ArrayList de caracters a un String
     * @param caracters ArrayList de caracters
     * @return String amb els caracters separats per un espai
     */
    private String caractersToString(ArrayList<Character> caracters) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character c : caracters) {
            stringBuilder.append(c).append(" ");
        }
        return stringBuilder.toString().trim();
    }

}
