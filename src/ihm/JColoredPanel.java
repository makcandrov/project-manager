package ihm;

import java.awt.Color;

import javax.swing.JPanel;

public class JColoredPanel extends JPanel {

	public static final Color DEFAULT_COLOR = new Color(251, 251, 251);

	public JColoredPanel(Color color) {
		super();
		this.setBackground(color);
	}

	public JColoredPanel() {
		this(DEFAULT_COLOR);
	}
}
