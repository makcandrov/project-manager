package ihm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class JRoundedButton extends JButton {

	private int roundValue = 16;

	private Color defaultInnerColor = new Color(245, 245, 245);
	private Color hoverInnerColor = new Color(220, 220, 220);
	private Color enabledInnerColor = new Color(190, 190, 190);
	private Color currentInnerColor = defaultInnerColor;

	private Color defaultBorderColor = new Color(170, 170, 170);
	private Color hoverBorderColor = new Color(253, 253, 253);
	private Color enabledBorderColor = new Color(170, 170, 170);
	private Color currentBorderColor = defaultBorderColor;

	private String defaultText = null;
	private String hoverText = null;
	private String enabledText = null;

	private Icon defaultIcon = null;
	private Icon hoverIcon = null;
	private Icon enabledIcon = null;

	public void setColor(Color c) {
		this.setBackground(c);
	}

	public JRoundedButton() {
		super();
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
		this.setMinimumSize(this.getMinimumSize());

		this.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent evt) {
				statutHover();
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				statutDefault();
			}

			public void mousePressed(java.awt.event.MouseEvent evt) {
				statutEnabled();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override

			public void mouseReleased(MouseEvent e) {
			}

		});
	}

	public void setMultipleIcon(Icon defaultIcon, Icon hoverIcon, Icon enabledIcon) {
		this.setIcon(defaultIcon);
		this.defaultIcon = defaultIcon;
		this.hoverIcon = hoverIcon;
		this.enabledIcon = enabledIcon;
	}

	public void setMultipleText(String defaultText, String hoverText, String enabledText) {
		this.setText(defaultText);
		this.defaultText = defaultText;
		this.hoverText = hoverText;
		this.enabledText = enabledText;
	}

	public void setMultipleInnerColor(Color defaultInnerColor, Color hoverInnerColor, Color enabledInnerColor) {
		this.defaultInnerColor = defaultInnerColor;
		this.hoverInnerColor = hoverInnerColor;
		this.enabledInnerColor = enabledInnerColor;
	}

	public void setMultipleBorderColor(Color defaultBorderColor, Color hoverBorderColor, Color enabledBorderColor) {
		this.defaultBorderColor = defaultBorderColor;
		this.hoverBorderColor = hoverBorderColor;
		this.enabledBorderColor = enabledBorderColor;
	}

	public void setRoundValue(int roundValue) {
		this.roundValue = roundValue;
	}

	public Color getDefaultInnerColor() {
		return this.defaultInnerColor;
	}

	public Color getHoverInnerColor() {
		return this.hoverInnerColor;
	}

	public Color getEnabledInnerColor() {
		return this.enabledInnerColor;
	}

	public Color getDefaultBorderColor() {
		return this.defaultBorderColor;
	}

	public Color getHoverBorderColor() {
		return this.hoverBorderColor;
	}

	public Color getEnabledBorderColor() {
		return this.enabledBorderColor;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(this.currentInnerColor);
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, roundValue, roundValue);
		g2.setPaint(this.currentBorderColor);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, roundValue, roundValue);
		g2.dispose();
		super.paintComponent(g);
	}

	public void statutDefault() {
		this.currentInnerColor = this.defaultInnerColor;
		this.currentBorderColor = this.defaultBorderColor;

		if (this.defaultIcon != null) {
			this.setIcon(defaultIcon);
		}

		if (this.defaultText != null) {
			this.setText(defaultText);
		}
	}

	public void statutHover() {
		this.currentInnerColor = this.hoverInnerColor;
		this.currentBorderColor = this.hoverBorderColor;

		if (this.hoverIcon != null) {
			this.setIcon(hoverIcon);
		}

		if (this.hoverText != null) {
			this.setText(hoverText);
		}
	}

	public void statutEnabled() {
		this.currentInnerColor = this.enabledInnerColor;
		this.currentBorderColor = this.enabledBorderColor;

		if (this.enabledIcon != null) {
			this.setIcon(enabledIcon);
		}

		if (this.enabledText != null) {
			this.setText(enabledText);
		}
	}

}
