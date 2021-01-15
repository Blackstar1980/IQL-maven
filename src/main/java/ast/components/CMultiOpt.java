package ast.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.StyleId;
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
		this.options = initOpts(ctx);
		this.constraints = extractConstraints(ctx);
		this.defValues = setDefaultValues(constraints);
	}
	
	private List<String> setDefaultValues(List<Constraint> constraints) {
		String selected =  constraints.stream()
			.filter(con -> ConstraintId.SELECTED == con.getID())
			.map(c -> ((Constraint.SelectedCon)c).value())
			.findFirst()
			.get();
		return Stream.of(selected.split("\\|"))
			.map(String::trim)
		    .collect(Collectors.toList());
	}
	
	private List<String> initOpts(ComponentContext ctx) {
		String input = extractCompDefVal(ctx);
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
		StyleId style = (StyleId)mapConstraints.get(ConstraintId.STYLE);
		if(style == null ||  StyleId.Block == style) {
			style = StyleId.BlockList;
		}
		JLabel title = new JLabel(getTitle());

		return switch (style) {
			case InlineCheckbox -> setMultiInlineCheckboxStyle(title, mapConstraints);
			case BlockCheckbox -> setMultiBlockCheckboxStyle(title, mapConstraints);
			case InlineList -> setMultiInlineListStyle(title, mapConstraints);
			case BlockList -> setMultiBlockListStyle(title, mapConstraints);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + style);
		};
	}
	
	private JPanelWithValue setMultiBlockListStyle(JLabel title, Map<ConstraintId, Constraint> constraints) {
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
				return setErrorLabel(validateConstraints(Id.MultiOpt, selectedItems, constraints));
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
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		MultiOptComboBox comboBox = new MultiOptComboBox();
		comboBox.setPreferredSize(new Dimension(120, 25));
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
		return panel;
	}

	private JPanelWithValue setMultiInlineListStyle(JLabel title, Map<ConstraintId, Constraint> constraints) {
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
				return setErrorLabel(validateConstraints(Id.MultiOpt, selectedItems, constraints));
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
					
//				if("".equals(values)) {
//					for(int i=0; i<options.size(); i++) {
//						items[i].setSelected(defValues.contains(options.get(i)));
//						setValue(removeParentheses(defValues.toString()));
//					}
//					ccb.updateUI();
//				} 
				else {
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(values.contains(options.get(i)));
						setValue(values);
					}
					ccb.updateUI();
				}
			}
		};
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		MultiOptComboBox comboBox = new MultiOptComboBox();
		comboBox.setPreferredSize(new Dimension(120, 25));
		comboBox.setEditable(true);
		comboBox.addItems(options);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(ccb, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setMultiBlockCheckboxStyle(JLabel title, Map<ConstraintId, Constraint> constraints) {
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
				}
//				if("".equals(value)) {
//					setCheckBoxValues(checkBoxes, defValues);
//					setValue(removeParentheses(defValues.toString()));
//				}
				else {
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
		gbc.insets = new Insets(0, 20, 0, 0);
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
		return panel;
	}

	private JPanelWithValue setMultiInlineCheckboxStyle(JLabel title, Map<ConstraintId, Constraint> constraints) {
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
				}
//				if("".equals(value)) {
//					setCheckBoxValues(checkBoxes, defValues);
//					setValue(removeParentheses(defValues.toString()));
//				}
				else {
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
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		options.forEach(option -> {
			JCheckBox checkBox = new JCheckBox(option);
			checkBoxes.add(checkBox);
			if(defValues.contains(option))
				checkBox.setSelected(true);
			gbc.gridx++;
			panel.add(checkBox, gbc);
		});
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

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}
	
	@Override
	public Id getType() { return Id.MultiOpt; }

	@Override
	public String toString() {
		return "MultiOpt [name=" + name + ", title=" + title + ", options=" + options + ", constraints=" + constraints
				+ "]";
	}

	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitMultiOpt(this);
	}
}
