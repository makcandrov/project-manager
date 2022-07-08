package ihm;

import javax.swing.JTabbedPane;

public class JCustomTabbedPane extends JTabbedPane {
	public JCustomTabbedPane() {
		this.setUI(new CustomTabbedPaneUI());
	}
}
