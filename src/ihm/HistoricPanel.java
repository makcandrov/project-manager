package ihm;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Image;
import java.awt.Color;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.Calendar;

import databases.DataBase;
import databases.ResultArray;
import operations.StringOperations;

public class HistoricPanel {

	public static JComboBox<String> dateCB;
	public static JTextField heureJTF;
	public static JTextField minuteJTF;
	public static JTextField missionJTF;
	public static JTextField heuresJTF;
	public static JTextField minutesJTF;
	public static JComboBox<String> missionsCB;

	public static JLabel erreurLabel;

	public static JPanel getPanel(MainFrame frame, int projectID, int persID, int sessionIDtoEdit) {

		DataBase db = DataBase.db;

		ResultArray project = db.getProject(projectID);

		String nomProjet = (String) project.get(0, "nom_projet");
		String clientProjet = (String) project.get(0, "client_projet");

		Calendar calendar = Calendar.getInstance();
		long currentTimestamp = calendar.getTimeInMillis();
		long limitTimestamp = currentTimestamp - 2 * 24 * 60 * 60 * 1000;
		calendar.setTimeInMillis(limitTimestamp);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		limitTimestamp = calendar.getTimeInMillis();

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

		goBackBtn.setName("historicPanel/goBack");
		goBackBtn.addActionListener(frame);

		String titleStyle = "style='font-weight:normal; font-size:14.5px; font-family:Optima; color:#444444'";
		String titleS = String.format("<html><span %s>HISTORIQUE</span></html>", titleStyle);

		String nomProjetStyle = "style='font-weight:bold; font-size:11px'";
		String clientProjetStyle = "style='font-weight:bold; font-size:10px; font-style:italic'";

		String titleProjectS = String.format(
				"<html><span style='display:inline-block'><span %s> %s •</span><span %s> %s </span></span></html>",
				nomProjetStyle, nomProjet.toUpperCase(), clientProjetStyle, clientProjet);

		JLabel titleLabel = new JLabel(titleS, JLabel.CENTER);
		JLabel titleProjectLabel = new JLabel(titleProjectS, JLabel.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 0;
		gbc.insets = new Insets(5, 0, 5, 0);
		innerPanel.add(goBackBtn, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		innerPanel.add(titleLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 10, 0);
		innerPanel.add(titleProjectLabel, gbc);

		JPanel tabPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbcTab = new GridBagConstraints();
		tabPanel.setBackground(new Color(251, 251, 251));

		String headerStyle = "style='font-weight:bold; font-size:11.5px; font-falmily:Optima'";
		String labelStyle = "style='font-weight:normal; font-size:11px; font-falmily:Optima'";

		String dateHeaderS = String.format("<html><span %s>DATE</span></html>", headerStyle);
		String missionHeaderS = String.format("<html><span %s>MISSION</span></html>", headerStyle);
		String dureeHeaderS = String.format("<html><span %s>TEMPS DE TRAVAIL</span></html>", headerStyle);

		JLabel dateHeaderLabel = new JLabel(dateHeaderS, JLabel.CENTER);
		JLabel missionHeaderLabel = new JLabel(missionHeaderS, JLabel.CENTER);
		JLabel dureeHeaderLabel = new JLabel(dureeHeaderS, JLabel.CENTER);

		Border cellLineBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border cellEmptyBorder = new EmptyBorder(7, 12, 7, 12);
		CompoundBorder cellBorder = new CompoundBorder(cellLineBorder, cellEmptyBorder);

		dateHeaderLabel.setBorder(cellBorder);
		missionHeaderLabel.setBorder(cellBorder);
		dureeHeaderLabel.setBorder(cellBorder);

		gbcTab.gridx = 0;
		gbcTab.gridy = 0;
		gbcTab.gridheight = 1;
		gbcTab.gridwidth = 1;
		gbcTab.weightx = 1;
		gbcTab.fill = GridBagConstraints.HORIZONTAL;
		gbcTab.anchor = GridBagConstraints.CENTER;
		gbcTab.insets = new Insets(0, 0, 0, 0);
		tabPanel.add(dateHeaderLabel, gbcTab);

		gbcTab.gridx = 1;
		tabPanel.add(missionHeaderLabel, gbcTab);

		gbcTab.gridx = 2;
		tabPanel.add(dureeHeaderLabel, gbcTab);

		ResultArray sessions = db.getMySessions(projectID, persID);

		for (int i = 0; i < sessions.size; i++) {

			Number dateTimestamp = (Number) sessions.get(i, "date");
			calendar.setTimeInMillis(dateTimestamp.longValue() * 1000);
			String dateEntry = String.format("%02d/%02d/%d", calendar.get(Calendar.DAY_OF_MONTH),
					calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));

			calendar.setTimeInMillis(dateTimestamp.longValue() * 1000);
			int heure = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			String heureEntry = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE));

			String missionEntry = (String) sessions.get(i, "mission");

			long dureeValue = ((Number) sessions.get(i, "temps_travail")).longValue();
			String dureeEntry = StringOperations.formatTime(dureeValue, "jhm");

			int sessionID = (int) sessions.get(i, "id");

			if (missionEntry.equals("_NEW_") || missionEntry.equals("_END_")) {

			} else if (sessionID == sessionIDtoEdit) {

				JPanel dateCellule = new JPanel();
				JPanel missionCellule = new JPanel();
				JPanel dureeCellule = new JPanel();

				String dateS1 = String.format("<html><span %s>Le </span></html>", labelStyle);
				String dateS2 = String.format("<html><span %s>à </span></html>", labelStyle);
				String dateS3 = String.format("<html><span %s>: </span></html>", labelStyle);

				JLabel dateLabel1 = new JLabel(dateS1);
				JLabel dateLabel2 = new JLabel(dateS2);
				JLabel dateLabel3 = new JLabel(dateS3);

				String[] dateCBChoices = new String[3];
				int dateCBSelectedIndex = 0;
				for (int k = 0; k < 3; k++) {
					calendar.setTimeInMillis(limitTimestamp + (2 - k) * 24 * 60 * 60 * 1000);
					if (dateTimestamp.longValue() * 1000 < calendar.getTimeInMillis()) {
						dateCBSelectedIndex = k;
					}
					dateCBChoices[k] = String.format("%02d / %02d / %d", calendar.get(Calendar.DAY_OF_MONTH),
							calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
				}
				dateCB = new JCustomComboBox<String>(dateCBChoices);
				dateCB.setSelectedIndex(dateCBSelectedIndex);

				dateCB.addMouseListener(new MouseAdapter() {
					public void mousePressed(java.awt.event.MouseEvent evt) {
						erreurLabel.setVisible(false);
					}
				});

				heureJTF = new JCustomTextField();
				heureJTF.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
				heureJTF.setText(((Integer) heure).toString());
				heureJTF.setPreferredSize(new Dimension(30, 22));
				heureJTF.setHorizontalAlignment(JTextField.RIGHT);

				heureJTF.addMouseListener(new MouseAdapter() {
					public void mousePressed(java.awt.event.MouseEvent evt) {
						erreurLabel.setVisible(false);
					}
				});

				heureJTF.addKeyListener(new KeyAdapter() {
					public void keyTyped(KeyEvent e) {
						char c = e.getKeyChar();
						if (!Character.isDigit(c)) {
							e.consume();
						} else {
							String heureS = heureJTF.getText();
							if (heureS.length() > 0) {
								int heure = Integer.parseInt(heureJTF.getText());
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

				minuteJTF = new JCustomTextField();
				minuteJTF.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
				minuteJTF.setText(((Integer) minute).toString());
				minuteJTF.setPreferredSize(new Dimension(30, 22));
				minuteJTF.setHorizontalAlignment(JTextField.RIGHT);

				minuteJTF.addMouseListener(new MouseAdapter() {
					public void mousePressed(java.awt.event.MouseEvent evt) {
						erreurLabel.setVisible(false);
					}
				});

				minuteJTF.addKeyListener(new KeyAdapter() {
					public void keyTyped(KeyEvent e) {
						char c = e.getKeyChar();
						if (!Character.isDigit(c)) {
							e.consume();
						} else {
							String minutesS = minuteJTF.getText();
							if (minutesS.length() > 0) {
								int minutes = Integer.parseInt(minuteJTF.getText());
								if (minutes >= 6) {
									e.consume();
								} else if (minutesS.length() > 1) {
									e.consume();
								}
							}
						}
					}
				});

				dateCellule.add(dateLabel1);
				dateCellule.add(dateCB);
				dateCellule.add(dateLabel2);
				dateCellule.add(heureJTF);
				dateCellule.add(dateLabel3);
				dateCellule.add(minuteJTF);

				ArrayList<String> missionsCBChoicesAL = db.getMissionsArrayList(projectID);
				String[] missionsCBChoices = missionsCBChoicesAL.toArray(new String[missionsCBChoicesAL.size() + 1]);
				int nbMissionsChoices = missionsCBChoices.length;
				missionsCBChoices[nbMissionsChoices
						- 1] = "<html><span style='font-weight:normal'>Autre...</span></html>";

				int selectedIndex = 0;
				while (selectedIndex < nbMissionsChoices && !missionsCBChoices[selectedIndex].equals(missionEntry)) {
					selectedIndex++;
				}

				missionsCB = new JCustomComboBox<String>(missionsCBChoices);
				missionsCB.setSelectedIndex(selectedIndex);

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
				missionJTF.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
				missionJTF.setPreferredSize(new Dimension(45, 22));
				missionJTF.setBorder(BorderFactory.createCompoundBorder(missionJTF.getBorder(),
						BorderFactory.createEmptyBorder(0, 5, 0, 0)));
				missionJTF.setVisible(false);

				missionJTF.addMouseListener(new MouseAdapter() {
					public void mousePressed(java.awt.event.MouseEvent evt) {
						erreurLabel.setVisible(false);
					}
				});

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

				missionCellule.add(missionsCB);
				missionCellule.add(missionJTF);

				String dureeS1 = String.format("<html><span %s>heures et </span></html>", labelStyle);
				String dureeS2 = String.format("<html><span %s>minutes </span></html>", labelStyle);

				JLabel dureeLabel1 = new JLabel(dureeS1);
				JLabel dureeLabel2 = new JLabel(dureeS2);

				heuresJTF = new JCustomTextField();
				heuresJTF.setText(((Long) (dureeValue / 3600)).toString());
				heuresJTF.setPreferredSize(new Dimension(30, 22));
				heuresJTF.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
				heuresJTF.setHorizontalAlignment(JTextField.RIGHT);

				heuresJTF.addMouseListener(new MouseAdapter() {
					public void mousePressed(java.awt.event.MouseEvent evt) {
						erreurLabel.setVisible(false);
					}
				});

				heuresJTF.addKeyListener(new KeyAdapter() {
					public void keyTyped(KeyEvent e) {
						char c = e.getKeyChar();
						if (!Character.isDigit(c)) {
							e.consume();
						} else {
							String heuresS = heuresJTF.getText();
							if (heuresS.equals("0") && Character.getNumericValue(c) == 0) {
								e.consume();
							}
						}
					}
				});

				minutesJTF = new JCustomTextField();
				minutesJTF.setText(((Long) ((dureeValue % 3600) / 60)).toString());
				minutesJTF.setPreferredSize(new Dimension(30, 22));
				minutesJTF.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
				minutesJTF.setHorizontalAlignment(JTextField.RIGHT);

				minutesJTF.addMouseListener(new MouseAdapter() {
					public void mousePressed(java.awt.event.MouseEvent evt) {
						erreurLabel.setVisible(false);
					}
				});

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
								} else if (minutes == 0 && Character.getNumericValue(c) == 0) {
									e.consume();
								}
							}
						}
					}
				});

				dureeCellule.add(heuresJTF);
				dureeCellule.add(dureeLabel1);
				dureeCellule.add(minutesJTF);
				dureeCellule.add(dureeLabel2);

				dateCellule.setBorder(cellBorder);
				missionCellule.setBorder(cellBorder);
				dureeCellule.setBorder(cellBorder);

				dateCellule.setBackground(new Color(251, 251, 251));
				missionCellule.setBackground(new Color(251, 251, 251));
				dureeCellule.setBackground(new Color(251, 251, 251));

				ImageIcon validerEditIcon = new ImageIcon(new ImageIcon("img/icone_valider_edit.png").getImage()
						.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
				JRoundedButton validerEditBtn = new JRoundedButton();
				validerEditBtn.setMultipleInnerColor(validerEditBtn.getDefaultInnerColor(), new Color(71, 255, 134),
						new Color(19, 203, 82));
				validerEditBtn.setMultipleBorderColor(validerEditBtn.getDefaultBorderColor(), new Color(19, 203, 82),
						new Color(71, 255, 134));
				validerEditBtn.setToolTipText("Valider les modifications");
				validerEditBtn.setPreferredSize(new Dimension(30, 30));
				validerEditBtn.setIcon(validerEditIcon);
				validerEditBtn.setName("historicPanel/validerEdit?sessionID=" + sessionID + "&projectID=" + projectID);
				validerEditBtn.addActionListener(frame);

				ImageIcon annulerEditIcon = new ImageIcon(new ImageIcon("img/icone_annuler_edit.png").getImage()
						.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
				JRoundedButton annulerEditBtn = new JRoundedButton();
				annulerEditBtn.setMultipleInnerColor(annulerEditBtn.getDefaultInnerColor(), new Color(255, 89, 89),
						new Color(222, 38, 38));
				annulerEditBtn.setMultipleBorderColor(annulerEditBtn.getDefaultBorderColor(), new Color(222, 38, 38),
						new Color(255, 89, 89));
				annulerEditBtn.setToolTipText("Annuler les modifications");
				annulerEditBtn.setPreferredSize(new Dimension(30, 30));
				annulerEditBtn.setIcon(annulerEditIcon);
				annulerEditBtn.setName("historicPanel/annulerEdit?sessionID=" + sessionID + "&projectID=" + projectID);
				annulerEditBtn.addActionListener(frame);

				gbcTab.gridwidth = 1;
				gbcTab.gridy = 1 + i;
				gbcTab.gridx = 0;
				gbcTab.weightx = 1;
				gbcTab.fill = GridBagConstraints.BOTH;
				tabPanel.add(dateCellule, gbcTab);

				gbcTab.gridx = 1;
				tabPanel.add(missionCellule, gbcTab);

				gbcTab.gridx = 2;
				tabPanel.add(dureeCellule, gbcTab);

				gbcTab.gridx = 3;
				gbcTab.weightx = 0;
				gbcTab.fill = GridBagConstraints.NONE;
				gbcTab.insets = new Insets(0, 0, 0, 2);
				tabPanel.add(validerEditBtn, gbcTab);

				gbcTab.gridx = 4;
				gbcTab.weightx = 0;
				gbcTab.fill = GridBagConstraints.NONE;
				gbcTab.insets = new Insets(0, 0, 0, 0);
				tabPanel.add(annulerEditBtn, gbcTab);

			} else {

				String dateCelluleStyle = "style='font-weight:normal; font-style:italic; font-size:11px'";
				String dateCelluleS = String.format("<html><span %s>Le %s à %s</span></html>", dateCelluleStyle,
						dateEntry, heureEntry);

				String missionCelluleStyle = "style='font-weight:bold; font-size:11px'";
				String missoinCelluleS = String.format("<html><span %s> %s </span></html>", missionCelluleStyle,
						missionEntry);

				String dureeCelluleStyle = "style='font-weight:normal; font-size:11px'";
				String dureeCelluleS = String.format("<html><span %s> %s </span></html>", dureeCelluleStyle,
						dureeEntry);

				JLabel dateCelluleLabel = new JLabel(dateCelluleS);
				JLabel missionCelluleLabel = new JLabel(missoinCelluleS);
				JLabel dureeCelluleLabel = new JLabel(dureeCelluleS);

				dateCelluleLabel.setBorder(cellBorder);
				missionCelluleLabel.setBorder(cellBorder);
				dureeCelluleLabel.setBorder(cellBorder);

				ImageIcon editIcon = new ImageIcon(
						new ImageIcon("img/icone_edit.png").getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH));
				JRoundedButton editBtn = new JRoundedButton();
				editBtn.setMultipleInnerColor(editBtn.getDefaultInnerColor(), new Color(255, 201, 57),
						new Color(251, 183, 0));
				editBtn.setMultipleBorderColor(editBtn.getDefaultBorderColor(), new Color(251, 183, 0),
						new Color(255, 201, 57));
				editBtn.setToolTipText("Modifier");
				editBtn.setPreferredSize(new Dimension(30, 30));
				editBtn.setIcon(editIcon);
				editBtn.setName("historicPanel/edit?sessionID=" + sessionID + "&projectID=" + projectID);
				editBtn.addActionListener(frame);

				ImageIcon deleteIcon = new ImageIcon(
						new ImageIcon("img/icone_delete.png").getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH));
				JRoundedButton deleteBtn = new JRoundedButton();
				deleteBtn.setMultipleInnerColor(editBtn.getDefaultInnerColor(), new Color(255, 89, 89),
						new Color(222, 38, 38));
				deleteBtn.setMultipleBorderColor(editBtn.getDefaultBorderColor(), new Color(222, 38, 38),
						new Color(255, 89, 89));
				deleteBtn.setToolTipText("Supprimer");
				deleteBtn.setPreferredSize(new Dimension(30, 30));
				deleteBtn.setIcon(deleteIcon);
				deleteBtn.setName("historicPanel/delete?sessionID=" + sessionID + "&projectID=" + projectID);
				deleteBtn.addActionListener(frame);

				gbcTab.gridwidth = 1;
				gbcTab.gridy = 1 + i;
				gbcTab.gridx = 0;
				gbcTab.weightx = 1;
				gbcTab.fill = GridBagConstraints.BOTH;
				tabPanel.add(dateCelluleLabel, gbcTab);

				gbcTab.gridx = 1;
				tabPanel.add(missionCelluleLabel, gbcTab);

				gbcTab.gridx = 2;
				tabPanel.add(dureeCelluleLabel, gbcTab);

				if (sessionIDtoEdit < 0 && dateTimestamp.longValue() >= limitTimestamp / 1000) {
					gbcTab.gridx = 3;
					gbcTab.weightx = 0;
					gbcTab.fill = GridBagConstraints.NONE;
					gbcTab.insets = new Insets(0, 0, 0, 2);
					tabPanel.add(editBtn, gbcTab);

					gbcTab.gridx = 4;
					gbcTab.weightx = 0;
					gbcTab.insets = new Insets(0, 0, 0, 0);
					tabPanel.add(deleteBtn, gbcTab);
				}

			}

		}

		erreurLabel = new JLabel("erreur", JLabel.CENTER);
		erreurLabel.setVisible(false);

		gbcTab.gridy += 1;
		gbcTab.gridx = 0;
		gbcTab.weighty = 1;
		tabPanel.add(new JLabel(""), gbcTab);

		JPanel tabPanelScroll = new JPanel(new BorderLayout());
		JScrollPane jsp = (new JScrollPane(tabPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		jsp.getVerticalScrollBar().setUnitIncrement(4);
		tabPanelScroll.add(jsp);
		tabPanelScroll.setPreferredSize(new Dimension(1, 1));

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = gbc.fill = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 20, 0);
		innerPanel.add(tabPanelScroll, gbc);

		gbc.gridy += 1;
		gbc.gridx = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		innerPanel.add(erreurLabel, gbc);

		return JPanelOperations.setBorders(innerPanel, 3.0);
	}
}
