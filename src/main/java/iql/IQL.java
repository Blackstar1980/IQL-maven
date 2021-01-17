package iql;

import java.util.List;
import java.util.Map;

import ast.Ast;
import parser.Parser;
import ui.MultiVisitor;
import ui.PagesVisitor;
import ui.SingleVisitor;

public class IQL {

	public static List<Map<String, String>> run(String query) {
		Ast.Query queryObj = Parser.parse(query);
		var visitor = switch (queryObj.dialog().getType()) {
			case Pages -> new PagesVisitor();
			case Single -> new SingleVisitor();
			case Multi -> new MultiVisitor();
			default ->
				throw new IllegalArgumentException("Unexpected value: " + queryObj.dialog().getType());
		};
		queryObj.accept(visitor);
		return visitor.getData().join();
	}
	
	public static void main(String[] args) {
//		var query = """
//				'User Registration Form' Single('please provide a accurate details about yourself')
//				'Personal Details' Tab{
//					name 'Full Name:' String{min=2}
//					age 'Age:' Integer{min=1 max=120 required=false}
//					middle 'Middle Name' String{required=false min=5}
//					'Family details' Group{
//						marry 'Are you marry?' Boolean('false'){required=false}
//					}
//				}
//				'Account Details' Tab{
//				password 'Password:' Password{holder='Password'}
//				comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
//				work 'Working Field' MultiOpt('Computers|Building|Teaching|Non of the above'){
//				display=blockList required=false selected='Computers|Building'}
//				}
//				""";
//		var query = """
//		'User Registration Form' Single('please provide a accurate details about yourself')
//		'Personal Details' Tab{
//			name 'Full Name:' String{min=2}
//			age 'Age:' Integer{min=1 max=120 required=false}
//			middle 'Middle Name' String{required=false min=5}
//			marry 'Are you marry?' Boolean('false'){required=false}
//		}
//		'Account Details' Tab{
//		password 'Password:' Password{holder='Password'}
//		comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
//		work 'Working Field' MultiOpt('Computers|Building|Teaching|Non of the above'){
//		display=blockList required=false selected='Computers|Building'}
//		}
//		""";
		
		var queryBlock = """
		'User Registration Form' Single('please provide a accurate details about
		yourself adfasdas a d asd asd a sd as d as')
		name 'Full Name:' String{min=2}
		age 'Age:' Integer{min=1 max=120 }
		middle 'Middle Name' String{ min=5}
		marry 'Are you marry?' Boolean('false')
		mar 'Are you marry?' Boolean{display=blockList}
		height 'Height:' Decimal
		password 'Password:' Password{holder='Password'}
		comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
		work 'Working Field' MultiOpt('Computers|Building|Teaching'){required=true}
		slider 'Slider' Slider('0,10,5')
		single 'Single' SingleOpt('Computers|Building|Teaching')
		singles 'Single2' SingleOpt('Computers|Building'){display=blockList}
		works 'Working Field2' MultiOpt('Computers|Building'){display=blockList selected='Computers|Building'}
		""";
		
		var queryInline = """
		'User Registration Form' Single('please provide a accurate details about yourself
		and your family in order to be able to do something... or not')
		name 'Full Name:' String{min=2 display=inline}
		age 'Age:' Integer{min=1 max=120 display=inline}
		middle 'Middle Name' String{ min=5 display=inline}
		marry 'Are you marry?' Boolean('false'){ display=inline}
		mar 'Are you marry?' Boolean{display=inlineList}
		height 'Height:' Decimal{display=inline}
		password 'Password:' Password{holder='Password' display=inline}
		comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you' display=inline}
		slider 'Slider' Slider('0,10,5'){display=inline}
		singles 'Single2' SingleOpt('Computers|Building'){display=inlineList}
		works 'Working Field2' MultiOpt('Computers|Building'){display=inlineList selected='Computers|Building'
		}
		""";
		
//		var queryBlock2 = """
//		'User Registration Form' Single('please provide a accurate details about
//		yourself adfasdas a d asd asd a sd as d as')
//		name 'Full Name:' String{min=2}
//		age 'Age:' Integer{min=1 max=120 required=false}
//		middle 'Middle Name' String{required=false min=5}
//		marry 'Are you marry?' Boolean('false'){required=false}
//		height 'Height:' Decimal
//		password 'Password:' Password{holder='Password'}
//		comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
//		work 'Working Field' MultiOpt('Computers|Building|Teaching|Non of the above'){display=blockList
//		required=false selected='Computers|Building'}
//		slider 'Slider' Slider('0,10,5')
//		single 'Single' SingleOpt('Computers|Building|Teaching|Non of the above'){display=blockList}
//		""";
		
		
//		var query = """
//		'User Registration Form' Multi('please provide a accurate details about yourself')
//			name 'Full Name:' String{min=2}
//			age 'Age:' Integer{min=1 max=120 required=false}
//			middle 'Middle Name' String{required=false min=5}
//			marry 'Are you marry?' Boolean('false'){required=false}
//		password 'Password:' Password{holder='Password'}
//		work 'Working Field' MultiOpt('Computers|Building|Teaching|Non of the above'){
//		display=blockList required=false selected='Computers|Building'}
//		""";
//		run(query);
		
		
		var exampleString01 = """
				'User Details' Single('Please provide your details below')
					name 'Full Name:' String
				""";
		
		var exampleString02 = """
				'User Details' Single('Please provide your details below')
					name 'First Name:' String('Jack')
				""";
		var exampleString03 = """
				'User Details' Single('Please provide your details below')
					name 'First Name:' String{required=false min=2 max=12 holder='Enter you name here' display=inline regex='[a-zA-Z]+'}
				""";
		
		run(exampleString03);
//		run(queryBlock);
	}

}
