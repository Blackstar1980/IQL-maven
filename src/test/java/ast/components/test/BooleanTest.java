package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CBoolean;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class BooleanTest {
	@Test
	public void testBooleanBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Boolean, "block");
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
	public void testBooleanInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Boolean, "inline");
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
	public void testBooleanBlockListDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Boolean, "blockList");
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
	public void testBooleanInlineListDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Boolean, "inlineList");
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
