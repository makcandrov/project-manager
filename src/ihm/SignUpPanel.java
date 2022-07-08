package ihm;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SignUpPanel {

    public static JCustomTextField nomJTF;
    public static JCustomTextField prenomJTF;
    public static JCustomTextField posteJTF;

    public static JLabel erreurLabel;

    public static JPanel getPanel(ActionListener frame) {
        JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        innerPanel.setBackground(new Color(251, 251, 251));

        String titleStyle = "style='font-weight:normal; font-size:14.5px; font-family:Optima; color:#444444'";
        String labelStyle = "style='font-weight:normal; font-size:11.5px; font-family:Optima; color:#444444'";

        String titleLabelS = "<html><span " + titleStyle + ">AJOUTER UNE PERSONNE</span></html>";
        String nomLabelS = "<html><span " + labelStyle + ">Nom</span></html>";
        String prenomLabelS = "<html><span " + labelStyle + ">Prénom</span></html>";
        String posteLabelS = "<html><span " + labelStyle + ">Poste</span></html>";

        JLabel titleLabel = new JLabel(titleLabelS, JLabel.CENTER);
        JLabel nomLabel = new JLabel(nomLabelS);
        JLabel prenomLabel = new JLabel(prenomLabelS);
        JLabel posteLabel = new JLabel(posteLabelS);

        nomJTF = new JCustomTextField();
        nomJTF.setPreferredSize(new Dimension(175, 24));
        nomJTF.setMinimumSize(new Dimension(175, 24));

        nomJTF.addMouseListener(new MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                erreurLabel.setVisible(false);
            }
        });

        prenomJTF = new JCustomTextField();
        prenomJTF.setPreferredSize(new Dimension(175, 24));
        prenomJTF.setMinimumSize(new Dimension(175, 24));

        prenomJTF.addMouseListener(new MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                erreurLabel.setVisible(false);
            }
        });

        posteJTF = new JCustomTextField();
        posteJTF.setPreferredSize(new Dimension(175, 24));
        posteJTF.setMinimumSize(new Dimension(175, 24));

        posteJTF.addMouseListener(new MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                erreurLabel.setVisible(false);
            }
        });

        erreurLabel = new JLabel("erreur", JLabel.CENTER);
        erreurLabel.setVisible(false);

        String validerTextD = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#555555'>Valider</span></html>";
        String validerTextH = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#f8f8ff'>Valider</span></html>";

        JRoundedButton validerBtn = new JRoundedButton();
        validerBtn.setMultipleText(validerTextD, validerTextH, null);
        validerBtn.setMultipleInnerColor(validerBtn.getDefaultInnerColor(), new Color(71, 255, 134),
                new Color(19, 203, 82));
        validerBtn.setMultipleBorderColor(validerBtn.getDefaultBorderColor(), new Color(19, 203, 82),
                new Color(71, 255, 134));

        validerBtn.setName("signUpValider");
        validerBtn.addActionListener(frame);

        String annulerTextD = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#555555'>Annuler</span></html>";
        String annulerTextH = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#f8f8ff'>Annuler</span></html>";

        JRoundedButton annulerBtn = new JRoundedButton();
        annulerBtn.setMultipleText(annulerTextD, annulerTextH, null);
        annulerBtn.setMultipleInnerColor(annulerBtn.getDefaultInnerColor(), new Color(255, 89, 89),
                new Color(222, 38, 38));
        annulerBtn.setMultipleBorderColor(annulerBtn.getDefaultBorderColor(), new Color(222, 38, 38),
                new Color(255, 89, 89));

        annulerBtn.setName("signUpAnnuler");
        annulerBtn.addActionListener(frame);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 0, 30, 0);
        innerPanel.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 30, 0, 0);
        innerPanel.add(nomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 30);
        innerPanel.add(nomJTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 30, 0, 0);
        innerPanel.add(prenomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 30);
        innerPanel.add(prenomJTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 30, 0, 0);
        innerPanel.add(posteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 30);
        innerPanel.add(posteJTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.insets = new Insets(10, 0, 0, 0);
        innerPanel.add(erreurLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        innerPanel.add(new JLabel(""), gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.insets = new Insets(10, 0, 20, 0);
        innerPanel.add(annulerBtn, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0;
        gbc.insets = new Insets(10, 10, 20, 30);
        innerPanel.add(validerBtn, gbc);

        return JPanelOperations.setBorders(innerPanel, 1.0 / 4.0);
    }

}
