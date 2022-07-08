package ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import databases.DataBase;
import databases.ResultArray;
import operations.StringOperations;

public class ProjectPanel {

	private static int persID;
	private static String nom;
	private static String prenom;
	private static String poste;

	public static JCustomTextField nomProjetJTF;
	public static JCustomTextField clientProjetJTF;

	public static JCustomTabbedPane tabPanel;

	public static JLabel newProjectErreurLabel;

	private static DataBase db = DataBase.db;

	private static MainFrame frame;

	public static JPanel getPanel(MainFrame frame, int persID) {
		ProjectPanel.frame = frame;
		ProjectPanel.persID = persID;

		ResultArray userInfos = db.getUserInfos(persID);
		nom = (String) userInfos.get(0, "nom");
		prenom = (String) userInfos.get(0, "prenom");
		poste = (String) userInfos.get(0, "qualification");

		JPanel innerPanel = new JPanel(new GridBagLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		innerPanel.setBackground(new Color(251, 251, 251));

		GridBagConstraints gbc = new GridBagConstraints();

		tabPanel = new JCustomTabbedPane();

		String nomStyle = "style='font-weight:bold; font-size:11px; font-family:Optima; color:#444444'";
		String prenomStyle = "style='font-weight:normal; font-size:11px; font-family:Optima; color:#444444'";
		String posteStyle = "style='font-weight:normal; font-size:11px; font-style:italic; font-family:Optima; color:#444444'";

		JLabel nomLabel = new JLabel(String.format("<html><div %s> %s </span><span %s> %s </span></div></html>",
				nomStyle, nom.toUpperCase(), prenomStyle, prenom));

		JLabel posteLabel = new JLabel(String.format("<html><span %s> %s </span></html>", posteStyle, poste));

		JRoundedButton changeUserBtn = new JRoundedButton();

		String changeUserStyleD = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String changeUserStyleH = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima; color:#f8f8ff'";

		String changeUserSD = String.format("<html><span %s> Changer de profil </span></html>", changeUserStyleD);
		String changeUserSH = String.format("<html><span %s> Changer de profil </span></html>", changeUserStyleH);

		changeUserBtn.setMultipleText(changeUserSD, changeUserSH, null);
		changeUserBtn.setName("projectChangeUser");
		changeUserBtn.addActionListener(frame);
		changeUserBtn.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));

		JRoundedButton refreshBtn = new JRoundedButton();
		refreshBtn.setToolTipText("Rafraîchir");

