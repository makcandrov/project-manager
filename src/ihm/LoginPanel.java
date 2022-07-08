package ihm;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Image;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import databases.DataBase;
import databases.ResultArray;

public abstract class LoginPanel {

	private static Color backgroundColor = new Color(251, 251, 251);

	public static JPanel getPanel(ActionListener frame) {
		DataBase db = DataBase.db;

		ResultArray users = db.getAllUsers();
		int nbPersonnes = users.size;

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new GridBagLayout());
		innerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		innerPanel.setBackground(backgroundColor);

		GridBagConstraints gbc = new GridBagConstraints();

		String labelStyle = "style='font-size:15px; font-weight:normal; font-family:Optima'";
		JLabel label = new JLabel("<html><span " + labelStyle + ">Je suis...</span></html>", JLabel.CENTER);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 20, 0);
		innerPanel.add(label, gbc);

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < nbPersonnes; i++) {
			String nom = (String) users.get(i, "nom");
			String prenom = (String) users.get(i, "prenom");
			String qualification = (String) users.get(i, "qualification");

			String nomStyleD = "style='font-size:12px; font-weight:bold; font-family:Optima'";
			String prenomStyleD = "style='font-size:12px; font-weight:normal; font-family:Optima'";
			String qualStyleD = "style='font-size:10px; font-weight:normal; font-style:italic; font-family:Optima'";

			String nomStyleH = "style='font-size:12px; font-weight:bold; font-family:Optima; color:#f8f8ff'";
			String prenomStyleH = "style='font-size:12px; font-weight:normal; font-family:Optima; color:#f8f8ff'";
			String qualStyleH = "style='font-size:10px; font-weight:normal; font-style:italic; font-family:Optima; color:#f8f8ff'";

			String pLine = "<html><span %s> %s </span> &ensp; <span %s> %s </span> &emsp;<span %s> %s </span></html>";
			String lineD = String.format(pLine, nomStyleD, nom.toUpperCase(), prenomStyleD, prenom, qualStyleD,
					qualification);
			String lineH = String.format(pLine, nomStyleH, nom.toUpperCase(), prenomStyleH, prenom, qualStyleH,
					qualification);

			JRoundedButton persBtn = new JRoundedButton();
			persBtn.setMultipleText(lineD, lineH, null);
			persBtn.setSize(new Dimension(500, 100));
			persBtn.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
			persBtn.addActionListener(frame);
			persBtn.setName("pers" + users.get(i, "id"));
			loginPanel.add(persBtn);
			loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		JPanel loginPanelScroll = new JPanel(new BorderLayout());
		JScrollPane jsp = (new JScrollPane(loginPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		jsp.getVerticalScrollBar().setUnitIncrement(4);
		loginPanelScroll.add(jsp);
		loginPanelScroll.setPreferredSize(new Dimension(1, 1));

		loginPanel.setBackground(backgroundColor);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.BOTH;
		innerPanel.add(loginPanelScroll, gbc);

		innerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		String newPersStyleD = "style='font-size:11px; font-weight:normal; font-style:italic; font-family:Optima'";
		String newPersStyleH = "style='font-size:11px; font-weight:normal; font-style:italic; font-family:Optima; color:#f8f8ff'";

		ImageIcon newPersIconD = new ImageIcon(
				new ImageIcon("img/icone_new_user.png").getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		ImageIcon newPersIconH = new ImageIcon(
				new ImageIcon("img/icone_new_user_hover.png").getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH));

		String newPersBtnSD = "<html><span " + newPersStyleD + ">&nbsp;Ajouter une personne...</span></html>";
		String newPersBtnSH = "<html><span " + newPersStyleH + ">&nbsp;Ajouter une personne...</span></html>";

		JRoundedButton newPersBtn = new JRoundedButton();
		newPersBtn.setMultipleText(newPersBtnSD, newPersBtnSH, null);
		newPersBtn.setMultipleIcon(newPersIconD, newPersIconH, null);

		newPersBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		newPersBtn.setSize(new Dimension(300, 50));
		newPersBtn.setName("addPerson");
		newPersBtn.addActionListener(frame);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(20, 0, 20, 0);
		innerPanel.add(newPersBtn, gbc);
		newPersBtn.setMinimumSize(newPersBtn.getSize());

		return JPanelOperations.setBorders(innerPanel, 1.0);
	}
}
