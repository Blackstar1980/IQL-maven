package ast.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.StyleId;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CSingleOpt implements Component {
	private final String name;
	private final String title;
	private final List<String> options;
	private final String defValue;
	private final List<Constraint> constraints;

	public CSingleOpt(String name, String title, List<String> options, String defValue, List<Constraint> constraints) {
		this.name = name;
		this.title = title;
		this.options = options;
		this.defValue = defValue;
		this.constraints = constraints;
	}

	public CSingleOpt(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		options = initOpts(ctx);
		defValue = extractDefValue(ctx);
		constraints = extractConstraints(ctx);
	}

	private String extractDefValue(ComponentContext ctx) {
		return getSelectedOpt(getMapConstraint(constraints));
	}

	private List<String> initOpts(ComponentContext ctx) {
		String input = extractCompDefVal(ctx);
		if (input == null || input.isEmpty() || input.endsWith("|") || input.startsWith("|"))
			throw new IllegalArgumentException("'" + input + "' are not a valid options");
		List<String> values = Arrays.asList(input.trim().split("\\|"));
		for (String value : values) {
			if (value.isBlank())
				throw new IllegalArgumentException("Empty value is not a valid option");
		}
		return values;
	}

	public JPanelWithValue make() {
		if (options == null || options.size() == 0)
			throw new IllegalArgumentException("Need to have a least one option");
		Map<ConstraintId, Constraint> mapConstraints = getMapConstraint(constraints);
		StyleId style = (StyleId) mapConstraints.get(ConstraintId.STYLE);
		JLabel title = new JLabel(getTitle());
		return switch (style) {
			case InlineRadio -> setSingleInlineRadioStyle(title, mapConstraints);
			case InlineList -> setSingleInlineListStyle(title, mapConstraints);
			case BlockRadio -> setSingleBlockRadioStyle(title, mapConstraints);
			case BlockList -> setSingleBlockListStyle(title, mapConstraints);
			default -> setSingleBlockRadioStyle(title, mapConstraints);
		};
	}

	private String getSelectedOpt(Map<ConstraintId, Constraint> constraints) {
		Constraint.SelectedCon selected = (Constraint.SelectedCon) constraints.get(ConstraintId.SELECTED);
		return selected == null ? "" : selected.value();
	}

	private JPanelWithValue setSingleBlockListStyle(JLabel title, Map<ConstraintId, Constraint> constraints) {
		String[] optionsArray = options.toArray(String[]::new);
		JComboBox<String> combo = new JComboBox<String>(optionsArray);
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name) {
			@Override
			public boolean checkForError() {
				if (combo.getSelectedItem() != null)
					setValue(combo.getSelectedItem().toString());
				return setErrorLabel(validateConstraints(Id.SingleOpt, getValue(), constraints));
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					combo.setSelectedItem(defValue);
					setValue(defValue);
				} else if ("".equals(value)) {
					combo.setSelectedIndex(-1);
					setValue(value);
					} else {
						combo.setSelectedItem(value);
						setValue(value);
					}
			}
		};
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(combo.getSelectedItem() == null)
					panel.setValue("");
				else
					panel.setValue(combo.getSelectedItem().toString());
				panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
			}
		});
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(combo, gbc);
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setSingleInlineListStyle(JLabel title, Map<ConstraintId, Constraint> constraints) {
		String[] optionsArray = options.toArray(String[]::new);
		JComboBox<String> combo = new JComboBox<String>(optionsArray);
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name) {
			@Override
			public boolean checkForError() {
				if (combo.getSelectedItem() != null)
					setValue(combo.getSelectedItem().toString());
				return setErrorLabel(validateConstraints(Id.SingleOpt, getValue(), constraints));
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					combo.setSelectedItem(defValue);
					setValue(defValue);
				} else if ("".equals(value)) {
					combo.setSelectedIndex(-1);
					setValue(value);
					} else {
						combo.setSelectedItem(value);
						setValue(value);
					}
			}
		};
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(combo.getSelectedItem() == null)
					panel.setValue("");
				else
					panel.setValue(combo.getSelectedItem().toString());
				panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
			}
		});

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(combo, gbc);
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setSingleInlineRadioStyle(JLabel title, Map<ConstraintId, Constraint> constraints) {
		ButtonGroup buttonGroup = new ButtonGroup();
		Map<String, JRadioButton> buttons = new HashMap<>();
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name) {
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.SingleOpt, getValue(), constraints));
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					setValue(defValue);
					buttons.get(defValue).setSelected(true);
				} else if ("".equals(value)) {
						buttonGroup.clearSelection();
						setValue("");
					} else {
						setValue(value);
						buttons.get(value).setSelected(true);
					}
			}
		};
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		options.forEach(option -> {
			JRadioButton button = new JRadioButton(option);
			buttons.put(option, button);
			if (option.equals(defValue)) {
				button.setSelected(true);
				panel.setValue(option);
			}
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(button.isSelected());
					panel.setValue(button.getText());
					panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
				}
			});
			panel.setValue(defValue);
			buttonGroup.add(button);
			gbc.gridx++;
			panel.add(button, gbc);
		});
		return panel;
	}

	private JPanelWithValue setSingleBlockRadioStyle(JLabel title, Map<ConstraintId, Constraint> constraints) {
		ButtonGroup buttonGroup = new ButtonGroup();
		Map<String, JRadioButton> buttons = new HashMap<>();
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name) {
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.SingleOpt, getValue(), constraints));
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					setValue(defValue);
					buttons.get(defValue).setSelected(true);
				} else if ("".equals(value)) {
						buttonGroup.clearSelection();
						setValue("");
					} else {
						setValue(value);
						buttons.get(value).setSelected(true);
					}
			}
		};
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		options.forEach(option -> {
			JRadioButton button = new JRadioButton(option);
			buttons.put(option, button);
			if (option.equals(defValue)) {
				button.setSelected(true);
				panel.setValue(option);
			}
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					panel.setValue(button.getText());
					panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
				}
			});
			panel.setValue(defValue);
			buttonGroup.add(button);
			gbc.gridy++;
			panel.add(button, gbc);
		});
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getOptions() {
		return Collections.unmodifiableList(options);
	}

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}

	@Override
	public Id getType() {
		return Id.SingleOpt;
	}

	@Override
	public String toString() {
		return "SingleOpt [name=" + name + ", title=" + title + ", options=" + options + ", constraints=" + constraints
				+ "]";
	}

	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitSingleOpt(this);
	}
}
