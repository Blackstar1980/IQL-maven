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
import ast.constraints.StyleId;
import ast.constraints.Constraint.HolderCon;
import fields.JPanelWithValue;
import fields.PlaceholderTextField;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CString implements Component, Placeholder, BasicLayout {
	private final String name;
	private final String title;
	private final String defVal;
	private final List<Constraint> constraints;
	
	public CString(String name, String title, String defVal, List<Constraint> constraints) {
		this.name = name;
		this.title = title;
		this.defVal = defVal;
		this.constraints = constraints;
	}
	
	public CString(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		defVal = extractCompDefVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	public JPanelWithValue make() {
		PlaceholderTextField textField = new PlaceholderTextField();
		Map<ConstraintId, Constraint> constraintMap = getMapConstraint(constraints);
		JPanelWithValue panel = new JPanelWithValue(Id.String, name) {
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.String, textField.getText(), constraintMap));
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					textField.setText(defVal == null? "": defVal);
					setValue(defVal);
				} else {
					textField.setText(value == null? "": value);
					setValue(value);
				}
			}
		};
		JLabel jTitle = generateTitle(title, constraintMap);
		JLabel errorMsg = panel.getErrorLabel();
		panel.setValueOrDefault("", true);
		StyleId style = (StyleId)constraintMap.get(ConstraintId.STYLE);
		if(textField.getText().isEmpty() && constraintMap.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraintMap.get(ConstraintId.HOLDER)).value());
		}
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setPlaceHolder(textField, constraintMap);
				boolean hasError = panel.setErrorLabel(validateConstraints(Id.String, textField.getText(), constraintMap));
				if(!hasError)
					panel.setValue(textField.getText());
			}	
			@Override
			public void focusGained(FocusEvent e) {}
		});
		textField.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String error = validateConstraints(Id.String, textField.getText(), constraintMap);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(textField.getText());
				 }
			 }
		});
		return setLayout(style, jTitle, textField, errorMsg, panel);
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
	public Id getType() { return Id.String; }

	@Override
	public String toString() {
		return "String [name=" + name + ", title=" + title + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}
	
	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitString(this);
	}
}