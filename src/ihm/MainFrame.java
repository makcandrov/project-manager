package ihm;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import databases.DataBase;
import databases.ResultArray;
import operations.StringOperations;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	DataBase db = DataBase.db;

	JPanel loginPanel;
	JPanel signupPanel;
	JPanel projectPanel;
	JPanel newWorkSessionPanel;
	JPanel historicPanel;
	JPanel statsPanel;

	public int persID;

	private static String erreurStyle = "style='font-weight:normal; font-size:10px; font-family:Optima; color:#ff3232'";

	public MainFrame() {
		super("Projects Manager");

		Image frameIcon = Toolkit.getDefaultToolkit().getImage("img/icone_frame.png");
		this.setIconImage(frameIcon);

		this.setPreferredSize(new Dimension(1200, 742));
		this.setMinimumSize(new Dimension(500, 309));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			String userIP = InetAddress.getLocalHost().getHostAddress();
			this.persID = db.getUserIDbyIP(userIP);
			if (this.persID < 0 || userIP.equals("127.0.0.1")) {
				loginPanel = LoginPanel.getPanel(this);
				this.add(loginPanel);
			} else {
				projectPanel = ProjectPanel.getPanel(this, persID);
				db.updateLastConnexion(persID, Calendar.getInstance().getTimeInMillis() / 1000);
				this.add(projectPanel);
			}
		} catch (UnknownHostException e) {
			loginPanel = LoginPanel.getPanel(this);
			this.add(loginPanel);
		} finally {
			this.setVisible(true);
			this.pack();
		}

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String btnName = ((Component) evt.getSource()).getName();
		if (StringOperations.regexMatching(btnName, "^pers\\d+$")) {
			persID = StringOperations.regexExtractNumber(btnName, "^pers(\\d+)$");
			try {
				String userIP = InetAddress.getLocalHost().getHostAddress();
				if (!userIP.equals("127.0.0.1")) {
					db.resetIP(userIP);
					db.updateIP(persID, userIP);
				}
				db.updateLastConnexion(persID, Calendar.getInstance().getTimeInMillis() / 1000);
			} catch (UnknownHostException e) {
			}

			loginPanel.setVisible(false);
			projectPanel = ProjectPanel.getPanel(this, persID);
			this.add(projectPanel);

		} else if (btnName.equals("addPerson")) {
			loginPanel.setVisible(false);
			signupPanel = SignUpPanel.getPanel(this);
			this.add(signupPanel);

		} else if (btnName.equals("signUpAnnuler")) {
			loginPanel = LoginPanel.getPanel(this);
			signupPanel.setVisible(false);
			this.add(loginPanel);

		} else if (btnName.equals("signUpValider")) {
			String nom = SignUpPanel.nomJTF.getText();
			String prenom = SignUpPanel.prenomJTF.getText();
			String poste = SignUpPanel.posteJTF.getText();
			if (nom.length() > 0 && prenom.length() > 0) {
				nom = StringOperations.capitalize(nom);
				prenom = StringOperations.capitalize(prenom);
				if (db.existsUser(nom, prenom)) {
					String erreurS = String.format("<html><span %s>Cette personne existe déjà.</span></html>",
							erreurStyle);
					SignUpPanel.erreurLabel.setText(erreurS);
					SignUpPanel.erreurLabel.setVisible(true);
				} else {
					String ipAddress = null;

					try {
						InetAddress inetadr = InetAddress.getLocalHost();
						ipAddress = (String) inetadr.getHostAddress();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}

					db.resetIP(ipAddress);
					db.addUser(nom, prenom, poste, ipAddress, Calendar.getInstance().getTimeInMillis() / 1000);

					persID = db.getUserID(nom, prenom, poste);

					signupPanel.setVisible(false);
					projectPanel = ProjectPanel.getPanel(this, persID);
					this.add(projectPanel);

				}
			} else {
				String erreurS = String.format("<html><span %s>Nom ou prénom vide.</span></html>", erreurStyle);
				SignUpPanel.erreurLabel.setText(erreurS);
				SignUpPanel.erreurLabel.setVisible(true);
			}

		} else if (btnName.equals("projectChangeUser")) {
			projectPanel.setVisible(false);
			loginPanel = LoginPanel.getPanel(this);
			this.add(loginPanel);

		} else if (btnName.equals("projectPanel/refresh")) {
			int selectedIndex = ProjectPanel.tabPanel.getSelectedIndex();
			projectPanel.setVisible(false);
			projectPanel = ProjectPanel.getPanel(this, persID);
			ProjectPanel.tabPanel.setSelectedIndex(selectedIndex);
			this.add(projectPanel);

		} else if (StringOperations.regexMatching(btnName, "^projectPanel/otherProjects/takePartBtn.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");
			int persID = StringOperations.regexExtractNumber(btnName, "persID=(\\d+)");
			db.addWorkSessions(projectID, persID, 0, "_NEW_");
			projectPanel.setVisible(false);
			projectPanel = ProjectPanel.getPanel(this, persID);
			this.add(projectPanel);

		} else if (btnName.equals("projectPanel/creerProjet/creerProjet")) {
			String nomProjet = ProjectPanel.nomProjetJTF.getText();
			String clientProjet = ProjectPanel.clientProjetJTF.getText();
			if (db.issetProject(nomProjet, clientProjet)) {
				String erreurS = String.format("<html><span %s>Ce projet existe déjà.</span></html>", erreurStyle);
				ProjectPanel.newProjectErreurLabel.setText(erreurS);
				ProjectPanel.newProjectErreurLabel.setVisible(true);
			} else if (nomProjet.length() == 0) {
				String erreurS = String.format("<html><span %s>Nom du projet vide.</span></html>", erreurStyle);
				ProjectPanel.newProjectErreurLabel.setText(erreurS);
				ProjectPanel.newProjectErreurLabel.setVisible(true);
			} else if (clientProjet.length() == 0) {
				String erreurS = String.format("<html><span %s>Nom du client vide.</span></html>", erreurStyle);
				ProjectPanel.newProjectErreurLabel.setText(erreurS);
				ProjectPanel.newProjectErreurLabel.setVisible(true);
			} else {
				db.addProject(nomProjet, clientProjet);
				int projectID = db.getProjectID(nomProjet, clientProjet);
				db.addWorkSessions(projectID, persID, 0, "_NEW_");
				projectPanel.setVisible(false);
				projectPanel = ProjectPanel.getPanel(this, persID);
				this.add(projectPanel);

			}
		} else if (StringOperations.regexMatching(btnName, "^projectPanel/myProjects/addSession.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");
			newWorkSessionPanel = NewWorkSessionPanel.getPanel(this, projectID, persID);
			projectPanel.setVisible(false);
			this.add(newWorkSessionPanel);

		} else if (btnName.equals("newWorkSession/goBack")) {
			newWorkSessionPanel.setVisible(false);
			projectPanel = ProjectPanel.getPanel(this, persID);
			this.add(projectPanel);

		} else if (StringOperations.regexMatching(btnName, "^newWorkSessionPanel/valider.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");

			String heuresS = NewWorkSessionPanel.heuresJTF.getText();
			String minutesS = NewWorkSessionPanel.minutesJTF.getText();
			String missionS = NewWorkSessionPanel.missionJTF.getText();
			String dateHeureS = NewWorkSessionPanel.dateHeureJTF.getText();
			String dateMinuteS = NewWorkSessionPanel.dateMinuteJTF.getText();

			String mission = NewWorkSessionPanel.missionsCB.getSelectedItem().toString();

			int itemCount = NewWorkSessionPanel.missionsCB.getItemCount();
			int selectedIndex = NewWorkSessionPanel.missionsCB.getSelectedIndex();
			if (itemCount - 1 == selectedIndex) {
				mission = missionS;
			}

			boolean dureeValide = true;
			boolean missionValide = true;
			boolean dateValide = true;

			if (heuresS.length() == 0 || minutesS.length() == 0) {
				dureeValide = false;
				String erreurS = String.format("<html><span %s>Durée invalide.</span></html>", erreurStyle);
				NewWorkSessionPanel.erreurLabel.setText(erreurS);
				NewWorkSessionPanel.erreurLabel.setVisible(true);
			}
			if (missionS.length() == 0 && itemCount - 1 == selectedIndex) {
				missionValide = false;
				String erreurS = String.format("<html><span %s>Mission invalide.</span></html>", erreurStyle);
				NewWorkSessionPanel.erreurLabel.setText(erreurS);
				NewWorkSessionPanel.erreurLabel.setVisible(true);
			}
			if (dateHeureS.length() == 0 || dateMinuteS.length() == 0) {
				dateValide = false;
				String erreurS = String.format("<html><span %s>Date invalide.</span></html>", erreurStyle);
				NewWorkSessionPanel.erreurLabel.setText(erreurS);
				NewWorkSessionPanel.erreurLabel.setVisible(true);
			}

			if (dureeValide && missionValide && dateValide) {
				int workTime = Integer.parseInt(heuresS) * 3600;
				workTime += Integer.parseInt(minutesS) * 60;

				Calendar calendar = Calendar.getInstance();
				long currentTimestamp = calendar.getTimeInMillis();

				currentTimestamp -= NewWorkSessionPanel.dateCB.getSelectedIndex() * 24 * 60 * 60 * 1000;
				calendar.setTimeInMillis(currentTimestamp);

				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH), Integer.parseInt(dateHeureS),
						Integer.parseInt(dateMinuteS));

				long date = calendar.getTimeInMillis() / 1000;

				db.addWorkSessions(projectID, persID, date, workTime, mission);

				newWorkSessionPanel.setVisible(false);
				projectPanel = ProjectPanel.getPanel(this, persID);
				this.add(projectPanel);
			}

		} else if (StringOperations.regexMatching(btnName, "^projectPanel/myProjects/historic.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");
			historicPanel = HistoricPanel.getPanel(this, projectID, persID, -1);
			projectPanel.setVisible(false);
			this.add(historicPanel);
		} else if (StringOperations.regexMatching(btnName, "^projectPanel/myProjects/finishProject.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");

			int nbWaitingParticipants = db.getNbWaitingParticipants(projectID);
			int nbParticipants = db.getParticipants(projectID).size;

			if (nbWaitingParticipants == nbParticipants - 1) {
				ConfirmationDialog confirmation = new ConfirmationDialog(this, "Archiver le projet", true);
				JPanel confirmationPanel = ConfirmationPanel.getPanel(confirmation, String.format(
						"Vous êtes le dernier membre à participer à ce projet. Si vous le terminez, il sera archivé."));
				confirmation.add(confirmationPanel);
				confirmation.pack();
				confirmation.setVisible(true);

				if (confirmation.res > 0) {
					db.addWorkSessions(projectID, persID, 0, "_END_");

					Calendar calendar = Calendar.getInstance();

					db.updateProjectEndDate(projectID, calendar.getTimeInMillis() / 1000);
				}
			} else {
				db.addWorkSessions(projectID, persID, 0, "_END_");
			}

			projectPanel.setVisible(false);
			projectPanel = ProjectPanel.getPanel(this, persID);
			this.add(projectPanel);
		} else if (StringOperations.regexMatching(btnName, "^projectPanel/myProjects/resumeProject.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");
			db.deleteMySessions(projectID, persID, "_END_");

			projectPanel.setVisible(false);
			projectPanel = ProjectPanel.getPanel(this, persID);
			this.add(projectPanel);
		} else if (StringOperations.regexMatching(btnName, "^projectPanel/myProjects/leaveProject.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");

			if (db.getParticipants(projectID).size <= 1) {
				ConfirmationDialog confirmation = new ConfirmationDialog(this, "Supprimer le projet", true);
				JPanel confirmationPanel = ConfirmationPanel.getPanel(confirmation, String
						.format("Vous êtes le seul membre de ce projet. Si vous quittez ce projet, il sera supprimé."));
				confirmation.add(confirmationPanel);
				confirmation.pack();
				confirmation.setVisible(true);

				if (confirmation.res > 0) {
					db.deleteMySessions(projectID, persID);
					db.deleteProject(projectID);
					projectPanel.setVisible(false);
					projectPanel = ProjectPanel.getPanel(this, persID);
					this.add(projectPanel);
				}
			} else {
				db.deleteMySessions(projectID, persID);
				projectPanel.setVisible(false);
				projectPanel = ProjectPanel.getPanel(this, persID);
				this.add(projectPanel);
			}

		} else if (StringOperations.regexMatching(btnName, "^projectPanel/myProjects/resumeArchivedProject.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");

			ConfirmationDialog confirmation = new ConfirmationDialog(this, "Reprendre un projet archivé", true);
			JPanel confirmationPanel = ConfirmationPanel.getPanel(confirmation, String.format(
					"Tous les membres de ce projet ont indiqué qu'ils l'ont terminé. Voulez-vous le reprendre ?"));
			confirmation.add(confirmationPanel);
			confirmation.pack();
			confirmation.setVisible(true);

			if (confirmation.res > 0) {
				db.deleteMySessions(projectID, persID, "_END_");
				db.updateProjectEndDate(projectID, null);
				projectPanel.setVisible(false);
				projectPanel = ProjectPanel.getPanel(this, persID);
				this.add(projectPanel);
			}

		} else if (btnName.equals("historicPanel/goBack")) {
			historicPanel.setVisible(false);
			projectPanel = ProjectPanel.getPanel(this, persID);
			this.add(projectPanel);

		} else if (StringOperations.regexMatching(btnName, "^historicPanel/delete.*")) {
			int sessionID = StringOperations.regexExtractNumber(btnName, "sessionID=(\\d+)");
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");

			ResultArray sessions = db.getSession(sessionID);

			Number dateTimestamp = (Number) sessions.get(0, "date");
			Date d = new Date(dateTimestamp.longValue() * 1000);
			DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			String dateS = f.format(d);

			ConfirmationDialog confirmation = new ConfirmationDialog(this, "Supprimer une session de travail", true);
			JPanel confirmationPanel = ConfirmationPanel.getPanel(confirmation,
					String.format("Voulez-vous vraiment supprimer cette session du %s ?", dateS));
			confirmation.add(confirmationPanel);
			confirmation.pack();
			confirmation.setVisible(true);

			if (confirmation.res > 0) {
				db.deleteSession(sessionID);
				historicPanel.setVisible(false);
				historicPanel = HistoricPanel.getPanel(this, projectID, persID, -1);
				this.add(historicPanel);

			}

		} else if (StringOperations.regexMatching(btnName, "^historicPanel/edit.*")) {
			int sessionID = StringOperations.regexExtractNumber(btnName, "sessionID=(\\d+)");
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");

			historicPanel.setVisible(false);
			historicPanel = HistoricPanel.getPanel(this, projectID, persID, sessionID);
			this.add(historicPanel);

		} else if (StringOperations.regexMatching(btnName, "^historicPanel/annulerEdit.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");
			historicPanel.setVisible(false);
			historicPanel = HistoricPanel.getPanel(this, projectID, persID, -1);
			this.add(historicPanel);

		} else if (StringOperations.regexMatching(btnName, "^historicPanel/validerEdit.*")) {
			int sessionID = StringOperations.regexExtractNumber(btnName, "sessionID=(\\d+)");
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");

			String dateS = (String) HistoricPanel.dateCB.getSelectedItem();

			String heureS = HistoricPanel.heureJTF.getText();
			String minuteS = HistoricPanel.minuteJTF.getText();
			String heuresS = HistoricPanel.heuresJTF.getText();
			String minutesS = HistoricPanel.minutesJTF.getText();

			String missionS = HistoricPanel.missionsCB.getSelectedItem().toString();

			int itemCount = HistoricPanel.missionsCB.getItemCount();
			int selectedIndex = HistoricPanel.missionsCB.getSelectedIndex();
			if (itemCount - 1 == selectedIndex) {
				missionS = HistoricPanel.missionJTF.getText();
			}

			boolean valideHeure = true;
			boolean valideMission = true;
			boolean valideDuree = true;

			if (heureS.length() == 0 || minuteS.length() == 0) {
				valideHeure = false;
				HistoricPanel.erreurLabel
						.setText(String.format("<html><span %s>Heure invalide.</span></html>", erreurStyle));
				HistoricPanel.erreurLabel.setVisible(true);
			}
			if (missionS.length() == 0 || missionS.equals("_NEW_")) {
				valideMission = false;
				HistoricPanel.erreurLabel
						.setText(String.format("<html><span %s>Mission invalide.</span></html>", erreurStyle));
				HistoricPanel.erreurLabel.setVisible(true);
			}
			if (heuresS.length() == 0 || minutesS.length() == 0) {
				HistoricPanel.erreurLabel
						.setText(String.format("<html><span %s>Durée invalide.</span></html>", erreurStyle));
				HistoricPanel.erreurLabel.setVisible(true);
			}
			if (valideHeure && valideMission && valideDuree) {
				int heure = Integer.parseInt(heureS);
				int minute = Integer.parseInt(minuteS);
				int heures = Integer.parseInt(heuresS);
				int minutes = Integer.parseInt(minutesS);

				int jour = StringOperations.regexExtractNumber(dateS, "(\\d+) / \\d+ / \\d+");
				int mois = StringOperations.regexExtractNumber(dateS, "\\d+ / (\\d+) / \\d+");
				int annee = StringOperations.regexExtractNumber(dateS, "\\d+ / \\d+ / (\\d+)");

				Calendar calendar = Calendar.getInstance();
				calendar.set(annee, mois - 1, jour, heure, minute, 0);

				long date = calendar.getTimeInMillis() / 1000;

				String mission = missionS;

				long workTime = (long) heures * 3600 + (long) minutes * 60;

				db.updateSession(sessionID, date, workTime, mission);

				historicPanel.setVisible(false);
				historicPanel = HistoricPanel.getPanel(this, projectID, persID, -1);
				this.add(historicPanel);

			}

		} else if (StringOperations.regexMatching(btnName, "^projectPanel/myProjects/stats.*")) {
			int projectID = StringOperations.regexExtractNumber(btnName, "projectID=(\\d+)");
			statsPanel = StatsPanel.getPanel(this, projectID, persID);
			projectPanel.setVisible(false);
			this.add(statsPanel);

		} else if (btnName.equals("statsPanel/goBack")) {
			statsPanel.setVisible(false);
			projectPanel = ProjectPanel.getPanel(this, persID);
			this.add(projectPanel);

		}

	}

}
