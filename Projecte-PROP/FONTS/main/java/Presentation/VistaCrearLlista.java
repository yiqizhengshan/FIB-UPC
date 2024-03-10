package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.util.*;
import java.io.File;

import exceptions.AlfabetNotFoundException;
import exceptions.LlistaAlreadyExistsException;
import exceptions.LlistaNotFoundException;
import exceptions.CharacterNotInAlphabetException;
import exceptions.TeclatNotFoundException;


/**
 * Aquesta classe representa la vista per crear una llista.
 * Pots crear una llista amb text o amb una llista de paraules segons si selecciones un botó o un altre.
 * També pots importar crear el teclat a partir d'un fitxer o tornar enrere a la vista de gestió de llistes.
 * @author Yiqi
 */
@SuppressWarnings("serial")
public class VistaCrearLlista extends JPanel{
    
    private CtrlPresentacio ctrlPresentacio;

    // Atributs
    private String nom_alfabet;

    // Panells
    private JPanel panell_TextFields = new JPanel();
    private JPanel panell_butons = new JPanel();
    private JPanel panell_enrere = new JPanel();

    // File Chooser
    private JFileChooser filechooser = new JFileChooser();

    // Labels
    private JLabel nom_llista_label = new JLabel("Nom Llista");
    private JLabel caracters_label = new JLabel("Text o Llista paraules");

    // TextFields
    private JTextField nom_llista = new JTextField();
    private JTextArea contingut = new JTextArea(10, 30);

    // Botons
    private JButton buto_importar_fitxer = new JButton("Importar Fitxer");
    private JButton buto_crear_text = new JButton("Crear amb Text");
    private JButton buto_crear_llista = new JButton("Crear amb Llista");
    private JButton buto_enrere = new JButton("enrere");

    /**
     * Constructora de la vista
     */
    public VistaCrearLlista() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }

    /**
     * Inicialitza els components de la vista
     */
    private void inicialitzar_components() {
        setLayout(new BorderLayout());

        // Configuracio dels panells
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5); // Padding between components
        grid.gridheight = 1; 
        grid.gridx = 0;
        grid.gridy = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.CENTER;

        JScrollPane listScroller = new JScrollPane(contingut);
        
        panell_TextFields.setLayout(new GridBagLayout());
        panell_TextFields.add(nom_llista_label,grid);

        ++grid.gridy;
        panell_TextFields.add(nom_llista, grid);

        ++grid.gridy;
        panell_TextFields.add(caracters_label, grid);
        ++grid.gridy;
        panell_TextFields.add(listScroller, grid);
        ++grid.gridy;
        panell_TextFields.add(new JLabel(""), grid);
        ++grid.gridy;
        
        panell_butons.setLayout(new GridLayout(1, 2));
        panell_butons.add(buto_crear_text);
        panell_butons.add(buto_crear_llista);
        panell_butons.add(buto_importar_fitxer);
        panell_TextFields.add(panell_butons,grid);

        panell_enrere.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panell_enrere.add(buto_enrere);

        // Afegim els panells al frame
        add(panell_TextFields,BorderLayout.CENTER);
        add(panell_enrere,BorderLayout.SOUTH);
    }

    /**
     * Inicialitza els listeners de la vista
     */
    private void inicialitzar_listeners() {

        buto_crear_text.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) { 
                try {
                    String nom = nom_llista.getText();
                    String contingut_text = contingut.getText();

                    if (nom.isEmpty() || contingut_text.isEmpty()) {
                        JOptionPane.showMessageDialog(null,"Hi ha camps buits");
                    }
                    else {
                        // Acción al hacer clic en el botón de Crear amb Text
                        ctrlPresentacio.afegirLlistaParaulesAmbText(nom_alfabet, contingut_text, nom);
                        nom_llista.setText("");
                        contingut.setText("");
                        ctrlPresentacio.canviarVistaGestioLlistes(nom_alfabet);
                    }
                }
                catch(AlfabetNotFoundException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
                catch(LlistaAlreadyExistsException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }        
                catch(LlistaNotFoundException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
                catch(TeclatNotFoundException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }  
            }
        });

        buto_crear_llista.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) { 
                try {
                    String nom = nom_llista.getText();
                    String contingut_llista = contingut.getText();
                    if (nom.isEmpty() || contingut_llista.isEmpty()) {
                        JOptionPane.showMessageDialog(null,"Hi ha camps buits");
                    }
                    else {
                        // Acción al hacer clic en el botón de Crear amb Llista
                        ctrlPresentacio.afegirLlistaParaulesDonada(nom_alfabet, contingut_llista, nom);
                        nom_llista.setText("");
                        contingut.setText("");
                        ctrlPresentacio.canviarVistaGestioLlistes(nom_alfabet);
                    }
                }
                catch(AlfabetNotFoundException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(LlistaAlreadyExistsException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(CharacterNotInAlphabetException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,"La llista ha de ser una llista de paraules amb numeros separats per espais", "Error", JOptionPane.ERROR_MESSAGE);
                }    
                catch(LlistaNotFoundException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(TeclatNotFoundException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }  
            }
        });

        buto_importar_fitxer.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                // Acción al hacer clic en el botón de Importar Fitxer
                int returnVal = filechooser.showOpenDialog(VistaCrearLlista.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File contingut_llista = filechooser.getSelectedFile();
                    try {
                        ctrlPresentacio.importarLlista(nom_alfabet, contingut_llista);
                        nom_llista.setText("");
                        contingut.setText("");
                        ctrlPresentacio.canviarVistaGestioLlistes(nom_alfabet);
                    }
                    catch(AlfabetNotFoundException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(LlistaAlreadyExistsException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(CharacterNotInAlphabetException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(NumberFormatException e) {
                        JOptionPane.showMessageDialog(null,"Error de format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(NoSuchElementException e) {
                        JOptionPane.showMessageDialog(null,"Error de format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(TeclatNotFoundException e) {
                        JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                     }  
                }
            }
        });

        buto_enrere.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                // Acción al hacer clic en el botón de enrere
                nom_llista.setText("");
                contingut.setText("");
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
}
