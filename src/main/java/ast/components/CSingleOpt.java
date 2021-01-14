package ast.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ast.Id;
import ast.constraints.Constraint;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CSingleOpt implements Component {
	private String name;
	private String title;
	private List<String> options;
	private List<Constraint> constraints;
	
	public CSingleOpt(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		options = initOpts(ctx);
		constraints = extractConstraints(ctx);
	}
	
	private List<String> initOpts(ComponentContext ctx) {
		String input = extractCompDefVal(ctx);
		if(input == null || input.isEmpty() || input.endsWith("|") || input.startsWith("|"))
			throw new IllegalArgumentException("'"+input + "' are not a valid options");
		List<String> values = Arrays.asList(input.trim().split("\\|"));
		for(String value : values) {
			if(value.isBlank())
				throw new IllegalArgumentException("Empty value is not a valid option");
		}
		return values;
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
	public Id getType() { return Id.SingleOpt; }

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
