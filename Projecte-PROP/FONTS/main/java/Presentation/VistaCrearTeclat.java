package Presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Array;

import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 * VistaCrearTeclat és una classe que representa la interfície gràfica per a la
 * creació d'un teclat en una aplicació. Aquesta classe consisteix en un JPanel
 * que conté camps de text, etiquetes i botons que permeten a l'usuari ingressar
 * informació per a la creació d'un teclat.
 *
 * La interfície d'usuari consta de camps de text pel nom del teclat i el nom de
 * l'alfabet, i un botó per la creació d'un alfabet que redirigeix a la vista de
 * creació d'alfabets per si l'usuari encara no ha creat cap alfabet o vol
 * crear-ne un de nou. Dos botons per la creació d'un teclat amb l'algoritme
 * Hungarian i amb l'algoritme SA. I un botó enrerere que redirigeix a la vista
 * de gestió teclats.
 * 
 * @author Jordi
 */
@SuppressWarnings("serial")
public class VistaCrearTeclat extends JPanel {

    private CtrlPresentacio ctrlPresentacio;

    // panells
    private JPanel panell_TextFields = new JPanel();
    private JPanel panell_enrere = new JPanel();

    private JFileChooser filechooser = new JFileChooser();

    // labels
    private JLabel nom_teclat_label = new JLabel("Nom Teclat");
    private JLabel nom_alfabet_label = new JLabel("Nom Alfabet");

    // textFields
    private JTextField nom_teclat = new JTextField();
    private JComboBox<String> comboBoxAlfabet = new JComboBox<String>();

    // botons
    private JButton boto_crear_alfabet = new JButton("Crear Alfabet");
    private JButton buto_crear_SA = new JButton("Crear amb SA");
    private JButton buto_crear_Hungarian = new JButton("Crear amb Hungarian");
    private JButton buto_importar_fitxer = new JButton("Importar Fitxer");
    private JButton boto_enrere = new JButton("enrere");

    /**
     * Constructor de la classe VistaCrearTeclat.
     * Inicialitza els components i estableix els listeners necessaris.
     */
    public VistaCrearTeclat() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }

    /**
     * Mètode per actualitzar la vista amb els alfabets disponibles.
     */
    public void update() {
        String[] alfabets = ctrlPresentacio.consultarAlfabets();
        comboBoxAlfabet.setModel(new DefaultComboBoxModel<String>(alfabets));
        comboBoxAlfabet.repaint();
    }

    /**
     * Mètode per inicialitzar els components de la interfície gràfica.
     * Estableix la disposició dels panells i afegeix els elements necessaris.
     */
    private void inicialitzar_components() {
        setLayout(new BorderLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5, 5, 5, 5);
        grid.gridheight = 1;
        grid.gridx = 0;
        grid.gridy = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.CENTER;

        panell_TextFields.setLayout(new GridBagLayout());
        panell_TextFields.add(nom_teclat_label, grid);
        ++grid.gridy;
        panell_TextFields.add(nom_teclat, grid);
        ++grid.gridy;
        panell_TextFields.add(nom_alfabet_label, grid);
        ++grid.gridy;
        panell_TextFields.add(comboBoxAlfabet, grid);
        ++grid.gridy;
        panell_TextFields.add(new JLabel(""), grid);
        ++grid.gridy;

        JPanel aux = new JPanel();
        aux.setLayout(new GridLayout(1, 2));
        aux.add(buto_crear_Hungarian);
        aux.add(buto_crear_SA);
        aux.add(boto_crear_alfabet);
        aux.add(buto_importar_fitxer);
        panell_TextFields.add(aux, grid);

        panell_enrere.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panell_enrere.add(boto_enrere);

        add(panell_TextFields, BorderLayout.CENTER);
        add(panell_enrere, BorderLayout.SOUTH);
    }

    /**
     * Mètode per inicialitzar els listeners dels botons.
     * Defineix les accions a realitzar quan es prem els botons.
     */
    private void inicialitzar_listeners() {

        buto_crear_Hungarian.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    String nom_tec = nom_teclat.getText();
                    if (!nom_tec.isEmpty()) {
                        String nom_alf = (String) comboBoxAlfabet.getSelectedItem();
                        int num_car = ctrlPresentacio.veureAlfabet(nom_alf).size();
                        if (num_car > 8) {
                            int result = JOptionPane.showConfirmDialog(
                                    null,
                                    "El nombre de caràcters és major a 8, és " + num_car
                                            + ", l'execució pot tardar molta estona (de minuts a hores o inclús dies).\n"
                                            + "Segur que vols continuar? ",
                                    "Confirmation",
                                    JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.YES_OPTION) {
                                nom_teclat.setText("");
                                JFrame frame = new JFrame("Creant teclat");
                                JLabel label = new JLabel("Creant teclat, això pot trigar molt...");
                                label.setFont(new Font("Arial", Font.BOLD, 18));
                                label.setHorizontalAlignment(JLabel.CENTER);
                                frame.setLayout(new BorderLayout());
                                frame.add(label, BorderLayout.CENTER);
                                frame.setSize(400, 200);
                                frame.setVisible(true);
                                frame.setLocationRelativeTo(null);
                                frame.setResizable(false);
                                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                                ctrlPresentacio.disable();
                                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                                    @Override
                                    protected Void doInBackground() throws Exception {
                                        ctrlPresentacio.crearTeclatQAP(nom_tec, nom_alf);
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        ctrlPresentacio.enable();
                                        ctrlPresentacio.canviarVistaGestioTeclat();
                                        frame.dispose();
                                    }
                                };
                                worker.execute();
                            }

                        } else {
                            ctrlPresentacio.crearTeclatQAP(nom_tec, nom_alf);
                            nom_teclat.setText("");
                            ctrlPresentacio.canviarVistaGestioTeclat();
                        }
                    } else
                        JOptionPane.showMessageDialog(null, "Hi ha camps buits");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buto_crear_SA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    String nom_tec = nom_teclat.getText();
                    if (!nom_tec.isEmpty()) {
                        String nom_alf = (String) comboBoxAlfabet.getSelectedItem();
                        ctrlPresentacio.crearTeclatSA(nom_tec, nom_alf);
                        nom_teclat.setText("");
                        ctrlPresentacio.canviarVistaGestioTeclat();
                    } else
                        JOptionPane.showMessageDialog(null, "Hi ha camps buits");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        boto_crear_alfabet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                nom_teclat.setText("");
                ctrlPresentacio.canviarVistaCrearAlfabet();
            }
        });

        buto_importar_fitxer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int returnVal = filechooser.showOpenDialog(VistaCrearTeclat.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File teclat = filechooser.getSelectedFile();
                    try {
                        ctrlPresentacio.importarTeclat(teclat);
                        nom_teclat.setText("");
                        ctrlPresentacio.canviarVistaGestioTeclat();
                    } catch (NoSuchElementException e) {
                        JOptionPane.showMessageDialog(null, "Error de format");
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error de format");

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());

                    }
                }
            }
        });

        boto_enrere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                nom_teclat.setText("");
                ctrlPresentacio.canviarVistaGestioTeclat();
            }
        });
    }
}