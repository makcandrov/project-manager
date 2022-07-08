package ihm;

import javax.swing.*;

public class JCustomComboBox<T> extends JComboBox<T> {

	public JCustomComboBox(T[] items) {
		super(items);
		this.setUI(new CustomComboBoxUI());
	}
}
