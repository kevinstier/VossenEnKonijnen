package view;

import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * Create a frame for the sliders
 * @author Ronald elzen
 *
 */

public class Settings extends JFrame {
	public Settings() {
		 setTitle("Settings" );	
		 setExtendedState(JFrame.MAXIMIZED_BOTH); 
		 setLayout(new GridLayout(6, 2, 15, 0));
		 setVisible(true);
		 
	}

}
