package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CDecimal;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class DecimalTest {
	@Test
	public void testDecimalBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Decimal, "block");
		List<Constraint> constraints = List.of(constraint);
		var cd = new CDecimal("name", "title", new BigDecimal("3.0"), constraints);
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
	public void testDecimalInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Decimal, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cd = new CDecimal("name", "title", new BigDecimal("3.0"), constraints);
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
