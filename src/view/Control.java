package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import engine.Game;
import engine.Player;
import engine.PriorityQueue;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;
import model.world.Hero;
import model.world.Villain;

public class Control implements ActionListener, KeyListener {
	Start startview;
	Availablechamps loadingchampsview;
	Game game;
	Game gametmp;
	ArrayList<JButton> champsbuttons = new ArrayList<>();
	int counter = 0;
	Choosefirstleaderview chooseleaderview;
	ArrayList<JButton> firstleaderbutton = new ArrayList<>();
	Choosesecondleaderview choosesecondleaderview;
	ArrayList<JButton> secondleaderbutton = new ArrayList<>();
	Gameview gameview;
	JLabel firstname;
	JLabel secondname;
	ArrayList<JButton> p1team = new ArrayList<>();
	ArrayList<JButton> p2team = new ArrayList<>();
	JLabel firstleaderabilityused;
	JLabel secondleaderabilityused;
	JLabel turnoorderq;
	JButton[][] boardbuttons;
	Object[][] gameboard;
	JButton move;
	Moveview moveview;
	Castview castview;
	Attackview attackview;
	JButton attack;
	JButton endturn;
	JButton leaderAbility;
	JButton castability;
	ArrayList<JButton> but = new ArrayList<>();

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	Control() {
		startview = new Start();
		startview.startbut.addActionListener(this);
		 Media audio = new Media();
		 audio.play("oghneya.wav");
	}

