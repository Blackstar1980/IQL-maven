package ast.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.DisplayId;
import fields.CheckedComboBox;
import fields.JPanelWithValue;
import fields.MultiOptComboBox;
import fields.MultiOptItem;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CMultiOpt implements Component {
	private final String name;
	private final String title;
	private final List<String> options;
	private final List<String> defValues;
	private final List<Constraint> constraints;
	
	public CMultiOpt(String name, String title, List<String> options, List<String> defValues,
			List<Constraint> constraints) {
		this.name = name;
		this.title = title;
		this.options = options;
		this.defValues = defValues;
		this.constraints = constraints;
	}
	
	public CMultiOpt(ComponentContext ctx) {
		this.name = extractCompName(ctx);
		this.title = extractCompTitle(ctx);
		this.options = setOptions(ctx);
		this.defValues = setDefaultValues(ctx);
		this.constraints = extractConstraints(ctx);
	}
	
	private List<String> setOptions(ComponentContext ctx) {
		String idText = ctx.CompId().getText();
		String options = idText.substring(idText.indexOf("['")+2, idText.indexOf("']")).trim();
		return extractOptions(options);
	}
	
	private List<String> setDefaultValues(ComponentContext ctx) {
		String input = extractCompDefVal(ctx);
		return "".equals(input)? List.of():extractOptions(input);
	}

	private List<String> extractOptions(String input) {
		if(input == null || input.isEmpty() || input.endsWith("|") || input.startsWith("|"))
			throw new IllegalArgumentException("'"+input + "' are not a valid options");
		List<String> values= Stream.of(input.split("\\|"))
			     .map(String::trim)
			     .collect(Collectors.toList());
		for(String value : values) {
			if(value.isBlank())
				throw new IllegalArgumentException("Empty value is not a valid option");
		}
		return values;
	}
	
	public JPanelWithValue make() {
		if(options == null || options.size() == 0)
			throw new IllegalArgumentException("Need to have a least one option");
		Map<ConstraintId, Constraint> mapConstraints = getMapConstraint(constraints);
		DisplayId display = (DisplayId)mapConstraints.get(ConstraintId.DISPLAY);
		if(display == DisplayId.Non) {
			display = DisplayId.BlockCheckbox;
		}
		JLabel jTitle = generateTitle(title, mapConstraints);

		return switch (display) {
			case InlineCheckbox -> setMultiInlineCheckboxDisplay(jTitle, mapConstraints);
			case BlockCheckbox -> setMultiBlockCheckboxDisplay(jTitle, mapConstraints);
			case InlineList -> setMultiInlineListDisplay(jTitle, mapConstraints);
			case BlockList -> setMultiBlockListDisplay(jTitle, mapConstraints);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + display);
		};
	}
	
	private JPanelWithValue setMultiBlockListDisplay(JLabel title, Map<ConstraintId, Constraint> constraints) {
		MultiOptItem[] items = new MultiOptItem[options.size()];
		for(int i=0; i<options.size(); i++) {
			items[i] = new MultiOptItem(options.get(i), defValues.contains(options.get(i)));
		}
		DefaultComboBoxModel<MultiOptItem> model = new DefaultComboBoxModel<>(items);
		CheckedComboBox<MultiOptItem> ccb = new CheckedComboBox<>(model);
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = ccb.getSelectedItems();
				setValue(selectedItems);		
				String errorMsg = validateConstraints(Id.MultiOpt, selectedItems, constraints);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(ccb, errorMsg, true);
				return haveError;
			}

			@Override
			public void setValueOrDefault(String values, boolean setDefault) {
				if(setDefault) {
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(defValues.contains(options.get(i)));
						setValue(removeParentheses(defValues.toString()));
					}
					ccb.updateUI();
				}
				else {
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(values.contains(options.get(i)));
						setValue(values);
					}
					ccb.updateUI();
				}
			}
		};
		
		ccb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ccb.getSelectedItem().toString() == ccb.getSelectedItems())
					panel.setValue("");
				else
					panel.setValue(ccb.getSelectedItem().toString());			
				String errorMsg = validateConstraints(Id.MultiOpt, panel.getValue(), constraints);
				panel.setComponentErrorIndicator(ccb, errorMsg, true);
				panel.setErrorLabel(errorMsg);
			}
		});
		ccb.setBackground(Color.white);
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		MultiOptComboBox comboBox = new MultiOptComboBox();
		comboBox.setPreferredSize(new Dimension(280, 22));
		comboBox.setEditable(true);
		comboBox.addItems(options);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(ccb, gbc);
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		panel.setPreferredSize(new Dimension(280, (int) panel.getPreferredSize().getHeight()));
		return panel;
	}

	private JPanelWithValue setMultiInlineListDisplay(JLabel title, Map<ConstraintId, Constraint> constraints) {
		MultiOptItem[] items = new MultiOptItem[options.size()];
		for(int i=0; i<options.size(); i++) {
			items[i] = new MultiOptItem(options.get(i), defValues.contains(options.get(i)));
		}
		DefaultComboBoxModel<MultiOptItem> model = new DefaultComboBoxModel<>(items);
		CheckedComboBox<MultiOptItem> ccb = new CheckedComboBox<>(model);
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = ccb.getSelectedItems();
				setValue(selectedItems);		
				String errorMsg = validateConstraints(Id.MultiOpt, selectedItems, constraints);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(ccb, errorMsg, true);
				return haveError;
			}

			@Override
			public void setValueOrDefault(String values, boolean setDefault) {
				if(setDefault) {
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(defValues.contains(options.get(i)));
						setValue(removeParentheses(defValues.toString()));
					}
					ccb.updateUI();
				} else {
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(values.contains(options.get(i)));
						setValue(values);
					}
					ccb.updateUI();
				}
			}
		};
		
		ccb.setBackground(Color.white);
		ccb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ccb.getSelectedItem().toString() == ccb.getSelectedItems())
					panel.setValue("");
				else
					panel.setValue(ccb.getSelectedItem().toString());			
				String errorMsg = validateConstraints(Id.MultiOpt, panel.getValue(), constraints);
				panel.setComponentErrorIndicator(ccb, errorMsg, true);
				panel.setErrorLabel(errorMsg);
			}
		});
		
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		JPanel comboBoxPanel = new JPanel(new GridBagLayout());
		comboBoxPanel.setPreferredSize(new Dimension(280, 22));
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		comboBoxPanel.add(ccb, gbc);
		MultiOptComboBox comboBox = new MultiOptComboBox();
		comboBox.setEditable(true);
		comboBox.addItems(options);
		gbc.gridx = 0;
		gbc.gridy = 0;
