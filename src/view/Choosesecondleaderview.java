package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Choosesecondleaderview extends JFrame {
	Choosesecondleaderview() {
		setBackground(Color.red);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Avengers End Game");
		//setSize(720, 600);
		setLayout(new GridLayout(0, 3));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		ImageIcon icon = new ImageIcon("avnlogo.jpg");
		setIconImage(icon.getImage());
		setVisible(true);
	}
}
