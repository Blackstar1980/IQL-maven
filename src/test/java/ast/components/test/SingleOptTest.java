package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CSingleOpt;
import ast.constraints.Constraint;
import ast.constraints.StyleId;
import fields.JPanelWithValue;

public class SingleOptTest {
	@Test
	public void testSingleOptBlockListStyle() {
		var frame = new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		Constraint constraint = StyleId.from(Id.SingleOpt, "blockList");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CSingleOpt("name", "title", options, "option1", constraints);
		JPanelWithValue single = cs.make();
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("option4", false);
		assertEquals("option4", single.getValue());
		single.setValueOrDefault("", true);
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("", false);
		assertEquals("", single.getValue());
		TestHelper.withGui(frame, single, false);
	}

	@Test
	public void testSingleOptInlineListStyle() {
		var frame = new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		Constraint constraint = StyleId.from(Id.SingleOpt, "inlineList");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CSingleOpt("name", "title", options, "option1", constraints);
		JPanelWithValue single = cs.make();
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("option4", false);
		assertEquals("option4", single.getValue());
		single.setValueOrDefault("", true);
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("", false);
		assertEquals("", single.getValue());
		TestHelper.withGui(frame, single, false);
	}

	@Test
	public void testSingleOptBlockRadioStyle() {
		var frame = new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		Constraint constraint = StyleId.from(Id.SingleOpt, "blockRadio");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CSingleOpt("name", "title", options, "option1", constraints);
		JPanelWithValue single = cs.make();
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("option4", false);
		assertEquals("option4", single.getValue());
		single.setValueOrDefault("", true);
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("", false);
		assertEquals("", single.getValue());
		TestHelper.withGui(frame, single, false);
	}

	@Test
	public void testSingleOptInlineRadioStyle() {
		var frame = new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		Constraint constraint = StyleId.from(Id.SingleOpt, "inlineRadio");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CSingleOpt("name", "title", options, "option1", constraints);
		JPanelWithValue single = cs.make();
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("option4", false);
		assertEquals("option4", single.getValue());
		single.setValueOrDefault("", true);
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("", false);
		assertEquals("", single.getValue());
		TestHelper.withGui(frame, single, false);
	}
}