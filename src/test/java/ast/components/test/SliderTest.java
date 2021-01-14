package ast.components.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.junit.Test;

import ast.components.CMultiOpt;
import ast.components.CSlider;
import fields.JPanelWithValue;

public class SliderTest {
	@Test public void test01() {
		  var f=new JFrame();
		  var cs=new CSlider("name","title",2,9,5,List.of());
		  JPanelWithValue slider=cs.make();
		  slider.setValueOrDefault("3");
		  f.add(slider);
		  f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		  f.pack();
		  f.setVisible(true);
		  assertEquals("3", slider.getValue());
	  }
	

}
