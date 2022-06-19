package com.daviddu64;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GuessNumber extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;
	private JSpinner spinner;
	private JTextField textField;
	private JButton btnStop;
	private JButton btnStart;
	private JButton btnOk;
	private JLabel lblResultat;
	private JLabel lblScore;
	private JLabel lblSteps;
	private JLabel lblTry;
	private int gameScore, gameTry, nbrTry, gameDifficult, randomNumber, CountDown, gameSteps, finalScore;
	private boolean isWinner = false;
	private Random random;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuessNumber frame = new GuessNumber();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuessNumber() {
		setResizable(false);
		setTitle("Devine le nombre");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0, 0, 205));
		progressBar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		progressBar.setValue(30);
		progressBar.setStringPainted(true);
		progressBar.setString("0 secondes");
		progressBar.setMaximum(30);
		progressBar.setOrientation(SwingConstants.VERTICAL);
		progressBar.setBounds(513, 108, 31, 188);
		contentPane.add(progressBar);

		JLabel lblNewLabel = new JLabel("Choisissez la dificulté:");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 167, 24);
		contentPane.add(lblNewLabel);

		spinner = new JSpinner();
		spinner.setBackground(Color.BLUE);
		spinner.setModel(new SpinnerListModel(new String[] { "Facile", "Moyen", "Difficile", "Expert" }));
		spinner.setForeground(Color.BLUE);
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinner.setBounds(143, 13, 89, 20);
		((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
		contentPane.add(spinner);

		JTextArea txtrFacileDe = new JTextArea();
		txtrFacileDe.setEditable(false);
		txtrFacileDe.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtrFacileDe.setForeground(Color.BLUE);
		txtrFacileDe.setText(
				"Facile de 0 à 5, Vous avez 3 essais\r\nMoyen de 0 à 10, Vous avez  5 essais\r\nDifficile de 0 à 20 Vous avez 10 essais\r\nExpert de 0 à 30, Vous avez 15 essais\r\nVous avez 30 secondes par étapes");
		txtrFacileDe.setBounds(242, 11, 309, 86);
		contentPane.add(txtrFacileDe);

		btnStart = new JButton("Commencer");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStop.setEnabled(true);
				btnStart.setEnabled(false);
				btnOk.setEnabled(true);
				lblResultat.setText("Jouer");
				lblResultat.setForeground(Color.blue);
				switch (spinner.getValue().toString()) {
				case "Facile":
					gameTry = 3;
					gameDifficult = 5;
					break;
				case "Moyen":
					gameTry = 5;
					gameDifficult = 10;
					break;
				case "Difficile":
					gameTry = 10;
					gameDifficult = 20;
					break;
				case "Expert":
					gameTry = 15;
					gameDifficult = 30;
					break;
				}
				CountDown = 30;
				gameSteps = 1;
				StartGame();
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStart.setBounds(10, 64, 123, 33);
		contentPane.add(btnStart);

		btnStop = new JButton("Arreter");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalStep();
			}
		});
		btnStop.setEnabled(false);
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStop.setBounds(143, 64, 89, 33);
		contentPane.add(btnStop);

		textField = new JTextField();
		textField.setText("0");
		textField.setFocusCycleRoot(true);
		textField.setFocusTraversalPolicyProvider(true);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField.setBounds(48, 240, 129, 33);
		contentPane.add(textField);
		textField.setColumns(1);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textField.getText();
				// On verifie si le champ est vide est si c'est un chiffre
				boolean isNumeric = str.matches("[+-]?\\d*(\\.\\d+)?");
				if (isNumeric == false || str.isEmpty() == true) {
					lblResultat.setText("Saisissez un chiffre");
					lblResultat.setForeground(Color.red);
				} else {
					// Si le nombre alèatoire est égale a celui saisie
					if (randomNumber == Integer.parseInt(textField.getText())) {
						isWinner = true;
					} else {
						nbrTry++;
						lblTry.setText("Nombres d'essai(s): " + nbrTry + " sur " + gameTry);
						if (nbrTry == gameTry) {
							btnOk.setEnabled(false);
						}
					}
				}
			}
		});
		btnOk.setEnabled(false);
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOk.setBounds(204, 240, 89, 33);
		contentPane.add(btnOk);

		lblResultat = new JLabel("En attente");
		lblResultat.setForeground(Color.BLUE);
		lblResultat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResultat.setBounds(44, 205, 133, 24);
		contentPane.add(lblResultat);

		lblScore = new JLabel("Score: 0");
		lblScore.setForeground(new Color(0, 128, 0));
		lblScore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblScore.setBounds(44, 162, 89, 24);
		contentPane.add(lblScore);

		lblSteps = new JLabel("Etapes: 0 sur 10");
		lblSteps.setForeground(new Color(0, 128, 0));
		lblSteps.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSteps.setBounds(354, 162, 133, 24);
		contentPane.add(lblSteps);

		lblTry = new JLabel("Nombres d'essai(s): 0 sur 3");
		lblTry.setForeground(new Color(0, 128, 0));
		lblTry.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTry.setBounds(150, 162, 194, 24);
		contentPane.add(lblTry);
	}

	public void StartGame() {
		// on creer un thread pour rafraichir les elements
		new Thread(new Runnable() {
			public void run() {
				random = new Random();
				while (CountDown >= 0 && gameSteps < 10) {
					if (CountDown == 30) {
						// On genere un nombre aléatoire
						randomNumber = random.nextInt(0, gameDifficult + 1);
						System.out.println(randomNumber);
					}
					lblTry.setText("Nombres d'essai(s): " + nbrTry + " sur " + gameTry);
					progressBar.setValue(CountDown);
					progressBar.setString(CountDown + " secondes");
					try {
						// On fait une pause pendant une seconde
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// si on perd
					if (CountDown == 0 || nbrTry == gameTry) {
						lblResultat.setText("Perdu");
						lblResultat.setForeground(Color.red);
						try {
							// On fait une pause pendant 3 secondes
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						nextSteps();
						return;
					}
					// Si on gagne
					if (isWinner == true) {
						finalScore = gameTry - nbrTry;
						gameScore = gameScore + finalScore;
						lblResultat.setText("Gagné");
						lblResultat.setForeground(Color.green);
						try {
							// On fait une pause pendant 3 secondes
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						nextSteps();
						return;
					}
					CountDown = CountDown - 1;
				}
				// Si le jeu est fini
				if (gameSteps == 10) {
					JOptionPane.showMessageDialog(null, "La partie est finie, avec le score de: " + gameScore);
					finalStep();
					return;
				}
			}

			// on lance le thread
		}).start();
	}

	// On réinitialise certaine variable aveant la prochaine étape
	public void nextSteps() {
		// Si le bouton stop est desactivé
		if (btnStop.isEnabled() == false) {
			finalStep();
			// btnStart.setEnabled(true);
			return;
		} else {
			btnOk.setEnabled(true);
		}
		CountDown = 30;
		nbrTry = 0;
		gameSteps++;
		lblResultat.setText("Jouer");
		lblResultat.setForeground(Color.blue);
		lblScore.setText("Score: " + gameScore);
		lblSteps.setText("Etapes: " + gameSteps + " sur 10");
		lblTry.setText("Nombres d'essai(s): " + nbrTry + " sur " + gameTry);
		isWinner = false;
		StartGame();
		return;
	}

	// On réinitialise tout à la dérnière étapes
	public void finalStep() {
		gameScore = 0;
		gameTry = 0;
		nbrTry = 0;
		gameScore = 0;
		gameDifficult = 0;
		randomNumber = 0;
		CountDown = 0;
		gameSteps = 0;
		finalScore = 0;
		isWinner = false;
		lblResultat.setText("En attente...");
		lblResultat.setForeground(Color.blue);
		lblScore.setText("Score: " + gameScore);
		lblSteps.setText("Etapes: " + gameSteps + " sur 10");
		progressBar.setValue(CountDown);
		progressBar.setString(CountDown + " secondes");
		lblTry.setText("Nombres d'essai(s): " + nbrTry + " sur " + gameTry);
		// On attend que l'autre Thread s'arrete
		try {
			// On fait une pause pendant 3 secondes
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (btnStop.isEnabled() == true) {
			btnStart.setEnabled(true);
		}
		btnStop.setEnabled(false);
		btnOk.setEnabled(false);
		return;
	}

}
