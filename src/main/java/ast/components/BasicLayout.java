package ast.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import ast.constraints.StyleId;
import fields.JPanelWithValue;

public interface BasicLayout {
	default JPanelWithValue setLayout(StyleId style, JLabel title, JComponent textField,
			JLabel errorMsg, JPanelWithValue panel) {
		return switch (style) {
			case Block -> setBlockStyle(title, textField, errorMsg, panel);
			case Inline -> setInlineStyle(title, textField, errorMsg, panel);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + style);
		};
	}
	
	private JPanelWithValue setInlineStyle(JLabel title, JComponent textField,
			JLabel errorMsg, JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		if(!(textField instanceof JScrollPane))
			textField.setPreferredSize(new Dimension(280, 30));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(textField, gbc);
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(errorMsg, gbc);
		return panel;
	}

	private JPanelWithValue setBlockStyle(JLabel title, JComponent textField, JLabel errorMsg, JPanelWithValue panel) {
		if(!(textField instanceof JScrollPane))
			textField.setPreferredSize(new Dimension(280, 30));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(textField, gbc);
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(errorMsg, gbc);
		return panel;
	}
}