	public static void main(String[] args) {
		new Control();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startview.startbut) {

			String firstplayername = JOptionPane.showInputDialog("What is the first Player's name?");
			String secondplayername = JOptionPane.showInputDialog("What is the second Player's name?");

			Player one = new Player(firstplayername);
			Player two = new Player(secondplayername);
			gametmp = new Game(one, two);
			try {
				gametmp.loadAbilities("Abilities.csv");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Abilities file not found", "Error", JOptionPane.ERROR_MESSAGE);
			}
			try {
				gametmp.loadChampions("Champions.csv");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Champions file not found ", "Error", JOptionPane.ERROR_MESSAGE);
			}

			startview.dispose();
			loadingchampsview = new Availablechamps();
			int w = 1;// for coloring the buttons
			for (Champion c : gametmp.getAvailableChampions()) {
				JButton x = new JButton();
				if (w % 2 == 0) { // for coloring the buttons
					x.setBackground(new Color(0x1c2e4a));
				} else
					x.setBackground(new Color(0x58181F));
				w++;
				x.setForeground(Color.white);
				x.setFocusable(false);
				x.addActionListener(this);
				champsbuttons.add(x);
				x.setText(c.toString());
				String s = "<html>" + "maxHP:" + c.getMaxHP() + "<br>" + "mana:" + c.getMana() + "<br>"
						+ "maxActionPointsPerTurn:" + c.getMaxActionPointsPerTurn() + "<br>" + "attackRange:"
						+ c.getAttackRange() + "<br>" + "attackDamage:" + c.getAttackDamage() + "<br>" + "speed:"
						+ c.getSpeed() + "Ability 1" + c.getAbilities().get(0).getName() + "<br>" + "Ability 2" + "<br>"
						+ c.getAbilities().get(1).getName() + "<br>" + "Ability 3" + c.getAbilities().get(2).getName()
						+ "</html>";

				x.setToolTipText(s);
				loadingchampsview.add(x);

			}
			JOptionPane.showMessageDialog(null, "The First player should choose 3 Champions", "Avengers End Game",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (champsbuttons.contains(e.getSource())) {
			((JButton) e.getSource()).setEnabled(false);
			if (counter < 3) {
				int x = champsbuttons.indexOf(e.getSource());
				Champion champ = gametmp.getAvailableChampions().get(x);
				gametmp.getFirstPlayer().getTeam().add(champ);
				counter++;

			}

			if (counter == 3) {
				JOptionPane.showMessageDialog(null, "The Second player should choose 3 Champions", "Avengers End Game",
						JOptionPane.INFORMATION_MESSAGE);
				counter++;

				return;
			}
			if (counter < 8 && counter > 3) {
				int x = champsbuttons.indexOf(e.getSource());
				Champion champ = Game.getAvailableChampions().get(x);
				gametmp.getSecondPlayer().getTeam().add(champ);
				counter++;

			}
			if (counter == 7) {

				loadingchampsview.dispose();
				game = new Game(gametmp.getFirstPlayer(), gametmp.getSecondPlayer());
				chooseleaderview = new Choosefirstleaderview();
				int w = 1;
				for (Champion c : game.getFirstPlayer().getTeam()) {
					JButton y = new JButton();
					if (w % 2 == 0) { // for coloring the buttons
						y.setBackground(new Color(0x1c2e4a));
					} else
						y.setBackground(new Color(0x58181F));
					w++;
					y.setForeground(Color.white);
					y.setFocusable(false);
					firstleaderbutton.add(y);
					y.addActionListener(this);
					y.setText(c.toString());
					String s = "<html>" + "maxHP:" + c.getMaxHP() + "<br>" + "mana:" + c.getMana() + "<br>"
							+ "maxActionPointsPerTurn:" + c.getMaxActionPointsPerTurn() + "<br>" + "attackRange:"
							+ c.getAttackRange() + "<br>" + "attackDamage:" + c.getAttackDamage() + "<br>" + "speed:"
							+ c.getSpeed() + "Ability 1" + c.getAbilities().get(0).getName() + "<br>" + "Ability 2"
							+ "<br>" + c.getAbilities().get(1).getName() + "<br>" + "Ability 3"
							+ c.getAbilities().get(2).getName() + "</html>";

					y.setToolTipText(s);
					chooseleaderview.add(y);

				}
				JOptionPane.showMessageDialog(null, "The First player should choose his leader", "Avengers End Game",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}
		if (firstleaderbutton.contains(e.getSource())) {
			((JButton) e.getSource()).setEnabled(false);
			chooseleaderview.dispose();
			int x = firstleaderbutton.indexOf(e.getSource());
			try {
				game.loadAbilities("Abilities.csv");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Abilities file not found ", "Error", JOptionPane.ERROR_MESSAGE);
			}
			try {
				game.loadChampions("Champions.csv");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Champions file not found ", "Error", JOptionPane.ERROR_MESSAGE);
			}
			Champion champ = game.getFirstPlayer().getTeam().get(x);
			game.getFirstPlayer().setLeader(champ);
			choosesecondleaderview = new Choosesecondleaderview();
			int w = 1;
			for (Champion c : game.getSecondPlayer().getTeam()) {
				JButton y = new JButton();
				if (w % 2 == 0) { // for coloring the buttons
					y.setBackground(new Color(0x1c2e4a));
				} else
					y.setBackground(new Color(0x58181F));
				w++;
				y.setForeground(Color.white);
				y.setFocusable(false);
				secondleaderbutton.add(y);
				y.addActionListener(this);
				y.setText(c.toString());
				String s = "<html>" + "maxHP:" + c.getMaxHP() + "<br>" + "mana:" + c.getMana() + "<br>"
						+ "maxActionPointsPerTurn:" + c.getMaxActionPointsPerTurn() + "<br>" + "attackRange:"
						+ c.getAttackRange() + "<br>" + "attackDamage:" + c.getAttackDamage() + "<br>" + "speed:"
						+ c.getSpeed() + "Ability 1" + c.getAbilities().get(0).getName() + "<br>" + "Ability 2" + "<br>"
						+ c.getAbilities().get(1).getName() + "<br>" + "Ability 3" + c.getAbilities().get(2).getName()
						+ "</html>";

				y.setToolTipText(s);
				choosesecondleaderview.add(y);
			}
			JOptionPane.showMessageDialog(null, "The Second player should choose his leader", "Avengers End Game",
					JOptionPane.INFORMATION_MESSAGE);

		}
		if (secondleaderbutton.contains(e.getSource())) {
			((JButton) e.getSource()).setEnabled(false);
			choosesecondleaderview.dispose();
			int x = secondleaderbutton.indexOf(e.getSource());
			Champion champ = game.getSecondPlayer().getTeam().get(x);
			game.getSecondPlayer().setLeader(champ);

			// opening game play view
			gameview = new Gameview();
			String p1name = game.getFirstPlayer().getName();
			firstname = new JLabel("P1 name:" + p1name);

			String p2name = game.getSecondPlayer().getName();
			// firstname.setBounds(0, 10, 100, 100);
			secondname = new JLabel("P2 name:" + p2name);

			// secondname.setBounds(1300, 10, 100, 100);
			gameview.top.add(firstname);
			gameview.top.add(secondname);

			// turnorder queue current player
			turnoorderq = new JLabel();
			ArrayList<Champion> tmpchamps = new ArrayList<>();
			String s1 = "1: " + game.getCurrentChampion().getName();
			int i1 = 2;
			while (!game.getTurnOrder().isEmpty()) {
				tmpchamps.add((Champion) game.getTurnOrder().remove());
				if (game.getTurnOrder().isEmpty())
					break;
				s1 += " " + i1 + ":" + ((Champion) game.getTurnOrder().peekMin()).getName();
				i1++;
			}
			for (int i = 0; i < tmpchamps.size(); i++) {
				game.getTurnOrder().insert(tmpchamps.get(i));
			}
			turnoorderq.setText(s1);
			turnoorderq.setFont(new Font("Consolas", Font.PLAIN, 8));
			gameview.top.add(turnoorderq);

			// getting first player champions
			for (Champion c : game.getFirstPlayer().getTeam()) {
				JButton y = new JButton();
				boolean flag = false;
				if (game.getFirstPlayer().getLeader() == c) {
					flag = true;
				}
				y.setFocusable(false);
				y.setBackground(new Color(0x58181F));
				y.setForeground(Color.white);
				y.addActionListener(this);
				y.setText(c.toString());
				String s = "<html>" + "maxHP:" + c.getMaxHP() + "<br>" + "mana:" + c.getMana() + "<br>"
						+ "maxActionPointsPerTurn:" + c.getMaxActionPointsPerTurn() + "<br>" + "attackRange:"
						+ c.getAttackRange() + "<br>" + "attackDamage:" + c.getAttackDamage() + "<br>" + "speed:"
						+ c.getSpeed() + "<br>" + "Leader: " + flag + "<br>";

				for (int j = 1; j <= c.getAbilities().size(); j++) {
					s += "Ability " + j + " :" + c.getAbilities().get(j - 1).getName() + "<br>";

				}
				if (c.getAppliedEffects().size() == 0) {
					s += "No Effects";
				}
				for (Effect eff : c.getAppliedEffects()) {
					s += "AppliedEffect :";
					s += eff.getName() + "<br>";
					s += "Duration :" + eff.getDuration() + "<br>";
				}
				s += "</html>";
				y.setToolTipText(s);
				gameview.bottomleft.add(y);
			}
			// //getting 2nd player champions
			for (Champion c : game.getSecondPlayer().getTeam()) {
				JButton y = new JButton();
				boolean flag = false;
				if (game.getSecondPlayer().getLeader() == c) {
					flag = true;
				}
				y.setFocusable(false);
				y.setBackground(new Color(0x1c2e4a));
				y.setForeground(Color.white);
				y.addActionListener(this);
				y.setText(c.toString());
				String s = "<html>" + "maxHP:" + c.getMaxHP() + "<br>" + "mana:" + c.getMana() + "<br>"
						+ "maxActionPointsPerTurn:" + c.getMaxActionPointsPerTurn() + "<br>" + "attackRange:"
						+ c.getAttackRange() + "<br>" + "attackDamage:" + c.getAttackDamage() + "<br>" + "speed:"
						+ c.getSpeed() + "<br>" + "Leader: " + flag + "<br>";

				for (int j = 1; j <= c.getAbilities().size(); j++) {
					s += "Ability " + j + " :" + c.getAbilities().get(j - 1).getName() + "<br>";

				}
				if (c.getAppliedEffects().size() == 0) {
					s += "No Effects";
				}
				for (Effect eff : c.getAppliedEffects()) {
					s += "AppliedEffect :";
					s += eff.getName() + "<br>";
					s += "Duration :" + eff.getDuration() + "<br>";
				}
				s += "</html>";
				y.setToolTipText(s);
				gameview.bottomright.add(y);
			}

			firstleaderabilityused = new JLabel();
			String l = String.valueOf(game.isFirstLeaderAbilityUsed());
			firstleaderabilityused.setText("P1 ability used:  " + l);
			firstleaderabilityused.setBackground(new Color(0x58181F));
			firstleaderabilityused.setOpaque(true);
			firstleaderabilityused.setForeground(Color.white);

			l = String.valueOf(game.isSecondLeaderAbilityUsed());
			secondleaderabilityused = new JLabel("P2 ability used:  " + l);
			secondleaderabilityused.setBackground(new Color(0x1c2e4a));
			secondleaderabilityused.setOpaque(true);
			secondleaderabilityused.setForeground(Color.white);

			gameview.bottomleft.add(firstleaderabilityused);
			gameview.bottomright.add(secondleaderabilityused);
			boardbuttons = new JButton[5][5];
			// first row buttons
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[4][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}
			// 2nd row
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[3][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}
			// 3rd row
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[2][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}
			// 4th row
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[1][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}
			// 5th row
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[0][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}

			// placing covers

			gameboard = game.getBoard();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (gameboard[i][j] instanceof Cover) {
						((JButton) boardbuttons[i][j]).setText("COVER");
						((JButton) boardbuttons[i][j]).setBackground(Color.darkGray);
						((JButton) boardbuttons[i][j]).setForeground(Color.white);
						int z = ((Cover) gameboard[i][j]).getCurrentHP();
						String s = "<html>" + "CurrentHP: " + z + "</html>";

						((JButton) boardbuttons[i][j]).setToolTipText(s);
					}
				}

			}
			// game.placeChampions();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (gameboard[i][j] instanceof Champion) {
						Champion champ1 = (Champion) gameboard[i][j];
						String k = champ1.getName();
						((JButton) boardbuttons[i][j]).setText(k);
						String z;
						if (game.getFirstPlayer().getTeam().contains(champ1)) {
							((JButton) boardbuttons[i][j]).setBackground(new Color(0x58181F));
							z = game.getFirstPlayer().getName();
						} else {
							((JButton) boardbuttons[i][j]).setBackground(new Color(0x1c2e4a));
							z = game.getSecondPlayer().getName();
						}
						((JButton) boardbuttons[i][j]).setForeground(Color.white);
						int m = champ1.getCurrentHP();
						String s = "<html>" + "Player: " + z + " " + "CurrentHP: " + m + "</html>";

						((JButton) boardbuttons[i][j]).setToolTipText(s);

					}
				}

			}

