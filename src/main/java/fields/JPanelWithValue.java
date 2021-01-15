package fields;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import ast.Id;

public abstract class JPanelWithValue extends JPanelContainer {
	private boolean hasError = false;
	private String value = "";
	private String name = null;
	private JLabel errorLabel;
	
	public abstract void setValueOrDefault(String value, boolean setDefault);
	
	public JPanelWithValue(Id id) {
		super(id);
		errorLabel = generateErrorLabel(" ");
	}

	public JPanelWithValue(Id id, String name) {
		this(id);
		this.name = name;
	}
	
	public void setValue(String value) {
		hasError = false;
		this.value = value;
	}
	public String getValue() {
		return hasError? null:value;
	}
	
	public JLabel getErrorLabel() {
		return errorLabel;
	}

	public boolean setErrorLabel(String errorMsg) {
		errorLabel.setText(errorMsg);
		hasError = !" ".equals(errorMsg);
		return hasError;
	}

	private JLabel generateErrorLabel(String msg) {
		JLabel label = new JLabel();
		Font font = label.getFont();
		label.setFont(font.deriveFont(6));
		label.setForeground(Color.red);
		label.setText(msg);
		return label;
	}

	public String getName() {
		return name;
	}
	
}