		ImageIcon refreshIconD = new ImageIcon(
				new ImageIcon("img/icone_refresh.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon refreshIconH = new ImageIcon(
				new ImageIcon("img/icone_refresh_hover.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));

		refreshBtn.setMultipleIcon(refreshIconD, refreshIconH, null);
		refreshBtn.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		refreshBtn.setName("projectPanel/refresh");
		refreshBtn.addActionListener(frame);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE;
		innerPanel.add(nomLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE;
		innerPanel.add(posteLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.BASELINE;
		innerPanel.add(changeUserBtn, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 3;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		innerPanel.add(refreshBtn, gbc);

		JPanel p1 = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new GridBagLayout());
		JPanel p3 = new JPanel(new GridBagLayout());

		p1.setBackground(new Color(251, 251, 251));
		p2.setBackground(new Color(251, 251, 251));
		p3.setBackground(new Color(251, 251, 251));

		JPanel p1scroll = new JPanel(new BorderLayout());
		JPanel p2scroll = new JPanel(new BorderLayout());
		JPanel p3scroll = new JPanel(new BorderLayout());

		// MES PROJETS

		GridBagConstraints gbc1 = new GridBagConstraints();

		int gridy = 0;

		ResultArray myOngoingProjetcs = db.getMyOngoingProjects(persID);
		ResultArray myWaitingProjetcs = db.getMyWaitingProjects(persID);
		ResultArray myEndedProjetcs = db.getMyEndedProjects(persID);

		// MES PROJETS EN COURS --------

		String ongoingProjectsStyle = "style='font-weight:normal; font-size:13px; font-family:Optima; color:#444444'";
		String ongoingProjectsS = String.format("<html><span %s>MES PROJETS EN COURS (%d)</span></html>",
				ongoingProjectsStyle, myOngoingProjetcs.size);
		JLabel ongoingProjectsLabel = new JLabel(ongoingProjectsS);

		gbc1.gridx = 0;
		gbc1.gridy = gridy;
		gbc1.gridheight = 1;
		gbc1.gridwidth = 1;
		gbc1.weightx = 1;
		gbc1.anchor = GridBagConstraints.NORTH;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.insets = new Insets(5, 15, 5, 0);
		p1.add(ongoingProjectsLabel, gbc1);

		gridy++;

		for (int i = 0; i < myOngoingProjetcs.size; i++) {

			JPanel tmpPanel = ProjectPanel.getAnOngoingProjectPanel(myOngoingProjetcs.getRow(i));

			tmpPanel.setBackground(new Color(242, 242, 242));
			tmpPanel.setOpaque(true);

			gbc1.gridx = 0;
			gbc1.gridy = gridy;
			gbc1.gridheight = 1;
			gbc1.gridwidth = 1;
			gbc1.weightx = 1;
			gbc1.anchor = GridBagConstraints.NORTH;
			gbc1.fill = GridBagConstraints.HORIZONTAL;
			gbc1.insets = new Insets(5, 0, 5, 0);
			p1.add(tmpPanel, gbc1);

			gridy++;
		}

		// MES PROJETS EN ATTENTE ------------

		String waitingProjectsStyle = "style='font-weight:normal; font-size:13px; font-family:Optima; color:#444444'";
		String waitingProjectsS = String.format("<html><span %s>MES PROJETS EN ATTENTE (%d)</span></html>",
				waitingProjectsStyle, myWaitingProjetcs.size);
		JLabel waitingProjectsLabel = new JLabel(waitingProjectsS);

		gbc1.gridx = 0;
		gbc1.gridy = gridy;
		gbc1.gridheight = 1;
		gbc1.gridwidth = 1;
		gbc1.weightx = 1;
		gbc1.anchor = GridBagConstraints.NORTH;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.insets = new Insets(5, 15, 5, 0);
		p1.add(waitingProjectsLabel, gbc1);

		gridy++;

		for (int i = 0; i < myWaitingProjetcs.size; i++) {

			JPanel tmpPanel = ProjectPanel.getAWaitingProjectPanel(myWaitingProjetcs.getRow(i));

			tmpPanel.setBackground(new Color(240, 236, 212));
			tmpPanel.setOpaque(true);

			gbc1.gridx = 0;
			gbc1.gridy = gridy;
			gbc1.gridheight = 1;
			gbc1.gridwidth = 1;
			gbc1.weightx = 1;
			gbc1.anchor = GridBagConstraints.NORTH;
			gbc1.fill = GridBagConstraints.HORIZONTAL;
			gbc1.insets = new Insets(5, 0, 5, 0);
			p1.add(tmpPanel, gbc1);

			gridy++;
		}

		// MES PROJETS ARCHIVES ------------

		String endedProjectsStyle = "style='font-weight:normal; font-size:13px; font-family:Optima; color:#444444'";
		String endedProjectsS = String.format("<html><span %s>MES PROJETS ARCHIVÉS (%d)</span></html>",
				endedProjectsStyle, myEndedProjetcs.size);
		JLabel endedProjectsLabel = new JLabel(endedProjectsS);

		gbc1.gridx = 0;
		gbc1.gridy = gridy;
		gbc1.gridheight = 1;
		gbc1.gridwidth = 1;
		gbc1.weightx = 1;
		gbc1.anchor = GridBagConstraints.NORTH;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.insets = new Insets(5, 15, 5, 0);
		p1.add(endedProjectsLabel, gbc1);

		gridy++;

		for (int i = 0; i < myEndedProjetcs.size; i++) {

			JPanel tmpPanel = ProjectPanel.getAnEndedProjectPanel(myEndedProjetcs.getRow(i));

			tmpPanel.setBackground(new Color(234, 208, 204));
			tmpPanel.setOpaque(true);

			gbc1.gridx = 0;
			gbc1.gridy = gridy;
			gbc1.gridheight = 1;
			gbc1.gridwidth = 1;
			gbc1.weightx = 1;
			gbc1.anchor = GridBagConstraints.NORTH;
			gbc1.fill = GridBagConstraints.HORIZONTAL;
			gbc1.insets = new Insets(5, 0, 5, 0);
			p1.add(tmpPanel, gbc1);

			gridy++;
		}

		gbc1.gridx = 0;
		gbc1.gridy = gridy;
		gbc1.weighty = 1;
		p1.add(new JLabel(""), gbc1);

		JScrollPane p1jsp = (new JScrollPane(p1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		p1jsp.getVerticalScrollBar().setUnitIncrement(4);
		p1scroll.add(p1jsp);

		// AUTRES PROJETS

		GridBagConstraints gbc2 = new GridBagConstraints();

		ResultArray otherOngoingProjects = db.getOtherOngoingProjects(persID);
		ResultArray otherEndedProjects = db.getOtherEndedProjects(persID);

		gridy = 0;

		// AUTRESPROJETS EN COURS --------

		String otherOngoingProjectsStyle = "style='font-weight:normal; font-size:13px; font-family:Optima; color:#444444'";
		String otherOngoingProjectsS = String.format("<html><span %s>AUTRES PROJETS EN COURS (%d)</span></html>",
				otherOngoingProjectsStyle, otherOngoingProjects.size);
		JLabel otherOngoingProjectsLabel = new JLabel(otherOngoingProjectsS);

		gbc2.gridx = 0;
		gbc2.gridy = gridy;
		gbc2.gridheight = 1;
		gbc2.gridwidth = 1;
		gbc2.weightx = 1;
		gbc2.anchor = GridBagConstraints.NORTH;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.insets = new Insets(5, 15, 5, 0);
		p2.add(otherOngoingProjectsLabel, gbc2);

		gridy++;

		for (int i = 0; i < otherOngoingProjects.size; i++) {

			JPanel tmpPanel = ProjectPanel.getOtherOngoingPanel(otherOngoingProjects.getRow(i));

			tmpPanel.setBackground(new Color(242, 242, 242));
			tmpPanel.setOpaque(true);

			gbc2.gridx = 0;
			gbc2.gridy = gridy;
			gbc2.gridheight = 1;
			gbc2.gridwidth = 1;
			gbc2.weightx = 1;
			gbc2.anchor = GridBagConstraints.CENTER;
			gbc2.fill = GridBagConstraints.HORIZONTAL;
			gbc2.insets = new Insets(5, 0, 5, 0);
			p2.add(tmpPanel, gbc2);

			gridy++;
		}

		// AUTRES PROJETS ARCHIVES --------

		String otherEndedProjectsStyle = "style='font-weight:normal; font-size:13px; font-family:Optima; color:#444444'";
		String otherEndedProjectsS = String.format("<html><span %s>AUTRES PROJETS ARCHIVÉS (%d)</span></html>",
				otherEndedProjectsStyle, otherEndedProjects.size);
		JLabel otherEndedProjectsLabel = new JLabel(otherEndedProjectsS);

		gbc2.gridx = 0;
		gbc2.gridy = gridy;
		gbc2.gridheight = 1;
		gbc2.gridwidth = 1;
		gbc2.weightx = 1;
		gbc2.anchor = GridBagConstraints.NORTH;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.insets = new Insets(5, 15, 5, 0);
		p2.add(otherEndedProjectsLabel, gbc2);

		gridy++;

		for (int i = 0; i < otherEndedProjects.size; i++) {

			JPanel tmpPanel = ProjectPanel.getOtherEndedPanel(otherEndedProjects.getRow(i));

			tmpPanel.setBackground(new Color(234, 208, 204));
			tmpPanel.setOpaque(true);

			gbc2.gridx = 0;
			gbc2.gridy = gridy;
			gbc2.gridheight = 1;
			gbc2.gridwidth = 1;
			gbc2.weightx = 1;
			gbc2.anchor = GridBagConstraints.CENTER;
			gbc2.fill = GridBagConstraints.HORIZONTAL;
			gbc2.insets = new Insets(5, 0, 5, 0);
			p2.add(tmpPanel, gbc2);

			gridy++;
		}

		gbc2.gridx = 0;
		gbc2.gridy += 1;
		gbc2.weighty = 1;
		p2.add(new JLabel(""), gbc2);

		JScrollPane p2jsp = (new JScrollPane(p2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		p2jsp.getVerticalScrollBar().setUnitIncrement(4);
		p2scroll.add(p2jsp);

		// CRÉER UN PROJET

		p3.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

		String titleNvProjetStyle = "style='font-weight:normal; font-size:14.5px; font-family:Optima; color:#444444'";
		String labelProjetStyle = "style='font-weight:normal; font-size:11.5px; font-family:Optima; color:#444444'";

		String titleNvProjetS = String.format("<html><span %s>CRÉER UN NOUVEAU PROJET</span></html>",
				titleNvProjetStyle);
		JLabel titleNvProjetLabel = new JLabel(titleNvProjetS);

		String nomProjetS = String.format("<html><span %s>Nom du projet</span></html>", labelProjetStyle);
		JLabel nomProjetLabel = new JLabel(nomProjetS);

		String clientProjetS = String.format("<html><span %s>Client du projet</span></html>", labelProjetStyle);
		JLabel clientProjetLabel = new JLabel(clientProjetS);

		nomProjetJTF = new JCustomTextField();
		nomProjetJTF.setPreferredSize(new Dimension(175, 24));

		nomProjetJTF.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				newProjectErreurLabel.setVisible(false);
			}
		});

		clientProjetJTF = new JCustomTextField();
		clientProjetJTF.setPreferredSize(new Dimension(175, 24));

		clientProjetJTF.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				newProjectErreurLabel.setVisible(false);
			}
		});

		newProjectErreurLabel = new JLabel("erreur", JLabel.CENTER);
		newProjectErreurLabel.setVisible(false);

		JRoundedButton creerProjetBtn = new JRoundedButton();

		String creerProjetSD = "<html><span style='font-size:11px; font-weight:normal; font-style:italic; font-family:Optima'>&nbsp;Créer le projet</span></html>";
		String creerProjetSH = "<html><span style='font-size:11px; font-weight:normal; font-style:italic; font-family:Optima; color:#f8f8ff'>&nbsp;Créer le projet</span></html>";
		creerProjetBtn.setMultipleText(creerProjetSD, creerProjetSH, null);

		ImageIcon creerProjetIconD = new ImageIcon(
				new ImageIcon("img/icone_new_project.png").getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH));
		ImageIcon creerProjetIconH = new ImageIcon(new ImageIcon("img/icone_new_project_hover.png").getImage()
				.getScaledInstance(18, 18, Image.SCALE_SMOOTH));
		creerProjetBtn.setMultipleIcon(creerProjetIconD, creerProjetIconH, null);

		creerProjetBtn.setName("projectPanel/creerProjet/creerProjet");
		creerProjetBtn.addActionListener(frame);

		GridBagConstraints gbc3 = new GridBagConstraints();

		gbc3.gridx = 0;
		gbc3.gridy = 0;
		gbc3.gridheight = 1;
		gbc3.gridwidth = 2;
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbc3.anchor = GridBagConstraints.CENTER;
		gbc3.insets = new Insets(10, 0, 10, 0);
		p3.add(titleNvProjetLabel, gbc3);

		gbc3.gridx = 0;
		gbc3.gridy = 1;
		gbc3.gridheight = 1;
		gbc3.gridwidth = 1;
		gbc3.weightx = 0;
		gbc3.anchor = GridBagConstraints.BASELINE;
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbc3.insets = new Insets(5, 0, 5, 0);
		p3.add(nomProjetLabel, gbc3);

		gbc3.gridx = 1;
		gbc3.gridy = 1;
		gbc3.gridheight = 1;
		gbc3.gridwidth = 1;
		gbc3.weightx = 0;
		gbc3.anchor = GridBagConstraints.BASELINE;
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbc3.insets = new Insets(5, 15, 5, 0);
		p3.add(nomProjetJTF, gbc3);

		gbc3.gridx = 0;
		gbc3.gridy = 2;
		gbc3.gridheight = 1;
		gbc3.gridwidth = 1;
		gbc3.anchor = GridBagConstraints.BASELINE;
		gbc3.insets = new Insets(5, 0, 5, 0);
		p3.add(clientProjetLabel, gbc3);

		gbc3.gridx = 1;
		gbc3.gridy = 2;
		gbc3.gridheight = 1;
		gbc3.gridwidth = 1;
		gbc3.anchor = GridBagConstraints.BASELINE;
		gbc3.insets = new Insets(5, 15, 5, 0);
		p3.add(clientProjetJTF, gbc3);

		gbc3.gridx = 0;
		gbc3.gridy = 3;
		gbc3.gridheight = 1;
		gbc3.gridwidth = 2;
		gbc3.anchor = GridBagConstraints.CENTER;
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		gbc3.weightx = 0;
		gbc3.insets = new Insets(0, 0, 0, 0);
		p3.add(newProjectErreurLabel, gbc3);

		gbc3.gridx = 1;
		gbc3.gridy = 4;
		gbc3.gridheight = 1;
		gbc3.gridwidth = 1;
		gbc3.anchor = GridBagConstraints.LINE_END;
		gbc3.fill = GridBagConstraints.NONE;
		gbc3.insets = new Insets(5, 15, 5, 0);
		p3.add(creerProjetBtn, gbc3);

		JScrollPane p3jsp = (new JScrollPane(p3, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		p3scroll.add(p3jsp);

		tabPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		tabPanel.add(
				"<html><span style='font-size:11px; font-weight:bold; font-family:Optima'>Mes projets</span></html>",
				p1scroll);
		tabPanel.add(
				"<html><span style='font-size:11px; font-weight:bold; font-family:Optima'>Autres projets</span></html>",
				p2scroll);
		tabPanel.add(
				"<html><span style='font-size:11px; font-weight:normal; font-family:Optima; font-style:italic'>Créer un projet</span></html>",
				p3scroll);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		innerPanel.add(tabPanel, gbc);
		;

		p1scroll.setPreferredSize(new Dimension(1, 1));
		p2scroll.setPreferredSize(new Dimension(1, 1));
		p3scroll.setPreferredSize(new Dimension(1, 1));

		return JPanelOperations.setBorders(innerPanel, 5.0);
	}

	// MY ONGOING PANEL ------------------------

	private static JPanel getAnOngoingProjectPanel(HashMap<String, Object> ongoingProject) {
		JPanel tmpPanel = new JPanel(new GridBagLayout());
		GridBagConstraints tmpGbc = new GridBagConstraints();

		String nomProjet = (String) ongoingProject.get("nom_projet");
		String clientProjet = (String) ongoingProject.get("client_projet");
		int projectID = (int) ongoingProject.get("id");

		Number dateDebutTimestamp = (Number) ongoingProject.get("date_debut");
		Date dd = new Date(dateDebutTimestamp.longValue() * 1000);
		DateFormat fd = new SimpleDateFormat("dd/MM/yyyy");
		String dateDebut = fd.format(dd);

		ResultArray myLastActivity = db.getLastWork(projectID, persID); // Votre dernière activité

		Number myLastActivityTimestamp = (Number) myLastActivity.get(0, "date");
		Date dm = new Date(myLastActivityTimestamp.longValue() * 1000);
		DateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		String myLastActivityDate = fm.format(dm);

		String myLastActivityMission = (String) myLastActivity.get(0, "mission");

		long myLastActivityWorkTimeSec = ((Number) myLastActivity.get(0, "temps_travail")).longValue();
		String myLastActivityWorkTime = StringOperations.formatTime(myLastActivityWorkTimeSec, "jh");

		ResultArray lastActivity = db.getLastWork(projectID); // Dernière activité

		Number lastActivityTimestamp = (Number) lastActivity.get(0, "date");
		Date dl = new Date(lastActivityTimestamp.longValue() * 1000);
		DateFormat fl = new SimpleDateFormat("dd/MM/yyyy");
		String lastActivityDate = fl.format(dl);

		String lastActivityMission = (String) lastActivity.get(0, "mission");

		long lastActivityWorkTimeSec = ((Number) lastActivity.get(0, "temps_travail")).longValue();
		String lastActivityWorkTime = StringOperations.formatTime(lastActivityWorkTimeSec, "jh");

		int lastActivityPersId = (int) lastActivity.get(0, "id_personne");
		ResultArray lastActivityPersInfos = db.getUserInfos(lastActivityPersId);
		String lastActivityNom = (String) lastActivityPersInfos.get(0, "nom");
		String lastActivityPrenom = (String) lastActivityPersInfos.get(0, "prenom");

		long myWorkTimeSec = db.getTimeWork(projectID, persID);
		long totalWorkTimeSec = db.getTimeWork(projectID);
		String myWorkTime = StringOperations.formatTime(myWorkTimeSec, "jh");
		String totalWorkTime = StringOperations.formatTime(totalWorkTimeSec, "jh");

		String nomProjetStyle = "style='font-weight:normal; font-size:12px'";
		String nomProjetS = String.format("<span %s> %s  • </span>", nomProjetStyle, nomProjet.toUpperCase());

		String clientProjetStyle = "style='font-weight:bold; font-size:10px; font-style:italic'";
		String clientProjetS = String.format("<span %s> %s </span>", clientProjetStyle, clientProjet);

		String titleProjectS = String.format("<html>%s %s</html>", nomProjetS, clientProjetS);

		String dateProjectStyle = "style='font-weight:normal; font-size:8.5px; font-style:italic'";
		String dateProjectS = String.format("<html><span %s>Débuté le %s</span></html>", dateProjectStyle, dateDebut);

		String subtitleStyle = "style='font-weight:normal; font-size:10px; font-style:italic'";
		String dateStyle = "style='font-weight:normal; font-size:9px'";
		String personneStyle = "style='font-weight:normal; font-size:9px'";
		String missionStyle = "style='font-weight:bold; font-size:8.8px'";
		String sessionWorkTimeStyle = "style='font-weight:normal; font-size:9px; font-style:italic'";

		String myLastActivityS;
		if (myLastActivityMission.equals("_NEW_")) {
			myLastActivityS = String.format(
					"<html><span %s>Votre dernière activité</span> <br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>A rejoint le projet</span></html>",
					subtitleStyle, dateStyle, myLastActivityDate, sessionWorkTimeStyle);
		} else if (myLastActivityMission.equals("_END_")) {
			myLastActivityS = String.format(
					"<html><span %s>Votre dernière activité</span> <br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>A terminé le projet</span></html>",
					subtitleStyle, dateStyle, myLastActivityDate, sessionWorkTimeStyle);
		} else {
			myLastActivityS = String.format(
					"<html><span %s>Votre dernière activité</span> <br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s> %s </span><span %s> - %s</span></html>",
					subtitleStyle, dateStyle, myLastActivityDate, missionStyle, myLastActivityMission,
					sessionWorkTimeStyle, myLastActivityWorkTime);
		}

		String lastActivityS;
		if (lastActivityMission.equals("_NEW_")) {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s>A rejoint le projet</span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, sessionWorkTimeStyle);
		} else if (lastActivityMission.equals("_END_")) {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s>A terminé le projet</span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, sessionWorkTimeStyle);
		} else if (lastActivityMission.equals("_QUIT_")) {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s>A quitté le projet</span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, sessionWorkTimeStyle);
		} else {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s> %s </span><span %s> - %s </span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, missionStyle, lastActivityMission, sessionWorkTimeStyle, lastActivityWorkTime);
		}

