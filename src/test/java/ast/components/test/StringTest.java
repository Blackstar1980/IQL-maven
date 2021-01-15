package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CString;
import ast.constraints.Constraint;
import ast.constraints.StyleId;
import fields.JPanelWithValue;

public class StringTest {

	@Test
	public void testStringBlockStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.String, "block");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CString("name", "title", "defVal1", constraints);
		JPanelWithValue stringPanel = cs.make();
		assertEquals("defVal1", stringPanel.getValue());
		stringPanel.setValueOrDefault("defVal2", false);
		assertEquals("defVal2", stringPanel.getValue());
		stringPanel.setValueOrDefault("", false);
		assertEquals("", stringPanel.getValue());
		stringPanel.setValueOrDefault("defVal2", true);
		assertEquals("defVal1", stringPanel.getValue());
		TestHelper.withGui(frame, stringPanel, false);
	}
	
	@Test
	public void testStringInlineStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.String, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CString("name", "title", "defVal1", constraints);
		JPanelWithValue stringPanel = cs.make();
		assertEquals("defVal1", stringPanel.getValue());
		stringPanel.setValueOrDefault("defVal2", false);
		assertEquals("defVal2", stringPanel.getValue());
		stringPanel.setValueOrDefault("", false);
		assertEquals("", stringPanel.getValue());
		stringPanel.setValueOrDefault("defVal2", true);
		assertEquals("defVal1", stringPanel.getValue());
		TestHelper.withGui(frame, stringPanel, false);
	}
}