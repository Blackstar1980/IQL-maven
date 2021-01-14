package ast.components;

import java.util.Collections;
import java.util.List;

import ast.Id;
import ast.constraints.Constraint;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CDecimal implements Component {
	private String name;
	private String title;
	private Double defVal;
	private List<Constraint> constraints;
	
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
	
	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public double getDefVal() {
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
