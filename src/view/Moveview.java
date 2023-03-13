package view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Moveview extends JFrame {
	Moveview() {
		setBackground(Color.black);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Please enter a direction (up,down,left,right)");
		setSize(300, 150);
		// setLayout(new GridLayout(0, 3));
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		ImageIcon icon = new ImageIcon("avnlogo.jpg");
		setIconImage(icon.getImage());
		
	}

}