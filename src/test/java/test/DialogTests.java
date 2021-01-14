package test;

import org.junit.Test;

public class DialogTests {
	@Test public void missingDialogInformation01() {	
		TestHelper.checkParseError("");
	}
	
	@Test public void missingDialogInformation02() {
		TestHelper.checkParseError("'Dialog Title'");
	}
	
	@Test public void missingDialogInformation03() {
		TestHelper.checkParseError("'Dialog Title' Single");
	}
	
	@Test public void missingDialogInformation04() {
		TestHelper.checkParseError("'Dialog Title' Single()");	
	}
	
	@Test public void missingDialogInformation05() {
		TestHelper.checkParseError("'Dialog Title' Single('dialog description')");
	}
	
	@Test public void missingDialogInformation06() {
		TestHelper.checkParseError("'Dialog Title' Single('dialog description'){}");	
	}
	
	@Test public void wrongDialogInformation01() {
		TestHelper.checkParseError("""
			Dialog Title Single('dialog description') name 'Name:' String
			""");	
	}
	
	@Test public void wrongDialogInformation02() {
		TestHelper.checkParseError("'Dialog Title' Big('dialog description')  name 'Name:' String");
	}
	
	@Test public void wrongDialogInformation03() {
		TestHelper.checkParseError("'Dialog Title' Single('dialog description'){fax=4}  name 'Name:' String");
	}
	
	@Test public void wrongDialogInformation04() {
		TestHelper.checkParseError("'Dialog Title' Single('dialog description'){max=big}  name 'Name:' String");
	}
	
	@Test public void wrongDialogInformation05() {
		TestHelper.arrgumentException(
				"'Dialog Title' Single('dialog description'){max=-2}  name 'Name:' String",
				"Single is not support Max constraint");
	}
	
	@Test public void wrongDialogInformation06() {
		TestHelper.arrgumentException(
				"'Dialog Title' Multi('dialog description'){max=-2}  name 'Name:' String",
				"Multi max constraint, must be bigger than 0");
	}
	
	@Test public void wrongDialogInformation07() {
		TestHelper.numberException(
				"'Dialog Title' Pages('dialog description'){max=2.5}  name 'Name:' String",
				"For input string: \"2.5\""	);
	}
	
	@Test public void wrongDialogInformation08() {
		TestHelper.checkParseError("'Dialog Title' Single('dialog description'){holder = 'my holder'} name 'Name:' String");
	}
		
	@Test public void dialog01() {
		TestHelper.checkAst(
				"""
				'Dialog Title' Single('dialog description')
				name 'Name:' String
				""",
				"""
				Query[dialog=Single[title=Dialog Title, \
				description=dialog description, constraints=[]], \
				containers=[String [name=name, title=Name:, defVal=, constraints=[]]]]\
				""");
	}
	
	@Test public void dialog02() {
		TestHelper.checkAst(
				"""
				'Dialog Title' Pages('dialog description') {min=4 max =  6}
				name 'Name:' String
				""",
				"""
				Query[dialog=Pages[title=Dialog Title, \
				description=dialog description, \
				constraints=[MinCon[value=4.0], MaxCon[value=6.0]]], \
				containers=[String [name=name, title=Name:, defVal=, constraints=[]]]]\
				""");
	}
	
	@Test public void dialog03() {
		TestHelper.checkAst("""
				'Dialog Title' Single('dialog description')
				name 'Name:' String('default value')
				""",
				"""
				Query[dialog=Single[title=Dialog Title, \
				description=dialog description, constraints=[]], \
				containers=[String [name=name, title=Name:, defVal=default value, constraints=[]]]]\
				""");
	}
	
	@Test public void dialog04() {
		TestHelper.checkAst("""
				'Dialog Title' Single('dialog description')
				'My group:' Group{
					name 'Name:' String('default value')
				}
				""",
				"""
				Query[dialog=Single[title=Dialog Title, \
				description=dialog description, constraints=[]], \
				containers=[Group [title=My group:, \
				components=[String [name=name, title=Name:, \
				defVal=default value, constraints=[]]]]]]\
				""");
	}
	
	@Test public void dialog05() {
		TestHelper.checkAst("""
				'Dialog Title' Single('dialog description')
				'First Tab:' Tab{
					name 'Name:' String('John')
				}
				'Second Tab:' Tab{
					surname 'Surname:' String('Doe')
				}
				""",
				"""
				Query[dialog=Single[title=Dialog Title, \
				description=dialog description, constraints=[]], \
				containers=[Tab [title=First Tab:, \
				containers=[String [name=name, title=Name:, defVal=John, \
				constraints=[]]]], Tab [title=Second Tab:, \
				containers=[String [name=surname, title=Surname:, \
				defVal=Doe, constraints=[]]]]]]\
				""");
	}
	
	@Test public void dialog06() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my 
			description')
			cats 'Have cats?' Slider('2,44,7')
			{style= inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, description=single my
			description, constraints=[]], containers=[Slider [name=cats, \
			title=Have cats?, minVal=2, maxVal=44, defVal=7, constraints=[Inline]]]]\
			""");
	}
}
