package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CBoolean;
import ast.constraints.Constraint;
import ast.constraints.StyleId;
import fields.JPanelWithValue;

public class BooleanTest {
	@Test
	public void testBooleanBlockStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.Boolean, "block");
		List<Constraint> constraints = List.of(constraint);
		var cb = new CBoolean("name", "title", "true", constraints);
		JPanelWithValue booleanPanel = cb.make();
		assertEquals("true", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", false);
		assertEquals("false", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("", false);
		assertEquals("", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", true);
		assertEquals("true", booleanPanel.getValue());
		TestHelper.withGui(frame, booleanPanel, false);
	}
	
	@Test
	public void testBooleanInlineStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.Boolean, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cb = new CBoolean("name", "title", "true", constraints);
		JPanelWithValue booleanPanel = cb.make();
		assertEquals("true", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", false);
		assertEquals("false", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("", false);
		assertEquals("", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", true);
		assertEquals("true", booleanPanel.getValue());
		TestHelper.withGui(frame, booleanPanel, false);
	}
}