//		gbc.insets = new Insets(0, 0, 0, 10);
		panel.add(title, gbc);
//		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		panel.add(comboBoxPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setMultiBlockCheckboxDisplay(JLabel title, Map<ConstraintId, Constraint> constraints) {
		List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = checkBoxes.stream()
						.filter(c -> c.isSelected())
						.map(c -> c.getText())
						.collect(Collectors.toList()).toString();
				setValue(removeParentheses(selectedItems));
				return setErrorLabel(validateConstraints(Id.MultiOpt, removeParentheses(selectedItems), constraints));
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					setCheckBoxValues(checkBoxes, defValues);
					setValue(removeParentheses(defValues.toString()));
				} else {
					List<String> values = Arrays.stream(value.split(",")).map(String::trim).collect(Collectors.toList());
					setCheckBoxValues(checkBoxes, values);
					setValue(value);
				}
			}
		};
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
//		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		options.forEach(option -> {
			JCheckBox checkBox = new JCheckBox(option);
			checkBoxes.add(checkBox);
			if(defValues.contains(option))
				checkBox.setSelected(true);
			gbc.gridy++;
			panel.add(checkBox, gbc);
		});
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		panel.setPreferredSize(new Dimension(280, (int) panel.getPreferredSize().getHeight()));
		return panel;
	}

	private JPanelWithValue setMultiInlineCheckboxDisplay(JLabel title, Map<ConstraintId, Constraint> constraints) {
		List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = checkBoxes.stream()
						.filter(c -> c.isSelected())
						.map(c -> c.getText())
						.collect(Collectors.toList()).toString();
				setValue(removeParentheses(selectedItems));
				return setErrorLabel(validateConstraints(Id.MultiOpt, selectedItems, constraints));
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					setCheckBoxValues(checkBoxes, defValues);
					setValue(removeParentheses(defValues.toString()));
				} else {
					List<String> values = Arrays.stream(value.split(",")).map(String::trim).collect(Collectors.toList());
					setCheckBoxValues(checkBoxes, values);
					setValue(value);
				}
			}
		};
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.anchor = GridBagConstraints.EAST;
		JPanel checkBoxesPanel = new JPanel(new GridBagLayout());
		options.forEach(option -> {
			JCheckBox checkBox = new JCheckBox(option);
			checkBoxes.add(checkBox);
			if(defValues.contains(option))
				checkBox.setSelected(true);
			checkBoxesPanel.add(checkBox, gbc);
			gbc.gridx++;
		});
		checkBoxesPanel.setPreferredSize(new Dimension(280, (int) panel.getPreferredSize().getHeight()));
		gbc.gridx = 1;
		panel.add(checkBoxesPanel, gbc);
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);		
		return panel;
	}
	
	private void setCheckBoxValues(List<JCheckBox> checkBoxes , List<String> values) {
		for(JCheckBox checkBox: checkBoxes ) {
			if(values.contains(checkBox.getText()))
				checkBox.setSelected(true);
			else
				checkBox.setSelected(false);
		}
	}
	
	private String removeParentheses(String text) {
		return text.substring(1, text.length()-1);
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

	public List<String> getDefValues() {
		return defValues;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}
	
	@Override
	public Id getType() { return Id.MultiOpt; }

//	@Override
//	public String toString() {
//		return "MultiOpt [name=" + name + ", title=" + title + ", options=" + options + ", constraints=" + constraints
//				+ "]";
//	}

	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitMultiOpt(this);
	}

	@Override
	public String toString() {
		return "MultiOpt [name=" + name + ", title=" + title + ", options=" + options + ", defValues=" + defValues
				+ ", constraints=" + constraints + "]";
	}
	
}
