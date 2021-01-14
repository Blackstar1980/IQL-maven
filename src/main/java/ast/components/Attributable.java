package ast.components;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import ast.Ast;
import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;

public interface Attributable<T> extends Ast<T> {
	default String extractDefVal(TerminalNode constraintsNode) {
		return constraintsNode == null? "":checkNewLine(subString(constraintsNode.getText(), 2, 2));
	}
	
	default List<Constraint> extractConstraints(Id id, List<ParseTree> children) {
		if(children == null) return List.of();
		Set<String> stringconsts = new HashSet<String>();
		List<Constraint> constraints = children.stream()
		.filter(c-> !("{".equals(c.getText()) || "}".equals(c.getText())))
		.map(c -> {
			String constraint = c.getText();
			String constraintName = constraint.substring(0, constraint.indexOf('=')).trim();
			if(stringconsts.contains(constraintName))
				throw new IllegalArgumentException("'"+ constraintName + "' constraint repeat more then once");
			stringconsts.add(constraintName);
			return ConstraintId.from(id, constraint);
			})
		.collect(Collectors.toList());
		return constraints;
	}
}
