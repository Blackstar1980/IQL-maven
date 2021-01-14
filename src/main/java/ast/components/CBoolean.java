package ast.components;

import java.util.Collections;
import java.util.List;

import ast.Id;
import ast.constraints.Constraint;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CBoolean implements Component {
	private String name;
	private String title;
	private String defVal;
	private List<Constraint> constraints;
	
	public CBoolean(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		defVal = initDefaultVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	private String initDefaultVal(ComponentContext ctx) {
		String value = extractCompDefVal(ctx);
		if("".equals(value))
			return "";
		if("true".equals(value) || "false".equals(value))
			return value;
		else 
			throw new IllegalArgumentException("Boolean default value must be only 'true' or 'false'");
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
	public Id getType() { return Id.Boolean; }

	@Override
	public String toString() {
		return "Boolean [name=" + name + ", title=" + title + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}}

