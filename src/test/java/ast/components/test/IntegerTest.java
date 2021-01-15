package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CInteger;
import ast.constraints.Constraint;
import ast.constraints.StyleId;
import fields.JPanelWithValue;

public class IntegerTest {
	@Test
	public void testIntegerBlockStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.Integer, "block");
		List<Constraint> constraints = List.of(constraint);
		var ci = new CInteger("name", "title", 3, constraints);
		JPanelWithValue integerPanel = ci.make();
		assertEquals("3", integerPanel.getValue());
		integerPanel.setValueOrDefault("5", false);
		assertEquals("5", integerPanel.getValue());
		integerPanel.setValueOrDefault("", false);
		assertEquals("", integerPanel.getValue());
		integerPanel.setValueOrDefault("5", true);
		assertEquals("3", integerPanel.getValue());
		TestHelper.withGui(frame, integerPanel, false);
	}
	
	@Test
	public void testIntegerInlineStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.Integer, "inline");
		List<Constraint> constraints = List.of(constraint);
		var ci = new CInteger("name", "title", 3, constraints);
		JPanelWithValue integerPanel = ci.make();
		assertEquals("3", integerPanel.getValue());
		integerPanel.setValueOrDefault("5", false);
		assertEquals("5", integerPanel.getValue());
		integerPanel.setValueOrDefault("", false);
		assertEquals("", integerPanel.getValue());
		integerPanel.setValueOrDefault("5", true);
		assertEquals("3", integerPanel.getValue());
		TestHelper.withGui(frame, integerPanel, false);
	}
}
