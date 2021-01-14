package test;

import org.junit.Test;

public class ConstraintTests {
	@Test void con01() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' String('My default value')
				{max=-3}
				""",
				"String max constraint, must be bigger than 0");
	}
	
	@Test void con02() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' String('My default value')
				{min=-1}
				""",
				"String min constraint must be bigger than 0");
	}
	
	@Test void con03() {
		TestHelper.checkParseError("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' String('My default value')
			{required= yes}
			""");			
	}
	
	@Test void con04() {
		TestHelper.checkParseError("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' String('My default value')
			{holder=my holder}
			""");			
	}
	
	@Test void con05() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' String('My default value')
				{style= inlineRadio}
				""",
				"inlineRadio is not a valide component style");
	}
	
	@Test void con06() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' SingleOpt('Yes|No |Maybe |Yes no')
				{style= inlineCheckbox}
				""",
				"inlineCheckbox is not a valide component style");
	}
	
	@Test void con07() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' MultiOpt('Yes|No |Maybe |Yes no')
				{style= inlineRadio}
				""",
				"inlineRadio is not a valide component style");
	}
	
	@Test void con08() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' MultiOpt('Yes|No |Maybe |Yes no|')
				{style= inlineCheckbox}
				""",
				"'Yes|No |Maybe |Yes no|' are not a valid options");
	}
	
	@Test void con09() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' MultiOpt('Yes| |Maybe |Yes no')
				{style= inlineCheckbox}
				""",
				"Empty value is not a valid option");
	}
	
	@Test void con10() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' Slider('My default value')
				{style= inline}
				""",
				"My default value must contain 2 or 3 numbers seperated by comma");
	}
	
	@Test void con11() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' Slider('23,2,5')
				{style= inline}
				""",
				"Slider max value must be bigger than the min value");
	}
	
	@Test void con12() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				cats 'Have cats?' Slider('2,4,5')
				{style= inline}
				""",
				"Default value must be between or equal to the min and max values");
	}
	
	@Test void con13() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Slider('2,4')
			{style= inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=cats, title=Have cats?, minVal=2, \
			maxVal=4, defVal=2, constraints=[Inline]]]]\
			""");
	}
	
	@Test void con14() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Slider('2,44,7')
			{style= inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=cats, title=Have cats?, \
			minVal=2, maxVal=44, defVal=7, constraints=[Inline]]]]\
			""");
	}
	
	@Test void con15() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			cats 'Have\ncats?' Slider('2,44,7')
			{style= inline}
			""",
			"""
			Have		
			cats? must not contain a new line\
			""");
	}
	
	@Test void con16() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			cats 'Have
			cats?' Slider('2,44,7')
			{style= inline}
			""",
			"""
			Have		
			cats? must not contain a new line\
			""");
	}
	
	@Test void con17() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean('true') {style=inline}
			dogs 'Have dogs?' Boolean('false'){style=block}
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
