package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CSlider;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class SliderTest {
	@Test public void testSliderBlockDisplay() {
		  var frame=new JFrame();
		  Constraint constraint = DisplayId.from(Id.String, "block");
		  List<Constraint> constraints = List.of(constraint);
		  var cs=new CSlider("name","title",2,9,5,constraints);
		  JPanelWithValue slider=cs.make();
		  assertEquals("5", slider.getValue());
		  slider.setValueOrDefault("3", false);
		  assertEquals("3", slider.getValue());
		  slider.setValueOrDefault("3", true);
		  assertEquals("5", slider.getValue());
		  TestHelper.withGui(frame, slider, false);
	  }
	
	@Test public void testSliderinlineDisplay() {
		  var frame=new JFrame();
		  Constraint constraint = DisplayId.from(Id.String, "inline");
		  List<Constraint> constraints = List.of(constraint);
		  var cs=new CSlider("name","title",2,9,5,constraints);
		  JPanelWithValue slider=cs.make();
		  assertEquals("5", slider.getValue());
		  slider.setValueOrDefault("3", false);
		  assertEquals("3", slider.getValue());
		  slider.setValueOrDefault("3", true);
		  assertEquals("5", slider.getValue());
		  TestHelper.withGui(frame, slider, false);
	  }
	
}