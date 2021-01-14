package ast.components;

import java.util.Collections;
import java.util.List;

import ast.Id;
import ast.constraints.Constraint;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CInteger implements Component {
	private String name;
	private String title;
	private Integer defVal;
	private List<Constraint> constraints;
	
	public CInteger(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		defVal = initDefVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	private Integer initDefVal(ComponentContext ctx) {
		String value = extractCompDefVal(ctx);
		try {
			return value.isEmpty()? null :Integer.valueOf(value);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(value + " is not a valid integer");
		}
	}
	
	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public Integer getDefVal() {
		return defVal;
	}

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}

	@Override
	public Id getType() { return Id.Integer; }

	@Override
	public String toString() {
		return "Integer [name=" + name + ", title=" + title + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}

	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitInteger(this);
	}
}