			JLabel curnamelb = new JLabel();
			String s21 = game.getCurrentChampion().getName();
			curnamelb.setText("Current Champ: " + s21);
			curnamelb.setForeground(Color.white);
			JLabel curtypelb = new JLabel();
			if (game.getCurrentChampion() instanceof Hero) {
				curtypelb.setText("Type: Hero");
			}
			if (game.getCurrentChampion() instanceof AntiHero) {
				curtypelb.setText("Type: AntiHero");
			}
			if (game.getCurrentChampion() instanceof Villain) {
				curtypelb.setText("Type: Villain");
			}
			curtypelb.setForeground(Color.white);

			JLabel curhplb = new JLabel();
			int x11 = game.getCurrentChampion().getCurrentHP();
			curhplb.setText("Current HP: " + x11);
			curhplb.setForeground(Color.white);

			JLabel curmanalb = new JLabel();
			x11 = game.getCurrentChampion().getMana();
			curmanalb.setText("Current Mana: " + x11);
			curmanalb.setForeground(Color.white);

			JLabel curactionplb = new JLabel();
			x11 = game.getCurrentChampion().getCurrentActionPoints();
			curactionplb.setText("Current ActionPoints: " + x11);
			curactionplb.setForeground(Color.white);

			JLabel curattackrangeb = new JLabel();
			x11 = game.getCurrentChampion().getAttackRange();
			curattackrangeb.setText("AttackRange: " + x11);
			curattackrangeb.setForeground(Color.white);

