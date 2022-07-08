package ihm;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class ConfirmationDialog extends JDialog implements ActionListener {

	public int res = -1;

	public ConfirmationDialog(JFrame frame, String title, boolean modal) {
		super(frame, title, modal);
		this.setLocationRelativeTo(frame);
	}

	public void actionPerformed(ActionEvent evt) {
		String btnName = ((Component) evt.getSource()).getName();

		if (btnName.equals("confirmationPanel/valider")) {
			this.res = 1;
			this.dispose();
		} else if (btnName.equals("confirmationPanel/annuler")) {
			this.res = 0;
			this.dispose();
		}
	}

}
