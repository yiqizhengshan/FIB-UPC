package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * VistaInici és una classe que representa la interfície gràfica per a l'inici de l'aplicació.
 * Aquesta classe consisteix en un JPanel que conté camps de text, etiquetes i botons que permeten a l'usuari ingressar 
 * informació per a l'inici d'una aplicació. 
 *
 * La interfície d'usuari consta de camps de text pel nom d'usuari i la contrasenya, i un botó per iniciar sessió. 
 * També hi ha un botó per registrar-se i un botó per tancar l'aplicació.
 * 
 * @author Jordi
 */
@SuppressWarnings("serial")
public class VistaInici extends JPanel {
	// atributs
	private CtrlPresentacio ctrlPresentacio;
   
    // panells
    private JPanel panelVistaInici = new JPanel();
    private JPanel panelButtons = new JPanel();
    
    // etiquetes
    private JLabel username_label = new JLabel("Nom usuari:");
    private JLabel password_label = new JLabel("Contrasenya:");
    
	// text boxes
    private JTextField username = new JTextField(20);
    private JPasswordField password = new JPasswordField(20);

    // botons
    private JButton button_tancar = new JButton("tancar");
    private JButton button_inciar_sessio = new JButton("inciar sessio");
	private JButton button_registrarse = new JButton("registrar-se");

	/**
	 * Constructora que inicialitza la VistaInici
	 */
	public VistaInici () {
	    ctrlPresentacio = ctrlPresentacio.getInstance();
	    inicialitzar_components();
	    inicialitzar_listeners();
	}

	/**
	 * Aquest mètode inicialitza els components de la VistaInici
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
		panelVistaInici.setLayout(new GridBagLayout());
		panelVistaInici.add(username_label,grid);
  		++grid.gridy;
		panelVistaInici.add(username,grid);
  		++grid.gridy;
		panelVistaInici.add(password_label,grid);
  		++grid.gridy;
		panelVistaInici.add(password,grid);
		++grid.gridy;
		panelVistaInici.add(new JLabel(""),grid);
		++grid.gridy;

		// afegim els botons al panell
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(1, 2));
		aux.add(button_inciar_sessio);
		aux.add(button_registrarse);
		panelVistaInici.add(aux,grid);
        panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelButtons.add(button_tancar);
		add(panelVistaInici,BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
	}

	/**
	 * Aquest mètode inicialitza els listeners de la VistaInici
	 */
	private void inicialitzar_listeners() {

		// listener per al boto de tancar
		button_tancar.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				ctrlPresentacio.tancarFinestra();
			}
		});

		// listener per al boto de registrar-se
		button_registrarse.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				username.setText("");
				password.setText("");
				ctrlPresentacio.canviarVistaRegistrarse();
			}
		});

		// // listener per al boto de tancar
		button_inciar_sessio.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				String name = username.getText();
				String pwd = new String(password.getPassword());
				if (!name.isEmpty() && !pwd.isEmpty() ) {
					try {
						if (ctrlPresentacio.iniciarSessio(name, pwd)) {
							username.setText("");
							password.setText("");
							ctrlPresentacio.canviarVistaPrincipal();
						}
						else {
							JOptionPane.showMessageDialog(null, "Usuari o contrasenya incorrectes", "Error", JOptionPane.ERROR_MESSAGE);
						}	

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else JOptionPane.showMessageDialog(null,"Hi ha camps buits");
			}
		});
	}
}