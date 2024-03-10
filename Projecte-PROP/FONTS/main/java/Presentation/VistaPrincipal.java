package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 * Aquesta classe representa la vista principal de l'aplicació.
 * Pots escollir amb 2 botons, si vols gestionar l'alfabet o el teclat.
 * Amb un altre botó pots tancar la sessió.
 * @author Yiqi
 */
@SuppressWarnings("serial")
public class VistaPrincipal extends JPanel{
    private CtrlPresentacio ctrlPresentacio;

    // Panells
    private JPanel panel = new JPanel();
    private JPanel logout = new JPanel();

    // Botons
    private JButton btnGestioAlfabet = new JButton("Gestió Alfabet");
    private JButton btnGestioTeclat = new JButton("Gestió Teclat");
    private JButton btnLogout = new JButton("log out");

    /**
     * Constructora de la vista
     */
    public VistaPrincipal() {
        ctrlPresentacio = CtrlPresentacio.getInstance();
        inicialitzar_components();
        inicialitzar_listeners();
    }

    /**
     * Inicialitza els components de la vista
     */
    private void inicialitzar_components() {
        setLayout(new GridLayout(3, 3));

        // Panel del mig
        panel.setLayout(new GridLayout(3, 1));
        panel.add(btnGestioAlfabet);
        panel.add(new JPanel());
        panel.add(btnGestioTeclat);

        // Panel de logout
        logout.setLayout(new FlowLayout(FlowLayout.RIGHT));
		logout.add(btnLogout);

        JPanel auxiliar = new JPanel();
        auxiliar.setLayout(new BorderLayout());
        auxiliar.add(logout, BorderLayout.SOUTH);

        // Añadir los paneles al frame

        add(new JPanel());  // Celda superior izquierda
        add(new JPanel());  // Celda superior centro
        add(new JPanel());  // Celda superior derecha
        add(new JPanel());  // Celda centro izquierda
        add(panel);        // Celda centro centro
        add(new JPanel());  // Celda centro derecha
        add(new JPanel());  // Celda inferior izquierda
        add(new JPanel());  // Celda inferior centro
        add(auxiliar);       // Celda inferior derecha
    }

    /**
     * Inicialitza els listeners de la vista
     */
    private void inicialitzar_listeners() {

        btnGestioAlfabet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic en el botón de Gestió Alfabet
                ctrlPresentacio.canviarVistaGestioAlfabet();
            }
        });

        btnGestioTeclat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic en el botón de Gestió Teclat
                ctrlPresentacio.canviarVistaGestioTeclat();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic en el botón de Logout
                ctrlPresentacio.tancarSessio();
                ctrlPresentacio.canviarVistaInici();
            }
        });
    }
}
