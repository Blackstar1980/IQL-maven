package test;

import org.junit.Test;

public class ConstraintTests {
	@Test public void con01() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' String('My default value')
				{max=-3}
				""",
				"String max constraint, must be bigger than 0");
	}
	
	@Test public void con02() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' String('My default value')
				{min=-1}
				""",
				"String min constraint must be bigger than 0");
	}
	
	@Test public void con03() {
		TestHelper.checkParseError("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' String('My default value')
			{required= yes}
			""");			
	}
	
	@Test public void con04() {
		TestHelper.checkParseError("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' String('My default value')
			{holder=my holder}
			""");			
	}
	
	@Test public void con05() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' String('My default value')
				{display= inlineRadio}
				""",
				"inlineRadio is not a valide component display");
	}
	
	@Test public void con06() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' SingleOpt('Yes|No |Maybe |Yes no')
				{display= inlineCheckbox}
				""",
				"inlineCheckbox is not a valide component display");
	}
	
	@Test public void con07() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' MultiOpt('Yes|No |Maybe |Yes no')
				{display= inlineRadio}
				""",
				"inlineRadio is not a valide component display");
	}
	
	@Test public void con08() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' MultiOpt('Yes|No |Maybe |Yes no|')
				{display= inlineCheckbox}
				""",
				"'Yes|No |Maybe |Yes no|' are not a valid options");
	}
	
	@Test public void con09() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' MultiOpt('Yes| |Maybe |Yes no')
				{display= inlineCheckbox}
				""",
				"Empty value is not a valid option");
	}
	
	@Test public void con10() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' Slider('My default value')
				{display= inline}
				""",
				"My default value must contain 2 or 3 numbers seperated by comma");
	}
	
	@Test public void con11() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' Slider('23,2,5')
				{display= inline}
				""",
				"Slider max value must be bigger than the min value");
	}
	
	@Test public void con12() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' Slider('2,4,5')
				{display= inline}
				""",
				"Default value must be between or equal to the min and max values");
	}
	
	@Test public void con13() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Slider('2,4')
			{display= inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=cats, title=Have cats?, minVal=2, \
			maxVal=4, defVal=2, constraints=[Inline]]]]\
			""");
	}
	
	@Test public void con14() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Slider('2,44,7')
			{display= inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=cats, title=Have cats?, \
			minVal=2, maxVal=44, defVal=7, constraints=[Inline]]]]\
			""");
	}
	
	@Test public void con15() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			cats 'Have\ncats?' Slider('2,44,7')
			{display= inline}
			""",
			"""
			Have		
			cats? must not contain a new line\
			""");
	}
	
	@Test public void con16() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			cats 'Have
			cats?' Slider('2,44,7')
			{display= inline}
			""",
			"""
			Have		
			cats? must not contain a new line\
			""");
	}
	
	@Test public void con17() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean('true') {display=inline}
			dogs 'Have dogs?' Boolean('false'){display=block}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Boolean [name=cats, title=Have cats?, \
			defVal=true, constraints=[Inline]], Boolean [name=dogs, \
			title=Have dogs?, defVal=false, constraints=[Block]]]]\
			""");
	}
}
