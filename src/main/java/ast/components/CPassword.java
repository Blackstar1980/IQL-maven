package ast.components;

import java.util.Collections;
import java.util.List;

import ast.Id;
import ast.constraints.Constraint;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CPassword implements Component {	
	private String name;
	private String title;
	private String defVal;
	private List<Constraint> constraints;
	
	public CPassword(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		defVal = extractCompDefVal(ctx);
		constraints = extractConstraints(ctx);
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
	public Id getType() { return Id.Password; }

	@Override
	public String toString() {
		return "Password [name=" + name + ", title=" + title + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}
	
	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitPassword(this);
	}	
}
