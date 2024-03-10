package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import exceptions.UsuariAlreadyExistsException;

/**
 * Vista on un usuari pot registrar-se.
 */ 
@SuppressWarnings("serial")
public class VistaRegistrarse extends JPanel{
    private CtrlPresentacio ctrlPresentacio;    
    // panells
    private JPanel panelVistaRegistrarse = new JPanel();
    private JPanel panelButtons = new JPanel();
    
    // etiquetes
    private JLabel username_label = new JLabel("Nom usuari:");
    private JLabel password_label = new JLabel("Contrasenya:");
    private JLabel confirm_password_label = new JLabel("Confirmar contrasenya:");
    //Text boxes

    private JTextField username = new JTextField(20);
    private JPasswordField password = new JPasswordField(20);
    private JPasswordField confirm_password = new JPasswordField(20);

    // botons
    private JButton button_enrere = new JButton("enrere");
    private JButton button_registrar = new JButton("registrar-se");

	/**
	 * Constructora de la vista
	 */
	public VistaRegistrarse() {
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
		//grid.gridwidth = 1;
		panelVistaRegistrarse.setLayout(new GridBagLayout());

		panelVistaRegistrarse.add(username_label,grid);
  		++grid.gridy;
		panelVistaRegistrarse.add(username,grid);
  		++grid.gridy;
		panelVistaRegistrarse.add(password_label,grid);
  		++grid.gridy;
		panelVistaRegistrarse.add(password,grid);
  		++grid.gridy;
		panelVistaRegistrarse.add(confirm_password_label,grid);
  		++grid.gridy;
		panelVistaRegistrarse.add(confirm_password,grid);
  		++grid.gridy;

		grid.fill = GridBagConstraints.NONE;
		grid.anchor = GridBagConstraints.EAST;
		
		panelVistaRegistrarse.add(button_registrar,grid);

        panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelButtons.add(button_enrere);

		add(panelVistaRegistrarse,BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
	}

	/**
	 * Inicialitza els listeners de la vista
	 */
	private void inicialitzar_listeners() {

		button_enrere.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				username.setText("");
				password.setText("");
				confirm_password.setText("");
			ctrlPresentacio.canviarVistaInici();
			}
		});

		button_registrar.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				String name = username.getText();
				String pwd = new String(password.getPassword());
				String conf_pwd = new String(confirm_password.getPassword());
				if(!name.isEmpty() && !pwd.isEmpty() && !conf_pwd.isEmpty()) {
					if(pwd.equals(conf_pwd)) {
						try {
							ctrlPresentacio.registrarse(name, pwd);
							username.setText("");
							password.setText("");
							confirm_password.setText("");
							ctrlPresentacio.canviarVistaInici();
						} catch(UsuariAlreadyExistsException e) {
							JOptionPane.showMessageDialog(null,e.getMessage());
						}
					}
					else JOptionPane.showMessageDialog(null,"Els camps contrasenya i confirmar contrasenya no coincideixen");
				}
				else JOptionPane.showMessageDialog(null,"Hi ha camps buits");
			}
		});
	}
}