package ihm;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class CustomTabbedPaneUI extends BasicTabbedPaneUI {

	@Override
	protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
	}

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
		g.setColor(new Color(170, 170, 170));
		g.drawLine(x + 8, y, x + w - 8, y);
		g.drawLine(x, y + 8, x, y + h);
		g.drawLine(x + w, y + 8, x + w, y + h);
		g.drawArc(x, y, 16, 16, 90, 90);
		g.drawArc(x + w - 16, y, 16, 16, 90, -90);
	}

	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
		if (isSelected) {
			g.setColor(new Color(190, 190, 190));
		} else {
			g.setColor(new Color(245, 245, 245));
		}
		g.fillRect(x, y + 8, w, h);
		g.fillRoundRect(x, y, w, h, 16, 16);
	}

	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
		int vHeight = fontHeight;
		return vHeight + 10;
	}

	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + metrics.getHeight();
	}

	protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
		return 0;
	}

	protected Insets getContentBorderInsets(int tabPlacement) {
		return new Insets(0, 0, 0, 0);
	}

	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
			Rectangle iconRect, Rectangle textRect, boolean isSelected) {
	}

	protected void installDefaults() {
		super.installDefaults();
		tabAreaInsets.left = 0;
		selectedTabPadInsets = new Insets(0, 0, 0, 0);
		tabInsets = selectedTabPadInsets;
	}
}
