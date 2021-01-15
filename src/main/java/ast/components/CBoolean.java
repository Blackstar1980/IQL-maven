package ast.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JRadioButton;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.StyleId;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CBoolean implements Component {
	private final String name;
	private final String title;
	private final String defVal;
	private final List<Constraint> constraints;

	public CBoolean(String name, String title, String defVal, List<Constraint> constraints) {
		this.name = name;
		this.title = title;
		this.defVal = defVal;
		this.constraints = constraints;
	}

	public CBoolean(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		defVal = initDefaultVal(ctx);
		constraints = extractConstraints(ctx);
	}

	private String initDefaultVal(ComponentContext ctx) {
		String value = extractCompDefVal(ctx);
		if ("".equals(value))
			return "";
		if ("true".equals(value) || "false".equals(value))
			return value;
		else
			throw new IllegalArgumentException("Boolean default value must be only 'true' or 'false'");
	}

	public JPanelWithValue make() {
		JRadioButton yesButton = new JRadioButton("Yes");
		JRadioButton noButton = new JRadioButton("No");
		Map<ConstraintId, Constraint> constraintMap = getMapConstraint(constraints);
		JPanelWithValue panel = new JPanelWithValue(Id.Boolean, name) {
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.Boolean, getValue(), constraintMap));
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if (setDefault)
					value = defVal;
				if ("".equals(value)) {
					yesButton.setSelected(false);
					noButton.setSelected(false);
					setValue("");
				}
				if ("true".equals(value)) {
					yesButton.setSelected(true);
					noButton.setSelected(false);
					setValue("true");
				}
				if ("false".equals(value)) {
					noButton.setSelected(true);
					yesButton.setSelected(false);
					setValue("false");
				}
			}
		};
		panel.setValueOrDefault("", true);
		JLabel jTitle = generateTitle(title, constraintMap);

		StyleId style = (StyleId) constraintMap.get(ConstraintId.STYLE);
		yesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				noButton.setSelected(false);
				yesButton.setSelected(yesButton.isSelected());
				if (yesButton.isSelected())
					panel.setValue("true");
				else
					panel.setValue("");
			}
		});
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yesButton.setSelected(false);
				noButton.setSelected(noButton.isSelected());
				if (noButton.isSelected())
					panel.setValue("false");
				else
					panel.setValue("");
			}
		});
		return setBooleanLayout(style, jTitle, yesButton, noButton, panel);
	}

	private JPanelWithValue setBooleanLayout(StyleId style, JLabel title, JRadioButton yesButton, JRadioButton noButton,
			JPanelWithValue panel) {
		return switch (style) {
		case Block -> setBooleanBlockStyle(title, yesButton, noButton, panel);
		case Inline -> setBooleanInlineStyle(title, yesButton, noButton, panel);
		default -> throw new IllegalArgumentException("Unexpected value: " + style);
		};
	}

	private JPanelWithValue setBooleanInlineStyle(JLabel title, JRadioButton yesButton, JRadioButton noButton,
			JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		panel.add(yesButton, gbc);
		gbc.gridx = 2;
		panel.add(noButton, gbc);
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setBooleanBlockStyle(JLabel title, JRadioButton yesButton, JRadioButton noButton,
			JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(yesButton, gbc);
		gbc.gridy = 2;
		panel.add(noButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitBoolean(this);
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getDefVal() {
		return defVal;
	}

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}

	@Override
	public Id getType() {
		return Id.Boolean;
	}

	@Override
	public String toString() {
		return "Boolean [name=" + name + ", title=" + title + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}
}
