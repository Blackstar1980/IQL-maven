package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CMultiOpt;
import ast.constraints.Constraint;
import ast.constraints.StyleId;
import fields.JPanelWithValue;

public class MultiOptTest {
	public static boolean slow=false;
	
	private static void withGui(JFrame frame, JPanelWithValue panel) {
		if(slow == false)
			return;
		frame.add(panel);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true); 
		try{System.in.read();}catch(IOException e){}
	}
	
	@Test
	public void testBlockListStyle() {
		  var frame=new JFrame();
		  List<String> options = List.of("option1", "option2", "option3", "option4");
		  List<String> defValues = List.of("option1", "option2");
		  Constraint constraint = StyleId.from(Id.MultiOpt, "blockList");
		  List<Constraint> constraints = List.of(constraint);
		  var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		  JPanelWithValue multi=cmulti.make();
		  assertEquals("option1, option2", multi.getValue());
		  withGui(frame, multi);
	  }
	
	@Test 
	public void testInlineListStyle() {
		  var frame=new JFrame();
		  List<String> options = List.of("option1", "option2", "option3", "option4");
		  List<String> defValues = List.of("option1", "option2");
		  Constraint constraint = StyleId.from(Id.MultiOpt, "inlineList");
		  List<Constraint> constraints = List.of(constraint);
		  var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		  JPanelWithValue multi=cmulti.make();
		  assertEquals("option1, option2", multi.getValue());
		  withGui(frame, multi);
//		  f.add(multi);
//		  f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		  f.pack();
//		  f.setVisible(true);
//		  try{System.in.read();}catch(IOException e){}
		  
	  }
	
	@Test 
	public void testChangeListDefaultValue() {
		  var frame=new JFrame();
		  List<String> options = List.of("option1", "option2", "option3", "option4");
		  List<String> defValues = List.of("option1", "option2");
		  Constraint constraint = StyleId.from(Id.MultiOpt, "inlineList");
		  List<Constraint> constraints = List.of(constraint);
		  var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		  JPanelWithValue multi=cmulti.make();
		  multi.setValueOrDefault("option3, option4");
		  assertEquals("option3, option4", multi.getValue());
		  withGui(frame, multi);
//		  f.add(multi);
//		  f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		false  f.pack();
//		  f.setVisible(true);
//		  try{System.in.read();}catch(IOException e){}
	  }
	  
	@Test
	public void testBlockCheckboxStyle() {
//		slow= true;
		var frame=new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		List<String> defValues = List.of("option1", "option2");
		Constraint constraint = StyleId.from(Id.MultiOpt, "blockCheckbox");
		List<Constraint> constraints = List.of(constraint);
		var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		JPanelWithValue multi=cmulti.make();
//		assertEquals("option1, option2", multi.getValue());
		withGui(frame, multi);
//		f.add(multi);
//		f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		f.pack();
//		f.setVisible(true);
//		if(slow)try{System.in.read();}catch(IOException e){}
	}
	
	
	
	
	
	
	
	
	
	
}
