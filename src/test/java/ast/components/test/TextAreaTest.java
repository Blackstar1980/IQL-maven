package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CTextArea;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class TextAreaTest {
	@Test
	public void testStringBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.TextArea, "block");
		List<Constraint> constraints = List.of(constraint);
		var cta = new CTextArea("name", "title", "defVal1", constraints);
		JPanelWithValue textArea = cta.make();
		assertEquals("defVal1", textArea.getValue());
		textArea.setValueOrDefault("defVal2", false);
		assertEquals("defVal2", textArea.getValue());
		textArea.setValueOrDefault("", false);
		assertEquals("", textArea.getValue());
		textArea.setValueOrDefault("defVal2", true);
		assertEquals("defVal1", textArea.getValue());
		TestHelper.withGui(frame, textArea, false);
	}
	
	@Test
	public void testStringInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.TextArea, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cta = new CTextArea("name", "title", "defVal1", constraints);
		JPanelWithValue textArea = cta.make();
		assertEquals("defVal1", textArea.getValue());
		textArea.setValueOrDefault("defVal2", false);
		assertEquals("defVal2", textArea.getValue());
		textArea.setValueOrDefault("", false);
		assertEquals("", textArea.getValue());
		textArea.setValueOrDefault("defVal2", true);
		assertEquals("defVal1", textArea.getValue());
		TestHelper.withGui(frame, textArea, false);
	}
}
