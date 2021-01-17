package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CMultiOpt;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class MultiOptTest {	
	
	@Test
	public void testMultiOptBlockListDisplay() {
		  var frame=new JFrame();
		  List<String> options = List.of("option1", "option2", "option3", "option4");
		  List<String> defValues = List.of("option1", "option2");
		  Constraint constraint = DisplayId.from(Id.MultiOpt, "blockList");
		  List<Constraint> constraints = List.of(constraint);
		  var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		  JPanelWithValue multi=cmulti.make();
		  assertEquals("option1, option2", multi.getValue());
		  multi.setValueOrDefault("option3, option4", false);
		  assertEquals("option3, option4", multi.getValue());
		  multi.setValueOrDefault("", true);
		  assertEquals("option1, option2", multi.getValue());
		  multi.setValueOrDefault("", false);
		  assertEquals("", multi.getValue());
		  TestHelper.withGui(frame, multi, false);
	  }
	
	@Test 
	public void testMultiOptInlineListDisplay() {
		  var frame=new JFrame();
		  List<String> options = List.of("option1", "option2", "option3", "option4");
		  List<String> defValues = List.of("option1", "option2");
		  Constraint constraint = DisplayId.from(Id.MultiOpt, "inlineList");
		  List<Constraint> constraints = List.of(constraint);
		  var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		  JPanelWithValue multi=cmulti.make();
		  assertEquals("option1, option2", multi.getValue());
		  multi.setValueOrDefault("option3, option4", false);
		  assertEquals("option3, option4", multi.getValue());
		  multi.setValueOrDefault("", true);
		  assertEquals("option1, option2", multi.getValue());
		  multi.setValueOrDefault("", false);
		  assertEquals("", multi.getValue());
		  TestHelper.withGui(frame, multi, false);
		  
	  }

	@Test
	public void testMultiOptBlockCheckboxDisplay() {
		var frame=new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		List<String> defValues = List.of("option1", "option2");
		Constraint constraint = DisplayId.from(Id.MultiOpt, "blockCheckbox");
		List<Constraint> constraints = List.of(constraint);
		var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		JPanelWithValue multi=cmulti.make();
		assertEquals("option1, option2", multi.getValue());
		multi.setValueOrDefault("option3, option4", false);
		assertEquals("option3, option4", multi.getValue());
		multi.setValueOrDefault("", true);
		assertEquals("option1, option2", multi.getValue());
		multi.setValueOrDefault("", false);
		  assertEquals("", multi.getValue());
		TestHelper.withGui(frame, multi, false);
	}
	
}
