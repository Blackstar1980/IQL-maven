package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CDecimal;
import ast.constraints.Constraint;
import ast.constraints.StyleId;
import fields.JPanelWithValue;

public class DecimalTest {
	@Test
	public void testDecimalBlockStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.Decimal, "block");
		List<Constraint> constraints = List.of(constraint);
		var cd = new CDecimal("name", "title", 3.0, constraints);
		JPanelWithValue decimalPanel = cd.make();
		assertEquals("3.0", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("5.0", false);
		assertEquals("5.0", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("", false);
		assertEquals("", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("5.0", true);
		assertEquals("3.0", decimalPanel.getValue());
		TestHelper.withGui(frame, decimalPanel, false);
	}
	
	@Test
	public void testDecimalInlineStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.Decimal, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cd = new CDecimal("name", "title", 3.0, constraints);
		JPanelWithValue decimalPanel = cd.make();
		assertEquals("3.0", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("5.0", false);
		assertEquals("5.0", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("", false);
		assertEquals("", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("5.0", true);
		assertEquals("3.0", decimalPanel.getValue());
		TestHelper.withGui(frame, decimalPanel, false);
	}
}
