package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gameview extends JFrame implements KeyListener{

	JPanel top;
	JPanel bottom;
	JPanel centre;
	JPanel bottomleft;
	JPanel bottomright;
	JPanel left;
	JPanel right;
	

	Gameview() {
		// frame
		setBackground(Color.red);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Avengers End Game");
		//setSize(720, 600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout(10,10));
		ImageIcon icon = new ImageIcon("avnlogo.jpg");
		setIconImage(icon.getImage());

		
		// paneltop
		top = new JPanel();
		//top.setBounds(0, 0, 1537, 50);
		top.setBackground(Color.LIGHT_GRAY);
		top.setLayout(new GridLayout(0, 4));
		top.setPreferredSize(new Dimension(1366,50));
		
		centre = new JPanel();
		//centre.setBounds(0, 0, 1537, 50);
		centre.setBackground(new Color(0x1c2e4a));
		centre.setLayout(new GridLayout(5, 5));
		centre.setPreferredSize(new Dimension(1366,113));

		// panelbottom
		bottom = new JPanel();
		//bottom.setBounds(0, 670, 1550, 50);
		bottom.setBackground(Color.LIGHT_GRAY);
		bottom.setLayout(new GridLayout(0, 2));
		bottom.setPreferredSize(new Dimension(1366,60));
		
		//bottom left
		bottomleft = new JPanel();
		bottomleft.setLayout(new GridLayout(0, 4));
		bottom.add(bottomleft,BorderLayout.WEST);
		//bottom right
		bottomright = new JPanel();
		bottomright.setLayout(new GridLayout(0, 4));
		bottom.add(bottomright,BorderLayout.EAST);
		
		
		//left
		left = new JPanel();
		left.setPreferredSize(new Dimension(200,100));
		left.setBackground(new Color(0x1c2e4a));
		left.setLayout(new FlowLayout());
		//right
		right = new JPanel();
		right.setPreferredSize(new Dimension(100,100));
		right.setBackground(new Color(0x58181F));
		
		this.add(right,BorderLayout.EAST);
		this.add(left,BorderLayout.WEST);
		this.add(top,BorderLayout.NORTH);
		this.add(bottom,BorderLayout.SOUTH);
		this.add(centre,BorderLayout.CENTER);
		setVisible(true);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	System.out.println("you entered keycode: "+e.getKeyCode());
		
	}
}
