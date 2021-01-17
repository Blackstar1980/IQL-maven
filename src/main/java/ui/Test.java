package ui;

import ast.Ast;
import parser.Parser;

public class Test {

	public static void main(String[] args) {
		var query = """
				'User Registration Form' Pages('please provide a accurate details about yourself')
				'Personal Details' Tab{
					name 'Full Name:' String{min=2}
					age 'Age:' Integer{min=1 max=120 required=false}
					middle 'Middle Name' String{required=false min=5}
					'Family details' Group{
						marry 'Are you marry?' Boolean('false'){required=false display=blockList}
						children 'How many children do you have?' Slider('0,12,0'){majorTicks=3 minorTicks=1}
						aaa 'aaaa' SingleOpt('Red1|Red2|Red3') {required=false display=blockList}
					}
				}
				'Account Details' Tab{
				password 'Password:' Password{holder='Password'}
				comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
				work 'Working Field' MultiOpt('Computers|Building|Teaching|Non of the above'){
				display=blockList required=false selected='Computers|Building'}
				}
				""";
//		var query = """
//				'my dialog title' Single('title description with a loot of\
//				lines is it work?')
//				name 'Name:' String{min=5 max =6 holder='my holder' regex='ddfgfd' required=false}
//				password 'Password:' Password{m124in=2 max =9 holder='password' required=false}
//				age 'Age:' Integer{min=4 max=99 holder='integer only!'}
//				'My Super Group' Group{
//				height 'Height:' Decimal{min=4.5 max=99.7 holder='Decimal only!!' required=false}
//				comments 'Comments:' TextArea{min=4 max=20 holder='textarea only!'}
//				slider 'My Slider!' Slider('0,30,11'){majorTicks=5 minorTicks=2}
//				color 'Select color:' SingleOpt('Red|Blue|Green'){display=inlineList }
//				car 'Car?' MultiOpt('Big|Small|Van | Mini') {display=blockList selected='Van | Mini'}
//				big 'Are you big?' Boolean
//				}
//				""";
	
//		var query = """
//				'my dialog title' Single('title description with a loot of\
//				lines is it work?')
//				name 'Name:' String{min=5 max =6 holder='my holder' regex='ddfgfd' required=false}
//				password 'Password:' Password{display=inline min=2 max =9 holder='password' required=false}
//				age 'Age:' Integer{min=4 max=99 holder='integer only!'}
//				'My Super Group' Group{
//				height 'Height:' Decimal{min=4.5 max=99.7 holder='Decimal only!!' required=false}
//				comments 'Comments:' TextArea{min=4 max=20 holder='textarea only!'}
//				slider 'My Slider!' Slider('0,30,11'){majorTicks=5 minorTicks=2 display=inline}
//				color 'Select color:' SingleOpt('Red|Blue|Green'){display=blockRadio selected='Green'}
//				car 'Car?' MultiOpt('Big|Small|Van | Mini') {display=inlineList selected='Van | Mini'}
//				big 'Are you big?' Boolean{display=inline}
//				}
//				""";
		Ast.Query a = Parser.parse(query);
		var visitor = new PagesVisitor();
		a.accept(visitor);
		System.out.println(visitor.getData().join());
	}	
		
//	{slider=27, big=false, password=dfg, comments=sdfgfsd, color=Blue, car=, name=ddfgfd, age=23, height=33}
	
//		var query = """
//		'Person data' Single('Please fill your personal data bellow')
//		name 'Name' String
//		age 'Age:' Integer
//		""";
//		Ast.Query a = Parser.parse(query);
//		var visitor = new UiVisitor();
//		a.accept(visitor);
//		
//		
////		PersonData p = new PersonData();
//		
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

//class PersonData {
//	public PersonData() {
//		JDialog dialog= new JDialog();  
//		dialog.setTitle("Person data");
//		dialog.setLayout(new GridBagLayout());
//		JLabel description = new JLabel("Please fill your personal data bellow");
//		JLabel name = new JLabel("Name");
//		JTextField tfName = new JTextField();
//		tfName.setPreferredSize(new Dimension(250, 30));
//		JLabel age = new JLabel("Age");
//		JTextField tfAge = new JTextField();
//		tfAge.setPreferredSize(new Dimension(250, 30));
//		JButton bOk = new JButton ("OK"); 
//		JButton bCancel = new JButton ("Cancel");
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.anchor = GridBagConstraints.WEST;
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		dialog.add(description, gbc);
//		gbc.gridy++;
//		dialog.add(name, gbc);
//		gbc.gridy++;
//		dialog.add(tfName, gbc);
//		gbc.gridy++;
//		dialog.add(age, gbc);
//		gbc.gridy++;
//		dialog.add(tfAge, gbc);
//		gbc.gridy++;
//		dialog.add(bOk, gbc);
//		gbc.anchor = GridBagConstraints.EAST;
//		dialog.add(bCancel, gbc);
//		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		dialog.setResizable(false);
//		dialog.pack();
//		dialog.setVisible(true);
//	}
//}
