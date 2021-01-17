package ast.components;

import java.awt.Dimension;
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
import ast.constraints.DisplayId;
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
		DisplayId display = (DisplayId) mapConstraints.get(ConstraintId.DISPLAY);
		if(display == DisplayId.Non) {
			display = DisplayId.BlockRadio;
		}
		JLabel title = generateTitle(getTitle(), mapConstraints);
		return switch (display) {
			case InlineRadio -> setSingleInlineRadioDisplay(title, mapConstraints);
			case InlineList -> setSingleInlineListDisplay(title, mapConstraints);
			case BlockRadio -> setSingleBlockRadioDisplay(title, mapConstraints);
			case BlockList -> setSingleBlockListDisplay(title, mapConstraints);
			default -> setSingleBlockRadioDisplay(title, mapConstraints);
		};
	}

	private String getSelectedOpt(Map<ConstraintId, Constraint> constraints) {
		Constraint.SelectedCon selected = (Constraint.SelectedCon) constraints.get(ConstraintId.SELECTED);
		return selected == null ? "" : selected.value();
	}

	private JPanelWithValue setSingleBlockListDisplay(JLabel title, Map<ConstraintId, Constraint> constraints) {
		String[] optionsArray = toArrayStartWithEmptyValue(options);
		JComboBox<String> combo = new JComboBox<String>(optionsArray);
		combo.setPreferredSize(new Dimension(280, 22));
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name) {
			@Override
			public boolean checkForError() {
				if (combo.getSelectedItem() != null)
					setValue(combo.getSelectedItem().toString());
				String errorMsg = validateConstraints(Id.SingleOpt, getValue(), constraints);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(combo, errorMsg, true);
				return haveError;
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
				String errorMsg = validateConstraints(Id.SingleOpt, panel.getValue(), constraints);
				panel.setComponentErrorIndicator(combo, errorMsg, true);
				panel.setErrorLabel(errorMsg);
			}
		});
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(combo, gbc);
		gbc.gridy = 2;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}
	
	private JPanelWithValue setSingleInlineListDisplay(JLabel title, Map<ConstraintId, Constraint> constraints) {
		String[] optionsArray = toArrayStartWithEmptyValue(options);
		JComboBox<String> combo = new JComboBox<String>(optionsArray);
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name) {
			@Override
			public boolean checkForError() {
				if (combo.getSelectedItem() != null)
					setValue(combo.getSelectedItem().toString());
				String errorMsg = validateConstraints(Id.SingleOpt, getValue(), constraints);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(combo, errorMsg, true);
				return haveError;
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
				String errorMsg = validateConstraints(Id.SingleOpt, panel.getValue(), constraints);
				panel.setComponentErrorIndicator(combo, errorMsg, true);
				panel.setErrorLabel(errorMsg);
			}
		});

		combo.setPreferredSize(new Dimension(280, 22));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(combo, gbc);
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}
	
	private String[] toArrayStartWithEmptyValue(List<String> array) {
		String[] newArray = new String[array.size()+1];
		newArray[0] = "";
		for(int i=0; i<array.size(); i++) 
			newArray[i+1] = array.get(i);
		return newArray;
	}

	private JPanelWithValue setSingleInlineRadioDisplay(JLabel title, Map<ConstraintId, Constraint> constraints) {
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
			button.addActionListener(e-> {
				if(panel.getValue() == button.getText()) {
					panel.setValue("");
					button.setSelected(false);
					buttonGroup.clearSelection();
				} else {
					panel.setValue(button.getText());
					button.setSelected(true);
				}					
				panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
		});
			panel.setValue(defValue);
			buttonGroup.add(button);
			gbc.gridx++;
			panel.add(button, gbc);
		});
		return panel;
	}

	private JPanelWithValue setSingleBlockRadioDisplay(JLabel title, Map<ConstraintId, Constraint> constraints) {
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
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
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
			button.addActionListener(e-> {
					if(panel.getValue() == button.getText()) {
						panel.setValue("");
						button.setSelected(false);
						buttonGroup.clearSelection();
					} else {
						panel.setValue(button.getText());
						button.setSelected(true);
					}					
					panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
				
			});
			panel.setValue(defValue);
			buttonGroup.add(button);
			gbc.gridy++;
			panel.add(button, gbc);
		});
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		panel.setPreferredSize(new Dimension(280, (int) panel.getPreferredSize().getHeight()));
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

	public String getDefValue() {
		return defValue;
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