		String myWorkTimeS = String.format("<html><span %s>Votre temps de travail</span><br>&nbsp; %s </html>",
				subtitleStyle, myWorkTime);
		String workTimeS = String.format("<html><span %s>Temps de travail de l'équipe</span><br> &nbsp; %s </html>",
				subtitleStyle, totalWorkTime);

		ImageIcon myWorkTimeIcon = new ImageIcon(
				new ImageIcon("img/icone_my_worktime.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));
		ImageIcon workTimeIcon = new ImageIcon(
				new ImageIcon("img/icone_total_worktime.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));

		JLabel titleProjectLabel = new JLabel(titleProjectS);
		JLabel dateProjetLabel = new JLabel(dateProjectS);
		JLabel myLastActivityLabel = new JLabel(myLastActivityS);
		JLabel lastActivityLabel = new JLabel(lastActivityS);
		JLabel myWorkTimeLabel = new JLabel(myWorkTimeS, myWorkTimeIcon, JLabel.LEFT);
		JLabel workTimeLabel = new JLabel(workTimeS, workTimeIcon, JLabel.LEFT);

		ImageIcon addSessionIconD = new ImageIcon(
				new ImageIcon("img/icone_add_activity.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon addSessionIconH = new ImageIcon(new ImageIcon("img/icone_add_activity_hover.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		JRoundedButton addSessionBtn = new JRoundedButton();
		addSessionBtn.setToolTipText("Ajouter une session de travail");
		addSessionBtn.setPreferredSize(new Dimension(34, 34));
		addSessionBtn.setMultipleIcon(addSessionIconD, addSessionIconH, null);
		addSessionBtn
				.setName(String.format("projectPanel/myProjects/addSession?projectID=%s&persID=%s", projectID, persID));
		addSessionBtn.addActionListener(frame);

		ImageIcon historicIconD = new ImageIcon(new ImageIcon("img/icone_historique_sessions.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon historicIconH = new ImageIcon(new ImageIcon("img/icone_historique_sessions_hover.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		JRoundedButton historicBtn = new JRoundedButton();
		historicBtn.setToolTipText("Historique de mes sessions de travail");
		historicBtn.setPreferredSize(new Dimension(34, 34));
		historicBtn.setMultipleIcon(historicIconD, historicIconH, null);
		historicBtn
				.setName(String.format("projectPanel/myProjects/historic?projectID=%s&persID=%s", projectID, persID));
		historicBtn.addActionListener(frame);

		ImageIcon projectOverviewIconD = new ImageIcon(new ImageIcon("img/icone_project_overview.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon projectOverviewIconH = new ImageIcon(new ImageIcon("img/icone_project_overview_hover.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		JRoundedButton projectOverviewBtn = new JRoundedButton();
		projectOverviewBtn.setToolTipText("Détails du projet");
		projectOverviewBtn.setPreferredSize(new Dimension(34, 34));
		projectOverviewBtn.setMultipleIcon(projectOverviewIconD, projectOverviewIconH, null);
		projectOverviewBtn
				.setName(String.format("projectPanel/myProjects/stats?projectID=%s&persID=%s", projectID, persID));
		projectOverviewBtn.addActionListener(frame);

		String leaveProjectStyleD = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String leaveProjectStyleH = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima; color:#f8f8ff'";
		String leaveProjectSD = String.format("<html><span %s>Quitter le projet</span></html>", leaveProjectStyleD);
		String leaveProjectSH = String.format("<html><span %s>Quitter le projet</span></html>", leaveProjectStyleH);
		JRoundedButton leaveProjectBtn = new JRoundedButton();
		leaveProjectBtn.setToolTipText(
				"<html>Vous ne pouvez pas quitter un projet dans lequel vous avez du temps de travail.</html>");
		leaveProjectBtn.setMultipleText(leaveProjectSD, leaveProjectSH, null);
		leaveProjectBtn.setMultipleInnerColor(leaveProjectBtn.getDefaultInnerColor(), new Color(255, 89, 89),
				new Color(222, 38, 38));
		leaveProjectBtn.setMultipleBorderColor(leaveProjectBtn.getDefaultBorderColor(), new Color(222, 38, 38),
				new Color(255, 89, 89));
		leaveProjectBtn.setName(
				String.format("projectPanel/myProjects/leaveProject?projectID=%s&persID=%s", projectID, persID));
		leaveProjectBtn.addActionListener(frame);

		int nbWaitingParticipants = db.getNbWaitingParticipants(projectID);
		int nbParticipants = db.getParticipants(projectID).size;

		String finishProjectStyleD = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String finishProjectStyleH = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String finishProjectSD = String.format("<html><span %s>Projet terminé (%d/%d)</span></html>",
				finishProjectStyleD, nbWaitingParticipants, nbParticipants);
		String finishProjectSH = String.format("<html><span %s>Projet terminé (%d/%d)</span></html>",
				finishProjectStyleH, nbWaitingParticipants, nbParticipants);
		JRoundedButton finishProjectBtn = new JRoundedButton();
		finishProjectBtn.setToolTipText(
				"<html>Ce projet n'apparaîtra plus dans vos projets en cours.<br>Tous les membres du projet doivent indiquer qu'ils ont terminé pour que celui-ci soit archivé.</html>");
		finishProjectBtn.setMultipleText(finishProjectSD, finishProjectSH, null);
		finishProjectBtn.setMultipleInnerColor(finishProjectBtn.getDefaultInnerColor(), new Color(255, 229, 39),
				new Color(222, 199, 34));
		finishProjectBtn.setMultipleBorderColor(finishProjectBtn.getDefaultBorderColor(), new Color(222, 199, 34),
				new Color(255, 229, 39));
		finishProjectBtn.setName(
				String.format("projectPanel/myProjects/finishProject?projectID=%s&persID=%s", projectID, persID));
		finishProjectBtn.addActionListener(frame);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 0;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(7, 10, 0, 10);
		tmpPanel.add(titleProjectLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 10, 10, 10);
		tmpPanel.add(dateProjetLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(myWorkTimeLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 3;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(workTimeLabel, tmpGbc);

		tmpGbc.gridx = 1;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 15, 5, 15);
		tmpPanel.add(myLastActivityLabel, tmpGbc);

		tmpGbc.gridx = 2;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 15, 5, 15);
		tmpPanel.add(lastActivityLabel, tmpGbc);

		tmpGbc.gridx = 3;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 20, 0, 10);
		tmpPanel.add(addSessionBtn, tmpGbc);

		tmpGbc.gridx = 4;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 0, 0, 10);
		tmpPanel.add(historicBtn, tmpGbc);

		tmpGbc.gridx = 5;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 0, 0, 10);
		tmpPanel.add(projectOverviewBtn, tmpGbc);

		tmpGbc.gridx = 3;
		tmpGbc.gridy = 3;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 3;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.HORIZONTAL;
		tmpGbc.insets = new Insets(0, 20, 0, 10);
		tmpPanel.add(finishProjectBtn, tmpGbc);

		if (myWorkTimeSec == 0) {
			tmpGbc.gridx = 3;
			tmpGbc.gridy = 4;
			tmpGbc.gridheight = 1;
			tmpGbc.gridwidth = 3;
			tmpGbc.anchor = GridBagConstraints.CENTER;
			tmpGbc.fill = GridBagConstraints.HORIZONTAL;
			tmpGbc.insets = new Insets(0, 20, 5, 10);
			tmpPanel.add(leaveProjectBtn, tmpGbc);
		}
		return tmpPanel;
	}

	// MY WAITING PANEL ------------------------

	private static JPanel getAWaitingProjectPanel(HashMap<String, Object> ongoingProject) {
		JPanel tmpPanel = new JPanel(new GridBagLayout());
		GridBagConstraints tmpGbc = new GridBagConstraints();

		String nomProjet = (String) ongoingProject.get("nom_projet");
		String clientProjet = (String) ongoingProject.get("client_projet");
		int projectID = (int) ongoingProject.get("id");

		Number dateDebutTimestamp = (Number) ongoingProject.get("date_debut");
		Date dd = new Date(dateDebutTimestamp.longValue() * 1000);
		DateFormat fd = new SimpleDateFormat("dd/MM/yyyy");
		String dateDebut = fd.format(dd);

		ResultArray myLastActivity = db.getLastWork(projectID, persID); // Votre dernière activité

		Number myLastActivityTimestamp = (Number) myLastActivity.get(0, "date");
		Date dm = new Date(myLastActivityTimestamp.longValue() * 1000);
		DateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		String myLastActivityDate = fm.format(dm);

		String myLastActivityMission = (String) myLastActivity.get(0, "mission");

		long myLastActivityWorkTimeSec = ((Number) myLastActivity.get(0, "temps_travail")).longValue();
		String myLastActivityWorkTime = StringOperations.formatTime(myLastActivityWorkTimeSec, "jh");

		ResultArray lastActivity = db.getLastWork(projectID); // Dernière activité

		Number lastActivityTimestamp = (Number) lastActivity.get(0, "date");
		Date dl = new Date(lastActivityTimestamp.longValue() * 1000);
		DateFormat fl = new SimpleDateFormat("dd/MM/yyyy");
		String lastActivityDate = fl.format(dl);

		String lastActivityMission = (String) lastActivity.get(0, "mission");

		long lastActivityWorkTimeSec = ((Number) lastActivity.get(0, "temps_travail")).longValue();
		String lastActivityWorkTime = StringOperations.formatTime(lastActivityWorkTimeSec, "jh");

		int lastActivityPersId = (int) lastActivity.get(0, "id_personne");
		ResultArray lastActivityPersInfos = db.getUserInfos(lastActivityPersId);
		String lastActivityNom = (String) lastActivityPersInfos.get(0, "nom");
		String lastActivityPrenom = (String) lastActivityPersInfos.get(0, "prenom");

		long myWorkTimeSec = db.getTimeWork(projectID, persID);
		long totalWorkTimeSec = db.getTimeWork(projectID);
		String myWorkTime = StringOperations.formatTime(myWorkTimeSec, "jh");
		String totalWorkTime = StringOperations.formatTime(totalWorkTimeSec, "jh");

		String nomProjetStyle = "style='font-weight:normal; font-size:12px'";
		String nomProjetS = String.format("<span %s> %s  • </span>", nomProjetStyle, nomProjet.toUpperCase());

		String clientProjetStyle = "style='font-weight:bold; font-size:10px; font-style:italic'";
		String clientProjetS = String.format("<span %s> %s </span>", clientProjetStyle, clientProjet);

		String titleProjectS = String.format("<html>%s %s</html>", nomProjetS, clientProjetS);

		String dateProjectStyle = "style='font-weight:normal; font-size:8.5px; font-style:italic'";
		String dateProjectS = String.format("<html><span %s>Débuté le %s</span></html>", dateProjectStyle, dateDebut);

		String subtitleStyle = "style='font-weight:normal; font-size:10px; font-style:italic'";
		String dateStyle = "style='font-weight:normal; font-size:9px'";
		String personneStyle = "style='font-weight:normal; font-size:9px'";
		String missionStyle = "style='font-weight:bold; font-size:8.8px'";
		String sessionWorkTimeStyle = "style='font-weight:normal; font-size:9px; font-style:italic'";

		String myLastActivityS;
		if (myLastActivityMission.equals("_NEW_")) {
			myLastActivityS = String.format(
					"<html><span %s>Votre dernière activité</span> <br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>A rejoint le projet</span></html>",
					subtitleStyle, dateStyle, myLastActivityDate, sessionWorkTimeStyle);
		} else if (myLastActivityMission.equals("_END_")) {
			myLastActivityS = String.format(
					"<html><span %s>Votre dernière activité</span> <br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>A terminé le projet</span></html>",
					subtitleStyle, dateStyle, myLastActivityDate, sessionWorkTimeStyle);
		} else {
			myLastActivityS = String.format(
					"<html><span %s>Votre dernière activité</span> <br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s> %s </span><span %s> - %s</span></html>",
					subtitleStyle, dateStyle, myLastActivityDate, missionStyle, myLastActivityMission,
					sessionWorkTimeStyle, myLastActivityWorkTime);
		}

		String lastActivityS;
		if (lastActivityMission.equals("_NEW_")) {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s>A rejoint le projet</span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, sessionWorkTimeStyle);
		} else if (lastActivityMission.equals("_END_")) {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s>A terminé le projet</span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, sessionWorkTimeStyle);
		} else if (lastActivityMission.equals("_QUIT_")) {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s>A quitté le projet</span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, sessionWorkTimeStyle);
		} else {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s> %s </span><span %s> - %s </span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, missionStyle, lastActivityMission, sessionWorkTimeStyle, lastActivityWorkTime);
		}

		String myWorkTimeS = String.format("<html><span %s>Votre temps de travail</span><br>&nbsp; %s </html>",
				subtitleStyle, myWorkTime);
		String workTimeS = String.format("<html><span %s>Temps de travail de l'équipe</span><br> &nbsp; %s </html>",
				subtitleStyle, totalWorkTime);

		ImageIcon myWorkTimeIcon = new ImageIcon(
				new ImageIcon("img/icone_my_worktime.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));
		ImageIcon workTimeIcon = new ImageIcon(
				new ImageIcon("img/icone_total_worktime.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));

		JLabel titleProjectLabel = new JLabel(titleProjectS);
		JLabel dateProjetLabel = new JLabel(dateProjectS);
		JLabel myLastActivityLabel = new JLabel(myLastActivityS);
		JLabel lastActivityLabel = new JLabel(lastActivityS);
		JLabel myWorkTimeLabel = new JLabel(myWorkTimeS, myWorkTimeIcon, JLabel.LEFT);
		JLabel workTimeLabel = new JLabel(workTimeS, workTimeIcon, JLabel.LEFT);

		ImageIcon historicIconD = new ImageIcon(new ImageIcon("img/icone_historique_sessions.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon historicIconH = new ImageIcon(new ImageIcon("img/icone_historique_sessions_hover.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		JRoundedButton historicBtn = new JRoundedButton();
		historicBtn.setToolTipText("Historique de mes sessions de travail");
		historicBtn.setPreferredSize(new Dimension(34, 34));
		historicBtn.setMultipleIcon(historicIconD, historicIconH, null);
		historicBtn
				.setName(String.format("projectPanel/myProjects/historic?projectID=%s&persID=%s", projectID, persID));
		historicBtn.addActionListener(frame);

		ImageIcon projectOverviewIconD = new ImageIcon(new ImageIcon("img/icone_project_overview.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon projectOverviewIconH = new ImageIcon(new ImageIcon("img/icone_project_overview_hover.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		JRoundedButton projectOverviewBtn = new JRoundedButton();
		projectOverviewBtn.setToolTipText("Détails du projet");
		projectOverviewBtn.setPreferredSize(new Dimension(34, 34));
		projectOverviewBtn.setMultipleIcon(projectOverviewIconD, projectOverviewIconH, null);
		projectOverviewBtn
				.setName(String.format("projectPanel/myProjects/stats?projectID=%s&persID=%s", projectID, persID));
		projectOverviewBtn.addActionListener(frame);

		String leaveProjectStyleD = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String leaveProjectStyleH = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima; color:#f8f8ff'";
		String leaveProjectSD = String.format("<html><span %s>Quitter le projet</span></html>", leaveProjectStyleD);
		String leaveProjectSH = String.format("<html><span %s>Quitter le projet</span></html>", leaveProjectStyleH);
		JRoundedButton leaveProjectBtn = new JRoundedButton();
		leaveProjectBtn.setToolTipText(
				"<html>Vous devrez attendre une semaine pour rejoindre de nouveau ce projet.<br>Vous ne pouvez pas quitter un projet dans lequel vous avez du temps de travail.</html>");
		leaveProjectBtn.setMultipleText(leaveProjectSD, leaveProjectSH, null);
		leaveProjectBtn.setMultipleInnerColor(leaveProjectBtn.getDefaultInnerColor(), new Color(255, 89, 89),
				new Color(222, 38, 38));
		leaveProjectBtn.setMultipleBorderColor(leaveProjectBtn.getDefaultBorderColor(), new Color(222, 38, 38),
				new Color(255, 89, 89));
		leaveProjectBtn.setName(
				String.format("projectPanel/myProjects/leaveProject?projectID=%s&persID=%s", projectID, persID));
		leaveProjectBtn.addActionListener(frame);

		int nbWaitingParticipants = db.getNbWaitingParticipants(projectID);
		int nbParticipants = db.getParticipants(projectID).size;

		String resumeProjectStyleD = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String resumeProjectStyleH = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String fresumeProjectSD = String.format("<html><span %s>Reprendre le projet (%d/%d)</span></html>",
				resumeProjectStyleD, nbWaitingParticipants, nbParticipants);
		String resumeProjectSH = String.format("<html><span %s>Reprendre le projet (%d/%d)</span></html>",
				resumeProjectStyleH, nbWaitingParticipants, nbParticipants);
		JRoundedButton resumeProjectBtn = new JRoundedButton();
		resumeProjectBtn.setToolTipText(
				"<html>Ce projet réapparaîtra dans vos projets en cours.<br>Tous les membres du projet doivent indiquer qu'ils ont terminé pour que celui-ci soit archivé.</html>");
		resumeProjectBtn.setMultipleText(fresumeProjectSD, resumeProjectSH, null);
		resumeProjectBtn.setMultipleInnerColor(resumeProjectBtn.getDefaultInnerColor(), new Color(255, 229, 39),
				new Color(222, 199, 34));
		resumeProjectBtn.setMultipleBorderColor(resumeProjectBtn.getDefaultBorderColor(), new Color(222, 199, 34),
				new Color(255, 229, 39));
		resumeProjectBtn.setName(
				String.format("projectPanel/myProjects/resumeProject?projectID=%s&persID=%s", projectID, persID));
		resumeProjectBtn.addActionListener(frame);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 0;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(7, 10, 0, 10);
		tmpPanel.add(titleProjectLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 10, 10, 10);
		tmpPanel.add(dateProjetLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(myWorkTimeLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 3;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(workTimeLabel, tmpGbc);

		tmpGbc.gridx = 1;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 15, 5, 15);
		tmpPanel.add(myLastActivityLabel, tmpGbc);

		tmpGbc.gridx = 2;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 15, 5, 15);
		tmpPanel.add(lastActivityLabel, tmpGbc);

		tmpGbc.gridx = 4;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 20, 0, 10);
		tmpPanel.add(historicBtn, tmpGbc);

		tmpGbc.gridx = 5;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 0, 0, 10);
		tmpPanel.add(projectOverviewBtn, tmpGbc);

		tmpGbc.gridx = 3;
		tmpGbc.gridy = 3;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 3;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.HORIZONTAL;
		tmpGbc.insets = new Insets(0, 20, 0, 10);
		tmpPanel.add(resumeProjectBtn, tmpGbc);

		return tmpPanel;
	}

	// MY ENDED PANEL -----------------

	private static JPanel getAnEndedProjectPanel(HashMap<String, Object> endedProject) {
		JPanel tmpPanel = new JPanel(new GridBagLayout());
		GridBagConstraints tmpGbc = new GridBagConstraints();

		String nomProjet = (String) endedProject.get("nom_projet");
		String clientProjet = (String) endedProject.get("client_projet");
		int projectID = (int) endedProject.get("id");

		Number dateDebutTimestamp = (Number) endedProject.get("date_debut");
		Date dd = new Date(dateDebutTimestamp.longValue() * 1000);
		DateFormat fd = new SimpleDateFormat("dd/MM/yyyy");
		String dateDebut = fd.format(dd);

		Number dateFinTimestamp = (Number) endedProject.get("date_fin");
		dd = new Date(dateFinTimestamp.longValue() * 1000);
		fd = new SimpleDateFormat("dd/MM/yyyy");
		String dateFin = fd.format(dd);

		ResultArray myLastActivity = db.getLastWork(projectID, persID); // Votre dernière activité

		Number myLastActivityTimestamp = (Number) myLastActivity.get(0, "date");
		Date dm = new Date(myLastActivityTimestamp.longValue() * 1000);
		DateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		String myLastActivityDate = fm.format(dm);

		String myLastActivityMission = (String) myLastActivity.get(0, "mission");

		long myLastActivityWorkTimeSec = ((Number) myLastActivity.get(0, "temps_travail")).longValue();
		String myLastActivityWorkTime = StringOperations.formatTime(myLastActivityWorkTimeSec, "jh");

		ResultArray lastActivity = db.getLastWork(projectID); // Dernière activité

		Number lastActivityTimestamp = (Number) lastActivity.get(0, "date");
		Date dl = new Date(lastActivityTimestamp.longValue() * 1000);
		DateFormat fl = new SimpleDateFormat("dd/MM/yyyy");
		String lastActivityDate = fl.format(dl);

		String lastActivityMission = (String) lastActivity.get(0, "mission");

		long lastActivityWorkTimeSec = ((Number) lastActivity.get(0, "temps_travail")).longValue();
		String lastActivityWorkTime = StringOperations.formatTime(lastActivityWorkTimeSec, "jh");

		int lastActivityPersId = (int) lastActivity.get(0, "id_personne");
		ResultArray lastActivityPersInfos = db.getUserInfos(lastActivityPersId);
		String lastActivityNom = (String) lastActivityPersInfos.get(0, "nom");
		String lastActivityPrenom = (String) lastActivityPersInfos.get(0, "prenom");

		long myWorkTimeSec = db.getTimeWork(projectID, persID);
		long totalWorkTimeSec = db.getTimeWork(projectID);
		String myWorkTime = StringOperations.formatTime(myWorkTimeSec, "jh");
		String totalWorkTime = StringOperations.formatTime(totalWorkTimeSec, "jh");

		String nomProjetStyle = "style='font-weight:normal; font-size:12px'";
		String nomProjetS = String.format("<span %s> %s  • </span>", nomProjetStyle, nomProjet.toUpperCase());

		String clientProjetStyle = "style='font-weight:bold; font-size:10px; font-style:italic'";
		String clientProjetS = String.format("<span %s> %s </span>", clientProjetStyle, clientProjet);

		String titleProjectS = String.format("<html>%s %s</html>", nomProjetS, clientProjetS);

		String dateProjectStyle = "style='font-weight:normal; font-size:8.5px; font-style:italic; text-align:center'";
		String dateProjectS = String.format("<html><div %s>Débuté le %s<br>Terminé le %s</div></html>",
				dateProjectStyle, dateDebut, dateFin);

		String subtitleStyle = "style='font-weight:normal; font-size:10px; font-style:italic'";
		String dateStyle = "style='font-weight:normal; font-size:9px'";
		String personneStyle = "style='font-weight:normal; font-size:9px'";
		String missionStyle = "style='font-weight:bold; font-size:8.8px'";
		String sessionWorkTimeStyle = "style='font-weight:normal; font-size:9px; font-style:italic'";

		String myLastActivityS;
		if (myLastActivityMission.equals("_NEW_")) {
			myLastActivityS = String.format(
					"<html><span %s>Votre dernière activité</span> <br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>A rejoint le projet</span></html>",
					subtitleStyle, dateStyle, myLastActivityDate, sessionWorkTimeStyle);
		} else if (myLastActivityMission.equals("_END_")) {
			myLastActivityS = String.format(
					"<html><span %s>Votre dernière activité</span> <br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>A terminé le projet</span></html>",
					subtitleStyle, dateStyle, myLastActivityDate, sessionWorkTimeStyle);
		} else {
			myLastActivityS = String.format(
					"<html><span %s>Votre dernière activité</span> <br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s> %s </span><span %s> - %s</span></html>",
					subtitleStyle, dateStyle, myLastActivityDate, missionStyle, myLastActivityMission,
					sessionWorkTimeStyle, myLastActivityWorkTime);
		}

		String lastActivityS;
		if (lastActivityMission.equals("_NEW_")) {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s>A rejoint le projet</span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, sessionWorkTimeStyle);
		} else if (lastActivityMission.equals("_END_")) {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s>A terminé le projet</span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, sessionWorkTimeStyle);
		} else if (lastActivityMission.equals("_QUIT_")) {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s>A quitté le projet</span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, sessionWorkTimeStyle);
		} else {
			lastActivityS = String.format(
					"<html><span %s>Activité la plus récente</span><br><br> &nbsp; <span %s>Le %s</span><br> &nbsp; <span %s>Par %s %s</span><br> &nbsp; <span %s> %s </span><span %s> - %s </span></html>",
					subtitleStyle, dateStyle, lastActivityDate, personneStyle, lastActivityNom.toUpperCase(),
					lastActivityPrenom, missionStyle, lastActivityMission, sessionWorkTimeStyle, lastActivityWorkTime);
		}

		String myWorkTimeS = String.format("<html><span %s>Votre temps de travail</span><br>&nbsp; %s </html>",
				subtitleStyle, myWorkTime);
		String workTimeS = String.format("<html><span %s>Temps de travail de l'équipe</span><br> &nbsp; %s </html>",
				subtitleStyle, totalWorkTime);

		ImageIcon myWorkTimeIcon = new ImageIcon(
				new ImageIcon("img/icone_my_worktime.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));
		ImageIcon workTimeIcon = new ImageIcon(
				new ImageIcon("img/icone_total_worktime.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));

		JLabel titleProjectLabel = new JLabel(titleProjectS);
		JLabel dateProjetLabel = new JLabel(dateProjectS, JLabel.CENTER);
		JLabel myLastActivityLabel = new JLabel(myLastActivityS);
		JLabel lastActivityLabel = new JLabel(lastActivityS);
		JLabel myWorkTimeLabel = new JLabel(myWorkTimeS, myWorkTimeIcon, JLabel.LEFT);
		JLabel workTimeLabel = new JLabel(workTimeS, workTimeIcon, JLabel.LEFT);

		ImageIcon historicIconD = new ImageIcon(new ImageIcon("img/icone_historique_sessions.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon historicIconH = new ImageIcon(new ImageIcon("img/icone_historique_sessions_hover.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		JRoundedButton historicBtn = new JRoundedButton();
		historicBtn.setToolTipText("Historique de mes sessions de travail");
		historicBtn.setPreferredSize(new Dimension(34, 34));
		historicBtn.setMultipleIcon(historicIconD, historicIconH, null);
		historicBtn
				.setName(String.format("projectPanel/myProjects/historic?projectID=%s&persID=%s", projectID, persID));
		historicBtn.addActionListener(frame);

		ImageIcon projectOverviewIconD = new ImageIcon(new ImageIcon("img/icone_project_overview.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon projectOverviewIconH = new ImageIcon(new ImageIcon("img/icone_project_overview_hover.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		JRoundedButton projectOverviewBtn = new JRoundedButton();
		projectOverviewBtn.setToolTipText("Détails du projet");
		projectOverviewBtn.setPreferredSize(new Dimension(34, 34));
		projectOverviewBtn.setMultipleIcon(projectOverviewIconD, projectOverviewIconH, null);
		projectOverviewBtn
				.setName(String.format("projectPanel/myProjects/stats?projectID=%s&persID=%s", projectID, persID));
		projectOverviewBtn.addActionListener(frame);

		String leaveProjectStyleD = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String leaveProjectStyleH = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima; color:#f8f8ff'";
		String leaveProjectSD = String.format("<html><span %s>Quitter le projet</span></html>", leaveProjectStyleD);
		String leaveProjectSH = String.format("<html><span %s>Quitter le projet</span></html>", leaveProjectStyleH);
		JRoundedButton leaveProjectBtn = new JRoundedButton();
		leaveProjectBtn.setToolTipText(
				"<html>Vous devrez attendre une semaine pour rejoindre de nouveau ce projet.<br>Vous ne pouvez pas quitter un projet dans lequel vous avez du temps de travail.</html>");
		leaveProjectBtn.setMultipleText(leaveProjectSD, leaveProjectSH, null);
		leaveProjectBtn.setMultipleInnerColor(leaveProjectBtn.getDefaultInnerColor(), new Color(255, 89, 89),
				new Color(222, 38, 38));
		leaveProjectBtn.setMultipleBorderColor(leaveProjectBtn.getDefaultBorderColor(), new Color(222, 38, 38),
				new Color(255, 89, 89));
		leaveProjectBtn.setName(
				String.format("projectPanel/myProjects/leaveProject?projectID=%s&persID=%s", projectID, persID));
		leaveProjectBtn.addActionListener(frame);

		String resumeProjectStyleD = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String resumeProjectStyleH = "style='font-size:9px; font-weight:normal; font-style:italic; font-family:Optima'";
		String fresumeProjectSD = String.format("<html><span %s>Reprendre le projet</span></html>",
				resumeProjectStyleD);
		String resumeProjectSH = String.format("<html><span %s>Reprendre le projet</span></html>", resumeProjectStyleH);
		JRoundedButton resumeProjectBtn = new JRoundedButton();
		resumeProjectBtn.setToolTipText(
				"<html>Tout le monde a terminé ce projet.<br>Si vous reprenez ce projet, il apparaîtra en attente pour tous les participants.</html>");
		resumeProjectBtn.setMultipleText(fresumeProjectSD, resumeProjectSH, null);
		resumeProjectBtn.setMultipleInnerColor(resumeProjectBtn.getDefaultInnerColor(), new Color(255, 229, 39),
				new Color(222, 199, 34));
		resumeProjectBtn.setMultipleBorderColor(resumeProjectBtn.getDefaultBorderColor(), new Color(222, 199, 34),
				new Color(255, 229, 39));
		resumeProjectBtn.setName(String.format("projectPanel/myProjects/resumeArchivedProject?projectID=%s&persID=%s",
				projectID, persID));
		resumeProjectBtn.addActionListener(frame);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 0;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(7, 10, 0, 10);
		tmpPanel.add(titleProjectLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 10, 10, 10);
		tmpPanel.add(dateProjetLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(myWorkTimeLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 3;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(workTimeLabel, tmpGbc);

		tmpGbc.gridx = 1;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 15, 5, 15);
		tmpPanel.add(myLastActivityLabel, tmpGbc);

		tmpGbc.gridx = 2;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 15, 5, 15);
		tmpPanel.add(lastActivityLabel, tmpGbc);

		tmpGbc.gridx = 4;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 20, 0, 10);
		tmpPanel.add(historicBtn, tmpGbc);

		tmpGbc.gridx = 5;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 0, 0, 10);
		tmpPanel.add(projectOverviewBtn, tmpGbc);

		tmpGbc.gridx = 3;
		tmpGbc.gridy = 3;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 3;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.HORIZONTAL;
		tmpGbc.insets = new Insets(0, 20, 0, 10);
		tmpPanel.add(resumeProjectBtn, tmpGbc);

		return tmpPanel;
	}

	// OTHER ONGOING PANEL ------------------------

	private static JPanel getOtherOngoingPanel(HashMap<String, Object> ongoingProject) {
		JPanel tmpPanel = new JPanel(new GridBagLayout());
		GridBagConstraints tmpGbc = new GridBagConstraints();

		String nomProjet = (String) ongoingProject.get("nom_projet");
		String clientProjet = (String) ongoingProject.get("client_projet");
		int projectID = (int) ongoingProject.get("id");

		ResultArray participants = db.getParticipants(projectID);

		long totalWorkTimeSec = db.getTimeWork(projectID);
		String totalWorkTime = StringOperations.formatTime(totalWorkTimeSec, "jh");

		String nomProjetStyle = "style='font-weight:normal; font-size:12px'";
		String nomProjetS = String.format("<html><span %s> %s </span></html>", nomProjetStyle, nomProjet.toUpperCase());

		String clientProjetStyle = "style='font-weight:bold; font-size:10px; font-style:italic'";
		String clientProjetS = String.format("<html><span %s> %s </span></html>", clientProjetStyle, clientProjet);

		String subtitleStyle = "style='font-weight:normal; font-size:10px; font-style:italic'";
		String workTimeS = String.format("<html><span %s>Temps de travail de l'équipe</span><br> &nbsp; %s </html>",
				subtitleStyle, totalWorkTime);

		ImageIcon workTimeIcon = new ImageIcon(
				new ImageIcon("img/icone_total_worktime.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));

		String nbParticipantsS;
		if (participants.size == 1) {
			nbParticipantsS = "1 participant";
		} else {
			nbParticipantsS = String.format("%d participants", participants.size);
		}

		String nomsParticipantsS = "";

		for (int j = 0; j < participants.size; j++) {
			nomsParticipantsS += String.format("&nbsp; %s %s<br>", ((String) participants.get(j, "nom")).toUpperCase(),
					(String) participants.get(j, "prenom"));
		}

		String nomsParticipantsStyle = "style='font-weight:normal; font-size:9px'";
		String participantsS = String.format("<html><span %s> %s </span><br><span %s> %s </span></html>", subtitleStyle,
				nbParticipantsS, nomsParticipantsStyle, nomsParticipantsS);

		ImageIcon participantsIcon = new ImageIcon(
				new ImageIcon("img/icone_teamworkers.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));

		JLabel nomProjetLabel = new JLabel(nomProjetS);
		JLabel clientProjetLabel = new JLabel(clientProjetS);
		JLabel workTimeLabel = new JLabel(workTimeS, workTimeIcon, JLabel.LEFT);
		JLabel participantsLabel = new JLabel(participantsS, participantsIcon, JLabel.LEFT);

		JRoundedButton takePartBtn = new JRoundedButton();

		ImageIcon takePartIconD = new ImageIcon(
				new ImageIcon("img/icone_take_part.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		ImageIcon takePartIconH = new ImageIcon(new ImageIcon("img/icone_take_part_hover.png").getImage()
				.getScaledInstance(26, 26, Image.SCALE_SMOOTH));
		takePartBtn.setMultipleIcon(takePartIconD, takePartIconH, null);

		takePartBtn.setToolTipText("Participer à ce projet");
		takePartBtn.setPreferredSize(new Dimension(34, 34));
		takePartBtn.setName(
				String.format("projectPanel/otherProjects/takePartBtn?projectID=%s&persID=%s", projectID, persID));
		takePartBtn.addActionListener(frame);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 0;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(7, 10, 0, 10);
		tmpPanel.add(nomProjetLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 10, 10, 10);
		tmpPanel.add(clientProjetLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(workTimeLabel, tmpGbc);

		tmpGbc.gridx = 1;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(participantsLabel, tmpGbc);

		tmpGbc.gridx = 2;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 2;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(takePartBtn, tmpGbc);

		return tmpPanel;
	}

	// OTHER ENDED PANEL ------------------------

	private static JPanel getOtherEndedPanel(HashMap<String, Object> endedProject) {
		JPanel tmpPanel = new JPanel(new GridBagLayout());
		GridBagConstraints tmpGbc = new GridBagConstraints();

		String nomProjet = (String) endedProject.get("nom_projet");
		String clientProjet = (String) endedProject.get("client_projet");
		int projectID = (int) endedProject.get("id");

		ResultArray participants = db.getParticipants(projectID);

		long totalWorkTimeSec = db.getTimeWork(projectID);
		String totalWorkTime = StringOperations.formatTime(totalWorkTimeSec, "jh");

		String nomProjetStyle = "style='font-weight:normal; font-size:12px'";
		String nomProjetS = String.format("<html><span %s> %s </span></html>", nomProjetStyle, nomProjet.toUpperCase());

		String clientProjetStyle = "style='font-weight:bold; font-size:10px; font-style:italic'";
		String clientProjetS = String.format("<html><span %s> %s </span></html>", clientProjetStyle, clientProjet);

		String subtitleStyle = "style='font-weight:normal; font-size:10px; font-style:italic'";
		String workTimeS = String.format("<html><span %s>Temps de travail de l'équipe</span><br> &nbsp; %s </html>",
				subtitleStyle, totalWorkTime);

		ImageIcon workTimeIcon = new ImageIcon(
				new ImageIcon("img/icone_total_worktime.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));

		String nbParticipantsS;
		if (participants.size == 1) {
			nbParticipantsS = "1 participant";
		} else {
			nbParticipantsS = String.format("%d participants", participants.size);
		}

		String nomsParticipantsS = "";

		for (int j = 0; j < participants.size; j++) {
			nomsParticipantsS += String.format("&nbsp; %s %s<br>", ((String) participants.get(j, "nom")).toUpperCase(),
					(String) participants.get(j, "prenom"));
		}

		String nomsParticipantsStyle = "style='font-weight:normal; font-size:9px'";
		String participantsS = String.format("<html><span %s> %s </span><br><span %s> %s </span></html>", subtitleStyle,
				nbParticipantsS, nomsParticipantsStyle, nomsParticipantsS);

		ImageIcon participantsIcon = new ImageIcon(
				new ImageIcon("img/icone_teamworkers.png").getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH));

		JLabel nomProjetLabel = new JLabel(nomProjetS);
		JLabel clientProjetLabel = new JLabel(clientProjetS);
		JLabel workTimeLabel = new JLabel(workTimeS, workTimeIcon, JLabel.LEFT);
		JLabel participantsLabel = new JLabel(participantsS, participantsIcon, JLabel.LEFT);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 0;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(7, 10, 0, 10);
		tmpPanel.add(nomProjetLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 1;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 6;
		tmpGbc.anchor = GridBagConstraints.CENTER;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(0, 10, 10, 10);
		tmpPanel.add(clientProjetLabel, tmpGbc);

		tmpGbc.gridx = 0;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(workTimeLabel, tmpGbc);

		tmpGbc.gridx = 1;
		tmpGbc.gridy = 2;
		tmpGbc.gridheight = 1;
		tmpGbc.gridwidth = 1;
		tmpGbc.anchor = GridBagConstraints.BASELINE_LEADING;
		tmpGbc.fill = GridBagConstraints.NONE;
		tmpGbc.insets = new Insets(5, 20, 5, 10);
		tmpPanel.add(participantsLabel, tmpGbc);

		return tmpPanel;
	}
}
