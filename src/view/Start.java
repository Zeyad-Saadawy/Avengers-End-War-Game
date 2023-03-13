package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.Game;
import engine.Player;

public class Start extends JFrame {

	JButton startbut;
	JPanel top;
	JPanel bottom;
	JPanel centre;
	JPanel left;
	JPanel right;

	Start() {
		/*
		 * JPanel panel1= new JPanel(); panel1.setBackground(Color.black);
		 * panel1.setPreferredSize((new Dimension(100,100)));
		 * frame.add(panel1,BorderLayout.CENTER); panel1.setBackground(Color.red);
		 */

		
		setBackground(Color.red);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Avengers End Game");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new GridLayout(1,2));
		ImageIcon icon = new ImageIcon("avnlogo.jpg");
		setIconImage(icon.getImage());

		
		ImageIcon icon1 = new ImageIcon("avnlogo.jpg");
		ImageIcon icon2 = new ImageIcon("photo2.jpg");
		
		
		
		//left
		left = new JPanel();
		left.setPreferredSize(new Dimension(100,100));
		JLabel x= new JLabel();
		x.setIcon(icon1);
		left.add(x);
		this.add(left,BorderLayout.WEST);
		//right
		right = new JPanel();
		right.setPreferredSize(new Dimension(100,100));
		JLabel x1= new JLabel();
		x1.setIcon(icon2);
		right.add(x1);
		
	
		
		
		startbut = new JButton("Start");
		startbut.setFocusable(false);
		startbut.setFont(new Font("MV Boli",Font.PLAIN,50));
		startbut.setBackground(new Color(0x58181F));
		startbut.setForeground(Color.white);
		add(startbut,BorderLayout.CENTER); 
		
		
		this.add(right,BorderLayout.EAST);
		setVisible(true);
		
		// startbut.setBorder(BorderFactory.createEtchedBorder());
		/*
		 * 
		 * setDefaultCloseOperation(EXIT_ON_CLOSE); setTitle("Avengers End Game");
		 * //setSize(720,600); this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		 * setLayout(null);
		 * 
		 * ImageIcon icon = new ImageIcon("avnlogo.jpg"); setIconImage(icon.getImage());
		 * setVisible(true); JLabel b= new JLabel(); ImageIcon back = new
		 * ImageIcon("background.jpg"); b.setIcon(back); b.setOpaque(true); add(b);
		 * 
		 */
	}

}
