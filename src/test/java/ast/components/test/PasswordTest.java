package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CPassword;
import ast.constraints.Constraint;
import ast.constraints.StyleId;
import fields.JPanelWithValue;

public class PasswordTest {
	@Test
	public void testPasswordBlockStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.Password, "block");
		List<Constraint> constraints = List.of(constraint);
		var cp = new CPassword("name", "title", "Pass1", constraints);
		JPanelWithValue password = cp.make();
		assertEquals("Pass1", password.getValue());
		password.setValueOrDefault("Pass2", false);
		assertEquals("Pass2", password.getValue());
		password.setValueOrDefault("", false);
		assertEquals("", password.getValue());
		password.setValueOrDefault("Pass2", true);
		assertEquals("Pass1", password.getValue());
		TestHelper.withGui(frame, password, false);
	}
	
	@Test
	public void testPasswordInlineStyle() {
		var frame = new JFrame();
		Constraint constraint = StyleId.from(Id.Password, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cp = new CPassword("name", "title", "Pass1", constraints);
		JPanelWithValue password = cp.make();
		assertEquals("Pass1", password.getValue());
		password.setValueOrDefault("Pass2", false);
		assertEquals("Pass2", password.getValue());
		password.setValueOrDefault("", false);
		assertEquals("", password.getValue());
		password.setValueOrDefault("Pass2", true);
		assertEquals("Pass1", password.getValue());
		TestHelper.withGui(frame, password, false);
	}
}
