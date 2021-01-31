package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CInteger;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class IntegerTest {
	@Test
	public void testIntegerBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Integer, "block");
		List<Constraint> constraints = List.of(constraint);
		var ci = new CInteger("name", "title", new BigInteger("3"), constraints);
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
	public void testIntegerInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Integer, "inline");
		List<Constraint> constraints = List.of(constraint);
		var ci = new CInteger("name", "title", new BigInteger("3"), constraints);
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
