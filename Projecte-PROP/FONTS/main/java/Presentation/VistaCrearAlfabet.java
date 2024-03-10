package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.File;

import exceptions.AlfabetAlreadyExistsException;
import exceptions.InsufficientCharactersException;
import exceptions.TooManyCharactersException;


/**
 * Vista on es pot crear un Alfabet a partir de les dades introduïdes per l'usuari
 * o a partir de les dades d'un fitxer.
 * @author Dídac
 */ 
@SuppressWarnings("serial")
public class VistaCrearAlfabet extends JPanel{

    private CtrlPresentacio ctrlPresentacio;

    private JPanel panell_TextFields = new JPanel();
    private JPanel panell_butons = new JPanel();
    private JPanel panell_enrere = new JPanel();

    private JFileChooser filechooser = new JFileChooser();

    private JLabel nom_alfabet_label = new JLabel("Nom Alfabet");
    private JLabel caracters_label = new JLabel("Caràcters Alfabet");

    private JTextField nom_alfabet = new JTextField();
    private JTextField caracters = new JTextField();

    private JButton buto_importar_fitxer = new JButton("Importar Fitxer");
    private JButton buto_crear = new JButton("Crear");

    private JButton buto_enrere = new JButton("enrere");

    /**
     * Constructora de la vista
     */
    public VistaCrearAlfabet() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }

    /**
     * Inicialitza els components de la vista
     */
    private void inicialitzar_components() {
        setLayout(new BorderLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5); // Padding between components
        grid.gridheight = 1; 
        grid.gridx = 0;
        grid.gridy = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.CENTER;
        //grid.weightx = 1.0;
        //grid.gridwidth = 5;
        panell_TextFields.setLayout(new GridBagLayout());
        panell_TextFields.add(nom_alfabet_label,grid);

        ++grid.gridy;
        panell_TextFields.add(nom_alfabet,grid);

        ++grid.gridy;
        panell_TextFields.add(caracters_label,grid);
        ++grid.gridy;
        panell_TextFields.add(caracters,grid);
        ++grid.gridy;
        panell_TextFields.add(new JLabel(""),grid);
        ++grid.gridy;
        JPanel aux = new JPanel();
        aux.setLayout(new GridLayout(1, 2));
        aux.add(buto_crear);
        aux.add(buto_importar_fitxer);
        panell_TextFields.add(aux,grid);


        panell_enrere.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panell_enrere.add(buto_enrere);

        add(panell_TextFields,BorderLayout.CENTER);
        add(panell_enrere,BorderLayout.SOUTH);
    }

    /**
     * Inicialitza els listeners de la vista
     */
    private void inicialitzar_listeners() {

        buto_crear.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) { 
                try {
                    String nom = nom_alfabet.getText();
                    String caracter_string = caracters.getText();
                    if(!nom.isEmpty() && !caracter_string.isEmpty()) {
                        ArrayList<Character> caracter_list = new ArrayList<Character>();
                        for (char c : caracter_string.toCharArray())
                           if (c != ' ') caracter_list.add(c);
                        ctrlPresentacio.crearAlfabet(nom,caracter_list);
                        nom_alfabet.setText("");
                        caracters.setText("");
                        ctrlPresentacio.canviarVistaGestioAlfabet();
                    }
                    else JOptionPane.showMessageDialog(null,"Hi ha camps buits");
                }
                catch(AlfabetAlreadyExistsException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
                catch(InsufficientCharactersException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
                catch(TooManyCharactersException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }           
            }
        });

        buto_importar_fitxer.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                int returnVal = filechooser.showOpenDialog(VistaCrearAlfabet.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File alfabet = filechooser.getSelectedFile();
                    try {
                        ctrlPresentacio.importarAlfabet(alfabet);
                        nom_alfabet.setText("");
                        caracters.setText("");
                        ctrlPresentacio.canviarVistaGestioAlfabet();  
                    } catch(NoSuchElementException e) {
                        JOptionPane.showMessageDialog(null,"Error de format");
                    } 
                    catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(null,"Error de format");

                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }
                }
            }
        });

        buto_enrere.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
            nom_alfabet.setText("");
            caracters.setText("");
            ctrlPresentacio.canviarVistaGestioAlfabet();
            }
        });


    }

}