package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ast.Ast;
import parser.Parser;

public class Test {

	public static void main(String[] args) {
		new PersonData();

//		UiBooster booster = new UiBooster();
//		FilledForm form = booster.createForm("Person data")
//					.addLabel("Please fill your personal data bellow")
//		            .addText("Name")
//		            .addText("Age")
//		            .addButton("Ok", () -> /*Button press event implementation*/)
//		            .addButton("Cancel", () -> /*Button press event implementation*/)
//		            .show();
//	}
	}
}

class PersonData {
	public PersonData() {
		JFrame frame = new JFrame();
		frame.setTitle("Person data");
		frame.setLayout(new GridBagLayout());
		JLabel description = new JLabel("Please fill your personal data bellow");
		JLabel name = new JLabel("Name");
		JTextField tfName = new JTextField();
		tfName.setPreferredSize(new Dimension(250, 30));
		JLabel age = new JLabel("Age");
		JTextField tfAge = new JTextField();
		tfAge.setPreferredSize(new Dimension(250, 30));
		JButton bApprove = new JButton("Approve");
		JButton bCancel = new JButton("Cancel");
		bCancel.addActionListener(e -> {
			/* Button clicked event implementation  */
		});
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		frame.add(description, gbc);
		gbc.gridy++;
		frame.add(name, gbc);
		gbc.gridy++;
		frame.add(tfName, gbc);
		gbc.gridy++;
		frame.add(age, gbc);
		gbc.gridy++;
		frame.add(tfAge, gbc);
		gbc.gridy++;
		frame.add(bApprove, gbc);
		gbc.anchor = GridBagConstraints.EAST;
		frame.add(bCancel, gbc);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
