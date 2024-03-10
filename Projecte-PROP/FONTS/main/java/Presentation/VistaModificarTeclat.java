package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import exceptions.CharacterNotInAlphabetException;

/**
 * La classe consisteix en un JPanel que conté camps de text i etiquetes que
 * permeten a l'usuari ingressar
 * dos caracters i intercanviar-los.
 * 
 * Conté un botó de intercanviar caràcters que intercanvia la posició dels dos
 * caràcters seleccionats i un
 * botó enrere que redirigeix a la vista de veure teclat.
 * 
 * @author Jordi
 */
@SuppressWarnings("serial")
public class VistaModificarTeclat extends JPanel {
    // atributs
    private CtrlPresentacio ctrlPresentacio;
    private String nom_teclat;

    // panells
    private JPanel panell_TextFields = new JPanel();
    private JPanel panell_enrere = new JPanel();

    // labels
    private JLabel nom_caracter1_label = new JLabel("Caracter 1:");
    private JLabel nom_caracter2_label = new JLabel("Caracter 2:");

    // textFields
    private JTextField nom_caracter1 = new JTextField();
    private JTextField nom_caracter2 = new JTextField();

    // botons
    private JButton boto_intercanviar = new JButton("Intercanviar caracters");
    private JButton boto_enrere = new JButton("enrere");

    /**
     * Constructora que inicialitza la VistaModificarTeclat
     */
    public VistaModificarTeclat() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }

    /**
     * Aquest mètode inicialitza els components de la VistaModificarTeclat
     */
    private void inicialitzar_components() {
        // configuracio dels components
        setLayout(new BorderLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.gridheight = 1;
        grid.gridx = 0;
        grid.gridy = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.CENTER;

        // configuracio panells
        panell_TextFields.setLayout(new GridBagLayout());
        panell_TextFields.add(nom_caracter1_label, grid);
        ++grid.gridy;
        panell_TextFields.add(nom_caracter1, grid);
        ++grid.gridy;
        panell_TextFields.add(nom_caracter2_label, grid);
        ++grid.gridy;
        panell_TextFields.add(nom_caracter2, grid);
        ++grid.gridy;
        panell_TextFields.add(new JLabel(""), grid);
        ++grid.gridy;

        // afegim els botons al panell
        JPanel aux = new JPanel();
        aux.setLayout(new GridLayout(1, 2));
        aux.add(boto_intercanviar);
        panell_TextFields.add(aux, grid);
        panell_enrere.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panell_enrere.add(boto_enrere);
        add(panell_TextFields, BorderLayout.CENTER);
        add(panell_enrere, BorderLayout.SOUTH);
    }

    /**
     * Aquest mètode inicialitza els listeners de la VistaModificarTeclat
     */
    private void inicialitzar_listeners() {

        boto_intercanviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // En el cas que els caracters no formin part del teclat, es llença una excepció
                try {
                    // Llegir els caracters dels camps de text
                    String caracter_1 = nom_caracter1.getText();
                    String caracter_2 = nom_caracter2.getText();
                    // Comprovar que els caracters tinguin longitud 1
                    if (caracter_1.length() == 1 && caracter_2.length() == 1) {
                        nom_caracter1.setText("");
                        nom_caracter2.setText("");
                        Character c1 = caracter_1.charAt(0);
                        Character c2 = caracter_2.charAt(0);

                        double percentatge = ctrlPresentacio.costIntercanviarCaractersTeclat(nom_teclat, c1, c2);
                        String percentatge_string = String.format("%.2f", percentatge);
                        int result = JOptionPane.showConfirmDialog(
                                null,
                                "El teclat " + nom_teclat +
                                        ", millorara un " + percentatge_string
                                        + "% respecte el cost del teclat original. \n"
                                        + "Segur que vols continuar? ",
                                "Confirmation",
                                JOptionPane.YES_NO_OPTION);

                        if (result == JOptionPane.YES_OPTION) {
                            ctrlPresentacio.intercanviarCaractersTeclat(nom_teclat, c1, c2);
                            String nomAlfabetTeclat = ctrlPresentacio.consultarNomAlfabetTeclat(nom_teclat);
                            int cost = ctrlPresentacio.consultarCostTeclat(nom_teclat);
                            boolean alfabet_eliminat = ctrlPresentacio.getAlfabetEliminat(nom_teclat);
                            boolean alfabet_modificat = ctrlPresentacio.getAlfabetModificat(nom_teclat);
                            ctrlPresentacio.canviarVistaVeureTeclat(nom_teclat, nomAlfabetTeclat, cost,
                                    alfabet_eliminat,
                                    alfabet_modificat);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Nomes pots posar caracters de longitud 1", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (CharacterNotInAlphabetException se) {
                    JOptionPane.showMessageDialog(null, "Un dels caracters no es troba en el teclat", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        boto_enrere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    String nomAlfabetTeclat = ctrlPresentacio.consultarNomAlfabetTeclat(nom_teclat);
                    int cost = ctrlPresentacio.consultarCostTeclat(nom_teclat);
                    boolean alfabet_eliminat = ctrlPresentacio.getAlfabetEliminat(nom_teclat);
                    boolean alfabet_modificat = ctrlPresentacio.getAlfabetModificat(nom_teclat);
                    ctrlPresentacio.canviarVistaVeureTeclat(nom_teclat, nomAlfabetTeclat, cost, alfabet_eliminat,
                            alfabet_modificat);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

    }

    /**
     * Aquest mètode actualitza la llista de teclats
     */
    public void setNomTeclat(String nom_teclat) {
        this.nom_teclat = nom_teclat;
    }

}