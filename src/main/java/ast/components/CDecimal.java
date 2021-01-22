package ast.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;
import fields.PlaceholderDecimalField;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CDecimal implements Component, Placeholder, BasicLayout {
	private final String name;
	private final String title;
	private final Double defVal;
	private final List<Constraint> constraints;
	
	public CDecimal(String name, String title, Double defVal, List<Constraint> constraints) {
		this.name = name;
		this.title = title;
		this.defVal = defVal;
		this.constraints = constraints;
	}
	
	public CDecimal(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		defVal = initDefVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	private Double initDefVal(ComponentContext ctx) {
		String value = extractCompDefVal(ctx);
		try {
			return value.isEmpty()? null: Double.valueOf(value);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(value + " is not a valid decimal");
		}
	}
	
	public JPanelWithValue make() {
		PlaceholderDecimalField textField = new PlaceholderDecimalField();
		Map<ConstraintId, Constraint> constraintMap = getMapConstraint(constraints);
		JPanelWithValue panel = new JPanelWithValue(Id.Decimal, name){
			@Override
			public boolean checkForError() {
				String errorMsg = validateConstraints(Id.Decimal, textField.getText(), constraintMap);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(textField, errorMsg, true);
				return haveError;
			}
			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					textField.setText(String.valueOf(defVal));
					setValue(String.valueOf(defVal));
				} else {
					try {
						textField.setText(value);
						setValue(String.valueOf(value));
					} catch (NumberFormatException e) {
						throw new NumberFormatException(value + " is not a valid decimal");
					}
				}
			}
		};
		panel.setValueOrDefault("", true);
		JLabel jTitle = generateTitle(title, constraintMap);
		JLabel errorMsg = panel.getErrorLabel();
		DisplayId display = (DisplayId)constraintMap.get(ConstraintId.DISPLAY);
		if(display == DisplayId.Non) {
			display = DisplayId.Block;
		}
		setPlaceHolder(textField, constraintMap);
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				String errorMsg = validateConstraints(Id.Decimal, textField.getText(), constraintMap);
				setPlaceHolder(textField, constraintMap);
				boolean haveError = panel.setErrorLabel(errorMsg);
				panel.setComponentErrorIndicator(textField, errorMsg, true);
				if(!haveError)
					panel.setValue(textField.getText());
			}
			@Override
			public void focusGained(FocusEvent e) {}
		});
		textField.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String error = validateConstraints(Id.Decimal, textField.getText(), constraintMap);
				 panel.setComponentErrorIndicator(textField, error, false);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(textField.getText());
				 }
			 }
		});
		return setLayout(display, jTitle, textField, errorMsg, panel);
	}
	
	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public Double getDefVal() {
		return defVal;
	}

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}
	
	@Override
	public Id getType() { return Id.Decimal; }

	@Override
	public String toString() {
		return "Decimal [name=" + name + ", title=" + title + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}

	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitDecimal(this);
	}}
