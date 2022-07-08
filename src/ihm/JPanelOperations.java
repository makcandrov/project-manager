package ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class JPanelOperations {

    public static JPanel setBorders(JPanel innerPanel, double ratio) {
        JPanel panel = new JColoredPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbcP = new GridBagConstraints();

        gbcP.gridx = 0;
        gbcP.gridy = 0;
        gbcP.gridheight = 1;
        gbcP.gridwidth = 1;
        gbcP.weightx = 1;
        gbcP.anchor = GridBagConstraints.BASELINE;
        gbcP.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JColoredPanel(), gbcP);

        gbcP.gridx = 1;
        gbcP.gridy = 0;
        gbcP.gridheight = 1;
        gbcP.gridwidth = 1;
        gbcP.weightx = 1.618 * ratio;
        gbcP.weighty = 1;
        gbcP.anchor = GridBagConstraints.BASELINE;
        gbcP.fill = GridBagConstraints.BOTH;
        panel.add(innerPanel, gbcP);

        gbcP.gridx = 2;
        gbcP.gridy = 0;
        gbcP.gridheight = 1;
        gbcP.gridwidth = 1;
        gbcP.weightx = 1;
        gbcP.anchor = GridBagConstraints.BASELINE;
        gbcP.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JColoredPanel(), gbcP);

        return panel;
    }
}
