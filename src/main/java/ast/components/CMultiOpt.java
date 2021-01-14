package ast.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
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
	private String name;
	private String title;
	private List<String> options;
	private List<String> defValues;
	private List<Constraint> constraints;
	
	public CMultiOpt(String name, String title, List<String> options, List<String> defValues,
			List<Constraint> constraints) {
		this.name = name;
		this.title = title;
		this.options = options;
		this.defValues = defValues;
		this.constraints = constraints;
	}
	
	public CMultiOpt(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		options = initOpts(ctx);
		constraints = extractConstraints(ctx);
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
//		title.setText(getTitle());
		
		// TODO make the ctx implement that
//		List<String> defaultValues= Stream.of(getSelectedOpt(mapConstraints).split("\\|"))
//			     .map(String::trim)
//			     .collect(Collectors.toList());
		return switch (style) {
			case InlineCheckbox -> setMultiInlineCheckboxStyle(name, title, options, defValues, mapConstraints);
			case BlockCheckbox -> setMultiBlockCheckboxStyle(name, title, options, defValues, mapConstraints);
			case InlineList -> setMultiInlineListStyle(name, title, options, defValues, mapConstraints);
			case BlockList -> setMultiBlockListStyle(name, title, options, defValues, mapConstraints);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + style);
		};
	}
	
//	private String getSelectedOpt(Map<ConstraintId, Constraint> constraints) {
//		Constraint.SelectedCon selected = (Constraint.SelectedCon)constraints.get(ConstraintId.SELECTED);
//		return selected == null? "" : selected.value();
//	}
	
	private JPanelWithValue setMultiBlockListStyle(String name, JLabel title,
			List<String> options, List<String> defaultValues,
			Map<ConstraintId, Constraint> constraints) {
		MultiOptItem[] items = new MultiOptItem[options.size()];
		for(int i=0; i<options.size(); i++) {
			items[i] = new MultiOptItem(options.get(i), defaultValues.contains(options.get(i)));
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
			public void setValueOrDefault(String values) {
				if("".equals(values)) {
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(defaultValues.contains(options.get(i)));
						setValue(values);
					}
					ccb.updateUI();
				} else {
//					List<String> values = new ArrayList<>(Arrays.asList(values.split(Pattern.quote("|"))));
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(values.contains(options.get(i)));
						setValue(values);
					}
					ccb.updateUI();
				}
			}
		};
//		defaultValues.stream().collect(Collectors.)
		String defaultValuesAsString = defaultValues.toString();
		panel.setValueOrDefault(defaultValuesAsString.substring(1, defaultValuesAsString.length()-1));
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

	private JPanelWithValue setMultiInlineListStyle(String name, JLabel title, List<String> options,
			List<String> defaultValues, Map<ConstraintId, Constraint> constraints) {
		MultiOptItem[] items = new MultiOptItem[options.size()];
		for(int i=0; i<options.size(); i++) {
			items[i] = new MultiOptItem(options.get(i), defaultValues.contains(options.get(i)));
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
			public void setValueOrDefault(String values) {
				if("".equals(values)) {
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(defaultValues.contains(options.get(i)));
						setValue(values);
					}
					ccb.updateUI();
				} else {
//					List<String> values = new ArrayList<>(Arrays.asList(values.split(Pattern.quote("|"))));
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(values.contains(options.get(i)));
						setValue(values);
					}
					ccb.updateUI();
				}
			}
		};
		String defaultValuesAsString = defaultValues.toString();
		panel.setValueOrDefault(defaultValuesAsString.substring(1, defaultValuesAsString.length()-1));
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

	private JPanelWithValue setMultiBlockCheckboxStyle(String name, JLabel title,
			List<String> options, List<String> defaultValues, Map<ConstraintId, Constraint> constraints) {
		List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = checkBoxes.stream()
						.filter(c -> c.isSelected())
						.map(c -> c.getText())
						.collect(Collectors.toList()).toString();
				// Remove the '[' and ']' characters of the list
				selectedItems = selectedItems.substring(1, selectedItems.length()-1);
				setValue(selectedItems);
				return setErrorLabel(validateConstraints(Id.MultiOpt, selectedItems, constraints));
			}

			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value)) {
					setCheckBoxValues(checkBoxes, defaultValues);
				}else {
					List<String> values = new ArrayList<String>(Arrays.asList(value.split(",")));
					setCheckBoxValues(checkBoxes, values);
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
			JCheckBox checkBox = new JCheckBox(option);
			checkBoxes.add(checkBox);
			if(defaultValues.contains(option))
				checkBox.setSelected(true);
			gbc.gridy++;
			panel.add(checkBox, gbc);
		});
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setMultiInlineCheckboxStyle(String name, JLabel title,
			List<String> options, List<String> defaultValues, Map<ConstraintId, Constraint> constraints) {
		List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = checkBoxes.stream()
				.filter(c -> c.isSelected())
				.map(c -> c.getText())
				.collect(Collectors.toList()).toString();
				// Remove the '[' and ']' characters of the list
				selectedItems = selectedItems.substring(1, selectedItems.length()-1);
				setValue(selectedItems);
				return setErrorLabel(validateConstraints(Id.MultiOpt, selectedItems, constraints));
			}
			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value)) {
					setCheckBoxValues(checkBoxes, defaultValues);
//					for(JCheckBox checkBox: checkBoxes ) {
//						if(defaultValues.contains(checkBox.getText()))
//							checkBox.setSelected(true);
//						else
//							checkBox.setSelected(false);
//					}
				}else {
					List<String> values = new ArrayList<String>(Arrays.asList(value.split(",")));
					setCheckBoxValues(checkBoxes, values);
//					for(JCheckBox checkBox: checkBoxes ) {
//						if(values.contains(checkBox.getText()))
//							checkBox.setSelected(true);
//						else
//							checkBox.setSelected(false);
//					}	
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
			JCheckBox checkBox = new JCheckBox(option);
			checkBoxes.add(checkBox);
			if(defaultValues.contains(option))
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
