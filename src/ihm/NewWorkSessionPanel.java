package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Image;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.Calendar;

import databases.DataBase;
import databases.ResultArray;

public class NewWorkSessionPanel {

	public static JCustomTextField missionJTF;
	public static JCustomTextField heuresJTF;
	public static JCustomTextField minutesJTF;
	public static JCustomComboBox<String> missionsCB;
	public static JCustomComboBox<String> dateCB;
	public static JCustomTextField dateHeureJTF;
	public static JCustomTextField dateMinuteJTF;

	public static JLabel erreurLabel;

	public static JPanel getPanel(MainFrame frame, int projectID, int persID) {

		DataBase db = DataBase.db;

		ResultArray project = db.getProject(projectID);

		String nomProjet = (String) project.get(0, "nom_projet");
		String clientProjet = (String) project.get(0, "client_projet");

		Calendar calendar = Calendar.getInstance();
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currentMinute = calendar.get(Calendar.MINUTE);

		JPanel innerPanel = new JPanel(new GridBagLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		innerPanel.setBackground(new Color(251, 251, 251));

		JRoundedButton goBackBtn = new JRoundedButton();

		ImageIcon goBackIconD = new ImageIcon(
				new ImageIcon("img/icone_back.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon goBackIconH = new ImageIcon(
				new ImageIcon("img/icone_back_hover.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		goBackBtn.setMultipleIcon(goBackIconD, goBackIconH, null);

		goBackBtn.setToolTipText("Retour à mes projets");
		goBackBtn.setPreferredSize(new Dimension(34, 34));

		goBackBtn.setName("newWorkSession/goBack");
		goBackBtn.addActionListener(frame);

		String titleStyle = "style='font-weight:normal; font-size:14.5px; font-family:Optima; color:#444444'";
		String titleS = String.format("<html><span %s>NOUVELLE SESSION DE TRAVAIL</span></html>", titleStyle);

		String nomProjetStyle = "style='font-weight:bold; font-size:11px'";
		String nomProjetS = String.format("<span %s> %s  • </span>", nomProjetStyle, nomProjet.toUpperCase());

		String clientProjetStyle = "style='font-weight:bold; font-size:10px; font-style:italic'";
		String clientProjetS = String.format("<span %s> %s </span>", clientProjetStyle, clientProjet);

		String titleProjectS = String.format("<html>%s %s</html>", nomProjetS, clientProjetS);

		String labelStyle = "style='font-weight:normal; font-size:11.5px; font-family:Optima; color:#444444'";

		String nbHeures1S = String.format("<html><span %s>Durée de la session :</span></html>", labelStyle);
		String nbHeures2S = String.format("<html><span %s>heures et</span></html>", labelStyle);
		String nbHeures3S = String.format("<html><span %s>minutes.</span></html>", labelStyle);

		String missionS = String.format("<html><span %s>Mission :</span></html>", labelStyle);

		String dateS1 = String.format("<html><span %s>Date de la session :</span></html>", labelStyle);
		String dateS2 = String.format("<html><span %s>à</span></html>", labelStyle);
		String dateS3 = String.format("<html><span %s>:</span></html>", labelStyle);

		JLabel titleLabel = new JLabel(titleS, JLabel.CENTER);
		JLabel titleProjectLabel = new JLabel(titleProjectS, JLabel.CENTER);
		JLabel nbHeures1Label = new JLabel(nbHeures1S);
		JLabel nbHeures2Label = new JLabel(nbHeures2S);
		JLabel nbHeures3Label = new JLabel(nbHeures3S);
		JLabel missionLabel = new JLabel(missionS);
		JLabel dateLabel1 = new JLabel(dateS1);
		JLabel dateLabel2 = new JLabel(dateS2);
		JLabel dateLabel3 = new JLabel(dateS3);

		String validerTextD = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#555555'>Valider</span></html>";
		String validerTextH = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#f8f8ff'>Valider</span></html>";

		JRoundedButton validerBtn = new JRoundedButton();
		validerBtn.setMultipleText(validerTextD, validerTextH, null);
		validerBtn.setMultipleInnerColor(validerBtn.getDefaultInnerColor(), new Color(71, 255, 134),
				new Color(19, 203, 82));
		validerBtn.setMultipleBorderColor(validerBtn.getDefaultBorderColor(), new Color(19, 203, 82),
				new Color(71, 255, 134));
		validerBtn.setName("newWorkSessionPanel/valider?projectID=" + projectID);
		validerBtn.addActionListener(frame);

		ArrayList<String> missionsCBChoicesAL = db.getMissionsArrayList(projectID);
		String[] missionsCBChoices = missionsCBChoicesAL.toArray(new String[missionsCBChoicesAL.size() + 1]);
		int nbMissionsChoices = missionsCBChoices.length;
		missionsCBChoices[nbMissionsChoices - 1] = "<html><span style='font-weight:normal'>Autre...</span></html>";
		missionsCB = new JCustomComboBox<String>(missionsCBChoices);

		missionsCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choiceIndex = missionsCB.getSelectedIndex();
				if (choiceIndex == nbMissionsChoices - 1) {
					missionJTF.setVisible(true);
					frame.pack();
				} else {
					missionJTF.setVisible(false);
				}
			}
		});

		missionsCB.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				erreurLabel.setVisible(false);
			}
		});

		missionJTF = new JCustomTextField();
		missionJTF.setPreferredSize(new Dimension(45, 22));
		missionJTF.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		missionJTF.setBorder(BorderFactory.createCompoundBorder(missionJTF.getBorder(),
				BorderFactory.createEmptyBorder(0, 5, 0, 0)));
		missionJTF.setVisible(nbMissionsChoices == 1);

		missionJTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && !Character.isLetter(c)) {
					e.consume();
				} else {
					e.setKeyChar(Character.toUpperCase(c));
				}
			}
		});

		missionJTF.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				erreurLabel.setVisible(false);
			}
		});

		heuresJTF = new JCustomTextField();
		heuresJTF.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		heuresJTF.setText("2");
		heuresJTF.setPreferredSize(new Dimension(30, 22));
		heuresJTF.setHorizontalAlignment(JTextField.RIGHT);

		heuresJTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});

		heuresJTF.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				erreurLabel.setVisible(false);
			}
		});

		minutesJTF = new JCustomTextField();
		minutesJTF.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		minutesJTF.setText("0");
		minutesJTF.setPreferredSize(new Dimension(30, 22));
		minutesJTF.setHorizontalAlignment(JTextField.RIGHT);

		minutesJTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				} else {
					String minutesS = minutesJTF.getText();
					if (minutesS.length() > 0) {
						int minutes = Integer.parseInt(minutesJTF.getText());
						if (minutes >= 6) {
							e.consume();
						} else if (minutesS.length() > 1) {
							e.consume();
						}
					}
				}
			}
		});

		minutesJTF.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				erreurLabel.setVisible(false);
			}
		});

		String[] dateCBChoices = { "Aujourd'hui", "Hier", "Avant-hier" };
		dateCB = new JCustomComboBox<String>(dateCBChoices);

		dateCB.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				erreurLabel.setVisible(false);
			}
		});

		dateHeureJTF = new JCustomTextField();
		dateHeureJTF.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		dateHeureJTF.setText(((Integer) currentHour).toString());
		dateHeureJTF.setPreferredSize(new Dimension(30, 22));
		dateHeureJTF.setHorizontalAlignment(JTextField.RIGHT);

		dateHeureJTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				} else {
					String heureS = dateHeureJTF.getText();
					if (heureS.length() > 0) {
						int heure = Integer.parseInt(dateHeureJTF.getText());
						if (heure >= 3 || (heure == 2 && Character.getNumericValue(c) >= 4)) {
							e.consume();
						} else if (heureS.length() > 1) {
							e.consume();
						} else if (heureS.equals("0") && Character.getNumericValue(c) == 0) {
							e.consume();
						}
					}
				}
			}
		});

		dateHeureJTF.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				erreurLabel.setVisible(false);
			}
		});

		dateMinuteJTF = new JCustomTextField();
		dateMinuteJTF.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		dateMinuteJTF.setText(((Integer) currentMinute).toString());
		dateMinuteJTF.setPreferredSize(new Dimension(30, 22));
		dateMinuteJTF.setHorizontalAlignment(JTextField.RIGHT);

		dateMinuteJTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				} else {
					String minutesS = dateMinuteJTF.getText();
					if (minutesS.length() > 0) {
						int minutes = Integer.parseInt(dateMinuteJTF.getText());
						if (minutes >= 6) {
							e.consume();
						} else if (minutesS.length() > 1) {
							e.consume();
						}
					}
				}
			}
		});

		dateMinuteJTF.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				erreurLabel.setVisible(false);
			}
		});

		erreurLabel = new JLabel("erreur", JLabel.CENTER);
		erreurLabel.setVisible(false);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 0;
		gbc.insets = new Insets(5, 0, 5, 0);
		innerPanel.add(goBackBtn, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);
		innerPanel.add(titleLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		innerPanel.add(titleProjectLabel, gbc);

		JPanel dureePanel = new JColoredPanel();
		dureePanel.add(nbHeures1Label);
		dureePanel.add(heuresJTF);
		dureePanel.add(nbHeures2Label);
		dureePanel.add(minutesJTF);
		dureePanel.add(nbHeures3Label);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 10, 0);
		innerPanel.add(dureePanel, gbc);

		JPanel missionPanel = new JColoredPanel();
		missionPanel.add(missionLabel);
		missionPanel.add(missionsCB);
		missionPanel.add(missionJTF);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 10, 0);
		innerPanel.add(missionPanel, gbc);

		JPanel datePanel = new JColoredPanel();
		datePanel.add(dateLabel1);
		datePanel.add(dateCB);
		datePanel.add(dateLabel2);
		datePanel.add(dateHeureJTF);
		datePanel.add(dateLabel3);
		datePanel.add(dateMinuteJTF);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 10, 0);
		innerPanel.add(datePanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 0, 5, 20);
		innerPanel.add(erreurLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 0, 5, 20);
		innerPanel.add(validerBtn, gbc);

		return JPanelOperations.setBorders(innerPanel, 1.0 / 3.0);
	}
}
