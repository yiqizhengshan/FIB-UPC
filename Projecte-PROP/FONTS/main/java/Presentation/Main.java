package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * La classe Main conté la funció principal que inicia l'aplicació gràfica. Aquesta funció crea una instància
 * del controlador de presentació i inicia el seu funcionament.
 * Aquesta classe no té cap funcionalitat més enllà de la inicialització de l'aplicació.
 */	
public class Main {
	public static void main (String[] args) {
		javax.swing.SwingUtilities.invokeLater (
		new Runnable() {
			public void run() {
				CtrlPresentacio ctrlPresentacion = CtrlPresentacio.getInstance();
				ctrlPresentacion.iniciarCtrlPresentacio();
		}});
	}
}