package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextField;
import javax.swing.BorderFactory;

public class JCustomTextField extends JTextField {

	private Color defaultBorderColor = new Color(170, 170, 170);
	private Color currentBorderColor = defaultBorderColor;

	public JCustomTextField() {
		super();
		this.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		this.setFont(new Font("family:Optima", Font.PLAIN, 12));
	}

	public void setCurrentBorderColor(Color color) {
		this.currentBorderColor = color;
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(currentBorderColor);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
		g2.dispose();
		super.paintBorder(g);
	}

}
