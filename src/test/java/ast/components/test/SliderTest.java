package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import ast.Id;
import ast.components.CSlider;
import ast.constraints.Constraint;
import ast.constraints.StyleId;
import fields.JPanelWithValue;

public class SliderTest {
	@Test public void testSliderBlockStyle() {
		  var frame=new JFrame();
		  Constraint constraint = StyleId.from(Id.String, "block");
		  List<Constraint> constraints = List.of(constraint);
		  var cs=new CSlider("name","title",2,9,5,constraints);
		  JPanelWithValue slider=cs.make();
		  assertEquals("5", slider.getValue());
		  slider.setValueOrDefault("3", false);
		  assertEquals("3", slider.getValue());
		  slider.setValueOrDefault("3", true);
		  assertEquals("5", slider.getValue());
		  TestHelper.withGui(frame, slider, true);
	  }
	
	@Test public void testSliderinlineStyle() {
		  var frame=new JFrame();
		  Constraint constraint = StyleId.from(Id.String, "inline");
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