			JLabel curattackdamb = new JLabel();
			x11 = game.getCurrentChampion().getAttackDamage();
			curattackdamb.setText("AttackDamage: " + x11);
			curattackdamb.setForeground(Color.white);

			gameview.left.add(curnamelb);
			gameview.left.add(curtypelb);
			gameview.left.add(curhplb);
			gameview.left.add(curmanalb);
			gameview.left.add(curactionplb);
			gameview.left.add(curattackdamb);
			gameview.left.add(curattackrangeb);

			for (int i = 0; i < game.getCurrentChampion().getAbilities().size(); i++) {
				Ability a = game.getCurrentChampion().getAbilities().get(i);
				String q = "<html>" + "Ability";
				q += "Name: " + a.getName() + "<br>";
				q += "Type: ";
				if (a instanceof CrowdControlAbility) {
					q += "CrowdControlAbility: " + "<br>";
					CrowdControlAbility b = (CrowdControlAbility) game.getCurrentChampion().getAbilities().get(i);
					q += "Effect: " + b.getEffect().getName() + "<br>";
				}
				if (a instanceof DamagingAbility) {
					q += "DamagingAbility" + "<br>";
					DamagingAbility b = (DamagingAbility) game.getCurrentChampion().getAbilities().get(i);
					q += "DamageAmount: " + b.getDamageAmount() + "<br>";
				}
				if (a instanceof HealingAbility) {
					q += "HealingAbility" + "<br>";
					HealingAbility b = (HealingAbility) game.getCurrentChampion().getAbilities().get(i);
					q += "HealingAbility: " + b.getHealAmount() + "<br>" + "</html>";
				}
				q += "AreaOfEffect: " + a.getCastArea() + "<br>";
				q += "CastRange: " + a.getCastRange() + "<br>";
				q += "ManaCost: " + a.getManaCost() + "<br>";
				q += "ActionCost: " + a.getRequiredActionPoints() + "<br>";
				q += "baseCooldown: " + a.getBaseCooldown() + "<br>";
				q += "CurrentCoolDown: " + a.getCurrentCooldown() + "<br>";

				JLabel khara = new JLabel();
				khara.setText(q);
				khara.setFont(new Font("My Boli", Font.PLAIN, 10));
				khara.setForeground(Color.white);

				gameview.left.add(khara);

			}

			move = new JButton();
			move.setText("Move");
			move.setBackground(new Color(0x1c2e4a));
			move.setForeground(Color.white);
			move.setFocusable(false);
			move.addActionListener(this);
			gameview.right.add(move);

			attack = new JButton();
			attack.setText("Attack");
			attack.setBackground(new Color(0x1c2e4a));
			attack.setForeground(Color.white);
			attack.setFocusable(false);
			attack.addActionListener(this);
			gameview.right.add(attack);

			endturn = new JButton();
			endturn.setText("Endturn");
			endturn.setBackground(new Color(0x1c2e4a));
			endturn.setForeground(Color.white);
			endturn.setFocusable(false);
			endturn.addActionListener(this);
			gameview.right.add(endturn);

			moveview = new Moveview();
			attackview = new Attackview();
			// castview = new Castview();

			leaderAbility = new JButton();
			leaderAbility.setText("leaderAbility");
			leaderAbility.setBackground(new Color(0x1c2e4a));
			leaderAbility.setForeground(Color.white);
			leaderAbility.setFocusable(false);
			leaderAbility.addActionListener(this);
			gameview.right.add(leaderAbility);

