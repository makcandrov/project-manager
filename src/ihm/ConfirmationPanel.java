package ihm;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class ConfirmationPanel {

	public static JPanel getPanel(ConfirmationDialog frame, String text) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(251, 251, 251));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		String textStyle = "style='font-weight:normal; font-size:11px'";
		String textS = String.format("<html><span %s> %s </span></html>", textStyle, text);

		JLabel textLabel = new JLabel(textS);

		String validerTextD = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#555555'>Confirmer</span></html>";
		String validerTextH = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#f8f8ff'>Confirmer</span></html>";

		JRoundedButton validerBtn = new JRoundedButton();
		validerBtn.setMultipleText(validerTextD, validerTextH, null);
		validerBtn.setMultipleInnerColor(validerBtn.getDefaultInnerColor(), new Color(71, 255, 134),
				new Color(19, 203, 82));
		validerBtn.setMultipleBorderColor(validerBtn.getDefaultBorderColor(), new Color(19, 203, 82),
				new Color(71, 255, 134));

		validerBtn.setName("confirmationPanel/valider");
		validerBtn.addActionListener(frame);

		String annulerTextD = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#555555'>Annuler</span></html>";
		String annulerTextH = "<html><span style='font-weight:bold; font-size:10px; font-family:Optima; color:#f8f8ff'>Annuler</span></html>";

		JRoundedButton annulerBtn = new JRoundedButton();
		annulerBtn.setMultipleText(annulerTextD, annulerTextH, null);
		annulerBtn.setMultipleInnerColor(validerBtn.getDefaultInnerColor(), new Color(255, 89, 89),
				new Color(222, 38, 38));
		annulerBtn.setMultipleBorderColor(validerBtn.getDefaultBorderColor(), new Color(222, 38, 38),
				new Color(255, 89, 89));

		annulerBtn.setName("confirmationPanel/annuler");
		annulerBtn.addActionListener(frame);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(15, 10, 15, 10);
		panel.add(textLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(""), gbc);

		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(15, 0, 15, 0);
		panel.add(annulerBtn, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(15, 10, 15, 10);
		panel.add(validerBtn, gbc);

		return panel;
	}
}
