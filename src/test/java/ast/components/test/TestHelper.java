package ast.components.test;

import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;

import fields.JPanelWithValue;

public class TestHelper {
	static void withGui(JFrame frame, JPanelWithValue panel, boolean enabled) {
		if(enabled == false)
			return;
		frame.add(panel);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true); 
		try{System.in.read();}catch(IOException e){}
	}
}