			for (int i = 0; i < game.getCurrentChampion().getAbilities().size(); i++) {
				Ability a = game.getCurrentChampion().getAbilities().get(i);
				castability = new JButton();
				castability.setText(a.getName());
				castability.setBackground(new Color(0x1c2e4a));
				castability.setForeground(Color.white);
				castability.setFocusable(false);
				castability.addActionListener(this);
				gameview.right.add(castability);
				but.add(castability);
			}
			/*
			 * for(int i=0;i<but.size();i++) { but.get(i).addActionListener(this); }
			 */
		}
		if (e.getSource() == move)

		{

			moveview.setVisible(true);
			moveview.addKeyListener(this);

		}
		if (e.getSource() == attack) {
			attackview.setVisible(true);
			attackview.addKeyListener(this);

		}
		if (e.getSource() == endturn) {
			game.endTurn();
			refresh();
		}

		if (e.getSource() == leaderAbility) {
			try {
				game.useLeaderAbility();
				refresh();
			} catch (LeaderNotCurrentException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (LeaderAbilityAlreadyUsedException e1) {
				JOptionPane.showMessageDialog(null, "Leader Ability is Already Used", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if (but.contains(e.getSource())) {
			// castview.setVisible(true);
			int i = but.indexOf(e.getSource());
			Ability a = game.getCurrentChampion().getAbilities().get(i);
			AreaOfEffect area = a.getCastArea();
			switch (area) {
			case DIRECTIONAL:
				Direction[] options = { Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT };
				int h = JOptionPane.showOptionDialog(null, "choose direction", "Ability", JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				switch (h) {
				case 3:
					try {
						game.castAbility(a, Direction.RIGHT);
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null,
								"out of range target or being in a condition that prevents you from casting an ability",
								"Error", JOptionPane.ERROR_MESSAGE);
					} catch (CloneNotSupportedException e1) {
						JOptionPane.showMessageDialog(null, "Call tayesh ASAP Virusss", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, "No Enough Resources fa2eerrr", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					// castview.dispose();
					refresh();
					break;
				case 0:
					try {
						game.castAbility(a, Direction.UP);
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (CloneNotSupportedException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (NotEnoughResourcesException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					// castview.dispose();
					refresh();
					break;
				case 1:
					try {
						game.castAbility(a, Direction.DOWN);
					} catch (AbilityUseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						// JOptionPane.showMessageDialog(null, "out of range target or being in a
						// condition that prevents you from casting an ability", "Error",
						// JOptionPane.ERROR_MESSAGE);
					} catch (CloneNotSupportedException e1) {
						// JOptionPane.showMessageDialog(null, "Call tayesh ASAP Virusss", "Error",
						// JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (NotEnoughResourcesException e1) {
						// JOptionPane.showMessageDialog(null, "No Enough Resources fa2eerrr", "Error",
						// JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					// castview.dispose();
					refresh();
					break;
				case 2:
					try {
						game.castAbility(a, Direction.LEFT);
					} catch (AbilityUseException e1) {
						// JOptionPane.showMessageDialog(null, "out of range target or being in a
						// condition that prevents you from casting an ability", "Error",
						// JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (CloneNotSupportedException e1) {
						// JOptionPane.showMessageDialog(null, "CloneNotSupportedException", "Error",
						// JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (NotEnoughResourcesException e1) {
						// JOptionPane.showMessageDialog(null, "No Enough Resources fa2eerrr", "Error",
						// JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					// castview.dispose();
					refresh();
					break;
				}
				break;

			case SINGLETARGET:
				// this.SingleTarget=true;
				JTextField x = new JTextField();
				JTextField y = new JTextField();
				Object[] s = { "row", x, "column", y };

				int t = JOptionPane.showConfirmDialog(null, s, "choose cell", JOptionPane.OK_CANCEL_OPTION);
				if (t == 0) {

					try {
						game.castAbility(a, Integer.parseInt(x.getText()), Integer.parseInt(y.getText()));

					} catch (AbilityUseException e1) {
						// JOptionPane.showMessageDialog(null, e1.getMessage(),"out of range target or
						// being in a condition that prevents you from casting an ability",
						// JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (InvalidTargetException e2) {
						// JOptionPane.showMessageDialog(null, e2.getMessage(), " can not cast an
						// ability on an invalid target or an empty cell", JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (CloneNotSupportedException e3) {
						// JOptionPane.showMessageDialog(null, e3.getMessage(), "Error",
						// JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(null, e3.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (NotEnoughResourcesException e4) {
						// JOptionPane.showMessageDialog(null, e4.getMessage(), "Error",
						// JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(null, e4.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					refresh();
					// castview.dispose();
				}

				break;

			case TEAMTARGET:
				try {
					game.castAbility(a);
				} catch (AbilityUseException e1) {
					// JOptionPane.showMessageDialog(null, "AbilityUseException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (CloneNotSupportedException e1) {
					// JOptionPane.showMessageDialog(null, "CloneNotSupportedException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException e1) {
					// JOptionPane.showMessageDialog(null, "NotEnoughResourcesException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				// castview.dispose();
				refresh();
				break;

			case SELFTARGET:
				try {
					game.castAbility(a);
				} catch (AbilityUseException e1) {
					// JOptionPane.showMessageDialog(null, "AbilityUseException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (CloneNotSupportedException e1) {
					// JOptionPane.showMessageDialog(null, "CloneNotSupportedException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException e1) {
					// JOptionPane.showMessageDialog(null, "NotEnoughResourcesException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				// castview.dispose();
				refresh();
				break;

			case SURROUND:
				try {
					game.castAbility(a);
				} catch (AbilityUseException e1) {
					// JOptionPane.showMessageDialog(null, "AbilityUseException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (CloneNotSupportedException e1) {
					// JOptionPane.showMessageDialog(null, "CloneNotSupportedException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException e1) {
					// JOptionPane.showMessageDialog(null, "NotEnoughResourcesException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				// castview.dispose();
				refresh();
				break;

			}
		}
	}

	private void refresh() {
		if (game.checkGameOver() != null) {
			Player x = game.checkGameOver();
			String y = x.getName();
			JOptionPane.showMessageDialog(null, "the winner is: " + y, "Avengers End Game", JOptionPane.PLAIN_MESSAGE);
			gameview.dispose();
			startview = new Start();
		} else {
			gameview.dispose();
			gameview = new Gameview();

			String p1name = game.getFirstPlayer().getName();
			firstname = new JLabel("P1 name:" + p1name);

			String p2name = game.getSecondPlayer().getName();
			// firstname.setBounds(0, 10, 100, 100);
			secondname = new JLabel("P2 name:" + p2name);

			// secondname.setBounds(1300, 10, 100, 100);
			gameview.top.add(firstname);
			gameview.top.add(secondname);
//System.out.println("1");
			turnoorderq = new JLabel();
			ArrayList<Champion> tmpchamps = new ArrayList<>();
			String s1 = "1: " + game.getCurrentChampion().getName();
			int i1 = 2;
			while (!game.getTurnOrder().isEmpty()) {
				tmpchamps.add((Champion) game.getTurnOrder().remove());
				if (game.getTurnOrder().isEmpty())
					break;
				s1 += " " + i1 + ":" + ((Champion) game.getTurnOrder().peekMin()).getName();
				i1++;

			}
			for (int i = 0; i < tmpchamps.size(); i++) {
				game.getTurnOrder().insert(tmpchamps.get(i));
			}
			turnoorderq.setText(s1);
			turnoorderq.setFont(new Font("Consolas", Font.BOLD, 10));
			gameview.top.add(turnoorderq);

			// getting first player champions
			for (Champion c : game.getFirstPlayer().getTeam()) {
				JButton y = new JButton();
				boolean flag = false;
				if (game.getFirstPlayer().getLeader() == c) {
					flag = true;
				}
				y.setFocusable(false);
				y.setBackground(new Color(0x58181F));
				y.setForeground(Color.white);
				y.addActionListener(this);
				y.setText(c.toString());
				String s = "<html>" + "maxHP:" + c.getMaxHP() + "<br>" + "mana:" + c.getMana() + "<br>"
						+ "maxActionPointsPerTurn:" + c.getMaxActionPointsPerTurn() + "<br>" + "attackRange:"
						+ c.getAttackRange() + "<br>" + "attackDamage:" + c.getAttackDamage() + "<br>" + "speed:"
						+ c.getSpeed() + "<br>" + "Leader: " + flag + "<br>";

				for (int j = 1; j <= c.getAbilities().size(); j++) {
					s += "Ability " + j + " :" + c.getAbilities().get(j - 1).getName() + "<br>";

				}
				if (c.getAppliedEffects().size() == 0) {
					s += "No Effects";
				}
				for (Effect eff : c.getAppliedEffects()) {
					s += "AppliedEffect :";
					s += eff.getName() + "<br>";
					s += "Duration :" + eff.getDuration() + "<br>";
				}
				s += "</html>";
				y.setToolTipText(s);
				gameview.bottomleft.add(y);
			}
			// //getting 2nd player champions
			for (Champion c : game.getSecondPlayer().getTeam()) {
				JButton y = new JButton();
				boolean flag = false;
				if (game.getSecondPlayer().getLeader() == c) {
					flag = true;

				}
				y.setFocusable(false);
				y.setBackground(new Color(0x1c2e4a));
				y.setForeground(Color.white);
				y.addActionListener(this);
				y.setText(c.toString());
				String s = "<html>" + "maxHP:" + c.getMaxHP() + "<br>" + "mana:" + c.getMana() + "<br>"
						+ "maxActionPointsPerTurn:" + c.getMaxActionPointsPerTurn() + "<br>" + "attackRange:"
						+ c.getAttackRange() + "<br>" + "attackDamage:" + c.getAttackDamage() + "<br>" + "speed:"
						+ c.getSpeed() + "<br>" + "Leader: " + flag + "<br>";

				for (int j = 1; j <= c.getAbilities().size(); j++) {
					s += "Ability " + j + " :" + c.getAbilities().get(j - 1).getName() + "<br>";

				}
				if (c.getAppliedEffects().size() == 0) {
					s += "No Effects";
				}
				for (Effect eff : c.getAppliedEffects()) {
					s += "AppliedEffect :";
					s += eff.getName() + "<br>";
					s += "Duration :" + eff.getDuration() + "<br>";
				}
				s += "</html>";
				y.setToolTipText(s);
				gameview.bottomright.add(y);
			}
			firstleaderabilityused = new JLabel();
			String l = String.valueOf(game.isFirstLeaderAbilityUsed());
			firstleaderabilityused.setText("P1 ability used:  " + l);
			firstleaderabilityused.setBackground(new Color(0x58181F));
			firstleaderabilityused.setOpaque(true);
			firstleaderabilityused.setForeground(Color.white);

			l = String.valueOf(game.isSecondLeaderAbilityUsed());
			secondleaderabilityused = new JLabel("P2 ability used:  " + l);
			secondleaderabilityused.setBackground(new Color(0x1c2e4a));
			secondleaderabilityused.setOpaque(true);
			secondleaderabilityused.setForeground(Color.white);

			gameview.bottomleft.add(firstleaderabilityused);
			gameview.bottomright.add(secondleaderabilityused);
			// making the board
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[4][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}
			// 2nd row
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[3][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}
			// 3rd row
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[2][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}
			// 4th row
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[1][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}
			// 5th row
			for (int i = 0; i < 5; i++) {
				JButton y = new JButton();
				y.setFocusable(false);
				boardbuttons[0][i] = y;
				y.addActionListener(this);
				gameview.centre.add(y);
			}
			gameboard = game.getBoard();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (gameboard[i][j] instanceof Cover) {
						((JButton) boardbuttons[i][j]).setText("COVER");
						((JButton) boardbuttons[i][j]).setBackground(Color.darkGray);
						((JButton) boardbuttons[i][j]).setForeground(Color.white);
						int z = ((Cover) gameboard[i][j]).getCurrentHP();
						String s = "<html>" + "CurrentHP: " + z + "</html>";

						((JButton) boardbuttons[i][j]).setToolTipText(s);
					}
				}

			}
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (gameboard[i][j] instanceof Champion) {
						Champion champ1 = (Champion) gameboard[i][j];
						String k = champ1.getName();
						((JButton) boardbuttons[i][j]).setText(k);
						String z;
						if (game.getFirstPlayer().getTeam().contains(champ1)) {
							((JButton) boardbuttons[i][j]).setBackground(new Color(0x58181F));
							z = game.getFirstPlayer().getName();
						} else {
							((JButton) boardbuttons[i][j]).setBackground(new Color(0x1c2e4a));
							z = game.getSecondPlayer().getName();
						}
						((JButton) boardbuttons[i][j]).setForeground(Color.white);
						int m = champ1.getCurrentHP();
						String s = "<html>" + "Player: " + z + " " + "CurrentHP: " + m + "</html>";

						((JButton) boardbuttons[i][j]).setToolTipText(s);

					}
				}

			}
			JLabel curnamelb = new JLabel();
			String s21 = game.getCurrentChampion().getName();
			curnamelb.setText("Current Champ: " + s21);
			curnamelb.setForeground(Color.white);
			JLabel curtypelb = new JLabel();
			if (game.getCurrentChampion() instanceof Hero) {
				curtypelb.setText("Type: Hero");
			}
			if (game.getCurrentChampion() instanceof AntiHero) {
				curtypelb.setText("Type: AntiHero");
			}
			if (game.getCurrentChampion() instanceof Villain) {
				curtypelb.setText("Type: Villain");
			}
			curtypelb.setForeground(Color.white);

			JLabel curhplb = new JLabel();
			int x11 = game.getCurrentChampion().getCurrentHP();
			curhplb.setText("Current HP: " + x11);
			curhplb.setForeground(Color.white);

			JLabel curmanalb = new JLabel();
			x11 = game.getCurrentChampion().getMana();
			curmanalb.setText("Current Mana: " + x11);
			curmanalb.setForeground(Color.white);

			JLabel curactionplb = new JLabel();
			x11 = game.getCurrentChampion().getCurrentActionPoints();
			curactionplb.setText("Current ActionPoints: " + x11);
			curactionplb.setForeground(Color.white);

			JLabel curattackrangeb = new JLabel();
			x11 = game.getCurrentChampion().getAttackRange();
			curattackrangeb.setText("AttackRange: " + x11);
			curattackrangeb.setForeground(Color.white);

			JLabel curattackdamb = new JLabel();
			x11 = game.getCurrentChampion().getAttackDamage();
			curattackdamb.setText("AttackDamage: " + x11);
			curattackdamb.setForeground(Color.white);

			gameview.left.add(curnamelb);
			gameview.left.add(curtypelb);
			gameview.left.add(curhplb);
			gameview.left.add(curmanalb);
			gameview.left.add(curactionplb);
			gameview.left.add(curattackdamb);
			gameview.left.add(curattackrangeb);

			for (int i = 0; i < game.getCurrentChampion().getAbilities().size(); i++) {
				Ability a = game.getCurrentChampion().getAbilities().get(i);
				String q = "<html>" + "Ability";
				q += "Name: " + a.getName() + "<br>";
				q += "Type: ";
				if (a instanceof CrowdControlAbility) {
					q += "CrowdControlAbility: " + "<br>";
					CrowdControlAbility b = (CrowdControlAbility) game.getCurrentChampion().getAbilities().get(i);
					q += "Effect: " + b.getEffect().getName() + "<br>";
				}
				if (a instanceof DamagingAbility) {
					q += "DamagingAbility" + "<br>";
					DamagingAbility b = (DamagingAbility) game.getCurrentChampion().getAbilities().get(i);
					q += "DamageAmount: " + b.getDamageAmount() + "<br>";
				}
				if (a instanceof HealingAbility) {
					q += "HealingAbility" + "<br>";
					HealingAbility b = (HealingAbility) game.getCurrentChampion().getAbilities().get(i);
					q += "HealingAbility: " + b.getHealAmount() + "<br>" + "</html>";
				}
				q += "AreaOfEffect: " + a.getCastArea() + "<br>";
				q += "CastRange: " + a.getCastRange() + "<br>";
				q += "ManaCost: " + a.getManaCost() + "<br>";
				q += "ActionCost: " + a.getRequiredActionPoints() + "<br>";
				q += "baseCooldown: " + a.getBaseCooldown() + "<br>";
				q += "CurrentCoolDown: " + a.getCurrentCooldown() + "<br>";

				JLabel khara = new JLabel();
				khara.setText(q);
				khara.setFont(new Font("My Boli", Font.PLAIN, 10));
				khara.setForeground(Color.white);

				gameview.left.add(khara);

			}

			move = new JButton();
			move.setText("Move");
			move.setBackground(new Color(0x1c2e4a));
			move.setForeground(Color.white);
			move.setFocusable(false);
			move.addActionListener(this);
			gameview.right.add(move);

			attack = new JButton();
			attack.setText("Attack");
			attack.setBackground(new Color(0x1c2e4a));
			attack.setForeground(Color.white);
			attack.setFocusable(false);
			attack.addActionListener(this);
			gameview.right.add(attack);

			endturn = new JButton();
			endturn.setText("Endturn");
			endturn.setBackground(new Color(0x1c2e4a));
			endturn.setForeground(Color.white);
			endturn.setFocusable(false);
			endturn.addActionListener(this);
			gameview.right.add(endturn);

			moveview = new Moveview();
			attackview = new Attackview();
			// castview = new Castview();

			leaderAbility = new JButton();
			leaderAbility.setText("leaderAbility");
			leaderAbility.setBackground(new Color(0x1c2e4a));
			leaderAbility.setForeground(Color.white);
			leaderAbility.setFocusable(false);
			leaderAbility.addActionListener(this);
			gameview.right.add(leaderAbility);

			but = new ArrayList<>();
			for (int i = 0; i < game.getCurrentChampion().getAbilities().size(); i++) {
				Ability a = game.getCurrentChampion().getAbilities().get(i);
				castability = new JButton();
				castability.setText(a.getName());
				castability.setBackground(new Color(0x1c2e4a));
				castability.setForeground(Color.white);
				castability.setFocusable(false);
				castability.addActionListener(this);
				gameview.right.add(castability);
				but.add(castability);
			}

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		// System.out.println("you entered keycode: "+e.getKeyCode());
		if ((e.getKeyCode() == 40 || e.getKeyCode() == 39 || e.getKeyCode() == 38 || e.getKeyCode() == 37)
				&& moveview.isShowing() == true) {
			moveview.dispose();
			switch (e.getKeyCode()) {
			case 40:
				try {
					game.move(Direction.DOWN);
				} catch (UnallowedMovementException x1) {
					// JOptionPane.showMessageDialog(null, "UnallowedMovementException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException x1) {
					// JOptionPane.showMessageDialog(null, "NotEnoughResourcesException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				refresh();
				break;

			case 39:
				try {
					game.move(Direction.RIGHT);
				} catch (UnallowedMovementException x1) {
					// JOptionPane.showMessageDialog(null, "UnallowedMovementException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException x1) {
					// JOptionPane.showMessageDialog(null, "NotEnoughResourcesException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				refresh();
				break;

			case 38:
				try {
					game.move(Direction.UP);
				} catch (UnallowedMovementException x1) {
					// JOptionPane.showMessageDialog(null, "UnallowedMovementException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException x1) {
					// JOptionPane.showMessageDialog(null, "NotEnoughResourcesException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				refresh();
				break;

			case 37:
				try {
					game.move(Direction.LEFT);
				} catch (UnallowedMovementException x1) {
					// JOptionPane.showMessageDialog(null, "UnallowedMovementException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException x1) {
					// JOptionPane.showMessageDialog(null, "NotEnoughResourcesException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				refresh();
				break;
			}
		}
		if ((e.getKeyCode() == 40 || e.getKeyCode() == 39 || e.getKeyCode() == 38 || e.getKeyCode() == 37)
				&& attackview.isShowing() == true) {
			attackview.dispose();
			switch (e.getKeyCode()) {
			case 40:
				try {
					game.attack(Direction.DOWN);
				} catch (ChampionDisarmedException x1) {
					// JOptionPane.showMessageDialog(null, "ChampionDisarmedException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException x1) {
					// JOptionPane.showMessageDialog(null, "NotEnoughResourcesException", "Error",
					// JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (InvalidTargetException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				refresh();
				break;

			case 39:
				try {
					game.attack(Direction.RIGHT);
				} catch (ChampionDisarmedException x1) {
					//JOptionPane.showMessageDialog(null, "ChampionDisarmedException", "Error",
					//		JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException x1) {
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				refresh();
				break;

			case 38:
				try {
					game.attack(Direction.UP);
				} catch (ChampionDisarmedException x1) {
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException x1) {
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				refresh();
				break;

			case 37:
				try {
					game.attack(Direction.LEFT);
				} catch (ChampionDisarmedException x1) {
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NotEnoughResourcesException x1) {
					JOptionPane.showMessageDialog(null, x1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				refresh();
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
