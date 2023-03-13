package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import engine.Game;
import model.world.Champion;

public class Availablechamps extends JFrame{

	Availablechamps() {
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
