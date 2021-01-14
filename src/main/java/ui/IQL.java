package ui;

import java.util.List;
import java.util.Map;

import ast.Ast;
import parser.Parser;

public class IQL {

	public static List<Map<String, String>> run(String query) {
		Ast.Query a = Parser.parse(query);
		var visitor = new PagesVisitor();
		a.accept(visitor);
		return visitor.getData().join();
	}
	
	public static void main(String[] args) {
		var query = """
				'User Registration Form' Pages('please provide a accurate details about yourself')
				'Personal Details' Tab{
					name 'Full Name:' String{min=2}
					age 'Age:' Integer{min=1 max=120 required=false}
					middle 'Middle Name' String{required=false min=5}
					'Family details' Group{
						marry 'Are you marry?' Boolean('false'){required=false}
						children 'How many children do you have?' Slider('0,12,0'){majorTicks=3 minorTicks=1}
					}
				}
				'Account Details' Tab{
				password 'Password:' Password{holder='Password'}
				comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
				work 'Working Field' MultiOpt('Computers|Building|Teaching|Non of the above'){
				style=blockList required=false selected='Computers|Building'}
				}
				""";
		run(query);
	}

}
