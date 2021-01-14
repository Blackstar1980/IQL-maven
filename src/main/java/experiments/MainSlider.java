package experiments;
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import ast.components.CSlider;
import fields.JPanelWithValue;
public class MainSlider {
  public static void main(String[]a) {
	  var f=new JFrame();
	  var cs=new CSlider("name","title",2,9,5,List.of());
	  JPanelWithValue slider=cs.make();
	  slider.setValueOrDefault("3");
	  f.add(slider);
//	  f.getRootPane().add(BorderLayout.CENTER,slider);
	  f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	  f.pack();
	  f.setVisible(true);
	  //slider.getValue()  "3"
	  System.out.println(slider.getValue());
  }
}
